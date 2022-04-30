package com.kdp.golf.game;

import com.kdp.golf.DatabaseConnection;
import com.kdp.golf.IdGenerator;
import com.kdp.golf.game.db.GameRepository;
import com.kdp.golf.game.model.Game;
import com.kdp.golf.game.model.Player;
import com.kdp.golf.user.User;
import com.kdp.golf.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest
{
    static IdGenerator idGen;
    static DatabaseConnection dbConn;
    static UserService userService;
    static GameService gameService;
    static Logger log = LoggerFactory.getLogger(GameServiceTest.class);

    @BeforeEach
    static void setUp()
    {
        idGen = new IdGenerator();
        dbConn = new DatabaseConnection();
        userService = new UserService(dbConn, idGen);
        gameService = new GameService(idGen, userService, new GameRepository(dbConn));
    }

//    @Test
//    void createGame()
//    {
//        var user = new User(idGen.nextId(), "Bob", idGen.nextId());
//        userService.createUser(idGen.nextId());
//        var player = Player.from(user);
//        var game = Game.create(idGen.nextId(), player);
//    }
}
