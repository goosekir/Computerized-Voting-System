package com.example;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Home extends Application
{
    private static Scene scene;

    private static VBox top()
    {
        Button LoginButton = new Button("You are signed in as: GUEST");
        LoginButton.setPrefSize(250, 25);

        LoginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e)
            {
                App.LoadThis(2);
            }
        }); 

        VBox vb = new VBox(LoginButton);
        vb.setAlignment(Pos.TOP_RIGHT);
        return vb;
    }

    private static VBox center()
    {
        Button VoteNowButton = new Button("VOTE\nNOW");
        VoteNowButton.setPrefSize(100, 100);
        VoteNowButton.setFont(new Font("Verdana",20));
        VoteNowButton.setTextAlignment(TextAlignment.CENTER);

        VoteNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e)
            {
                if(true)    //Check if an election even exists
                {
                    App.LoadThis(4);
                }
            }
        }); 

        VBox vb = new VBox(VoteNowButton);
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
