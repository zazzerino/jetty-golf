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
        var dbConnection = new DatabaseConnection();
        dbConnection.dropAndCreateSchema();

        var userService = new UserService(dbConnection);
        var userController = new UserController(userService);

        var server = new Server(PORT);
        var handler = new ServletContextHandler(server, "/");
        JettyWebSocketServletContainerInitializer.configure(handler, null);

        var sessions = new Sessions();
        var servlet = new SocketServlet(sessions, userService, userController);
        handler.addServlet(new ServletHolder(servlet),"/ws");

        server.setHandler(handler);
        server.start();
        server.join();
    }
}
