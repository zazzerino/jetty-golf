package com.kdp.golf.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdp.golf.user.UserController;
import com.kdp.golf.user.UserService;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Socket implements WebSocketListener
{
    private final Long sessionId;
    private Session session;
    private final UserController userController;

    private static final Sessions sessions = new Sessions();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(Socket.class);

    public Socket(Long sessionId,
                  UserController userController)
    {
        this.sessionId = sessionId;
        this.userController = userController;
    }

    @Override
    public void onWebSocketConnect(Session session)
    {
        this.session = session;
        log.info("session connected: " + sessionId);
        sessions.add(sessionId, session);

        var request = new Request.CreateUser(sessionId);
        var response = userController.createUser(request);
        trySendResponse(session, response);
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason)
    {
        log.info("session closed: " + sessionId);
        sessions.remove(sessionId);
        userController.deleteUser(sessionId);
    }

    @Override
    public void onWebSocketText(String message)
    {
        log.info("message received: " + message);
        var request = tryParseJson(sessionId, message);
        var session = sessions.getSession(sessionId);

        switch (request) {
            case Request.UpdateName u -> handleUpdateName(u);
            default -> throw new IllegalStateException("Unexpected value: " + request);
        }
    }

    private void handleUpdateName(Request.UpdateName req)
    {
        var response = userController.updateName(req);
        log.info("response: " + response);
        trySendResponse(session, response);
    }

    @Override
    public void onWebSocketError(Throwable cause)
    {
        log.error("websocket error: " + cause);
    }

    private static void sendResponse(Session session, Response response) throws IOException
    {
        var json = objectMapper.writeValueAsString(response);
        session.getRemote().sendString(json);
    }

    private static void trySendResponse(Session session, Response response) {
        try {
            sendResponse(session, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Request parseJson(Long sessionId, String json) throws JsonProcessingException
    {
        var jsonNode = objectMapper.readTree(json);
        var typeText = jsonNode.get("type").asText();
        var requestType = Request.Type.valueOf(typeText);

        switch (requestType) {
            case UPDATE_NAME -> {
                var name = jsonNode.get("name").asText();
                return new Request.UpdateName(sessionId, name);
            }
            default -> throw new IllegalStateException("Unexpected value: " + requestType);
        }
    }

    private static Request tryParseJson(Long sessionId, String json) {
        try {
            return parseJson(sessionId, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
