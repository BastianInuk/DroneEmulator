package sample;

import java.io.IOException;
import javafx.scene.control.Control;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer
        implements Runnable
{
    private DatagramSocket udpSocket;
    private int port;
    private boolean running;
    private UDPListener listener;

    public UDPServer (int port, UDPListener listener) throws SocketException
    {
        // Port for the UDP server to bind to. This can be random, or hardcoded, this class doesn't care.
        this.port = port;
        // This socket listens to any given message
        this.udpSocket = new DatagramSocket(this.port);
        // UDPListener has a function called listen, which will handle the command from udp.
        this.listener = listener;
    }

    // running UDP in a background thread, to not obstruct the UI
    public void run() {
        try {
            listen();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    // rethrows IOException
    void listen () throws IOException
    {
        // Setting a variable to make it easier to stop listening on the UDP port
        running = true;
        while(running)
        {
            // takes a 5 character long command. having it bigger gave difficulties in our switch with variable case string lengths
            byte[] buffer = new byte[5];

            // Actually listening for a package
            var packet = new DatagramPacket(buffer, buffer.length);
            udpSocket.receive(packet);

            // Turn package into a string to be evaluated as a command
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buffer, buffer.length, address, port);
            String received = new String(packet.getData(), 0, packet.getLength());

            // If the command is end, end and terminate the socket
            if (received.equals("end")) {
                running = false;
                continue;
            } else {
                // otherwise forward the command to the UDPListener
                listener.listen(received);
            }

        }
        // Close the socket
        udpSocket.close();
    }
}
