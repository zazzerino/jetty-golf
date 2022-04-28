package com.kdp.golf.game.db;

import com.kdp.golf.DatabaseConnection;
import com.kdp.golf.game.model.Game;
import com.kdp.golf.game.model.Player;
import com.kdp.golf.user.User;

public class GameRepository
{
    private final PlayerDao playerDao;
    private final GameDao gameDao;

    public GameRepository(DatabaseConnection dbConn)
    {
        playerDao = dbConn.jdbi().onDemand(PlayerDao.class);
        gameDao = dbConn.jdbi().onDemand(GameDao.class);
    }

    public Game create(User u)
    {
        var player = Player.from(u);
//        var game = Game.cre
        return null;
    }

    public void update(Game g)
    {
    }

    public void delete(Game g)
    {
    }
}
