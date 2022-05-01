package com.kdp.golf.websocket;

import com.kdp.golf.user.UserController;
import org.eclipse.jetty.websocket.server.JettyWebSocketServlet;
import org.eclipse.jetty.websocket.server.JettyWebSocketServletFactory;

import java.time.Duration;

public class SocketServlet extends JettyWebSocketServlet
{
    private static final Duration IDLE_TIMEOUT = Duration.ofMinutes(4);

    private final UserController userController;

    public SocketServlet(UserController userController)
    {
        this.userController = userController;
    }

    @Override
    protected void configure(JettyWebSocketServletFactory factory)
    {
        var creator = new SocketCreator(userController);
        factory.setCreator(creator);
        factory.setIdleTimeout(IDLE_TIMEOUT);
    }
}
