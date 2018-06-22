package server;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ViewTuple<MainView, MainViewModel> mainView = FluentViewLoader.fxmlView(MainView.class).load();
        Parent root = mainView.getView();
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("conversation.png")));
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
            }
        });
        primaryStage.setTitle("ChatServer");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 564, 406));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
