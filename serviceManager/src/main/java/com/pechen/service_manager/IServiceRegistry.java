package com.pechen.service_manager;

/**
 * Created by pechen on 7/10/2017.
 */
public interface IServiceRegistry {
    boolean registerService(String name, String url);

    boolean unregisterService(String name, String url);

    String discoverServiceURI(String name);
}
