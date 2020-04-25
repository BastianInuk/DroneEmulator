package sample;

import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Controller
    implements UDPListener
{
    private int xCenter;
    private int yCenter;
    private int size;

    public static void main(String[] args){
        Controller controller = new Controller();
    }

    UDPServer udpServer;
    public Controller()
    {
        try {
            udpServer = new UDPServer(4000, this);
            new Thread(udpServer).start();
        } catch (SocketException e) {
            System.out.println(e);
        }
    }
    // invoking a method that receives commands via UDP
    // the commands transforms the shape's position and size in the canvas
    public void listen(String received)
    {
        System.out.println("Received: " + received);
        System.out.println("The package is " + received.length() + " bytes long");
        switch (received) {
            case "moveU":
                moveUp();
                break; // zoom in (make bigger)
            case "moveD":
                moveDown();
                break; // zoom out (make smaller)
            case "moveF":
                moveFwd();
                break;
            case "moveB":
                moveBkwd();
                break;
            case "moveL":
                moveLeft();
                break;
            case "moveR":
                moveRight();
                break;
            default:
                System.out.println("Invalid command");
        }
        // Shape doesn't redraw on change, calling manually after change is applied
        drawShapes();
    }
    // size modifier
    public void moveFwd()
    {
        if(size <= ourCanvas.getWidth())
        {
            size++;
        }
    }

    public void moveBkwd()
    {
        if(size >= 16)
        {
            size--;
        }
    }
    // direction modifier
    public void moveUp()
    {
        // implement move forward
        yCenter--;
    }

    public void moveDown()
    {
        yCenter++;
    }

    public void moveLeft()
    {
        xCenter--;
    }

    public void moveRight()
    {
        xCenter++;
    }

    @FXML
    private BorderPane BorderPane1;

    @FXML
    private ComboBox<?> colorPicker;

    @FXML
    private Button startBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private MenuBar menubar;

    @FXML
    private Canvas ourCanvas;
    // shapes constructor
    public void drawShapes(){
        System.out.println("Rendering box at " + xCenter + "," + yCenter);
        GraphicsContext graphics = ourCanvas.getGraphicsContext2D();

        // Clear the canvas
        graphics.setFill(Color.WHITE);
        graphics.fillRect(0,0,ourCanvas.getWidth(), ourCanvas.getHeight());

        // Draw drone
        graphics.setFill(Color.ORANGE);
        graphics.fillRect(xCenter - (size / 2), yCenter - (size / 2), size, size);

    }

    //Invoking drawShapes method and updates the shape's properties
    @FXML
    public void initialize() {
        size = 16;

        Double canvasYCenter = ourCanvas.getHeight() / 2.0 ;
        Double canvasXCenter = ourCanvas.getWidth() / 2.0;

        xCenter = canvasXCenter.intValue();
        yCenter = canvasYCenter.intValue();

        drawShapes();
    }
}
