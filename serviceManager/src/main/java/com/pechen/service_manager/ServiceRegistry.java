package com.pechen.service_manager;

import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;

/**
 * Created by pechen on 7/10/2017.
 */
@ApplicationScoped
public class ServiceRegistry implements IServiceRegistry{

    private ConcurrentHashMap<String, String> services = new ConcurrentHashMap<>();

    @Override
    public void registerService(String name, String url) {
        services.putIfAbsent(name, url);
    }

    @Override
    public void unregisterService(String name, String url) {
        services.remove(name, url);
    }

    @Override
    public String discoverServiceURI(String name) {
        if (services.keySet().contains(name)) {
            return services.get(name);
        }
        return null;
    }
}
