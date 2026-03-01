package com.example;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LogIn extends Application
{
    private static Scene scene;

    private static VBox center()
    {
        Label l1 = new Label("Username");
        l1.setPrefSize(250, 50);
        l1.setAlignment(Pos.BOTTOM_LEFT);
        TextField t1 = new TextField();
        t1.setPrefSize(250, 50);
        t1.setAlignment(Pos.CENTER);
        t1.setMaxSize(250,50);

        Label l2 = new Label("Password");
        l2.setPrefSize(250, 50);
        l2.setAlignment(Pos.BOTTOM_LEFT);
        PasswordField t2 = new PasswordField();
        t2.setPrefSize(250, 50);
        t2.setAlignment(Pos.CENTER);
        t2.setMaxSize(250,50);

        VBox vb = new VBox(0, l1,t1,l2,t2);
        vb.setAlignment(Pos.CENTER);
        return vb;
    }

    private static VBox top(Stage stage)
    {
        Button BackButton = new Button("<- Back");
        BackButton.setPrefSize(100,25);
        BackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e)
            {
                App.LoadThis(1);
            }
        });    

        VBox vb = new VBox(BackButton);
        vb.setAlignment(Pos.TOP_LEFT);
        
        return vb;
    }

    private static VBox bottom(Stage stage, VBox CVB)
    {
        Label WarningLabel = new Label("INVALID USERNAME/PASSWORD");
        WarningLabel.setPrefSize(400, 50);
        WarningLabel.setFont(Font.font("Verdana",FontWeight.BOLD,FontPosture.REGULAR,20));
        WarningLabel.setTextFill(Color.RED);
        WarningLabel.setVisible(false);
        WarningLabel.setAlignment(Pos.CENTER);

        Button LogInButton = new Button("Log In");
        LogInButton.setPrefSize(200,50);
        LogInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e)
            {
                TextField Text1 = (TextField)CVB.getChildren().get(1);
                PasswordField Text2 = (PasswordField)CVB.getChildren().get(3);

                if(Text1.getText().equals("ADMIN") && Text2.getText().equals("KRONOS"))
                {
                    App.LoadThis(3);
                }
                else
                {
                    WarningLabel.setVisible(true);
                }
            }
        });    

        VBox vb = new VBox(LogInButton,WarningLabel);
        vb.setAlignment(Pos.BOTTOM_CENTER);
        vb.setPadding(new Insets(10,0,100,0));
        return vb;
    }

    public static void Load(Stage stage)
    {
        stage.setTitle("Log In");

        BorderPane bp = new BorderPane();
        VBox CenterVB = center();

        //bp.setLeft(left());
        //bp.setRight(right());
        bp.setCenter(CenterVB);
        bp.setBottom(bottom(stage,CenterVB));
        bp.setTop(top(stage));

        scene = new Scene(bp,640,480);

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage stage) throws IOException {}
}
