package com.pechen.service_manager;

/**
 * Created by pechen on 7/10/2017.
 */
public interface IServiceRegistry {
    public void registerService(String name, String url);

    public void unregisterService(String name, String url);

    public String discoverServiceURI(String name);
}
