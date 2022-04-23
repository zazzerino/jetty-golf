package com.kdp.golf.websocket;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.server.config.JettyWebSocketServletContainerInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class WebSocketServer {
    public static final int PORT = 8080;
    public static final Duration IDLE_TIMEOUT = Duration.ofMinutes(4);
    public static final int MAX_TEXT_MESSAGE_SIZE = 128 * 1024;
    private final Server server;
    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    public WebSocketServer() {
        server = new Server(PORT);

        var handler = new ServletContextHandler(server, "/");
        JettyWebSocketServletContainerInitializer.configure(handler, (context, container) -> {
            container.setMaxTextMessageSize(MAX_TEXT_MESSAGE_SIZE);
            container.setIdleTimeout(IDLE_TIMEOUT);
            container.addMapping("/ws", WebSocketConnection.class);
        });

        server.setHandler(handler);
    }

    public void run() throws Exception {
        log.info("Starting server");
        server.start();
        server.join();
    }
}
