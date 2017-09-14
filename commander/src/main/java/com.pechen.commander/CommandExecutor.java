package com.pechen.commander;

/**
 * Created by pechen on 15.09.2017.
 */
public class CommandExecutor {

    public static CommandExecutor getInstance(){
        return new CommandExecutor();
    }

    public String executeCommand(String command){
        return "hello: " + command;
    }
}
