package com.pechen.user;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pechen on 7/6/2017.
 */
public class UsersContainer {

    private static final int MAX_NAME_LENGTH = 15;

    private static Set<String> userNames = ConcurrentHashMap.newKeySet();

    public static boolean logoutUser(String name){
        return userNames.remove(name);
    }

    public static boolean authNewUser(String name){
        if (name.length() > MAX_NAME_LENGTH) return false;
        return userNames.add(name);
    }

    public static Set<String> getAllUserNames(){
        return Collections.unmodifiableSet( userNames );
    }
}
