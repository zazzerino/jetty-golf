package com.kdp.golf;

import com.kdp.golf.user.UserService;
import com.kdp.golf.websocket.WebSocketServer;

public class App {
    public static void main(String[] args) throws Exception {
        var dbConnection = new DatabaseConnection();
        dbConnection.rebuildSchema();

        var userService = new UserService(dbConnection);

        var server = new WebSocketServer();
        server.run();
    }
}
