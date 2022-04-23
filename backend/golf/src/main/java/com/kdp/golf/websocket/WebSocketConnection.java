package com.kdp.golf.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketConnection extends WebSocketAdapter {
    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();
    private final String id = Integer.toHexString(hashCode());;
    private static final Logger log = LoggerFactory.getLogger(WebSocketConnection.class);

    @Override
    public void onWebSocketConnect(Session session) {
        super.onWebSocketConnect(session);
        log.info("session connected: " + id);
        sessions.put(id, session);
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        super.onWebSocketClose(statusCode, reason);
        log.info("session closed: " + id);
        sessions.remove(id);
    }

    @Override
    public void onWebSocketText(String message) {
        super.onWebSocketText(message);
        log.info("message received: " + message);
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        super.onWebSocketError(cause);
        log.error("websocket error: " + cause.getMessage());
    }

    public static void sendToSession(Session session, String text) throws IOException {
        session.getRemote().sendString(text);
    }

    public static void sendToSessionId(String sessionId, String text) throws IOException {
        var session = sessions.get(sessionId);
        sendToSession(session, text);
    }
}
