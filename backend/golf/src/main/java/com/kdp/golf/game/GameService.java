package com.kdp.golf.game;

import com.kdp.golf.game.db.GameRepository;
import com.kdp.golf.game.model.Game;

import java.util.Optional;

public class GameService
{
    private final GameRepository gameRepo;

    public GameService(GameRepository gameRepo)
    {
        this.gameRepo = gameRepo;
    }

    public Optional<Game> findById(Long gameId)
    {
        return Optional.empty();
    }

//    public Game create()
}
