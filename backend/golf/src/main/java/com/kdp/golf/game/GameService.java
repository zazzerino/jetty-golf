package com.kdp.golf.game;

import com.kdp.golf.game.db.GameRepository;
import com.kdp.golf.game.model.Game;
import com.kdp.golf.game.model.Player;
import com.kdp.golf.user.User;
import org.jdbi.v3.sqlobject.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class GameService
{
    private final GameRepository gameRepository;
    private final Logger log = LoggerFactory.getLogger(GameController.class);

    public GameService(GameRepository gameRepository)
    {
        this.gameRepository = gameRepository;
    }

    @Transaction
    public Game createGame(User user)
    {
        var player = Player.from(user);
        var gameNullId = Game.create(null, player); // the id will be generated by the database
        var game = gameRepository.create(gameNullId);
        log.info("game created: " + game);
        return game;
    }

    @Transaction
    public Game startGame(Long gameId, Long playerId)
    {
        var game = gameRepository.findById(gameId).orElseThrow();
        var playerIsHost = Objects.equals(playerId, game.hostId());

        if (!playerIsHost) {
            throw new IllegalStateException(
                    "user " + playerId + " attempted to start game but they aren't the host");
        }

        game.start();
        log.info("game started: " + game);
        gameRepository.update(game);
        return game;
    }
}
