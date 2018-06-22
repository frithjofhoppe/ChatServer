package server;

import de.saxsys.mvvmfx.ViewModel;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import org.omg.CORBA.Environment;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Optional;

public class MainViewModel implements ViewModel {

    ChatServer server;
    StringProperty lblIpAddress = new SimpleStringProperty();
    StringProperty lblStatus = new SimpleStringProperty();
    BooleanProperty txtPort = new SimpleBooleanProperty();
    ListProperty<String> tblUsers = new SimpleListProperty<>();
    ArrayList<String> users;
    UserListObservable userList;
    UserListObserver userListObserver;

    public MainViewModel() {
        initalizeData();
        initalizeView();
        intializeModel();
    }

    private void initalizeData() {
        users = new ArrayList<>();
        userList = new UserListObservable(users);
        userListObserver = new UserListObserver(this);
        userList.addObserver(userListObserver);
    }

    public void closeApplication() {


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close application");
        alert.setHeaderText("Do you want to close the application");
        alert.setContentText("The server will therefore be stopped");

        ButtonType buttonYES = new ButtonType("Yes");
        ButtonType buttonNO = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonYES, buttonNO);

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == buttonYES) {
            stopServer();
            System.exit(0);
        }
    }

    public void stopServer() {
        if (server != null) {
            server.shutdown();
            Platform.runLater(() -> {
                lblStatus.set("DISABLED");
                txtPort.set(false);
            });
            server = null;
        }
    }

    public void startServer(int port) {
        if (server == null) {
            initalizeData();
            server = new ChatServer(port, userList);
            server.start();
            Platform.runLater(() -> {
                lblStatus.set("ENABLED");
                txtPort.set(true);
            });
        }
    }

    private void intializeModel() {

    }

    private void initalizeView() {
        lblIpAddress.setValue(getIpAddress());
        lblStatus.setValue("DISABLED");
        txtPort.setValue(false);
        refreshUsers(users);
    }

    public synchronized void refreshUsers(ArrayList<String> updated) {
        users = updated;
        users.stream().forEach(i -> System.out.println(i));
        Platform.runLater(() -> {
            tblUsers.set(FXCollections.observableArrayList(users));
        });
    }

    private String getIpAddress() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            return ip.getHostAddress();
        } catch (IOException e) {

        }
        return "";
    }
}
