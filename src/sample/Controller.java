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

    void receiver(String response)
    {
        switch (response){
            case "up": // move up
                break;
            case "down": // move down
                break;
            case "left": // move left
                break;
            case "right": // Move down
                break;
            default: // stop
        }
        System.out.println(response); // Print response
    }

    void run()
    {
        try {
            udpServer.listen(this::receiver);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
