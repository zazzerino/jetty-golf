package com.kdp.golf.websocket;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import org.eclipse.jetty.websocket.api.Session;

public class Sessions {
    private final BiMap<Long, Session> sessionMap = Maps.synchronizedBiMap(HashBiMap.create());

    public void add(Long id, Session session) {
        synchronized (sessionMap) {
            sessionMap.put(id, session);
        }
    }

    public void remove(Long id) {
        synchronized (sessionMap) {
            sessionMap.remove(id);
        }
    }

    public Session getSession(Long id) {
        return sessionMap.get(id);
    }

    public Long getSessionId(Session session) {
        return sessionMap.inverse().get(session);
    }
}
