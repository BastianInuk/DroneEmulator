package sample;

import java.io.IOException;
import java.net.SocketException;

public class Controller
{
    public static void main(String[] args){
        Controller controller = new Controller();
        controller.run();
    }

    UDPServer udpServer;

    Controller()
    {
        try {
            udpServer = new UDPServer(4000);
        } catch (SocketException e) {
            System.out.println(e);
        }
    }

    void run()
    {
        try {
            udpServer.listen((String response) -> {
                System.out.println(response); // Print response
            });
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
