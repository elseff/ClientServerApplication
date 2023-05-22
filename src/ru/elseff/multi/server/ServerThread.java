package ru.elseff.multi.server;

import java.io.*;
import java.net.Socket;
import java.util.Locale;

public class ServerThread extends Thread {

    private final Socket clientSocket;

    public ServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {
                while (true) {
                    String message = in.readLine();
                    if ("close".equals(message.toLowerCase(Locale.ROOT)))
                        break;

                    System.out.print(clientSocket.getPort() + " - " + message + "\n");
                    String response = "accepted\n";
                    out.write(response);
                    out.flush();
                }
            } finally {
                clientSocket.close();
                System.out.println(clientSocket.getPort() + " disconnected");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
