package com.example.pipemaniagame.control;

import com.example.pipemaniagame.HelloApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    @FXML
    private Button solutionButton;


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

        Image buttonImage = new Image(getClass().getResource("/images/exitButton.png").toExternalForm());
        ImageView imageView = new ImageView(buttonImage);
        imageView.setFitWidth(imageView.getFitWidth());
        imageView.setFitHeight(imageView.getFitHeight());
        goBack.setStyle("-fx-padding: 0;");
        goBack.setGraphic(imageView);


        Image simulateImage = new Image(getClass().getResource("/images/simulate.png").toExternalForm());
        ImageView simulateImageView = new ImageView(simulateImage);
        simulateImageView.setFitWidth(simulateImageView.getFitWidth());
        simulateImageView.setFitHeight(simulateImageView.getFitHeight());
        simulate.setStyle("-fx-padding: 0;");
        simulate.setGraphic(simulateImageView);

        Image insertImage = new Image(getClass().getResource("/images/insert.png").toExternalForm());
        ImageView insertImageView = new ImageView(insertImage);
        insertImageView.setFitWidth(insertImageView.getFitWidth());
        insertImageView.setFitHeight(insertImageView.getFitHeight());
        ponerTuberiaButton.setStyle("-fx-padding: 0;");
        ponerTuberiaButton.setGraphic(insertImageView);

        Image solutionImage = new Image(getClass().getResource("/images/solution.png").toExternalForm());
        ImageView solutionImageView = new ImageView(solutionImage);
        solutionImageView.setFitWidth(solutionImageView.getFitWidth());
        solutionImageView.setFitHeight(solutionImageView.getFitHeight());
        solutionButton.setStyle("-fx-padding: 0;");
        solutionButton.setGraphic(solutionImageView);



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
