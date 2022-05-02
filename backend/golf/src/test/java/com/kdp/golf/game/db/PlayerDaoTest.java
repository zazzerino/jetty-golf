package com.kdp.golf.game.db;

import com.kdp.golf.DatabaseConnection;
import com.kdp.golf.game.model.Player;
import com.kdp.golf.user.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

class PlayerDaoTest
{
    static DatabaseConnection dbConn;
    static UserService userService;
    static PlayerDao playerDao;
    static AtomicLong nextId = new AtomicLong();
    static Logger log = LoggerFactory.getLogger(PlayerDaoTest.class);

    @BeforeAll
    static void setUp() throws IOException
    {
        dbConn = new DatabaseConnection();
        dbConn.runSchema();
        userService = new UserService(dbConn);
        playerDao = dbConn.jdbi().onDemand(PlayerDao.class);
    }

    @Test
    void createAndFind()
    {
        var sessionId = nextId.getAndIncrement();
        var user = userService.createUser(sessionId);
        var player = Player.from(user);
        var gameId = nextId.getAndIncrement();

        var playerRow = PlayerRow.from(gameId, player);
        log.info("player row: " + playerRow);

        playerDao.insert(playerRow);

        var foundPlayer = playerDao.findById(player.id()).orElseThrow();
        assertEquals(playerRow, foundPlayer);
    }
}
