package server.message;

import server.UserListObservable;
import server.UserListObserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MessageHandler extends Thread {

    Socket socket;
    DataInputStream in;
    DataOutputStream out;
    UserListObservable userListObservable;
    MessageObservable observable;
    MessageObserver observer;
    String client;
    boolean isRunning = true;

    public MessageHandler(Socket socket, MessageObservable observable, UserListObservable userListObservable) {
        this.client = client;
        try {
            this.socket = socket;
            this.in = new DataInputStream(this.socket.getInputStream());
            this.out = new DataOutputStream(this.socket.getOutputStream());
            this.userListObservable = userListObservable;
            this.observable = observable;
            this.observer = new MessageObserver(out, client);
            observable.addObserver(this.observer);
        } catch (IOException e) {
            System.out.println("INFO: Client -> " + client + " throws error while instatniating");
        }
    }

    public void shutdown(){
        isRunning = false;
    }

    @Override
    public void run() {
        String message = "";
        boolean run = true;
        boolean hasUsername = false;
        while (isRunning) {
            try {
                message = in.readUTF();
                if(hasUsername) {
                    System.out.println(client + " " + message);
                    observable.setMessage(client, message);
                }else {
                    boolean exists = userListObservable.addUserIfNotExists(message);
                    if(exists){
                        client = message;
                        hasUsername = true;
                        out.writeUTF(("server:username_ok").toUpperCase());
                        out.flush();
                        System.out.println("INFO: Username -> " + message + " ok");
                    }else{
                        out.writeUTF(("server:username_nok").toUpperCase());
                        out.flush();
                        System.out.println("INFO: Username -> " + message + " nok");
                    }
                }
            } catch (IOException ex) {
                System.out.println("INFO: CLIENT -> " + client + " Disconnected");
                isRunning = false;
            }
        }
    }
}
