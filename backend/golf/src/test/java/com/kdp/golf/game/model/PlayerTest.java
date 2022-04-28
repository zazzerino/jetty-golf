package com.kdp.golf.game.model;

import com.kdp.golf.game.model.card.Card;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest
{
    Logger log = LoggerFactory.getLogger(PlayerTest.class);

    @Test
    void giveCard()
    {
        var player = Player.create(0L, "Toots");
        assertEquals(0, player.handSize());
        log.info(player.toString());

        var card = Card.from("AS");
        player.giveCard(card);
        assertEquals(1, player.handSize());
    }

    @Test
    void uncover()
    {
        var card = Card.from("AS");
        var player = Player.create(0L, "Mariah");
        player.giveCard(card);
        player.uncoverCard(0);

        assertTrue(player.hand()
                .uncovered()
                .contains(0));
    }

    @Test
    void hold()
    {
        var card = Card.from("KH");
        var player = Player.create(0L, "Toots");
        player.holdCard(card);
        var heldCard = player.heldCard().orElseThrow();
        assertEquals(card, heldCard);
    }

    @Test
    void discard()
    {
        var card = Card.from("KH");
        var player = Player.create(0L, "Mariah");
        player.holdCard(card);

        assertTrue(player.heldCard().isPresent());
        assertEquals(card, player.heldCard().get());

        var discardedCard = player.discard();
        assertTrue(player.heldCard().isEmpty());
        assertEquals(card, discardedCard);
    }

    @Test
    void equals()
    {
        var player0 = Player.create(0L, "Toots");
        var player1 = Player.create(0L, "Toots");
        assertEquals(player0, player1);
    }
}
