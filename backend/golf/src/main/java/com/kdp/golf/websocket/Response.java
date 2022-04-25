package com.kdp.golf.websocket;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface Response
{
    @JsonProperty
    Type type();

    enum Type
    {
        USER,
        GAME,
    }

    record User(com.kdp.golf.user.User.Dto user) implements Response
    {
        @Override
        public Type type()
        {
            return Type.USER;
        }
    }
}
