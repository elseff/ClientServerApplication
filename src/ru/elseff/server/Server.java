package ru.elseff.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Locale;

public class Server extends Thread {

    private ServerSocket serverSocket;

    private Socket clientSocket;
    private BufferedReader in;
    private BufferedWriter out;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server is launched and waiting for connections");
            clientSocket = serverSocket.accept();
            System.out.println("New client connection " + clientSocket.getPort());
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            try {
                try {
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    while (true) {
                        String message = in.readLine();
                        if ("close".equals(message.toLowerCase(Locale.ROOT)))
                            break;

                        String response = clientSocket.getPort() + " - " + message + "\n";
                        System.out.print(response);
                        out.write(response);
                        out.flush();
                    }
                } finally {
                    in.close();
                    out.close();
                    clientSocket.close();
                    System.out.println("Client connection closed");
                }
            } finally {
                serverSocket.close();
                System.out.println("Server closed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
