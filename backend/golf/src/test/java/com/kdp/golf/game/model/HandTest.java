package com.kdp.golf.game.model;

import com.kdp.golf.game.model.card.Card;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HandTest
{
    Logger log = LoggerFactory.getLogger(HandTest.class);

    @Test
    void create()
    {
        var hand = Hand.empty();
        log.info(hand.toString());
        assertEquals(0, hand.cards().size());
        assertEquals(0, hand.uncovered().size());
    }

    @Test
    void uncover()
    {
        var hand = Hand.empty();
        hand.uncover(2);

        assertTrue(hand.uncovered()
                .stream()
                .allMatch(i -> i.equals(2)));
    }

    @Test
    void addCard()
    {
        var card = Card.from("AS");
        var hand = Hand.empty();
        hand.addCard(card);
        var firstHandCard = hand.cards().stream().findFirst().orElseThrow();
        assertEquals(card, firstHandCard);
    }

    @Test
    void swapCard()
    {
        var cards = Stream.of("2C", "3C", "4C", "5C", "6C", "7C")
                .map(Card::from)
                .collect(Collectors.toCollection(ArrayList::new));

        var hand = new Hand(cards, new HashSet<>());
        var newSwappedCard = Card.from("8C");
        var oldSwappedCard = hand.swapCard(newSwappedCard, 0);

        assertEquals(Card.from("2C"), oldSwappedCard);
        assertEquals(6, hand.cards().size());
    }

    @Test
    void equals()
    {
        var hand0 = Hand.empty();
        var hand1 = Hand.empty();
        assertEquals(hand0, hand0);
        assertEquals(hand0, hand1);
    }
}
