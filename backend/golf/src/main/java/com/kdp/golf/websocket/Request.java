package com.kdp.golf.websocket;

/**
 * An incoming websocket message.
 */
public interface Request
{
    Type type();
    Long sessionId();

    enum Type
    {
        UPDATE_NAME,
        CREATE_GAME,
        START_GAME,
        UNCOVER_EVENT,
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

    record UncoverEvent(Long sessionId, Long gameId, int handIndex) implements Request
    {
        @Override
        public Type type()
        {
            return Type.UNCOVER_EVENT;
        }
    }
}
