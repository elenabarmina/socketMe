package com.pechen.commander;

import com.pechen.service_manager.IServiceRegistry;
import com.pechen.service_manager.Services;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 * Created by pechen on 15.09.2017.
 */
@Startup
@Singleton
public class CommanderService {
    @Inject
    @Services
    IServiceRegistry services;

    private static final String serviceURL = "http://localhost:9090/commander/res/commander";

    private static final String serviceName = "commander";

    @PostConstruct
    public void registerService() {
        services.registerService(serviceName, serviceURL);
    }

    @PreDestroy
    public void unregisterService() {
        services.unregisterService(serviceName, serviceURL);
    }
}
