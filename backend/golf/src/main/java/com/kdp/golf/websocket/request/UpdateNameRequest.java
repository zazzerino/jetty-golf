package com.kdp.golf.websocket.request;

public record UpdateNameRequest(Long userId, String name) implements Request {
    @Override
    public Type type() {
        return Type.UPDATE_NAME;
    }
}
