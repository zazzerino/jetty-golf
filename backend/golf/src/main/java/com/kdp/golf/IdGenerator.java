package com.kdp.golf;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator
{
    private final AtomicLong nextId;

    public IdGenerator()
    {
        nextId = new AtomicLong(0);
    }

    public IdGenerator(long startVal)
    {
        nextId = new AtomicLong(startVal);
    }

    public long nextId()
    {
        return nextId.getAndIncrement();
    }
}
