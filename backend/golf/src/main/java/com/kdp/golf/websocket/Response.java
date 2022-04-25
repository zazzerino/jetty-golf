package com.kdp.golf.websocket;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kdp.golf.user.User;

public interface Response
{
    @JsonProperty
    Type type();

    enum Type
    {
        USER,
        GAME,
    }

    record UserResponse(User.Dto user) implements Response
    {
        @Override
        public Type type()
        {
            return Type.USER;
        }
    }
}
