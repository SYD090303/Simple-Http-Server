# 🌐 Simple HTTP Server in Java (From Scratch)

In my journey to strengthen backend fundamentals, I decided to build a basic HTTP server using **Core Java** — no frameworks, just raw sockets and multithreading. This project helped me explore how web servers actually handle client requests under the hood.

---

![Screenshot of the Simple HTTP Server](https://github.com/SYD090303/Simple-Http-Server/blob/master/assets/Screenshot%202025-04-04%20231208.png?raw=true)

## ✨ Features

- ✅ Handle browser HTTP requests using `Socket` and `ServerSocket`
- ✅ Parse HTTP request headers manually
- ✅ Serve static files (HTML, CSS, JS, images)
- ✅ Respond with valid HTTP headers and content
- ✅ Handle multiple clients concurrently (via multithreading)
- ✅ Log request details for monitoring and debugging
- ✅ Basic MIME type handling based on file extensions

---

## 🛠️ How It Works

1. **Start Server** using `ServerSocket` on a defined port (e.g., 8080)
2. **Wait for browser connections**
3. **Spawn a new thread** per incoming request to handle it without blocking others
4. **Read HTTP request** and extract the method and resource path
5. **Serve the requested file** (e.g., `/index.html`) if it exists
6. **Respond with appropriate HTTP status** and headers (200 OK or 404 Not Found)
7. **Send the content** (HTML, CSS, etc.) to the client
8. **Keep the server running** to handle further requests

---

<p align="center">
  <img src="https://github.com/SYD090303/Simple-Http-Server/blob/master/assets/5381146.jpg?raw=true" alt="Screenshot of the Simple HTTP Server" />
</p>
