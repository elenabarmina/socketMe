package com.pechen.socketme.socketserver;

import com.pechen.service_manager.ServiceDiscovery;
import com.pechen.socketme.enums.EnumMessageType;

import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

/**
 * Created by pechen on 6/29/2017.
 */
@SessionScoped
@Named
public class SocketServer implements Serializable{
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

        @Inject
        ServiceDiscovery services;

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
                    Response response = services.getUserService().request().post(Entity.json(name));

                    Response.StatusType statusInfo = response.getStatusInfo();

                    if (statusInfo.getFamily() == Response.Status.Family.SUCCESSFUL)
                        isAuth = true;
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
                    services.getUserService().path(name).request().delete();
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
