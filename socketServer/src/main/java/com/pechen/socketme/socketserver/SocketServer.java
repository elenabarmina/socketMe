package com.pechen.socketme.socketserver;

import com.pechen.socketme.beans.UserManager;
import com.pechen.socketme.enums.EnumMessageType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

/**
 * Created by pechen on 6/29/2017.
 */
@Named
@ApplicationScoped
public class SocketServer implements Serializable{
    private static HashSet<PrintWriter> userWriters = new HashSet<>();
    private static final int PORT = 8080;

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

        UserManager userManager = new UserManager();


        @Inject
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
                    isAuth = userManager.authUser(name);
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
                    userManager.logoutUser(name);
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
