package com.kdp.golf.game.db;

import com.kdp.golf.DatabaseConnection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameDaoTest
{
    static DatabaseConnection dbConn;
    static GameDao gameDao;
    static Logger log = LoggerFactory.getLogger(GameDaoTest.class);

    @BeforeAll
    static void setUp() throws IOException
    {
        dbConn = new DatabaseConnection();
        dbConn.runSchema();
        gameDao = dbConn.jdbi().onDemand(GameDao.class);
    }

    @Test
    void create()
    {
        var gameRow = new GameRow(
                0L, List.of(), List.of(), List.of(), 0L, "INIT", 0, 0L, false);
        log.info(gameRow.toString());
        gameDao.create(gameRow);
    }
}
