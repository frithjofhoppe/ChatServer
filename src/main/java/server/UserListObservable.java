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
            notifyObservers(users);
            notifyAll();
            return true;
        }
        return false;
    }

    public synchronized void removeUser(String user) {
        users.forEach(i -> {
            System.out.println(i);
            if(i.toUpperCase().equals(user.toUpperCase())){
                System.out.println("MATCHED");
            }
        });
        Optional<String> result = users.stream().filter(i -> i.toUpperCase().equals(user.toUpperCase())).findFirst();
        if(result.isPresent()){
            String value = result.get();
            users.remove(value);
            setChanged();
            notifyObservers(users);
            System.out.println("REMOVED");
        }
        System.out.println("REMOVED finished");
    }
}