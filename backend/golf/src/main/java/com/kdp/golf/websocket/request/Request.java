package com.kdp.golf.websocket.request;

public interface Request {
    Type type();

    enum Type {
        UPDATE_NAME,
    }
}
