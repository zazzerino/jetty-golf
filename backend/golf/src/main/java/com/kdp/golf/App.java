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
        // setup database
        var dbConnection = new DatabaseConnection();
        dbConnection.runSchema();

        // setup services
        var idGenerator = new IdGenerator(0);
        var userService = new UserService(dbConnection, idGenerator);
        var userController = new UserController(userService);

        // create server
        var server = new Server(PORT);
        var handler = new ServletContextHandler(server, "/");
        var servlet = new SocketServlet(idGenerator, new Sessions(), userService, userController);

        handler.addServlet(new ServletHolder(servlet),"/ws");
        JettyWebSocketServletContainerInitializer.configure(handler, null);
        server.setHandler(handler);

        // start server and wait for connections
        server.start();
        server.join();
    }
}
