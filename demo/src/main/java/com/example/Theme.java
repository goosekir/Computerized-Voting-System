package com.example;

public class Theme {
    public static final String BG = "#F7F8FC";
    public static final String CARD = "#FFFFFF";
    public static final String PRIMARY = "#4A6CFF";
    public static final String PRIMARY_DARK = "#3554D1";
    public static final String SUCCESS = "#1F9D55";
    public static final String ERROR = "#D64545";
    public static final String TEXT = "#1F2937";
    public static final String MUTED = "#6B7280";
    public static final String BORDER = "#D9DDF0";

    public static final String BUTTON_STYLE =
            "-fx-background-color: " + PRIMARY + ";" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 10;" +
            "-fx-border-radius: 10;" +
            "-fx-font-weight: bold;";

    public static final String SECONDARY_BUTTON_STYLE =
            "-fx-background-color: white;" +
            "-fx-text-fill: " + PRIMARY + ";" +
            "-fx-border-color: " + PRIMARY + ";" +
            "-fx-border-width: 2;" +
            "-fx-background-radius: 10;" +
            "-fx-border-radius: 10;" +
            "-fx-font-weight: bold;";

    public static final String CARD_STYLE =
            "-fx-background-color: " + CARD + ";" +
            "-fx-background-radius: 14;" +
            "-fx-border-color: " + BORDER + ";" +
            "-fx-border-radius: 14;" +
            "-fx-padding: 18;";
}