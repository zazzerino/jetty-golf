package com.kdp.golf.websocket;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import org.eclipse.jetty.websocket.api.Session;

public class Sessions
{
    private final BiMap<Long, Session> sessions = Maps.synchronizedBiMap(HashBiMap.create());

    public void add(Long sessionId, Session session)
    {
        synchronized (sessions) {
            sessions.put(sessionId, session);
        }
    }

    public void remove(Long sessionId)
    {
        synchronized (sessions) {
            sessions.remove(sessionId);
        }
    }

    public Session getSession(Long sessionId)
    {
        return sessions.get(sessionId);
    }

    public Long getSessionId(Session session)
    {
        return sessions.inverse().get(session);
    }
}
