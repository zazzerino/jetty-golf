package com.kdp.golf.websocket;

import com.kdp.golf.game.GameController;
import com.kdp.golf.user.UserController;
import org.eclipse.jetty.websocket.server.JettyWebSocketServlet;
import org.eclipse.jetty.websocket.server.JettyWebSocketServletFactory;

import java.time.Duration;

public class SocketServlet extends JettyWebSocketServlet
{
    private static final Duration IDLE_TIMEOUT = Duration.ofMinutes(4);

    private final UserController userController;
    private final GameController gameController;

    public SocketServlet(UserController userController,
                         GameController gameController)
    {
        this.userController = userController;
        this.gameController = gameController;
    }

    @Override
    protected void configure(JettyWebSocketServletFactory factory)
    {
        var creator = new SocketCreator(userController, gameController);
        factory.setCreator(creator);
        factory.setIdleTimeout(IDLE_TIMEOUT);
    }
}
