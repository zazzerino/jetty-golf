package com.kdp.golf.user;

import com.kdp.golf.DatabaseConnection;
import org.jdbi.v3.sqlobject.transaction.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    static DatabaseConnection dbConn;
    static UserService userService;
    static Logger log = LoggerFactory.getLogger(UserServiceTest.class);

    @BeforeAll
    static void setUp() throws IOException {
        dbConn = new DatabaseConnection();
        dbConn.dropAndCreateSchema();
        userService = new UserService(dbConn);
    }

    @Test
    @Transaction
    void createAndFind() {
        var user = userService.createUser(0L);
        log.info("user: " + user);
        assertNotNull(user);

        var foundById = userService.findById(user.id()).orElseThrow();
        assertEquals(user, foundById);

        var foundBySessionId = userService.findBySessionId(user.sessionId()).orElseThrow();
        assertEquals(user, foundBySessionId);
    }
}