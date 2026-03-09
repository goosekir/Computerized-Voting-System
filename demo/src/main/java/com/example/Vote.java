package com.example;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Vote
{
    private static boolean confirm = false;
    private static String RawToken = "";

    public static void setRawToken(String _RawToken)
    {
        RawToken = _RawToken;
    }

    private static VBox top()
    {
        Button backButton = new Button("<- Back");
        backButton.setPrefSize(100, 25);
        backButton.setStyle(Theme.SECONDARY_BUTTON_STYLE);
        backButton.setOnAction((ActionEvent e) -> { App.LoadThis(4); });

        VBox vb = new VBox(backButton);
        vb.setAlignment(Pos.TOP_LEFT);
        vb.setPadding(new Insets(15, 0, 0, 15));

        return vb;
    }

    private static VBox center()
    {
        confirm = false;
        ToggleGroup Candidates = new ToggleGroup();

        VBox vb = new VBox(15);
        vb.setAlignment(Pos.CENTER);
        vb.setPadding(new Insets(25));

        Label titleLabel = new Label("Choose Your Candidate");
        titleLabel.setFont(Font.font("Verdana", 24));
        titleLabel.setStyle("-fx-text-fill: " + Theme.TEXT + ";");

        Label subtitleLabel = new Label("Select one option and confirm your vote");
        subtitleLabel.setFont(Font.font("Verdana", 14));
        subtitleLabel.setStyle("-fx-text-fill: " + Theme.MUTED + ";");

        VBox candidateBox = new VBox(10);
        candidateBox.setAlignment(Pos.CENTER);
        candidateBox.setPadding(new Insets(20));
        candidateBox.setStyle(Theme.CARD_STYLE);

        String[] candidateList = DB.getCandidates(DB.getActiveElection());

        for (String candidate : candidateList) {
            RadioButton canRadButton = new RadioButton(candidate);
            canRadButton.setToggleGroup(Candidates);
            canRadButton.setFont(Font.font("Verdana", 16));
            canRadButton.setStyle("-fx-text-fill: " + Theme.TEXT + ";");
            candidateBox.getChildren().add(canRadButton);
        }

        Label statusLabel = new Label();
        statusLabel.setFont(Font.font("Verdana", 14));
        statusLabel.setStyle("-fx-text-fill: " + Theme.ERROR + ";");
        statusLabel.setVisible(false);

        Button SubmitButton = new Button("Cast Vote");
        SubmitButton.setPrefSize(220, 50);
        SubmitButton.setMaxSize(220, 50);
        SubmitButton.setFont(Font.font("Verdana", 18));
        SubmitButton.setStyle(Theme.BUTTON_STYLE);

        SubmitButton.setOnAction((ActionEvent e) -> {
            statusLabel.setVisible(false);

            if (Candidates.getSelectedToggle() == null) {
                statusLabel.setText("Please select a candidate.");
                statusLabel.setVisible(true);
                return;
            }

            if (confirm) {
                boolean success = DB.castVote(
                        RawToken,
                        DB.getActiveElection(),
                        ((RadioButton) Candidates.getSelectedToggle()).getText()
                );

                if (success) {
                    App.LoadThis(9);
                } else {
                    statusLabel.setText("Vote failed. Token may be invalid or already used.");
                    statusLabel.setVisible(true);
                    SubmitButton.setText("Cast Vote");
                    confirm = false;
                }
                return;
            }

            confirm = true;
            SubmitButton.setText("Confirm Vote");
        });

        vb.getChildren().addAll(titleLabel, subtitleLabel, candidateBox, SubmitButton, statusLabel);
        return vb;
    }

    public static BorderPane Load()
    {
        BorderPane bp = new BorderPane();
        bp.setTop(top());
        bp.setCenter(center());
        bp.setStyle("-fx-background-color: " + Theme.BG + ";");
        return bp;
    }
}