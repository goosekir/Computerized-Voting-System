package com.example;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class Results
{
    private static VBox center()
    {
        VBox root = new VBox(25);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: " + Theme.BG + ";");

        Label title = new Label("Election Results");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        title.setStyle("-fx-text-fill: " + Theme.TEXT + ";");

        Label subtitle = new Label("Live polling totals for the active election");
        subtitle.setFont(Font.font("Verdana", 14));
        subtitle.setStyle("-fx-text-fill: " + Theme.MUTED + ";");

        String electionId = DB.getActiveElection();
        String[] candidates = DB.getCandidates(electionId);
        int[] votes = DB.getResultsArray(electionId);

        VBox resultsContainer = new VBox(15);
        resultsContainer.setAlignment(Pos.CENTER);

        int maxVotes = 1;
        int totalVotes = 0;
        int winnerIndex = 0;

        for (int i = 0; i < votes.length; i++) {
            totalVotes += votes[i];
            if (votes[i] > maxVotes) {
                maxVotes = votes[i];
                winnerIndex = i;
            }
        }

        Label totalVotesLabel = new Label("Total Votes Cast: " + totalVotes);
        totalVotesLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        totalVotesLabel.setStyle("-fx-text-fill: " + Theme.PRIMARY + ";");

        for (int i = 0; i < candidates.length; i++) {
            boolean isWinner = votes.length > 0 && i == winnerIndex;
            resultsContainer.getChildren().add(
                    createCandidateCard(candidates[i], votes[i], maxVotes, totalVotes, isWinner)
            );
        }

        Button homeButton = new Button("Return Home");
        homeButton.setFont(Font.font("Verdana", 16));
        homeButton.setPrefSize(220, 45);
        homeButton.setStyle(Theme.BUTTON_STYLE);
        homeButton.setOnAction(e -> App.LoadThis(1));

        root.getChildren().addAll(title, subtitle, totalVotesLabel, resultsContainer, homeButton);
        return root;
    }

    private static HBox createCandidateCard(String name, int votes, int maxVotes, int totalVotes, boolean isWinner)
    {
        HBox card = new HBox(18);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPrefWidth(560);
        card.setMaxWidth(560);
        card.setPadding(new Insets(16));

        String cardStyle = isWinner
                ? "-fx-background-color: #FFF7D6;" +
                  "-fx-background-radius: 14;" +
                  "-fx-border-color: #F2C94C;" +
                  "-fx-border-radius: 14;" +
                  "-fx-border-width: 2;"
                : "-fx-background-color: #F4F6FF;" +
                  "-fx-background-radius: 14;" +
                  "-fx-border-color: #D8DDF7;" +
                  "-fx-border-radius: 14;";

        card.setStyle(cardStyle);

        Label nameLabel = new Label(isWinner ? "👑 " + name : name);
        nameLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        nameLabel.setStyle("-fx-text-fill: " + Theme.TEXT + ";");

        Label voteLabel = new Label(votes + " vote(s)");
        voteLabel.setFont(Font.font("Verdana", 15));
        voteLabel.setStyle("-fx-text-fill: " + Theme.MUTED + ";");

        double percent = (totalVotes == 0) ? 0 : (double) votes / totalVotes;
		double votePercent = (totalVotes == 0) ? 0 : (100.0 * votes / totalVotes);

		Label percentLabel = new Label(String.format("%.1f%%", votePercent));
		percentLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		percentLabel.setStyle("-fx-text-fill: " + Theme.PRIMARY + ";");
		percentLabel.setMinWidth(80);
		percentLabel.setPrefWidth(80);
		percentLabel.setMaxWidth(80);
		percentLabel.setAlignment(Pos.CENTER_RIGHT);

		double targetWidth = Math.max(20, 260 * percent);

        Region bar = new Region();
		bar.setPrefHeight(18);
		bar.setMinHeight(18);
		bar.setMaxHeight(18);
		bar.setPrefWidth(0);
		bar.setMinWidth(0);
		bar.setMaxWidth(targetWidth);

		if (isWinner) {
			bar.setStyle(
				"-fx-background-color: linear-gradient(to right, #F2C94C, #F7E48A);" +
				"-fx-background-radius: 9;"
			);
		} else {
			bar.setStyle(
				"-fx-background-color: linear-gradient(to right, " + Theme.PRIMARY + ", #7FA3FF);" +
				"-fx-background-radius: 9;"
			);
		}

		Pane barContainer = new Pane();
		barContainer.setPrefWidth(270);
		barContainer.setMinWidth(270);
		barContainer.setMaxWidth(270);
		barContainer.setPrefHeight(18);
		barContainer.setMinHeight(18);
		barContainer.setMaxHeight(18);
		barContainer.setStyle(
			"-fx-background-color: #DCE3FF;" +
			"-fx-background-radius: 9;"
		);

		bar.setLayoutX(0);
		bar.setLayoutY(0);
		barContainer.getChildren().add(bar);

		Timeline timeline = new Timeline(
			new KeyFrame(
				Duration.seconds(0.8),
                new KeyValue(bar.prefWidthProperty(), targetWidth)
			)
		);
		timeline.play();

        VBox textBox = new VBox(6, nameLabel, voteLabel);
        textBox.setMinWidth(170);
        textBox.setPrefWidth(170);
        textBox.setMaxWidth(170);

        card.getChildren().addAll(textBox, barContainer, percentLabel);
        return card;
    }

    private static VBox bottom()
    {
        Label credits = new Label("Created by Nicholas Pellegrino and Zakir Rizvi");
        credits.setFont(Font.font("Verdana", 10));
        credits.setStyle("-fx-text-fill: " + Theme.MUTED + ";");

        VBox vb = new VBox(credits);
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