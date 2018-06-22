package server;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Optional;

public class UserListObservable extends Observable {
    ArrayList<String> users;

    public UserListObservable(ArrayList<String> list) {
        this.users = list;
    }

    public synchronized boolean addUserIfNotExists(String user) {
        Optional<String> result = users.stream().filter(i -> i.toUpperCase() == user.toUpperCase()).findFirst();
        if(!result.isPresent()) {
            users.add(user);
            setChanged();
            System.out.println("NOTIFY");
            notifyObservers(users);
            System.out.println("NOTIFIED");
            notifyAll();
            return true;
        }
        return false;
    }

    public synchronized void removeUser(String user) {
        users.removeIf(i -> i.toUpperCase() == user.toUpperCase());
        hasChanged();
        notifyObservers();
    }
}
