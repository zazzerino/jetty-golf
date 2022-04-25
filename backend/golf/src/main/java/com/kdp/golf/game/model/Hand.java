package com.kdp.golf.game.model;

import com.kdp.golf.game.model.card.Card;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Hand
{
    private final List<Card> cards;
    private final Set<Integer> uncovered; // the index of each card that's been uncovered

    public static final int HAND_SIZE = 6;

    public Hand(List<Card> cards, Set<Integer> uncovered)
    {
        this.cards = cards;
        this.uncovered = uncovered;
    }

    public static Hand empty()
    {
        return new Hand(
                Collections.emptyList(),
                Collections.emptySet());
    }

    public void uncover(int index)
    {
        uncovered.add(index);
    }

    public void uncoverAll()
    {
        uncovered.addAll(Set.of(0, 1, 2, 3, 4, 5));
    }

    public boolean allCardsUncovered()
    {
        return uncovered.size() == HAND_SIZE;
    }

    public void addCard(Card card)
    {
        if (cards.size() >= HAND_SIZE) {
            throw new IllegalStateException("hand can only hold a maximum of six cards");
        }

        cards.add(card);
    }

    public Card swapCard(Card newCard, int index)
    {
        var oldCard = cards.get(index);
        cards.set(index, newCard);
        return oldCard;
    }

    // TODO: implement this
    public int score()
    {
        return 0;
    }

    public List<Card> cards()
    {
        return cards;
    }

    public Set<Integer> uncovered()
    {
        return uncovered;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hand hand = (Hand) o;
        return cards.equals(hand.cards) && uncovered.equals(hand.uncovered);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(cards, uncovered);
    }

    @Override
    public String toString()
    {
        return "Hand{" +
                "cards=" + cards +
                ", uncovered=" + uncovered +
                '}';
    }
}
