package com.pechen.socketme.socketserver;

import com.pechen.socketme.beans.UserManager;
import com.pechen.socketme.enums.EnumMessageType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.MarkerManager;

/**
 * Created by pechen on 6/29/2017.
 */
@Named
@ApplicationScoped
public class SocketServer implements Serializable{
    private static Logger logger = LogManager.getLogger(SocketServer.class);

    private static HashSet<PrintWriter> userWriters = new HashSet<>();
    private static final int PORT = 8080;

    public static void startApplication() throws Exception {
        logger.info("Starting aplication");
        ServerSocket listener = new ServerSocket(PORT);
        try {
            while (true) {
                logger.info("new listener catched");
                new Handler(listener.accept()).start();
            }
        } finally {
            listener.close();
            logger.info("listener closed");
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
            logger.info("new handler running");
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                boolean isAuth = false;
                while (!isAuth) {
                    logger.debug("send auth message");
                    out.println(EnumMessageType.AUTH.getActionNumber());
                    name = in.readLine();
                    if (name == null) {
                        logger.debug("name is null");
                        return;
                    }
                    long start = new Date().getTime();
                    isAuth = userManager.authUser(name);
                    logger.debug("auth user by service comlited within " + (new Date().getTime() - start));
                }

                logger.debug("auth successful");
                out.println(EnumMessageType.AUTH_SUCCESFUL.getActionNumber());
                userWriters.add(out);

                logger.debug("start listening user");
                while (true) {
                    String input = in.readLine();
                    if (input == null) {
                        return;
                    }
                    for (PrintWriter writer : userWriters) {
                        logger.info(MarkerManager.getMarker("message"), name + " : " + input);
                        writer.println(EnumMessageType.TEXT.getActionNumber() + " " + name + ": " + input);
                    }
                }
            } catch (IOException e) {
                logger.trace(e);
            } finally {
                if (name != null) {
                    userManager.logoutUser(name);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    logger.trace(e);
                }
            }
        }
    }
}
