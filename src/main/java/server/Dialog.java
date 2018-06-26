package server;

import javafx.scene.control.Alert;

public class Dialog {
    public static void Information(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void Error(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void Warning(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void InvalidCharachter(){
        Information("Invalid charachter", "The content mustn't contains ':'", "Please correct your content");
    }

    public static void InvalidContent(){
        Error("Invalid credentials", "The given information are invalid","Please check the content");
    }
}
