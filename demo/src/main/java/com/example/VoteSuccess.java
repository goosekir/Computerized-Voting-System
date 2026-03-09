package com.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class VoteSuccess
{
    private static VBox center()
    {
        VBox vb = new VBox(20);
        vb.setAlignment(Pos.CENTER);
        vb.setPadding(new Insets(30));
        vb.setStyle("-fx-background-color: " + Theme.BG + ";");

        Label successLabel = new Label("✓ Your vote was submitted successfully!");
        successLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        successLabel.setStyle("-fx-text-fill: " + Theme.SUCCESS + ";");

        Label thanksLabel = new Label("Thank you for participating in the election.");
        thanksLabel.setFont(Font.font("Verdana", 16));
        thanksLabel.setStyle("-fx-text-fill: " + Theme.TEXT + ";");

        Button resultsButton = new Button("View Results");
        resultsButton.setPrefSize(220, 50);
        resultsButton.setFont(Font.font("Verdana", 18));
        resultsButton.setStyle(Theme.BUTTON_STYLE);
        resultsButton.setOnAction(e -> App.LoadThis(8));

        Button homeButton = new Button("Return Home");
        homeButton.setPrefSize(220, 50);
        homeButton.setFont(Font.font("Verdana", 18));
        homeButton.setStyle(Theme.SECONDARY_BUTTON_STYLE);
        homeButton.setOnAction(e -> App.LoadThis(1));

        vb.getChildren().addAll(successLabel, thanksLabel, resultsButton, homeButton);
        return vb;
    }

    private static VBox bottom()
    {
        Label creditsLabel = new Label("Created by Nicholas Pellegrino and Zakir Rizvi");
        creditsLabel.setFont(Font.font("Verdana", 10));
        creditsLabel.setStyle("-fx-text-fill: " + Theme.MUTED + ";");

        VBox vb = new VBox(creditsLabel);
        vb.setAlignment(Pos.BOTTOM_RIGHT);
        vb.setPadding(new Insets(0, 15, 10, 0));
        return vb;
    }

    public static BorderPane Load()
    {
        BorderPane bp = new BorderPane();
        bp.setCenter(center());
        bp.setBottom(bottom());
        bp.setStyle("-fx-background-color: " + Theme.BG + ";");
        return bp;
    }
}