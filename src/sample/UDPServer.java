package sample;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.function.Consumer;

public class UDPServer
{
    private DatagramSocket udpSocket;
    private int port;
    private boolean running;

    public UDPServer (int port) throws SocketException
    {
        this.port = port;
        this.udpSocket = new DatagramSocket(this.port);
    }

    void listen ( Consumer<String> callback ) throws IOException
    {
        running = true;
        while(running)
        {
            byte[] buffer = new byte[256];
            var packet = new DatagramPacket(buffer, buffer.length);
            udpSocket.receive(packet);

            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buffer, buffer.length, address, port);
            String received = new String(packet.getData(), 0, packet.getLength());

            if (received.equals("end")) {
                running = false;
                continue;
            } else {
                callback.accept(received); // = callback(received)
            }
        }
        udpSocket.close();
    }
}
