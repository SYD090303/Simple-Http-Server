package com.httpserver.server.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.httpserver.server.exception.HttpConfigurationException;
import com.httpserver.server.util.Json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * The {@code ConfigurationManager} class is responsible for loading and managing
 * the server configuration. It follows the Singleton pattern to ensure only one
 * instance exists throughout the application's lifecycle.
 */
public class ConfigurationManager {

    /** The singleton instance of {@code ConfigurationManager}. */
    private static ConfigurationManager myConfigManager;

    /** The currently loaded configuration. */
    private static Configuration myCurrentConfiguration;

    /** Private constructor to prevent instantiation from outside the class. */
    private ConfigurationManager() {}

    /**
     * Retrieves the singleton instance of {@code ConfigurationManager}.
     *
     * @return The singleton instance of {@code ConfigurationManager}.
     */
    public static ConfigurationManager getInstance() {
        if (myConfigManager == null) {
            myConfigManager = new ConfigurationManager();
        }
        return myConfigManager;
    }

    /**
     * Loads the configuration from the specified JSON file.
     *
     * @param filePath The path to the configuration file.
     * @throws HttpConfigurationException If there is an error reading or parsing the file.
     */
    public void loadConfig(String filePath) {
        FileReader fileReader;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException("Configuration file not found: " + filePath, e);
        }

        StringBuilder buffer = new StringBuilder();
        int i;
        while (true) {
            try {
                if ((i = fileReader.read()) == -1) break;
            } catch (IOException e) {
                throw new HttpConfigurationException("Error reading configuration file", e);
            }
            buffer.append((char) i);
        }

        JsonNode config;
        try {
            config = Json.parse(buffer.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException("Error parsing configuration file", e);
        }

        try {
            myCurrentConfiguration = Json.fromJson(config, Configuration.class);
        } catch (IOException e) {
            throw new HttpConfigurationException("Error parsing configuration data internally", e);
        }
    }

    /**
     * Retrieves the currently loaded configuration.
     *
     * @return The current {@code Configuration} object.
     * @throws HttpConfigurationException If no configuration has been loaded.
     */
    public Configuration getCurrentConfig() {
        if (myCurrentConfiguration == null) {
            throw new HttpConfigurationException("No configuration found. Load configuration first.");
        }
        return myCurrentConfiguration;
    }
}
