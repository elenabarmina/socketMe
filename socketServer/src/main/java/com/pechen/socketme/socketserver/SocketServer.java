package com.pechen.socketme.socketserver;

import com.pechen.socketme.enums.EnumMessageType;
import com.pechen.socketme.user.UsersContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Properties;

/**
 * Created by pechen on 6/29/2017.
 */
public class SocketServer {
    Properties properties = new Properties();
    private static HashSet<PrintWriter> userWriters = new HashSet<>();
    private static final int PORT = 8081;

    public static void startApplication() throws Exception {
        ServerSocket listener = new ServerSocket(PORT);
        try {
            while (true) {
                new Handler(listener.accept()).start();
            }
        } finally {
            listener.close();
        }
    }

    private static class Handler extends Thread {
        private String name;
        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                boolean isAuth = false;
                while (!isAuth) {
                    out.println(EnumMessageType.AUTH.getActionNumber());
                    name = in.readLine();
                    if (name == null) {
                        return;
                    }
                    isAuth = UsersContainer.authNewUser(name);
                }

                out.println(EnumMessageType.AUTH_SUCCESFUL.getActionNumber());
                userWriters.add(out);

                while (true) {
                    String input = in.readLine();
                    if (input == null) {
                        return;
                    }
                    for (PrintWriter writer : userWriters) {
                        writer.println(EnumMessageType.TEXT.getActionNumber() + " " + name + ": " + input);
                }
                }
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                if (name != null) {
                    UsersContainer.logoutUser(name);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
