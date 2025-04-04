package com.httpserver.server.config;

import lombok.Data;

@Data
public class Configuration {

    private int port;
    private String webroot;
}
