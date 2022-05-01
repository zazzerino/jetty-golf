package com.kdp.golf.websocket;

import com.kdp.golf.user.UserController;
import org.eclipse.jetty.websocket.server.JettyServerUpgradeRequest;
import org.eclipse.jetty.websocket.server.JettyServerUpgradeResponse;
import org.eclipse.jetty.websocket.server.JettyWebSocketCreator;

import java.util.concurrent.atomic.AtomicLong;

public class SocketCreator implements JettyWebSocketCreator
{
    private final AtomicLong nextSessionId = new AtomicLong();
    private final UserController userController;

    public SocketCreator(UserController userController)
    {
        this.userController = userController;
    }

    @Override
    public Socket createWebSocket(JettyServerUpgradeRequest request,
                                  JettyServerUpgradeResponse response)
    {
        var sessionId = nextSessionId.getAndIncrement();
        return new Socket(sessionId, userController);
    }
}
