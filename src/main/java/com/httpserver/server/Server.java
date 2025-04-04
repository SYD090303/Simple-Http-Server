package com.httpserver.server;

import com.httpserver.server.config.Configuration;
import com.httpserver.server.config.ConfigurationManager;

public class Server {
    public static void main(String[] args) {
        System.out.println("Server starting...");

        ConfigurationManager.getInstance().loadConfig("src/main/resources/http.json");
        Configuration config = ConfigurationManager.getInstance().getCurrentConfig();

        System.out.println("Server config loaded");
        System.out.println("Using Port: " + config.getPort());
        System.out.println("Using WebRoot: " + config.getWebroot());
    }
}
