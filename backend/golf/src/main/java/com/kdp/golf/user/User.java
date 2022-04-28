package com.kdp.golf.user;

public record User(Long id,
                   String name,
                   Long sessionId)
{
    public static final String DEFAULT_NAME = "anon";
}
