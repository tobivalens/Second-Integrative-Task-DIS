package com.example.pipemaniagame.control;

import com.example.pipemaniagame.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class InstructionController implements Initializable {

    @FXML
    private AnchorPane pane;
    @FXML
    private Button back;

    public void onBackClick(){
        HelloApplication.openWindow("hello-view.fxml");
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image backgroundImage = new Image(getClass().getResource("/images/instructions.png").toExternalForm());
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        pane.setBackground(new Background(background));

        Image buttonImage = new Image(getClass().getResource("/images/exitButton.png").toExternalForm());
        ImageView imageView = new ImageView(buttonImage);
        imageView.setFitWidth(imageView.getFitWidth());
        imageView.setFitHeight(imageView.getFitHeight());
        back.setStyle("-fx-padding: 0;");
        back.setGraphic(imageView);

    }
}
