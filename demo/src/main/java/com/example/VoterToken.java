package com.example;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class VoterToken
{
    private static VBox top()
    {
        Button BackButton = new Button("<- Back");
        BackButton.setPrefSize(100, 25);
        BackButton.setOnAction((ActionEvent e) -> { App.LoadThis(1); });

        VBox vb = new VBox(BackButton);
        vb.setAlignment(Pos.TOP_LEFT);

        return vb;
    }

    private static VBox center()
    {
        VBox vb = new VBox(20);
        vb.setAlignment(Pos.CENTER);

        final boolean[] tokenGenerated = {false};
        final String[] generatedToken = {""};

        Label InstructLabel = new Label("Please enter your name");
        InstructLabel.setPrefSize(500, 50);
        InstructLabel.setTextAlignment(TextAlignment.CENTER);
        InstructLabel.setFont(Font.font("Verdana", 30));
        InstructLabel.setAlignment(Pos.CENTER);
        vb.getChildren().add(InstructLabel);

        TextField VoterNameField = new TextField();
        VoterNameField.setPromptText("Enter your name");
        VoterNameField.setPrefSize(400, 50);
        VoterNameField.setMaxSize(400, 50);
        VoterNameField.setAlignment(Pos.CENTER);
        vb.getChildren().add(VoterNameField);

        Label TokenLabel = new Label();
        TokenLabel.setPrefSize(500, 60);
        TokenLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
        TokenLabel.setTextFill(Color.DARKGREEN);
        TokenLabel.setVisible(false);
        TokenLabel.setAlignment(Pos.CENTER);
        TokenLabel.setWrapText(true);
        vb.getChildren().add(TokenLabel);

        Label WarningLabel = new Label();
        WarningLabel.setPrefSize(500, 50);
        WarningLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
        WarningLabel.setTextFill(Color.RED);
        WarningLabel.setVisible(false);
        WarningLabel.setAlignment(Pos.CENTER);
        WarningLabel.setWrapText(true);
        vb.getChildren().add(WarningLabel);

        Button SubmitVIDButton = new Button("Generate Voting Token");
        SubmitVIDButton.setPrefSize(300, 50);
        SubmitVIDButton.setMaxSize(300, 50);
        SubmitVIDButton.setFont(Font.font("Verdana", 20));

        SubmitVIDButton.setOnAction((ActionEvent e) -> {
            WarningLabel.setVisible(false);

            if (tokenGenerated[0]) {
                Vote.setRawToken(generatedToken[0]);
                App.LoadThis(5);
                return;
            }

            String voterName = VoterNameField.getText().trim();

            if (voterName.isEmpty()) {
                WarningLabel.setText("Please enter your name.");
                WarningLabel.setVisible(true);
                return;
            }

            String activeElection = DB.getActiveElection();

            if (activeElection.equals("NULL")) {
                WarningLabel.setText("No active election found.");
                WarningLabel.setVisible(true);
                return;
            }

            String rawToken = DB.generateTokenForVoter(voterName, activeElection);

            if (rawToken == null) {
                WarningLabel.setText("Could not generate a token.");
                WarningLabel.setVisible(true);
                return;
            }

            generatedToken[0] = rawToken;
            tokenGenerated[0] = true;

            TokenLabel.setText("Your voting token is: " + rawToken);
            TokenLabel.setVisible(true);

            SubmitVIDButton.setText("Continue to Vote");
            VoterNameField.setEditable(false);
        });

        vb.getChildren().add(SubmitVIDButton);

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