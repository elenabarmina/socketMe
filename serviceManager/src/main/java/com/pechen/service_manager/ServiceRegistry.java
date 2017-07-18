package com.pechen.service_manager;

import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Created by pechen on 7/10/2017.
 */
@Services
@ApplicationScoped
public class ServiceRegistry implements IServiceRegistry{

    private ConcurrentHashMap<String, String> services = new ConcurrentHashMap<>();

    @Inject
    public ServiceRegistry() {
    }

    @Override
    public void registerService(String name, String url) {
        services.put(name, url);
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
