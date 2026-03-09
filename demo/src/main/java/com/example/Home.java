package com.example;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class Home
{

    private static VBox center()
	{
		Label titleLabel = new Label("Computerized Voting System");
		titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
		titleLabel.setStyle("-fx-text-fill: " + Theme.TEXT + ";");
		titleLabel.setTextAlignment(TextAlignment.CENTER);

		Label subtitleLabel = new Label("Secure voting with real-time results");
		subtitleLabel.setFont(Font.font("Verdana", 14));
		subtitleLabel.setStyle("-fx-text-fill: " + Theme.MUTED + ";");

		Label WarningLabel = new Label("ERROR: NO CURRENT ELECTION FOUND");
		WarningLabel.setPrefSize(500, 50);
		WarningLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
		WarningLabel.setStyle("-fx-text-fill: " + Theme.ERROR + ";");
		WarningLabel.setVisible(false);
		WarningLabel.setAlignment(Pos.CENTER);

		Button VoteNowButton = new Button("VOTE NOW");
		VoteNowButton.setPrefSize(240, 70);
		VoteNowButton.setFont(new Font("Verdana", 24));
		VoteNowButton.setStyle(Theme.BUTTON_STYLE);

		VoteNowButton.setOnAction((ActionEvent e) -> {
			if(DB.getActiveElection().equals("NULL"))
			{
				WarningLabel.setVisible(true);
				return;
			}
        App.LoadThis(4);
		});

		Button ResultsButton = new Button("VIEW RESULTS");
		ResultsButton.setPrefSize(240, 55);
		ResultsButton.setFont(new Font("Verdana", 18));
		ResultsButton.setStyle(Theme.SECONDARY_BUTTON_STYLE);

		ResultsButton.setOnAction((ActionEvent e) -> {
			if(DB.getActiveElection().equals("NULL"))
			{
				WarningLabel.setVisible(true);
				return;
			}
		App.LoadThis(8);
		});

		VBox vb = new VBox(20, titleLabel, subtitleLabel, VoteNowButton, ResultsButton, WarningLabel);
		vb.setAlignment(Pos.CENTER);
		return vb;
	}

	private static VBox bottom()
	{
		Label credits = new Label("Created by Nicholas Pellegrino and Zakir Rizvi");
		credits.setFont(new Font("Verdana", 10));
		credits.setStyle("-fx-text-fill: " + Theme.MUTED + ";");

		VBox vb = new VBox(credits);
		vb.setAlignment(Pos.BOTTOM_RIGHT);
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