package com.pechen.socketme;

import com.pechen.socketme.socketserver.SocketServer;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by pechen on 7/7/2017.
 */
public class ApplicationConfig extends Application{

    private static Logger logger = LogManager.getLogger(ApplicationConfig.class);
    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.info("application started");
        SocketServer.startApplication();
    }
}
