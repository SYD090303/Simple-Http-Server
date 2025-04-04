package com.httpserver.server.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;

public class ServerListener extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerListener.class);
    private final int port;
    private final String webroot;
    private final ServerSocket serverSocket;

    public ServerListener(int port, String webroot) throws IOException {
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(this.port);
    }

    @Override
    public void run() {
        try {
            while (serverSocket.isBound() && !serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                LOGGER.info("New client connected: " + socket.getInetAddress());

                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

                String clientIp = socket.getInetAddress().getHostAddress();
                String serverHost = InetAddress.getLocalHost().getHostName();

                String htmlContent = "<!DOCTYPE html>" +
                        "<html lang='en'>" +
                        "<head>" +
                        "<meta charset='UTF-8'>" +
                        "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                        "<title>Futuristic Server Clock</title>" +
                        "<link href='https://fonts.googleapis.com/css2?family=Orbitron:wght@600&display=swap' rel='stylesheet'>" +
                        "<style>" +
                        "body {" +
                        "  margin: 0;" +
                        "  padding: 0;" +
                        "  height: 100vh;" +
                        "  display: flex;" +
                        "  justify-content: center;" +
                        "  align-items: center;" +
                        "  flex-direction: column;" +
                        "  font-family: 'Orbitron', sans-serif;" +
                        "  background: radial-gradient(ellipse at center, #0f2027, #203a43, #2c5364);" +
                        "  color: #00fff7;" +
                        "  overflow: hidden;" +
                        "}" +
                        ".glow {" +
                        "  font-size: 3.5rem;" +
                        "  color: #0ff;" +
                        "  text-shadow: 0 0 2px #0ff, 0 0 4px #0ff;" +
                        "  margin-bottom: 20px;" +
                        "}" +
                        "#clock {" +
                        "  font-size: 3rem;" +
                        "  padding: 20px 40px;" +
                        "  background: rgba(255, 255, 255, 0.05);" +
                        "  border-radius: 20px;" +
                        "  backdrop-filter: blur(10px);" +
                        "  border: 1px solid rgba(255, 255, 255, 0.1);" +
                        "  box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.37);" +
                        "  color: #00fff7;" +
                        "}" +
                        ".info {" +
                        "  font-size: 1rem;" +
                        "  margin-top: 10px;" +
                        "  color: #aefcff;" +
                        "}" +
                        ".toggle-btn {" +
                        "  padding: 10px 20px;" +
                        "  margin-top: 25px;" +
                        "  border: none;" +
                        "  border-radius: 12px;" +
                        "  cursor: pointer;" +
                        "  background-color: rgba(255, 255, 255, 0.1);" +
                        "  color: #00fff7;" +
                        "  font-weight: bold;" +
                        "  backdrop-filter: blur(5px);" +
                        "  box-shadow: 0 0 12px #00fff7;" +
                        "  transition: all 0.3s ease;" +
                        "}" +
                        ".toggle-btn:hover {" +
                        "  box-shadow: 0 0 20px #00fff7, 0 0 30px #00fff7;" +
                        "}" +
                        ".dark-mode {" +
                        "  background: #000;" +
                        "  color: #0ff;" +
                        "}" +
                        ".dark-mode #clock {" +
                        "  background-color: rgba(20, 20, 20, 0.7);" +
                        "  color: #0ff;" +
                        "}" +
                        "#runtime {" +
                        "  position: absolute;" +
                        "  top: 15px;" +
                        "  left: 20px;" +
                        "  font-size: 0.9rem;" +
                        "  background: rgba(0,0,0,0.4);" +
                        "  padding: 8px 12px;" +
                        "  border-radius: 10px;" +
                        "  color: #aefcff;" +
                        "  backdrop-filter: blur(4px);" +
                        "}" +
                        "</style>" +
                        "</head>" +
                        "<body>" +
                        "<div id='runtime'>Runtime: 0s</div>" +
                        "<div class='glow'>Java-based Personalized Server</div>" +
                        "<div id='clock'></div>" +
                        "<div class='info'>Client IP: " + clientIp + "</div>" +
                        "<div class='info'>Server: " + serverHost + ":" + port + "</div>" +
                        "<button class='toggle-btn' onclick='toggleTheme()'>Light/Dark Mode</button>" +
                        "<script>" +
                        "let seconds = 0;" +
                        "function updateClock() {" +
                        "  const now = new Date();" +
                        "  let formatted = now.toLocaleTimeString();" +
                        "  document.getElementById('clock').innerText = formatted;" +
                        "}" +
                        "function updateRuntime() {" +
                        "  document.getElementById('runtime').innerText = 'Runtime: ' + seconds + 's';" +
                        "  seconds++;" +
                        "}" +
                        "function toggleTheme() {" +
                        "  document.body.classList.toggle('dark-mode');" +
                        "}" +
                        "setInterval(updateClock, 1000);" +
                        "setInterval(updateRuntime, 1000);" +
                        "updateClock();" +
                        "</script>" +
                        "</body>" +
                        "</html>";

                String httpResponse = "HTTP/1.1 200 OK\r\n" +
                        "Content-Length: " + htmlContent.getBytes().length + "\r\n" +
                        "Content-Type: text/html\r\n" +
                        "\r\n" +
                        htmlContent;

                outputStream.write(httpResponse.getBytes());
                inputStream.close();
                outputStream.close();
                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Server encountered an error", e);
        }
    }
}
