package com.example;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Voting");

        BorderPane bp = new BorderPane();

        bp.setLeft(left());
        bp.setRight(right());
        bp.setCenter(center());

        scene = new Scene(bp, 640, 480);

        stage.setScene(scene);
        stage.show();
    }

    private VBox right()
    {
        Button b1 = new Button("You are signed in as: GUEST");
        b1.setPrefSize(200, 50);

        VBox vb = new VBox(b1);
        vb.setSpacing(5.0);
        vb.setAlignment(Pos.TOP_RIGHT);
        return vb;
    }

    private VBox left() //only here to keep the button centered
    {
        Button b1 = new Button("VOTE NOW");
        b1.setPrefSize(200, 50);
        b1.setVisible(false);
        VBox vb = new VBox(b1);
        vb.setSpacing(5.0);
        vb.setAlignment(Pos.TOP_LEFT);
        return vb;
    }

    private VBox center()
    {
        Button b1 = new Button("VOTE NOW");
        b1.setPrefSize(100, 100);
        VBox vb = new VBox(b1);
        vb.setSpacing(5.0);
        vb.setAlignment(Pos.CENTER);
        return vb;
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}