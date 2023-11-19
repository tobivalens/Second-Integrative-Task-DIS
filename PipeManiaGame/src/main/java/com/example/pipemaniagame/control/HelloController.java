package com.example.pipemaniagame.control;

import com.example.pipemaniagame.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class HelloController {
    @FXML
    private Canvas canvas;

    private GraphicsContext graphicsContext;

    @FXML
    private Button startButton;

    private Image backgroundImage;

    @FXML
    protected void onHelloButtonClick() {
        HelloApplication.openWindow("second-view.fxml");
        Stage stage = (Stage) startButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.backgroundImage = new Image(getClass().getResource("/images/firstScreen.png").toExternalForm());
        graphicsContext.drawImage(backgroundImage, 0, 0, canvas.getWidth(), canvas.getHeight());

        Image buttonImage = new Image(getClass().getResource("/images/pipeButon.png").toExternalForm());
        ImageView imageView = new ImageView(buttonImage);
        imageView.setFitWidth(startButton.getWidth());
        imageView.setFitHeight(startButton.getHeight());
        startButton.setGraphic(imageView);

    }
}
