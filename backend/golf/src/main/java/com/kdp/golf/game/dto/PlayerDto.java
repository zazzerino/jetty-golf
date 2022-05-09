package com.kdp.golf.game.dto;

import com.kdp.golf.game.model.Player;
import com.kdp.golf.game.model.card.Card;

import javax.annotation.Nullable;
import java.util.List;

/**
 * A Player data-transfer-object. (The player data that will be sent to the client.)
 */
public record PlayerDto(Long id,
                        String name,
                        int score,
                        List<String> cards,
                        List<Integer> uncovered,
                        @Nullable String heldCard,
                        HandPosition handPosition)
{
    public static PlayerDto from(Player player, HandPosition handPos)
    {
        var cards = player.hand().cards()
                .stream()
                .map(Card::name)
                .toList();

        var uncovered = player.hand().uncovered().stream().toList();

        var heldCard = player.heldCard()
                .map(Card::name)
                .orElse(null);

        return new PlayerDto(
                player.id(),
                player.name(),
                player.hand().score(),
                cards,
                uncovered,
                heldCard,
                handPos);
    }
}
