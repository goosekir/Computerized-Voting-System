package com.example;

import com.example.db.TestMongo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage stage;

    public static void LoadThis(int page)
    {
        BorderPane bp = null;
        stage.setTitle("Voting");

        switch(page)
        {
            case 1:
                bp = Home.Load();
                break;
            case 2:
                stage.setTitle("Log In");
                bp = LogIn.Load();
                break;
            case 3:
                stage.setTitle("Admin Dashboard");
                bp = AdminDashboard.Load();
                break;
            case 4:
                bp = VoterToken.Load();
                break;
            case 5:
                bp = Vote.Load();
                break;
            case 6:
                stage.setTitle("Admin Dashboard - Creating Election");
                bp = CreateElection.Load();
                break;
            case 7:
                stage.setTitle("Admin Dashboard - Adding Candidate");
                bp = AddCandidate.Load();
                break;
			case 8:
				stage.setTitle("Results");
				bp = Results.Load();
				break;
				
			case 9:
				stage.setTitle("Vote Submitted");
				bp = VoteSuccess.Load();
				break;
			}	

        scene = new Scene(bp, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage stage) {
        App.stage = stage;

        TestMongo.test();

        LoadThis(1);
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}