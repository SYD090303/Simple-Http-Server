package com.httpserver.server.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.httpserver.server.exception.HttpConfigurationException;
import com.httpserver.server.util.Json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {
    private static ConfigurationManager myConfigManager;
    private static  Configuration myCurrentConfiguration;
    private ConfigurationManager() {}

    public static ConfigurationManager getInstance() {
        if (myConfigManager == null) {
            myConfigManager = new ConfigurationManager();
        }
        return myConfigManager;
    }

    public void loadConfig(String filePath)  {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        StringBuffer buffer = new StringBuffer();
        int i;
        while(true){
            try {
                if (!((i = fileReader.read()) != -1)) break;
            } catch (IOException e) {
                throw new HttpConfigurationException(e);
            }
            buffer.append((char)i);
        }
        JsonNode config = null;
        try {
            config = Json.parse(buffer.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException("Error parsing configuration file", e);
        }
        try {
            myCurrentConfiguration =  Json.fromJson(config, Configuration.class);
        } catch (IOException e) {
            throw new HttpConfigurationException("Error parsing configuration file, internal", e);
        }
    }

    public Configuration getCurrentConfig() {
        if( myCurrentConfiguration == null ){
            throw new HttpConfigurationException("No configuration found");
        }
        return myCurrentConfiguration;
    }
}
