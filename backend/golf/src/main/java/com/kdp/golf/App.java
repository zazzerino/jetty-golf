package com.kdp.golf;

import com.kdp.golf.game.GameController;
import com.kdp.golf.game.GameService;
import com.kdp.golf.game.db.GameRepository;
import com.kdp.golf.user.UserController;
import com.kdp.golf.user.UserService;
import com.kdp.golf.websocket.SocketServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.server.config.JettyWebSocketServletContainerInitializer;

public class App
{
    public static final int PORT = 8080;

    public static void main(String[] args) throws Exception
    {
        // connect to database and initialize tables
        var dbConnection = new DatabaseConnection();
        dbConnection.runSchema();

        // setup services and controllers
        var userService = new UserService(dbConnection);
        var userController = new UserController(userService);

        var gameService = new GameService(new GameRepository(dbConnection));
        var gameController = new GameController(userService, gameService);

        // create jetty server
        var server = new Server(PORT);
        var servlet = new SocketServlet(userController, gameController);

        // create jetty handler
        var handler = new ServletContextHandler(server, "/");
        handler.addServlet(new ServletHolder(servlet),"/ws");
        JettyWebSocketServletContainerInitializer.configure(handler, null);

        // start server and wait for connections
        server.setHandler(handler);
        server.start();
        server.join();
    }
}
