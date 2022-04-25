package com.kdp.golf.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdp.golf.user.UserController;
import com.kdp.golf.user.UserService;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

public class Socket extends WebSocketAdapter
{
    public final Long id;
    private final Sessions sessions;
    private final UserService userService;
    private final UserController userController;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(Socket.class);

    public Socket(Long id,
                  Sessions sessions,
                  UserService userService,
                  UserController userController)
    {
        this.id = id;
        this.sessions = sessions;
        this.userService = userService;
        this.userController = userController;
    }

    @Override
    public void onWebSocketConnect(Session session)
    {
        super.onWebSocketConnect(session);
        log.info("session connected: " + session);
        sessions.add(id, session);

        var response = userController.createUser(id);
        trySendResponse(session, response);
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason)
    {
        super.onWebSocketClose(statusCode, reason);
        log.info("session closed: " + getSession());
        sessions.remove(id);
        userController.deleteUser(id);
    }

    @Override
    public void onWebSocketText(String message)
    {
        super.onWebSocketText(message);
        log.info("message received: " + message);
        var session = getSession();
        var userId = findUserId(session).orElseThrow();
        var request = tryParseJson(userId, message);

        switch (request) {
            case Request.UpdateName r -> handleUpdateName(session, r);
            default -> throw new IllegalStateException("Unexpected value: " + request);
        }
    }

    private void handleUpdateName(Session session, Request.UpdateName request)
    {
        var response = userController.updateName(id, request.name());
        trySendResponse(session, response);
    }

    @Override
    public void onWebSocketError(Throwable cause)
    {
        super.onWebSocketError(cause);
        log.error("websocket error: " + cause);
    }

    private static void sendResponse(Session session, Response response) throws IOException
    {
        var json = objectMapper.writeValueAsString(response);
        session.getRemote().sendString(json);
    }

    private Optional<Long> findUserId(Session session)
    {
        var sessionId = sessions.getSessionId(session);
        return userService.findUserId(sessionId);
    }

    private static Request parseJson(Long userId, String json) throws JsonProcessingException
    {
        var jsonNode = objectMapper.readTree(json);
        var typeVal = jsonNode.get("type").asText();
        var requestType = Request.Type.valueOf(typeVal);

        switch (requestType) {
            case UPDATE_NAME -> {
                var name = jsonNode.get("name").asText();
                return new Request.UpdateName(userId, name);
            }
            default -> throw new IllegalStateException("Unexpected value: " + requestType);
        }
    }

    private static void trySendResponse(Session session, Response response) {
        try {
            sendResponse(session, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Request tryParseJson(Long userId, String json) {
        try {
            return parseJson(userId, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}