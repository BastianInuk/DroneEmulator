package sample;

import java.net.SocketException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class Controller
    implements UDPListener
{
    private int xCenter;
    private int yCenter;
    private int size;

    ArrayList<Color> colours;
    int currentColour;
    Color boxColour;

    public static void main(String[] args){
        Controller controller = new Controller();
    }

    UDPServer udpServer;
    public Controller()
    {
        try {
            udpServer = new UDPServer(4000, this);
            new Thread(udpServer).start();

            colours.add(Color.ORANGE);
            colours.add(Color.BLUE);
            colours.add(Color.BEIGE);
            colours.add(Color.RED);

            boxColour = colours.get(0);
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
            case "chClr":
                changeColour();
                break;
            default:
                System.out.println("Invalid command");
        }
        // Shape doesn't redraw on change, calling manually after change is applied
        drawShapes();
    }
    public void changeColour()
    {
        if(currentColour == colours.size()){
            currentColour = 0;
        } else {
            currentColour++;
        }

        boxColour = colours.get(currentColour);
    }


    // size modifier
    public void moveUp()
    {
        if(size <= ourCanvas.getWidth())
        {
            size++;
        }
    }

    public void moveDown()
    {
        if(size >= 16)
        {
            size--;
        }
    }
    // direction modifier
    public void moveFwd()
    {
        // implement move forward
        yCenter--;
    }

    public void moveBkwd()
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
        graphics.setFill(boxColour);
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
