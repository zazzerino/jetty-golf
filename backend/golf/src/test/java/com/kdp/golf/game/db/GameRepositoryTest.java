package com.kdp.golf.game.db;

import com.kdp.golf.DatabaseConnection;
import com.kdp.golf.game.model.Game;
import com.kdp.golf.game.model.Player;
import com.kdp.golf.user.User;
import com.kdp.golf.user.UserService;
import com.kdp.golf.user.db.UserRow;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

class GameRepositoryTest
{
    static DatabaseConnection dbConn;
    static UserService userService;
    static GameRepository gameRepo;
    static AtomicLong nextId = new AtomicLong();
    static Logger log = LoggerFactory.getLogger(GameRepository.class);

    @BeforeAll
    static void setUp() throws IOException
    {
        dbConn = new DatabaseConnection();
        dbConn.runSchema();
        userService = new UserService(dbConn);
        gameRepo = new GameRepository(dbConn);
    }

    @Test
    void createAndFind()
    {
        var sessionId = nextId.getAndIncrement();
        var user = userService.createUser(sessionId);

        var player = Player.from(user);
        var game = Game.create(null, player);
        game = gameRepo.create(game);
        log.info(game.toString());

        var foundGame = gameRepo.findById(game.id()).orElseThrow();
        log.info(foundGame.toString());
        assertEquals(game, foundGame);
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