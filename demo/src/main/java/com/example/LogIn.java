package com.example;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class LogIn
{
    private static VBox center()
    {
        VBox vb = new VBox();
        vb.setAlignment(Pos.CENTER);

        Label UNLabel = new Label("Username");
        UNLabel.setPrefSize(250, 50);
        UNLabel.setMaxSize(250, 50);
        UNLabel.setAlignment(Pos.BOTTOM_LEFT);
        vb.getChildren().add(UNLabel);
        
        TextField UNField = new TextField();
        UNField.setPrefSize(250, 50);
        UNField.setMaxSize(250,50);
        UNField.setAlignment(Pos.CENTER);
        vb.getChildren().add(UNField);

        Label PWLabel = new Label("Password");
        PWLabel.setPrefSize(250, 50);
        PWLabel.setPrefSize(250, 50);
        PWLabel.setAlignment(Pos.BOTTOM_LEFT);
        vb.getChildren().add(PWLabel);

        PasswordField PWField = new PasswordField();
        PWField.setPrefSize(250, 50);
        PWField.setMaxSize(250,50);
        PWField.setAlignment(Pos.CENTER);
        vb.getChildren().add(PWField);

        return vb;
    }

    private static VBox top()
    {
        Button BackButton = new Button("<- Back");
        BackButton.setPrefSize(100,25);
        BackButton.setOnAction((ActionEvent e) -> { App.LoadThis(1); });    

        VBox vb = new VBox(BackButton);
        vb.setAlignment(Pos.TOP_LEFT);
        
        return vb;
    }

    private static VBox bottom(VBox CVB)
    {
        Label WarningLabel = new Label("INVALID USERNAME/PASSWORD");
        WarningLabel.setPrefSize(400, 50);
        WarningLabel.setFont(Font.font("Verdana",FontWeight.BOLD,FontPosture.REGULAR,20));
        WarningLabel.setTextFill(Color.RED);
        WarningLabel.setVisible(false);
        WarningLabel.setAlignment(Pos.CENTER);

        Button LogInButton = new Button("Log In");
        LogInButton.setPrefSize(200,50);
        LogInButton.setOnAction((ActionEvent e) -> {
            TextField Text1 = (TextField)CVB.getChildren().get(1);
            PasswordField Text2 = (PasswordField)CVB.getChildren().get(3);
            
            if(true)//Text1.getText().equals("ADMIN") && Text2.getText().equals("KRONOS"))
            { App.LoadThis(3); }
            else
            { WarningLabel.setVisible(true); }
        });    

        VBox vb = new VBox(LogInButton,WarningLabel);
        vb.setAlignment(Pos.BOTTOM_CENTER);
        vb.setPadding(new Insets(10,0,100,0));

        return vb;
    }

    public static BorderPane Load()
    {
        BorderPane bp = new BorderPane();
        VBox CenterVB = center();

        bp.setCenter(CenterVB);
        bp.setBottom(bottom(CenterVB));
        bp.setTop(top());

        return bp;
    }
}
