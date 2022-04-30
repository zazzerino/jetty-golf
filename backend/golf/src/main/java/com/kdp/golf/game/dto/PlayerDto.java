package com.kdp.golf.game.dto;

import com.kdp.golf.game.model.Player;
import com.kdp.golf.game.model.card.Card;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

public record PlayerDto(Long id,
                        String name,
                        int score,
                        Collection<Card> cards,
                        Collection<Integer> uncovered,
                        @Nullable Card heldCard,
                        HandPosition handPosition)
{
    public static PlayerDto from(Player p, HandPosition handPos)
    {
        return new PlayerDto(
                p.id(),
                p.name(),
                p.hand().score(),
                p.hand().cards(),
                p.hand().uncovered(),
                p.heldCard().orElse(null),
                handPos);
    }

    enum HandPosition
    {
        BOTTOM,
        LEFT,
        TOP,
        RIGHT;

        public static List<HandPosition> positions(int playerCount)
        {
            return switch (playerCount) {
                case 1 -> List.of(BOTTOM);
                case 2 -> List.of(BOTTOM, TOP);
                case 3 -> List.of(BOTTOM, LEFT, RIGHT);
                case 4 -> List.of(BOTTOM, LEFT, TOP, RIGHT);
                default -> throw new IllegalStateException(
                        "playerCount must be between 1 and Game.MAX_PLAYERS: " + playerCount);
            };
        }
    }
}
