package server;

import de.saxsys.mvvmfx.ViewModel;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Optional;

public class MainViewModel implements ViewModel {

    ChatServer server;
    StringProperty lblIpAddress = new SimpleStringProperty();
    StringProperty lblStatus = new SimpleStringProperty();
    ListProperty<String> tblUsers = new SimpleListProperty<>();
    ArrayList<String> users = new ArrayList<>();
    UserListObservable userList;
    UserListObserver userListObserver;

    public MainViewModel() {
        userList = new UserListObservable(users);
        userListObserver = new UserListObserver(this);
        userList.addObserver(userListObserver);
        initalizeView();
        intializeModel();
    }

    public void stopServer(){
        if(server != null) server.shutdown();
        lblStatus.set("DISABLED");
    }

    public void startServer(int port) {
        server = new ChatServer(port, userList);
        server.start();
        lblStatus.set("ENABLED");
    }

    private void intializeModel() {

    }

    private void initalizeView() {
        lblIpAddress.setValue(getIpAddress());
        refreshUsers(users);
    }

    public synchronized void refreshUsers(ArrayList<String> updated) {
        users = updated;
        System.out.println("Update list:");
        users.stream().forEach(i -> System.out.println(i));
        Platform.runLater(() -> {
            tblUsers.set(FXCollections.observableArrayList(users));
        });
        System.out.println("---");
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
