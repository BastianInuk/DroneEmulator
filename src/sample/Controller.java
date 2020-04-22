package sample;

import java.io.IOException;
import java.net.SocketException;

public class Controller
{
    public static void main(String[] args){
        Controller controller = new Controller();
    }

    UDPServer udpServer;

    Controller() {
        try {
            udpServer = new UDPServer(4000, this);
            new Thread(udpServer).start();
        } catch (SocketException e) {
            System.out.println(e);
        }
    }

    public void moveFwd() {
        // implement move forward
    }
}
