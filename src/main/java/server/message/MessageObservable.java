package server.message;

import java.util.Observable;

public class MessageObservable extends Observable {
    public synchronized void setMessage(String client, String message) {
        setChanged();
        notifyObservers(client + ":" + message);
        notifyAll();
    }
}
