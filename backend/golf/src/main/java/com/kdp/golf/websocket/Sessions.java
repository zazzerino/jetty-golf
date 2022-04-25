package com.kdp.golf.websocket;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import org.eclipse.jetty.websocket.api.Session;

public class Sessions
{
    private final BiMap<Long, Session> sessions = Maps.synchronizedBiMap(HashBiMap.create());

    public void add(Long id, Session session)
    {
        synchronized (sessions) {
            sessions.put(id, session);
        }
    }

    public void remove(Long id)
    {
        synchronized (sessions) {
            sessions.remove(id);
        }
    }

    public Session getSession(Long id)
    {
        return sessions.get(id);
    }

    public Long getSessionId(Session session)
    {
        return sessions.inverse().get(session);
    }
}
