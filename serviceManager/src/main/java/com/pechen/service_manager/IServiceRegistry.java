package com.pechen.service_manager;

/**
 * Created by pechen on 7/10/2017.
 */
public interface IServiceRegistry {
    void registerService(String name, String url);

    void unregisterService(String name, String url);

    String discoverServiceURI(String name);
}
