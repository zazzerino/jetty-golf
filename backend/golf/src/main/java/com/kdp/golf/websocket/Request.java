package com.kdp.golf.websocket;

public interface Request
{
    Type type();
    Long sessionId();

    enum Type
    {
        CREATE_USER,
        UPDATE_NAME,
        CREATE_GAME,
        START_GAME,
    }

    record CreateUser(Long sessionId) implements Request
    {
        @Override
        public Type type()
        {
            return Type.CREATE_USER;
        }
    }

    record UpdateName(Long sessionId, String name) implements Request
    {
        @Override
        public Type type()
        {
            return Type.UPDATE_NAME;
        }
    }

    record CreateGame(Long sessionId) implements Request
    {
        @Override
        public Type type()
        {
            return Type.CREATE_GAME;
        }
    }

    record StartGame(Long sessionId, Long gameId) implements Request
    {
        @Override
        public Type type()
        {
            return Type.START_GAME;
        }
    }
}
