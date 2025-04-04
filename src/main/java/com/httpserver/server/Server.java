package com.httpserver.server;

import com.httpserver.server.config.Configuration;
import com.httpserver.server.config.ConfigurationManager;
import com.httpserver.server.core.ServerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * A simple HTTP server implementation in Java.
 * This server loads configuration settings, listens on a specified port,
 * and responds with a basic HTML page.
 */
public class Server {
    private final static Logger LOGGER = LoggerFactory.getLogger(Server.class);
    /**
     * The main entry point for the HTTP server.
     * It loads configuration, starts the server, and handles incoming connections.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        LOGGER.info("Server starting...");

        // Load server configuration from JSON file
        ConfigurationManager.getInstance().loadConfig("src/main/resources/http.json");
        Configuration config = ConfigurationManager.getInstance().getCurrentConfig();

        LOGGER.info("Server config loaded");
        LOGGER.info("Using Port: {}", config.getPort());
        LOGGER.info("Using WebRoot: {}", config.getWebroot());

        // Create a server socket to listen on the specified port
        try {
            ServerListener serverListener = new ServerListener(config.getPort(), config.getWebroot());
            serverListener.start();
        } catch (IOException e) {
            LOGGER.error("Error starting server: ", e);
            throw new RuntimeException(e);
        }
    }
}
