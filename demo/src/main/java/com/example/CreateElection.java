package com.example;

import java.time.LocalDateTime;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.converter.NumberStringConverter;

public class CreateElection 
{
    private final static Font f = Font.font("Verdana",15);
    private final static Font fBold = Font.font("Verdana",FontWeight.BOLD,15);

    private static int[] fieldToInts(TextField d, TextField m, TextField y, TextField h)
    {
        int[] date = new int[4];
        TextField[] tfs = new TextField[]{d,m,y,h};

        for (int i = 0; i < 4; i++)
        {
            if(tfs[i].getText().equals(""))
            {
                date[i] = 0;
                continue;
            }
            date[i] = Integer.parseInt(tfs[i].getText());
        }

        return date;
    }

    private static boolean isValidDate(int[] date)
    {
        if (date[0] == 0 || date[1] == 0 || date[2] == 0 || date[3] == 0)
        { return true; }

        if(String.valueOf(date[0]).length() > 2 || String.valueOf(date[0]).length() < 1 || 
        String.valueOf(date[1]).length() > 2 || String.valueOf(date[1]).length() < 1 || 
        String.valueOf(date[2]).length() != 4 ||
        String.valueOf(date[3]).length() > 2 || String.valueOf(date[3]).length() < 1) 
        { System.out.println("DATE ERROR: INVALID LENGTH"); return false; }

        if(date[1] < 1 || date[1] > 12) { System.out.println("DATE ERROR: MONTH"); return false; }

        if(date[0] < 1 || 
            (date[0] > 31 && (date[1] == 1 || date[1] == 3 || date[1] == 5 || date[1] == 7 || date[1] == 8 || date[1] == 10 || date[1] == 12)) || 
            (date[0] > 30 && (date[1] == 4 || date[1] == 6 || date[1] == 9 || date[1] == 11)) || 
            (date[0] > 28 && date[1] == 2 && Math.floorMod(date[2], 4) == 0) || (date[0] > 29 && date[1] == 2 && Math.floorMod(date[2], 4) != 0)) 
            { System.out.println("DATE ERROR: INVALID DAY"); return false; }

        if(date[3] < 0 || date[3] > 23) { System.out.println("DATE ERROR: INVALID HOUR"); return false; }

        return true;
    }

    private static LocalDateTime dateConvert(int[] date)
    {
        if (date[0] == 0 || date[1] == 0 || date[2] == 0 || date[3] == 0)
        { return null; }

        return LocalDateTime.of(date[0],date[1],date[2],date[3],0);
    }

    private static void CalendarMaker(HBox hb)
    {   
        String timeType[] = new String[]{"Day:","\tMonth:","\tYear","\tHour:"};
        
        for(int i = 0; i < 4; i++)
        {
            Label l1 = new Label(timeType[i]);
            l1.setFont(f);
            hb.getChildren().add(l1);

            TextField tf1 = new TextField();
            tf1.setFont(f);
            tf1.setPrefSize(50,25);
            tf1.setMaxSize(50,25);
            tf1.setAlignment(Pos.CENTER);
            tf1.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
            hb.getChildren().add(tf1);
        }
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

    private static VBox center()
    {
        VBox vb = new VBox(30);
        vb.setAlignment(Pos.CENTER);

        HBox hb1 = new HBox();
        hb1.setAlignment(Pos.CENTER);
        Label startLabel = new Label("Start At - ");
        startLabel.setFont(fBold);
        hb1.getChildren().add(startLabel);
        CalendarMaker(hb1);
        vb.getChildren().add(hb1);

        HBox hb2 = new HBox();
        hb2.setAlignment(Pos.CENTER);
        Label endLabel = new Label("  End At - ");
        endLabel.setFont(fBold);
        hb2.getChildren().add(endLabel);
        CalendarMaker(hb2);
        vb.getChildren().add(hb2);

        HBox hb3 = new HBox();
        hb3.setAlignment(Pos.CENTER);
        Label elecIdLabel = new Label("Name the Election: ");
        elecIdLabel.setFont(fBold);
        hb3.getChildren().add(elecIdLabel);
        TextField elecId = new TextField();
        hb3.getChildren().add(elecId);
        
        vb.getChildren().add(hb3);

        return vb;
    }

    private static VBox bottom(BorderPane bp)
    {
        Button SubmitButton = new Button("Submit");
        SubmitButton.setPrefSize(200,50);
        SubmitButton.setMaxSize(200,50);
        SubmitButton.setFont(Font.font("Verdana",20));

        HBox hb1 = (HBox)((VBox)bp.getChildren().get(1)).getChildren().get(0);
        HBox hb2 = (HBox)((VBox)bp.getChildren().get(1)).getChildren().get(1);
        TextField elecId = (TextField)((HBox)((VBox)bp.getChildren().get(1)).getChildren().get(2)).getChildren().get(1);

        SubmitButton.setOnAction((ActionEvent e) -> {
            int[] fieldToDate1 = fieldToInts((TextField)hb1.getChildren().get(2),(TextField)hb1.getChildren().get(4),(TextField)hb1.getChildren().get(6),(TextField)hb1.getChildren().get(8));
            int[] fieldToDate2 = fieldToInts((TextField)hb2.getChildren().get(2),(TextField)hb2.getChildren().get(4),(TextField)hb2.getChildren().get(6),(TextField)hb2.getChildren().get(8));

            if(!isValidDate(fieldToDate1) || !isValidDate(fieldToDate2))
            { return; }
            
            LocalDateTime startAt = dateConvert(fieldToDate1);
            LocalDateTime endAt = dateConvert(fieldToDate2);
            
            if((startAt != null && endAt != null) && (startAt.isBefore(LocalDateTime.now()) || endAt.isBefore(startAt)))
            { return; }
            
            
            if(elecId.getText().isBlank())
            { return; }
            
            DB.createElection(elecId.getText(), startAt, endAt);
            App.LoadThis(3);
        });

        VBox vb = new VBox(SubmitButton);
        vb.setPadding(new Insets(-1000,0,50,0));
        vb.setAlignment(Pos.BOTTOM_CENTER);

        return vb;
    }

    
    public static BorderPane Load()
    {
        BorderPane bp = new BorderPane();
        
        bp.setTop(top());
        bp.setCenter(center());
        bp.setBottom(bottom(bp));
        
        return bp;
    }
}