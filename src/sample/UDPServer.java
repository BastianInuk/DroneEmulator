package sample;

import java.io.IOException;
import javafx.scene.control.Control;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.function.Consumer;

public class UDPServer implements Runnable
{
    private DatagramSocket udpSocket;
    private int port;
    private boolean running;
    private UDPListener listener;

    public UDPServer (int port, UDPListener listener) throws SocketException
    {
        this.port = port;
        this.udpSocket = new DatagramSocket(this.port);

        this.listener = listener;
    }

    public void run() {
        try {
            listen();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    void listen () throws IOException
    {
        running = true;
        while(running)
        {
            byte[] buffer = new byte[5];
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
                listener.listen(received);
            }

        }
        udpSocket.close();
    }
}
