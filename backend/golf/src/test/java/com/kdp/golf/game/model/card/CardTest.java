package com.kdp.golf.game.model.card;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest
{
    @Test
    void from()
    {
        var rank = Rank.from("2").orElseThrow();
        assertEquals(Rank.TWO, rank);

        var suit = Suit.from("D").orElseThrow();
        assertEquals(Suit.DIAMONDS, suit);

        var card = Card.from("JH");
        assertEquals(new Card(Rank.JACK, Suit.HEARTS), card);
    }

    @Test
    void equals()
    {
        var card0 = Card.from("AS");
        var card1 = Card.from("AS");

        assertEquals(card0, card0);
        assertEquals(card1, card1);
    }
}
