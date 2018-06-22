package server;

import server.message.MessageHandler;
import server.message.MessageObservable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Optional;

public class ChatServer extends Thread {

    MessageObservable message;
    UserListObservable userList;
    ServerSocket serverSocket;
    ArrayList<Socket> sockets;
    int counter = 1;
    boolean isRunning = true;

    public ChatServer(int port, UserListObservable userList) {
        try {
            this.serverSocket = new ServerSocket(port);
            message = new MessageObservable();
            sockets = new ArrayList<>();
            this.userList = userList;
        } catch (IOException e) {
            System.out.println("ERROR: IOException");
        }
    }

    public void shutdown() {
        isRunning = false;
        sockets.stream().forEach(i -> {
            try {
                i.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeSocket(Socket socket){
        Optional<Socket> result = sockets.stream().filter(i -> i == socket).findFirst();
        if(result.isPresent()){
            sockets.remove(result.get());
            System.out.println("REmoved socket");
        }
        System.out.println("REmoved socket finished");
    }


    @Override
    public void run() {
        try {
            System.out.println("INFO: ChatServer started");
            while (isRunning) {
                Socket socket = serverSocket.accept();
                MessageHandler handler = new MessageHandler(socket, message, userList, this);
                sockets.add(socket);
                System.out.println("INFO: Client established connection");
                counter++;
                handler.start();
            }
        } catch (IOException e) {
            System.out.println("ERROR: IOException");
        }
    }
}
