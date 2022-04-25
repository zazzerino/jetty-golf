package com.kdp.golf.websocket;

public interface Request
{
    Type type();

    enum Type
    {
        UPDATE_NAME,
    }

    record UpdateName(Long userId, String name) implements Request
    {
        @Override
        public Type type()
        {
            return Type.UPDATE_NAME;
        }
    }
}
