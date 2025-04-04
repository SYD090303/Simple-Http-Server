package com.httpserver.server;

import com.httpserver.server.config.Configuration;
import com.httpserver.server.config.ConfigurationManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A simple HTTP server implementation in Java.
 * This server loads configuration settings, listens on a specified port,
 * and responds with a basic HTML page.
 */
public class Server {
    /**
     * The main entry point for the HTTP server.
     * It loads configuration, starts the server, and handles incoming connections.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("Server starting...");

        // Load server configuration from JSON file
        ConfigurationManager.getInstance().loadConfig("src/main/resources/http.json");
        Configuration config = ConfigurationManager.getInstance().getCurrentConfig();

        System.out.println("Server config loaded");
        System.out.println("Using Port: " + config.getPort());
        System.out.println("Using WebRoot: " + config.getWebroot());

        try {
            // Create a server socket to listen on the specified port
            ServerSocket serverSocket = new ServerSocket(config.getPort());
            System.out.println("Server is listening on port " + config.getPort());

            // Accept an incoming client connection
            Socket socket = serverSocket.accept();
            System.out.println("New client connected");

            // Get input and output streams for communication
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            // Define the basic HTML response content
            String htmlContent = "<html>" +
                    "<head><title>Simple HTTP Server</title></head>" +
                    "<body><h1>Welcome to My HTTP Server</h1>" +
                    "<p>This is a simple HTTP server in Java.</p></body>" +
                    "</html>";

            // Construct the HTTP response with headers
            String httpResponse = "HTTP/1.1 200 OK\r\n" +
                    "Content-Length: " + htmlContent.getBytes().length + "\r\n" +
                    "Content-Type: text/html\r\n" +
                    "\r\n" +
                    htmlContent;

            // Send the HTTP response to the client
            outputStream.write(httpResponse.getBytes());

            // Close all resources
            inputStream.close();
            outputStream.close();
            socket.close();
            serverSocket.close();

            System.out.println("Server stopped.");
        } catch (IOException e) {
            throw new RuntimeException("Server encountered an error", e);
        }
    }
}
