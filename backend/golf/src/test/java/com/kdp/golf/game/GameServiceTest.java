package com.kdp.golf.game;

import com.kdp.golf.DatabaseConnection;
import com.kdp.golf.game.db.GameRepository;
import com.kdp.golf.game.model.Game;
import com.kdp.golf.game.model.Player;
import com.kdp.golf.user.User;
import com.kdp.golf.user.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

class GameServiceTest
{
    static DatabaseConnection dbConn;
    static UserService userService;
    static GameService gameService;
    static Logger log = LoggerFactory.getLogger(GameServiceTest.class);

    @BeforeAll
    static void setUp() throws IOException
    {
        dbConn = new DatabaseConnection();
        dbConn.runSchema();
        userService = new UserService(dbConn);
        gameService = new GameService(userService, new GameRepository(dbConn));
    }

    @Test
    void createGame()
    {
//        var userId = idGen.nextId();
//        var sessionId = idGen.nextId();
//        var user = new User(userId, "Bob", sessionId);
//        userService.createUser(sessionId);
//
//        var player = Player.from(user);
//        var gameId = idGen.nextId();
//        var game = Game.create(gameId, player);
//        log.info(game.toString());
    }
}
