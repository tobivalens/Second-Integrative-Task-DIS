package com.example.pipemaniagame.control;

import com.example.pipemaniagame.HelloApplication;
import com.example.pipemaniagame.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    private Canvas gameCanvas;


    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button solutionButton;

    private GraphicsContext graphicsContext;
    @FXML
    private Label puntaje;

    @FXML
    private Button ponerTuberiaButton;


    @FXML
    private Button goBack;

    @FXML
    private Button simulate;

    @FXML
    private TextField textRow;

    @FXML
    private TextField textColumn;

    @FXML
    private TextField textPipeType;

    private MatrixGameController gameController;

    private Image backgroundImage;

    @FXML
    private void handlePonerTuberia() {

        int x= Integer.parseInt(textRow.getText());
        int y=  Integer.parseInt(textColumn.getText());
        int pipe= Integer.parseInt(textPipeType.getText());
        if(pipe <1 || pipe>3){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setContentText("Insert a valid Pipe number");
            alert.showAndWait();
        }else if(x>7 || x<0 || y>7 ||y<0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setContentText("Insert a valid number for row and column");
            alert.showAndWait();
        } else{
            gameController.insertPipe(x,y,pipe);
        }

    }

    @FXML
    private void handleSimular() {

          if(gameController.simulate()){
            gameController.finalizarPartida();
            puntaje.setText(String.valueOf(gameController.calculateScore()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setContentText("La diste toda reina");
            alert.showAndWait();

        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setContentText("Perdiste horrible Payasota");
            alert.showAndWait();
        }
    }

    @FXML
    private void solutionButtonAction(){
       gameController.easierSolutionActiveButton();
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
        this.gameController= new MatrixGameController(this.gameCanvas);
        Image backgroundImage = new Image(getClass().getResource("/images/screen3.png").toExternalForm());
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        //anchorPane.setBackground(new Background(background));

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
