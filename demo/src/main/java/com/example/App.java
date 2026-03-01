package com.example;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage stage;

    public static void LoadThis(int page)
    {
        switch(page)
        {
            case 1:
                Home.Load(stage);
                break;
            case 2:
                LogIn.Load(stage);
                break;
            case 3:
                AdminDashboard.Load(stage);
                break;
            case 4:
                VoterToken.Load(stage);
                break;
            case 5:
                Vote.Load(stage);
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        LoadThis(1);
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