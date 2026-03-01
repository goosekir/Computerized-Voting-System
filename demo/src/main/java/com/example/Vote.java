package com.example;

import java.io.IOException;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Vote extends Application
{
    private static Scene scene;

    private static VBox center()
    {
        ToggleGroup tg = new ToggleGroup();

        VBox vb = new VBox();
        vb.setAlignment(Pos.CENTER);

        for(int i = 0; i < 0; i++)
        {
            RadioButton r1 = new RadioButton("John Doe");
            r1.setToggleGroup(tg);
            vb.getChildren().add(r1);
            //get the candidates and makes a bunch of radiobuttons for each candidate
        }
        
        return vb;
    }

    public static void Load(Stage stage)
    {
        stage.setTitle("Voting");

        BorderPane bp = new BorderPane();

        scene = new Scene(bp,640,480);

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage stage) throws IOException {}
}
