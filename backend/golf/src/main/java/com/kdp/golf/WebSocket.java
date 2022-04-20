package com.kdp.golf;

import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/ws")
public class WebSocket {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("session opened");
    }
}
