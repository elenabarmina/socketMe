package com.pechen.service_manager;

import java.util.Arrays;

/**
 * Created by pechen on 8/4/2017.
 */
public enum FamiliarServices {
    USER("user", "kiJ4t3TgXs"),
    COMMAND("commander", "PUM6tqaJv0"),
    MESSAGE("messager", "DGrtd6wazs"),
    CLIENT_API("client_api", "suWS6PBjxK");

    private String name;
    private String token;

    private FamiliarServices(String name, String token){
        this.name = name;
        this.token = token;
    }

    public String getName(){
        return this.name;
    }

    protected String getToken(){
        return this.token;
    }

    boolean isTrusted(String token, String name){
        return Arrays.stream(FamiliarServices.values())
                .filter(trustedService -> trustedService.getToken().equals(token) && trustedService.getName().equals(name))
                .count() > 0;
    }
}
