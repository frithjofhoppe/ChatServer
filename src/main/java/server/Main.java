package server;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ViewTuple<MainView, MainViewModel> mainView = FluentViewLoader.fxmlView(MainView.class).load();
        Parent root = mainView.getView();
        primaryStage.setTitle("ChatServer");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 564, 406));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
