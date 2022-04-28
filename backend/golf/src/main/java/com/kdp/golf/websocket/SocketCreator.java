package com.kdp.golf.websocket;

import com.kdp.golf.IdGenerator;
import com.kdp.golf.user.UserController;
import com.kdp.golf.user.UserService;
import org.eclipse.jetty.websocket.server.JettyServerUpgradeRequest;
import org.eclipse.jetty.websocket.server.JettyServerUpgradeResponse;
import org.eclipse.jetty.websocket.server.JettyWebSocketCreator;

public class SocketCreator implements JettyWebSocketCreator
{
    private final IdGenerator idGen;
    private final Sessions sessions;
    private final UserService userService;
    private final UserController userController;

    public SocketCreator(IdGenerator idGen,
                         Sessions sessions,
                         UserService userService,
                         UserController userController)
    {
        this.idGen = idGen;
        this.sessions = sessions;
        this.userService = userService;
        this.userController = userController;
    }

    @Override
    public Socket createWebSocket(JettyServerUpgradeRequest request,
                                  JettyServerUpgradeResponse response)
    {
        var id = idGen.generate();
        return new Socket(id, sessions, userService, userController);
    }
}
