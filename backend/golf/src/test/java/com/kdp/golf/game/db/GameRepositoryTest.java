package com.kdp.golf.game.db;

import com.kdp.golf.DatabaseConnection;
import com.kdp.golf.game.model.Game;
import com.kdp.golf.game.model.Player;
import com.kdp.golf.user.User;
import com.kdp.golf.user.db.UserDao;
import com.kdp.golf.user.db.UserRow;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GameRepositoryTest
{
    static DatabaseConnection dbConn;
    static UserDao userDao;
    static GameRepository gameRepo;
    static Logger log = LoggerFactory.getLogger(GameRepository.class);

    @BeforeAll
    static void setUp() throws IOException
    {
        dbConn = new DatabaseConnection();
        dbConn.runSchema();
        userDao = dbConn.jdbi().onDemand(UserDao.class);
        gameRepo = new GameRepository(dbConn);
    }

    @Test
    void createAndFind()
    {
//        var user = new User(idGen.nextId(), "Alice", idGen.nextId());
//        userDao.create(UserRow.from(user));
//
//        var player = Player.from(user);
//        var game = Game.create(idGen.nextId(), player);
//        log.info(game.toString());
//        gameRepo.create(game);
//
//        var foundGame = gameRepo.findById(game.id()).orElseThrow();
//        log.info(foundGame.toString());
//        assertEquals(game, foundGame);
    }

//    @Test
//    void update()
//    {
//    }
//
//    @Test
//    void delete()
//    {
//    }
}