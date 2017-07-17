package com.pechen.user;

import java.security.SecureRandom;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pechen on 7/6/2017.
 */
public class UsersContainer {

    private static Set<String> userNames = ConcurrentHashMap.newKeySet();

    public static boolean logoutUser(String name){

        return userNames.remove(name);
    }

    public static boolean authNewUser(String name){

        return userNames.add(name);
    }
}
