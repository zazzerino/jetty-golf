package com.kdp.golf.websocket;

import com.kdp.golf.game.GameController;
import com.kdp.golf.user.UserController;
import org.eclipse.jetty.websocket.server.JettyServerUpgradeRequest;
import org.eclipse.jetty.websocket.server.JettyServerUpgradeResponse;
import org.eclipse.jetty.websocket.server.JettyWebSocketCreator;

import java.util.concurrent.atomic.AtomicLong;

public class SocketCreator implements JettyWebSocketCreator
{
    private final AtomicLong nextSocketId = new AtomicLong();
    private final UserController userController;
    private final GameController gameController;

    public SocketCreator(UserController userController,
                         GameController gameController)
    {
        this.userController = userController;
        this.gameController = gameController;
    }

    @Override
    public Socket createWebSocket(JettyServerUpgradeRequest request,
                                  JettyServerUpgradeResponse response)
    {
        var socketId = nextSocketId.getAndIncrement();
        return new Socket(socketId, userController, gameController);
    }
}
