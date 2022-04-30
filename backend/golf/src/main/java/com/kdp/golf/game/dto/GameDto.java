package com.kdp.golf.game.dto;

import com.kdp.golf.game.model.Game;
import com.kdp.golf.game.model.GameState;
import com.kdp.golf.game.model.card.Card;
import com.kdp.golf.game.model.card.CardLocation;

import java.util.Collection;

public record GameDto(Long userId,
                      Long gameId,
                      Collection<Card> tableCards,
                      Collection<PlayerDto> players,
                      Long hostId,
                      GameState state,
                      int turn,
                      boolean finalTurn,
                      Long nextPlayerId,
                      boolean isFinalTurn,
                      Collection<CardLocation> playableCards)
{
    public static GameDto from(Game g, Long userId)
    {
        var player = g.getPlayer(userId).orElseThrow();
        var playableCards = g.playableCards(player);

//        var playerCount = g.players().size();
//        var positions = HandPosition.positions(playerCount);
//        var playerOrder = g.playerOrderFrom(userId);
//        var playerDtos = new ArrayList<PlayerDto>();
//
//        for (var i = 0; i < playerCount; i++) {
//            var gameId = playerOrder.get(i);
//            var player = g.getPlayer(gameId).orElseThrow();
//            var handPos = positions.get(i);
//            var dto = PlayerDto.from(player, handPos);
//            playerDtos.add(dto);
//        }

//        return new GameDto(
//                g.gameId(),
//                userId,
//                g.hostId(),
//                g.state(),
//                g.turn(),
//                g.isFinalTurn(),
//                g.playerTurn(),
//                g.tableCards(),
//                playableCards,
//                playerDtos
//        );
        return null;
    }
}
