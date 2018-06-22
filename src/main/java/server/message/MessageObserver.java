package server.message;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class MessageObserver implements Observer {

    DataOutputStream out;
    String client;

    public MessageObserver(DataOutputStream out, String client){
        this.out = out;
        this.client = client;
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            out.writeUTF(((String)arg));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
