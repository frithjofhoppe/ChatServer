package server;

import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class UserListObserver implements Observer {

    MainViewModel viewModel;

    public UserListObserver(MainViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("OBSERVER in");
        viewModel.refreshUsers((ArrayList<String>) arg);
        System.out.println("OBSERVER out");
    }
}
