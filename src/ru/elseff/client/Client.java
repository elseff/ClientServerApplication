package ru.elseff.client;

import java.io.*;
import java.net.Socket;
import java.util.Locale;

public class Client extends Thread {

    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private BufferedReader reader;

    public Client(String serverAddress, int serverPort) {
        try {
            socket = new Socket(serverAddress, serverPort);
            System.out.printf("Client %d launched\n", socket.getLocalPort());
            System.out.printf("Connected to %s:%d\n", serverAddress, serverPort);

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
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    reader = new BufferedReader(new InputStreamReader(System.in));
                    while (true) {
                        System.out.print("Write something: ");
                        String message = reader.readLine();
                        out.write(message + "\n");
                        out.flush();
                        if ("close".equals(message.toLowerCase(Locale.ROOT)))
                            break;
                        String response = in.readLine();
                        System.out.println(response);
                    }
                } finally {
                    in.close();
                    out.close();
                    reader.close();
                }
            } finally {
                socket.close();
                System.out.println("Connection closed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client("127.0.0.1", 7000);
    }
}