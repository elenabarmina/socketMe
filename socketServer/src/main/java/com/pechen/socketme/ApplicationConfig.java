package com.pechen.socketme;

import com.pechen.socketme.socketserver.SocketServer;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by pechen on 7/7/2017.
 */
public class ApplicationConfig extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        SocketServer.startApplication();
    }
}
