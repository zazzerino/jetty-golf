package com.kdp.golf.websocket;

import com.kdp.golf.user.UserController;
import com.kdp.golf.user.UserService;
import org.eclipse.jetty.websocket.server.JettyWebSocketServlet;
import org.eclipse.jetty.websocket.server.JettyWebSocketServletFactory;

import java.time.Duration;

public class SocketServlet extends JettyWebSocketServlet
{
    private static final Duration IDLE_TIMEOUT = Duration.ofMinutes(4);
    private final Sessions sessions;
    private final UserService userService;
    private final UserController userController;

    public SocketServlet(Sessions sessions,
                         UserService userService,
                         UserController userController)
    {
        this.sessions = sessions;
        this.userService = userService;
        this.userController = userController;
    }

    @Override
    protected void configure(JettyWebSocketServletFactory factory)
    {
        var creator = new SocketCreator(sessions, userService, userController);
        factory.setCreator(creator);
        factory.setIdleTimeout(IDLE_TIMEOUT);
    }
}
