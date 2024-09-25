package com.example.springmvvm.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class ServerConfig {

    @Autowired
    private ConfigurableEnvironment environment;

    @PostConstruct
    public void init() {
        try {
            String ipAddress = InetAddress.getLocalHost().getHostAddress();
            environment.getSystemProperties().put("server.ip", ipAddress);
        } catch (UnknownHostException e) {
            throw new RuntimeException("Unable to get the server's IP address", e);
        }
    }
}
