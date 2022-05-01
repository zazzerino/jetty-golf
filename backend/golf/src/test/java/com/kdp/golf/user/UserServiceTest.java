package com.kdp.golf.user;

import com.kdp.golf.DatabaseConnection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest
{
    static DatabaseConnection dbConn;
    static UserService userService;
    static AtomicLong nextId = new AtomicLong();
    static Logger log = LoggerFactory.getLogger(UserServiceTest.class);

    @BeforeAll
    static void setUp() throws IOException
    {
        dbConn = new DatabaseConnection();
        dbConn.runSchema();
        userService = new UserService(dbConn);
    }

    @Test
    void createAndFind()
    {
        var sessionId = nextId.getAndIncrement();
        var user = userService.createUser(sessionId);
        assertNotNull(user);
        log.info("user: " + user);

        var foundById = userService.findById(user.id()).orElseThrow();
        log.info("found: " + foundById);
        assertEquals(user, foundById);

        var foundBySessionId = userService.findBySessionId(user.sessionId()).orElseThrow();
        assertEquals(user, foundBySessionId);
    }

    @Test
    void updateName()
    {
        var name = "Brian Goetz";
        var sessionId = nextId.getAndIncrement();
        var user = userService.createUser(sessionId);
        var user2 = userService.updateName(user.sessionId(), name);

        var found = userService.findById(user.id()).orElseThrow();
        assertEquals(name, found.name());
        assertEquals(user2, found);
    }
}
