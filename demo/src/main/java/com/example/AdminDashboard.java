package com.example;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AdminDashboard extends Application
{
    private static Scene scene;
    private static boolean confirm = false;

    private static void adjb(Button b)  //Adjust Button
    {
        b.setPrefSize(200,50);
        b.setFont(new Font("Verdana",15));
    }

    private static VBox center()
    {
        VBox vb = new VBox(10);
        vb.setAlignment(Pos.CENTER);

        Label CurrentElectionLabel = new Label("Current Election: NONE");
        CurrentElectionLabel.setFont(new Font("Verdana",20));
        vb.getChildren().add(CurrentElectionLabel);

        Button CreateElectionButton = new Button("Create Election");
        adjb(CreateElectionButton);
        CreateElectionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e)
            {
                //Create Election Function
            }
        }); 
        vb.getChildren().add(CreateElectionButton);

        Button AddCandidatesButton = new Button("Add Candidates");
        adjb(AddCandidatesButton);
        AddCandidatesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e)
            {
                //Add Candidates Function
            }
        });
        vb.getChildren().add(AddCandidatesButton);
        
        Button ElectionStatusButton = new Button("Open/Close Election");
        adjb(ElectionStatusButton);
        ElectionStatusButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e)
            {
                //Election Status Function
            }
        });
        vb.getChildren().add(ElectionStatusButton);

        Button ViewResultsButton = new Button("View Results");
        adjb(ViewResultsButton);
        ViewResultsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e)
            {
                //View Results Function
            }
        });
        vb.getChildren().add(ViewResultsButton);

        Button ViewAuditButton = new Button("View Audit Log");
        adjb(ViewAuditButton);
        ViewAuditButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e)
            {
                //View Audit Function
            }
        });
        vb.getChildren().add(ViewAuditButton);

        return vb;
    }

    private static VBox top()
    {
        Button LogOut = new Button("You are signed in as: ADMIN");
        LogOut.setPrefSize(250, 25);

        LogOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e)
            {
                if(!confirm)
                {
                    LogOut.setText("Are you sure you want to log out?");
                    confirm = true;
                    return;
                }
                confirm = false;
                App.LoadThis(1);
            }
        });

        LogOut.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent e)
            {
                LogOut.setText("You are signed in as: ADMIN");
                confirm = false;
            }
        });

        VBox vb = new VBox(LogOut);
        vb.setAlignment(Pos.TOP_RIGHT);
        return vb;
    }

    public static void Load(Stage stage)
    {
        stage.setTitle("Log In");

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
