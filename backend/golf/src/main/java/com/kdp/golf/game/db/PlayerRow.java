package com.kdp.golf.game.db;

import com.kdp.golf.Lib;
import com.kdp.golf.game.model.Hand;
import com.kdp.golf.game.model.Player;
import com.kdp.golf.game.model.card.Card;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a row in the `player` table.
 */
public record PlayerRow(Long game,
                        Long person,
                        List<String> cards,
                        List<Integer> uncovered,
                        @Nullable String heldCard)
{
    public static PlayerRow from(Long gameId, Player p)
    {
        var cards = p.hand()
                .cards()
                .stream()
                .map(Card::name)
                .toList();

        var uncovered = p.hand().uncovered().stream().toList();

        var heldCard = p.heldCard()
                .map(Card::name)
                .orElse(null);

        return new PlayerRow(gameId, p.id(), cards, uncovered, heldCard);
    }

    public Player toPlayer(String name)
    {
        var cards = this.cards.stream()
                .map(Card::from)
                .collect(Collectors.toCollection(ArrayList::new));

        var hand = new Hand(cards, new HashSet<>(uncovered));
        var card = heldCard != null ? Card.from(heldCard) : null;
        return new Player(person, name, hand, card);
    }

    public static class Mapper implements RowMapper<PlayerRow>
    {
        @Override
        public PlayerRow map(ResultSet rs, StatementContext ctx) throws SQLException
        {
            var game = rs.getLong("game");
            var person = rs.getLong("person");

            var cards = Arrays.asList(
                    (String[]) rs.getArray("cards").getArray());

            var uncovered = Arrays.asList(
                    (Integer []) rs.getArray("uncovered").getArray());

            var heldCard = rs.getString("held_card");
            return new PlayerRow(game, person, cards, uncovered, heldCard);
        }
    }
}
