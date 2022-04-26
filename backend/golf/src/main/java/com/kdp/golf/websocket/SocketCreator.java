package com.kdp.golf.websocket;

import com.kdp.golf.user.UserController;
import com.kdp.golf.user.UserService;
import org.eclipse.jetty.websocket.server.JettyServerUpgradeRequest;
import org.eclipse.jetty.websocket.server.JettyServerUpgradeResponse;
import org.eclipse.jetty.websocket.server.JettyWebSocketCreator;

import java.util.concurrent.atomic.AtomicLong;

public class SocketCreator implements JettyWebSocketCreator
{
    private final AtomicLong idGenerator = new AtomicLong(0);
    private final Sessions sessions;
    private final UserService userService;
    private final UserController userController;

    public SocketCreator(Sessions sessions,
                         UserService userService,
                         UserController userController)
    {
        this.sessions = sessions;
        this.userService = userService;
        this.userController = userController;
    }

    @Override
    public Socket createWebSocket(JettyServerUpgradeRequest request,
                                  JettyServerUpgradeResponse response)
    {
        var id = idGenerator.getAndIncrement();
        return new Socket(id, sessions, userService, userController);
    }
}
