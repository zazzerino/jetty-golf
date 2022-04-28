package com.kdp.golf.game.model;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class GameTest
{
    Logger log = LoggerFactory.getLogger(GameTest.class);

    @Test
    void create()
    {
        var host = Player.create(0L, "Charlie");
        var game = Game.create(0L, host);
        assertEquals(1, game.players().size());
        log.info(game.toString());
    }

    @Test
    public void equals()
    {
        var gameId = 0L;
        var host = Player.create(0L, "Charlie");
        var game = Game.create(0L, host);
        log.info(game.toString());
        assertEquals(game, game);

        var game2 = Game.create(0L, host);
        log.info(game2.toString());
        assertEquals(game, game2);
    }

    @Test
    public void addPlayer()
    {
        var host = Player.create(0L, "Charlie");
        var game = Game.create(0L, host);
        assertEquals(1, game.players().size());

        var player = Player.create(1L, "Dee");
        game.addPlayer(player);
        log.info(game.toString());
        assertEquals(2, game.players().size());
    }

    @Test
    public void start()
    {
        var host = Player.create(0L, "Charlie");
        var game = Game.create(0L, host);
        game.start();
        log.info(game.toString());
        assertEquals(GameState.UNCOVER_TWO, game.state());

        var player = game.players().stream()
                .filter(p -> p.id().equals(game.nextPlayerId()))
                .findFirst()
                .orElseThrow();

        assertNotNull(player);
        assertFalse(player.hand().cards().isEmpty());
    }

//    @Test
//    void uncoverCard() {
//        var host = Player.create(0L, "Artemis");
//        var game = Game.create(0L, host);
//        game.start();
//        game.uncover(host, 0);
//        System.out.println(game);
//    }
}
