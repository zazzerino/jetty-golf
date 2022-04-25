package com.kdp.golf;

import com.kdp.golf.user.UserController;
import com.kdp.golf.user.UserService;
import com.kdp.golf.websocket.Sessions;
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
        // drop and create db tables
        var dbConnection = new DatabaseConnection();
        dbConnection.runSchema();

        // setup app services
        var sessions = new Sessions();
        var userService = new UserService(dbConnection);
        var userController = new UserController(userService);

        // create server and websocket servlet
        var server = new Server(PORT);
        var servlet = new SocketServlet(sessions, userService, userController);

        // create request handler
        var handler = new ServletContextHandler(server, "/");
        handler.addServlet(new ServletHolder(servlet),"/ws");
        JettyWebSocketServletContainerInitializer.configure(handler, null);
        server.setHandler(handler);

        // start server and wait for connections
        server.start();
        server.join();
    }
}
