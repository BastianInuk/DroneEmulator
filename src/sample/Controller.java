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

    public void listen(String received)
    {
        System.out.println("Received: " + received);
        switch (received) {
            case "moveUp":
                moveUp();
                break; // zoom in (make bigger)
            case "moveDown":
                moveDown();
                break; // zoom out (make smaller)
            case "moveFwd":
                moveFwd();
                break;
            case "moveBkwd":
                moveBkwd();
                break;
            case "moveLeft":
                moveLeft();
                break;
            case "moveRight":
                moveRight();
                break;
        }
        drawShapes();
    }

    public void moveUp()
    {

    }

    public void moveDown()
    {

    }

    public void moveFwd()
    {
        // implement move forward
        System.out.println("Old x: " + xCenter);
        xCenter=xCenter+1;
        System.out.println("New x: " + xCenter);
    }

    public void moveBkwd()
    {

    }

    public void moveLeft()
    {

    }

    public void moveRight()
    {

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

    public void drawShapes(){
        System.out.println("Rendering box at " + xCenter + "," + yCenter);
        GraphicsContext graphics = ourCanvas.getGraphicsContext2D();
        graphics.setFill(Color.ORANGE);
        graphics.fillRect(xCenter - (size / 2), yCenter - (size / 2), size, size);

    }

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
