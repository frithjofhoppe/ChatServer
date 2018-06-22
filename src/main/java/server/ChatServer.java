package server;

import server.message.MessageHandler;
import server.message.MessageObservable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
    }

    @Override
    public void run() {
        try {
            System.out.println("INFO: ChatServer started");
            while (isRunning) {
                Socket socket = serverSocket.accept();
                MessageHandler handler = new MessageHandler(socket, message, userList);
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
