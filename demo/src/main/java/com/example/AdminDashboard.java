package com.example;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class AdminDashboard
{
    private static boolean confirm = false;

    private static void adjb(Button b, VBox vb)  //Adjust Button
    {
        b.setPrefSize(300,50);
        b.setFont(new Font("Verdana",15));
        vb.getChildren().add(b);
    }

    private static VBox center()
    {
        VBox vb = new VBox(10);
        vb.setAlignment(Pos.CENTER);

        /*Label CurrentElectionLabel = new Label("Current Election: ");
        CurrentElectionLabel.setFont(new Font("Verdana",20));
        vb.getChildren().add(CurrentElectionLabel);*/

        Button CreateElectionButton = new Button("Create Election");
        adjb(CreateElectionButton, vb);
        CreateElectionButton.setOnAction((ActionEvent e) -> { App.LoadThis(6); }); 

        Button AddCandidatesButton = new Button("Add Candidates");
        adjb(AddCandidatesButton, vb);
        AddCandidatesButton.setOnAction((ActionEvent e) -> { App.LoadThis(7); });
        
        Button ElectionStatusButton = new Button("Open/Close Election");
        adjb(ElectionStatusButton, vb);
        ElectionStatusButton.setOnAction((ActionEvent e) -> { App.LoadThis(8); });

        Button ViewResultsButton = new Button("View Results");
        adjb(ViewResultsButton, vb);
        ViewResultsButton.setOnAction((ActionEvent e) -> { DB.getResults(DB.getActiveElection()); });

        Button ViewAuditButton = new Button("View Audit Log");
        adjb(ViewAuditButton, vb);
        ViewAuditButton.setOnAction((ActionEvent e) -> { DB.getAuditEvents(DB.getActiveElection()); });

        return vb;
    }

    private static VBox top()
    {
        Button LogOut = new Button("You are signed in as: ADMIN");
        LogOut.setPrefSize(250, 25);

        LogOut.setOnAction((ActionEvent e) -> {
            if(!confirm)
            {
                LogOut.setText("Are you sure you want to log out?");
                confirm = true;
                return;
            }
            confirm = false;
            App.LoadThis(1);
        });

        LogOut.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            LogOut.setText("Log Out?");
        });

        LogOut.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            LogOut.setText("You are signed in as: ADMIN");
            confirm = false;
        });

        VBox vb = new VBox(LogOut);
        vb.setAlignment(Pos.TOP_RIGHT);
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
