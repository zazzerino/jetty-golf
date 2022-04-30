package com.kdp.golf.game.db;

import com.kdp.golf.DatabaseConnection;
import com.kdp.golf.game.model.Game;
import com.kdp.golf.user.db.UserDao;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class GameRepository
{
    private final UserDao userDao;
    private final PlayerDao playerDao;
    private final GameDao gameDao;

    public GameRepository(DatabaseConnection dbConn)
    {
        userDao = dbConn.jdbi().onDemand(UserDao.class);
        playerDao = dbConn.jdbi().onDemand(PlayerDao.class);
        gameDao = dbConn.jdbi().onDemand(GameDao.class);
    }

    public Optional<Game> findById(Long id)
    {
        var players = playerDao.findPlayers(id)
                .stream()
                .map(row -> {
                    var name = userDao.findName(row.person()).orElseThrow();
                    return row.toPlayer(name);
                })
                .collect(Collectors.toCollection(ArrayList::new));

        var gameRow = gameDao.findById(id).orElseThrow();
        var game = gameRow.toGame(players);
        return Optional.of(game);
    }

    public void create(Game g)
    {
        assert(g.players().size() == 1);

        var player = g.players().get(0);
        var playerRow = PlayerRow.from(g.id(), player);
        playerDao.create(playerRow);

        var gameRow = GameRow.from(g);
        gameDao.create(gameRow);
    }

    public void update(Game g)
    {
        var gameRow = GameRow.from(g);
        gameDao.update(gameRow);
    }

    public void delete(Game g)
    {
        gameDao.delete(g.id());
    }
}
