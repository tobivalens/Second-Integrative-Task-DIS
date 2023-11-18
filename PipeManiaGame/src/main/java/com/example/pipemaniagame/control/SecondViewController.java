package com.example.pipemaniagame.control;

import com.example.pipemaniagame.HelloApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SecondViewController implements Initializable {
    @FXML
    private Canvas gameCanvas;


    @FXML
    private AnchorPane anchorPane;


    private GraphicsContext graphicsContext;

    @FXML
    private Button ponerTuberiaButton;
    @FXML
    private Button goBack;

    @FXML
    private Button simulate;

    private BaseControl gameController;

    private Image backgroundImage;

    @FXML
    private void handlePonerTuberia() {

    }

    @FXML
    private void handleSimular() {

    }

    @FXML
    private void goBack() {
            HelloApplication.openWindow("hello-view.fxml");
            Stage stage = (Stage) goBack.getScene().getWindow();
            stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.graphicsContext = this.gameCanvas.getGraphicsContext2D();
        this.gameController=  new BaseControl(this.gameCanvas);
        Image backgroundImage = new Image(getClass().getResource("/images/screen3.png").toExternalForm());
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        anchorPane.setBackground(new Background(background));

        new Thread(
                () ->{
                    while (true){
                        Platform.runLater( () -> {
                            gameController.paint();
                        });
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }
        ).start();


    }
}
