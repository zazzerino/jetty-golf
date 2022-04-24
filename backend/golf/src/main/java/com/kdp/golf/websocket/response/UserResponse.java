package com.kdp.golf.websocket.response;

import com.kdp.golf.user.User;

public record UserResponse(User.Dto user) implements Response {
    @Override
    public Type type() {
        return Type.USER;
    }
}
