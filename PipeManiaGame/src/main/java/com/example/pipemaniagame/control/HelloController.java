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

    @FXML
    private Button matrixButton;

    private GraphicsContext graphicsContext;

    @FXML
    private Button startButton;

    @FXML
    private Button instructionButton;

    private Image backgroundImage;

    @FXML
    protected void onMatrixButtonClick() {
        HelloApplication.openWindow("matrix-view.fxml");
        Stage stage = (Stage) matrixButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onHelloButtonClick() {
        HelloApplication.openWindow("second-view.fxml");
        Stage stage = (Stage) startButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onInstructionClick() {
        HelloApplication.openWindow("second-view.fxml");
        Stage stage = (Stage) startButton.getScene().getWindow();
        stage.close();
    }


    @FXML
    public void initialize() {
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.backgroundImage = new Image(getClass().getResource("/images/firstScreen.png").toExternalForm());
        graphicsContext.drawImage(backgroundImage, 0, 0, canvas.getWidth(), canvas.getHeight());

        Image buttonImage = new Image(getClass().getResource("/images/adjListButton.png").toExternalForm());
        ImageView imageView = new ImageView(buttonImage);
        imageView.setFitWidth(startButton.getWidth());
        imageView.setFitHeight(startButton.getHeight());
        startButton.setGraphic(imageView);

        Image button2Image = new Image(getClass().getResource("/images/matrixButton.png").toExternalForm());
        ImageView imageView2 = new ImageView(button2Image);
        imageView2.setFitWidth(matrixButton.getWidth());
        imageView2.setFitHeight(matrixButton.getHeight());
        matrixButton.setGraphic(imageView2);

        Image button3Image = new Image(getClass().getResource("/images/instructionButton.png").toExternalForm());
        ImageView imageView3 = new ImageView(button3Image);
        imageView3.setFitWidth(instructionButton.getWidth());
        imageView3.setFitHeight(instructionButton.getHeight());
        instructionButton.setGraphic(imageView3);

    }
}
