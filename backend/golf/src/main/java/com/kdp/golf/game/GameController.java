package com.kdp.golf.game;

import com.kdp.golf.game.dto.GameDto;
import com.kdp.golf.user.UserService;
import com.kdp.golf.websocket.Request;
import com.kdp.golf.websocket.Response;

public class GameController
{
    private final UserService userService;
    private final GameService gameService;

    public GameController(UserService userService, GameService gameService)
    {
        this.userService = userService;
        this.gameService = gameService;
    }

    public Response.Game createGame(Request.CreateGame request)
    {
        var user = userService.findBySessionId(request.sessionId()).orElseThrow();
        var game = gameService.createGame(user);
        var gameDto = GameDto.from(game, user.id());
        return new Response.Game(gameDto);
    }

    public Response.Game startGame(Request.StartGame request)
    {
        var user = userService.findBySessionId(request.sessionId()).orElseThrow();
        var game = gameService.startGame(request.gameId(), user.id());
        var gameDto = GameDto.from(game, user.id());
        return new Response.Game(gameDto);
    }
}
