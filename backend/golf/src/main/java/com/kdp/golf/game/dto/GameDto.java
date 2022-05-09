package com.kdp.golf.game.dto;

import com.kdp.golf.game.model.Game;
import com.kdp.golf.game.model.GameState;
import com.kdp.golf.game.model.card.Card;
import com.kdp.golf.game.model.card.CardLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * A Game data-transfer-object. Represents the game data that will be sent to a player.
 */
public record GameDto(Long id,
                      Long playerId,
                      List<String> tableCards,
                      List<PlayerDto> players,
                      Long hostId,
                      GameState state,
                      int turn,
                      Long nextPlayerId,
                      boolean isFinalTurn,
                      List<CardLocation> playableCards)
{
    public static GameDto from(Game game, Long userId) {
        var tableCards = game.tableCards()
                .stream()
                .map(Card::name)
                .toList();

        var playerCount = game.players().size();
        var handPositions = HandPosition.positions(playerCount);
        var playerOrder = game.playerOrderFrom(userId);
        var playerDtos = new ArrayList<PlayerDto>(playerCount);

        for (var i = 0; i < playerCount; i++) {
            var pid = playerOrder.get(i);
            var player = game.getPlayer(pid).orElseThrow();
            var handPos = handPositions.get(i);
            var playerDto = PlayerDto.from(player, handPos);
            playerDtos.add(playerDto);
        }

        var player = game.getPlayer(userId).orElseThrow();
        var playableCards = game.playableCards(player);

        return new GameDto(
                game.id(),
                userId,
                tableCards,
                playerDtos,
                game.hostId(),
                game.state(),
                game.turn(),
                game.nextPlayerId(),
                game.isFinalTurn(),
                playableCards);
    }
}
