package com.pechen.socketme;

import com.pechen.service_manager.ServiceDiscovery;
import com.pechen.service_manager.Services;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

/**
 * Created by pechen on 7/21/2017.
 */
@ApplicationScoped
public class Resources {
    @Inject
    @Services
    ServiceDiscovery services;

    @Produces
    public ServiceDiscovery getService() {
        return services;
    }
}
