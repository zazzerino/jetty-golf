package com.kdp.golf.game;

import com.kdp.golf.IdGenerator;
import com.kdp.golf.game.db.GameRepository;
import com.kdp.golf.game.model.Game;
import com.kdp.golf.game.model.Player;
import com.kdp.golf.user.UserService;
import org.jdbi.v3.sqlobject.transaction.Transaction;

public class GameService
{
    private final IdGenerator idGenerator;
    private final UserService userService;
    private final GameRepository gameRepository;

    public GameService(IdGenerator idGenerator, UserService userService, GameRepository gameRepository)
    {
        this.idGenerator = idGenerator;
        this.userService = userService;
        this.gameRepository = gameRepository;
    }

    @Transaction
    public Game createGame(Long userId)
    {
        var user = userService.findById(userId).orElseThrow();
        var player = Player.from(user);
        var gameId = idGenerator.nextId();
        var game = Game.create(gameId, player);
        gameRepository.create(game);
        return game;
    }
}
