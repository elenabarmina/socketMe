package com.pechen.socketme.enums;

import java.io.BufferedReader;
import java.io.PrintWriter;

import static java.lang.System.out;

/**
 * Created by pechen on 7/2/2017.
 */
public enum EnumMessageType {
    ERROR ("0"){
        public void handleMessage(BufferedReader in, PrintWriter out) {

        }
    },
    AUTH ("1") {
        public void handleMessage(BufferedReader in, PrintWriter out) {

        }
    },
    AUTH_SUCCESFUL ("2") {
        public void handleMessage(BufferedReader in, PrintWriter out) {

        }
    },
    TEXT ("3") {
        public void handleMessage(BufferedReader in, PrintWriter out) {

        }
    },
    COMMAND ("4") {
        public void handleMessage(BufferedReader in, PrintWriter out) {

        }
    };

    String actionNumber;

    EnumMessageType(String actionNumber) {
        this.actionNumber = actionNumber;
    }

    public String getActionNumber() {
        return actionNumber;
    }

    public abstract void handleMessage(BufferedReader in, PrintWriter out);
}
