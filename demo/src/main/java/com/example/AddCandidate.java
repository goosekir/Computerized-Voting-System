package com.example;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class AddCandidate 
{
    private static VBox center()
    {
        VBox vb = new VBox(20);
        vb.setAlignment(Pos.CENTER);

        HBox hb = new HBox();
        hb.setAlignment(Pos.CENTER);

        Label InstructLabel = new Label("Enter the Candidate's Name: ");
        InstructLabel.setFont(Font.font("Verdana",20));
        hb.getChildren().add(InstructLabel);

        TextField CandidateField = new TextField();
        CandidateField.setPrefSize(300,25);
        CandidateField.setMaxSize(300,25);
        CandidateField.setAlignment(Pos.CENTER);
        hb.getChildren().add(CandidateField);

        vb.getChildren().add(hb);

        Label WarningLabel = new Label("CANNOT ENTER THE SAME CANDIDATE TWICE");
        WarningLabel.setPrefSize(600, 50);
        WarningLabel.setFont(Font.font("Verdana",FontWeight.BOLD,FontPosture.REGULAR,20));
        WarningLabel.setTextFill(Color.RED);
        WarningLabel.setVisible(false);
        WarningLabel.setAlignment(Pos.CENTER);

        Button SubmitButton = new Button("Submit new Candidate");
        SubmitButton.setPrefSize(200,50);
        SubmitButton.setMaxSize(200,50);
        SubmitButton.setOnAction((ActionEvent e) -> {
            for(String candidate : DB.getCandidates(DB.getActiveElection()))
            {
                if (candidate.equals(CandidateField.getText()))
                {
                    WarningLabel.setVisible(true);
                    return;
                }
            }
            DB.addCandidate(DB.getActiveElection(), CandidateField.getText());
            CandidateField.clear();
        });
        vb.getChildren().add(SubmitButton);

        vb.getChildren().add(WarningLabel);

        return vb;
    }

    private static VBox top()
    {
        Button BackButton = new Button("<- Back");
        BackButton.setPrefSize(100,25);
        BackButton.setOnAction((ActionEvent e) -> { App.LoadThis(3); });    

        VBox vb = new VBox(BackButton);
        vb.setAlignment(Pos.TOP_LEFT);
        
        return vb;
    }

    public static BorderPane Load()
    {
        BorderPane bp = new BorderPane();

        bp.setCenter(center());
        bp.setTop(top());

        return bp;
    }
}
