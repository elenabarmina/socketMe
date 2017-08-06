package com.pechen.service_manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

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
        if (isInputExists(name, url))
            services.putIfAbsent(name, url);
    }

    @Override
    public void unregisterService(String name, String url) {
        if (isInputExists(name, url))
            services.remove(name, url);
    }

    @Override
    public String discoverServiceURI(String name) {
        if (services.keySet().contains(name)) {
            return services.get(name);
        }
        return null;
    }

    private boolean isInputExists(String name, String url){
        if(name == null || name.trim().length() == 0) {
            return false;
        }
        if(url == null || url.trim().length() == 0) {
            return false;
        }
        return true;
    }
}
