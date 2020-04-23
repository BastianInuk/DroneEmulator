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
{
    Pane p = new Pane();
    public static void main(String[] args){
        Controller controller = new Controller();
    }

    UDPServer udpServer;
    /*Controller() {
        try {
            udpServer = new UDPServer(4000, this);
            new Thread(udpServer).start();
        } catch (SocketException e) {
            System.out.println(e);
        }
    }*/

    public void moveFwd() {
        // implement move forward
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
        
        GraphicsContext graphics = ourCanvas.getGraphicsContext2D();
        //graphics.strokeOval(10, 10, 10, 10);
        graphics.setFill(Color.ORANGE);
        graphics.fillRect(20, 20, 15, 15);

    }


    @FXML
    public void initialize() {
        System.out.println("second");
        drawShapes();
    }
}
