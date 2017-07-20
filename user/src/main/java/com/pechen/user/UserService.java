package com.pechen.user;

import com.pechen.service_manager.IServiceRegistry;
import com.pechen.service_manager.Services;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 * Created by pechen on 7/10/2017.
 */
@Startup
@Singleton
public class UserService {
    @Inject @Services
    IServiceRegistry services;

    private static final String serviceURL = "http://localhost:8080/user/res/user";

    private static final String serviceName = "user";

    @PostConstruct
    public void registerService() {
        services.registerService(serviceName, serviceURL);
    }

    @PreDestroy
    public void unregisterService() {
        services.unregisterService(serviceName, serviceURL);
    }

}
