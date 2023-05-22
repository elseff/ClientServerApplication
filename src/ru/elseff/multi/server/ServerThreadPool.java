package ru.elseff.multi.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThreadPool extends Thread {

    private ServerSocket serverSocket;

    public ServerThreadPool() {
        try {
            serverSocket = new ServerSocket(4000);
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("Server is launched and waiting for connections");
            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("New client connection " + client.getPort());
                ServerThread server = new ServerThread(client);
                server.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
