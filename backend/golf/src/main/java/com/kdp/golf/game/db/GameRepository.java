package com.kdp.golf.game.db;

import com.kdp.golf.DatabaseConnection;
import com.kdp.golf.game.model.Game;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Uses the `player` and `game` tables to provide crud operations for game objects.
 */
public class GameRepository
{
    private final PlayerDao playerDao;
    private final GameDao gameDao;

    public GameRepository(DatabaseConnection dbConn)
    {
        playerDao = dbConn.jdbi().onDemand(PlayerDao.class);
        gameDao = dbConn.jdbi().onDemand(GameDao.class);
    }

    public Optional<Game> findById(Long id)
    {
        var players = playerDao.findPlayers(id)
                .stream()
                .map(p -> {
                    var name = playerDao.findName(p.person()).orElseThrow();
                    return p.toPlayer(name);
                })
                .collect(Collectors.toCollection(ArrayList::new));

        var gameRow = gameDao.findById(id).orElseThrow();
        var game = gameRow.toGame(players);
        return Optional.of(game);
    }

    public Game create(Game game)
    {
        assert(game.players().size() == 1);

        var gameRow = GameRow.from(game);
        var gameId = gameDao.insert(gameRow);

        var player = game.players().get(0);
        var playerRow = PlayerRow.from(gameId, player);
        playerDao.insert(playerRow);

        return game.withId(gameId);
    }

    public void update(Game game)
    {
        var gameId = game.id();
        var gameRow = GameRow.from(game);
        gameDao.update(gameRow);

        var existingPlayerIds = playerDao.findPlayers(gameId)
                .stream()
                .map(PlayerRow::person)
                .toList();

        for (var player : game.players()) {
            var playerRow = PlayerRow.from(gameId, player);
            var playerExists = existingPlayerIds.contains(player.id());

            if (playerExists) {
                playerDao.update(playerRow);
            } else {
                playerDao.insert(playerRow);
            }
        }
    }

    public void delete(Game game)
    {
        for (var player : game.players()) {
            playerDao.findById(player.id())
                    .ifPresent(row -> playerDao.delete(row.person()));
        }

        gameDao.delete(game.id());
    }
}
