package com.httpserver.server.config;

import lombok.Data;

/**
 * The {@code Configuration} class holds the configuration settings for the HTTP server.
 * It includes properties for specifying the server's port and the root directory for web files.
 */
@Data
public class Configuration {

    /**
     * The port number on which the server will listen for incoming connections.
     */
    private int port;

    /**
     * The root directory from which the server will serve web files.
     */
    private String webroot;
}
