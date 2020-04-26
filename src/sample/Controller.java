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
    //arrayList of colours
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

            colours = new ArrayList<Color>();
            //adds colours to the arrayList
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
    //colour changer
    public void changeColour()
    {
        // Cycle through colours
        if(currentColour == colours.size()-1){
            currentColour = 0;
        } else {
            currentColour++;
        }

        boxColour = colours.get(currentColour);
    }


    // size modifier
    public void moveFwd()
    {
        // Max size is the siz of the canvas
        if(size <= ourCanvas.getWidth())
        {
            size++;
        }
    }

    public void moveBkwd()
    {
        // Out of bound detection
        if(size >= 16)
        {
            size--;
        }
    }
    // direction modifier
    public void moveUp()
    {
        // Out of bound detection
        if (yCenter - size/2 > 0)
        {
            yCenter--;
        }
    }

    public void moveDown()
    {
        // Out of bound detection
        if (yCenter + size/2 < ourCanvas.getHeight())
        {
            yCenter++;
        }
    }

    public void moveLeft()
    {
        // Out of bound detection
        if (xCenter - size/2 > 0)
        {
            xCenter--;
        }
    }

    public void moveRight() {
        // Out of bound detection
        if (xCenter + size / 2 < ourCanvas.getWidth())
        {
            xCenter++;
        }
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
