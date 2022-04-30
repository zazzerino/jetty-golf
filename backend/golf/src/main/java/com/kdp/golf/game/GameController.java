package com.kdp.golf.game;

import com.kdp.golf.game.dto.GameDto;
import com.kdp.golf.user.UserService;
import com.kdp.golf.websocket.Request;
import com.kdp.golf.websocket.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameController
{
    private final UserService userService;
    private final GameService gameService;
    private final Logger log = LoggerFactory.getLogger(GameController.class);

    public GameController(UserService userService, GameService gameService)
    {
        this.userService = userService;
        this.gameService = gameService;
    }

    public Response.Game createGame(Request.CreateGame req)
    {
        var user = userService.findBySessionId(req.sessionId()).orElseThrow();
        var game = gameService.createGame(user.id());
        var gameDto = GameDto.from(game, user.id());
        return new Response.Game(gameDto);
    }
}
