package com.example;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class VoterToken extends Application
{
    private static Scene scene;

    private static VBox top()
    {
        Button b1 = new Button("<- Back");
        b1.setPrefSize(100,25);
        b1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e)
            {
                App.LoadThis(1);
            }
        });    

        VBox vb = new VBox(b1);
        vb.setAlignment(Pos.TOP_LEFT);
        
        return vb;
    }

    private static VBox center()
    {
        Label l1 = new Label("Please enter your Voter ID");
        l1.setPrefSize(200, 25);
        l1.setTextAlignment(TextAlignment.CENTER);
        l1.setAlignment(Pos.CENTER);

        TextField VoterIDField = new TextField();
        VoterIDField.setPrefSize(200, 25);
        VoterIDField.setMaxSize(200, 25);

        Button SubmitVIDButton = new Button("Submit");
        SubmitVIDButton.setPrefSize(100, 25);

        SubmitVIDButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e)
            {
                //Check if the ID has already voted
            }
        }); 

        VBox vb = new VBox(20,l1,VoterIDField,SubmitVIDButton);
        vb.setAlignment(Pos.CENTER);
        return vb;
    }

    public static void Load(Stage stage)
    {
        stage.setTitle("Voting");

        BorderPane bp = new BorderPane();
        bp.setCenter(center());
        bp.setTop(top());

        scene = new Scene(bp,640,480);

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage stage) throws IOException {}
}
