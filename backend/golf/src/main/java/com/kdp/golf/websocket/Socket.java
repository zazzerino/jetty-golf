package com.kdp.golf.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdp.golf.game.GameController;
import com.kdp.golf.user.UserController;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Socket implements WebSocketListener
{
    private final Long socketId;
    private Session session;
    private final UserController userController;
    private final GameController gameController;

    private static final Sessions sessions = new Sessions();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(Socket.class);

    public Socket(Long socketId,
                  UserController userController,
                  GameController gameController)
    {
        this.socketId = socketId;
        this.userController = userController;
        this.gameController = gameController;
    }

    @Override
    public void onWebSocketConnect(Session session)
    {
        this.session = session;
        log.info("session connected: " + socketId);
        sessions.add(socketId, session);

        var userResponse = userController.createUser(socketId);
        sendResponse(session, userResponse);
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason)
    {
        this.session = null;
        sessions.remove(socketId);
        userController.deleteUser(socketId);
        log.info("session closed: " + socketId);
    }

    @Override
    public void onWebSocketText(String message)
    {
        log.info("message received: " + message);
        var request = parseJson(socketId, message);

        var response = switch (request) {
            case Request.UpdateName r -> userController.updateName(r);
            case Request.CreateGame r -> gameController.createGame(r);
            case Request.StartGame r -> gameController.startGame(r);
            default -> throw new IllegalStateException("unexpected value: " + request);
        };

        log.info("sending response: " + response);
        sendResponse(session, response);
    }

    @Override
    public void onWebSocketError(Throwable cause)
    {
        log.error("websocket error: " + cause);
    }

    private static void sendResponse(Session session, Response response)
    {
        try {
            var json = objectMapper.writeValueAsString(response);
            session.getRemote().sendString(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Request parseJson(Long sessionId, String json)
    {
        try {
            var jsonNode = objectMapper.readTree(json);
            var typeText = jsonNode.get("type").asText();
            var requestType = Request.Type.valueOf(typeText);

            return switch (requestType) {
                case UPDATE_NAME -> {
                    var name = jsonNode.get("name").asText();
                    yield new Request.UpdateName(sessionId, name);
                }
                case CREATE_GAME -> new Request.CreateGame(sessionId);
                case START_GAME -> {
                    var gameId = jsonNode.get("gameId").asLong();
                    yield new Request.StartGame(sessionId, gameId);
                }
                default -> throw new IllegalStateException("unexpected value: " + requestType);
            };
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
