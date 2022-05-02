package com.kdp.golf.game.db;

import com.kdp.golf.game.model.Deck;
import com.kdp.golf.game.model.Game;
import com.kdp.golf.game.model.GameState;
import com.kdp.golf.game.model.Player;
import com.kdp.golf.game.model.card.Card;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A row in the `game` table.
 */
public record GameRow(Long id,
                      List<String> deck,
                      List<String> tableCards,
                      List<Long> players,
                      Long host,
                      String state,
                      int turn,
                      Long nextPlayer,
                      boolean finalTurn)
{
    public static GameRow from(Game g) {
        var deck = g.deck().cards()
                .stream()
                .map(Card::name)
                .toList();

        var tableCards = g.tableCards()
                .stream()
                .map(Card::name)
                .toList();

        var players = g.players()
                .stream()
                .map(Player::id)
                .toList();

        return new GameRow(
                g.id(),
                deck,
                tableCards,
                players,
                g.hostId(),
                g.state().toString(),
                g.turn(),
                g.nextPlayerId(),
                g.isFinalTurn());
    }

    public Game toGame(List<Player> players) {
        var state = GameState.valueOf(state());

        var deck = new Deck(this.deck.stream()
                .map(Card::from)
                .collect(Collectors.toCollection(ArrayDeque::new)));

        var tableCards = tableCards().stream()
                .map(Card::from)
                .collect(Collectors.toCollection(ArrayDeque::new));

        return new Game(id, deck, tableCards, players, host, state, turn, nextPlayer, finalTurn);
    }

    public static class Mapper implements RowMapper<GameRow>
    {
        @Override
        public GameRow map(ResultSet rs, StatementContext ctx) throws SQLException
        {
            var id = rs.getLong("id");
            var host = rs.getLong("host");
            var state = rs.getString("state");
            var turn = rs.getInt("turn");
            var nextPlayer = rs.getLong("next_player");
            var finalTurn = rs.getBoolean("final_turn");

            var deck = Arrays.asList(
                    (String []) rs.getArray("deck").getArray());

            var tableCards = Arrays.asList(
                    (String []) rs.getArray("table_cards").getArray());

            var players = Arrays.asList(
                    (Long []) rs.getArray("players").getArray());

            return new GameRow(id, deck, tableCards, players, host, state, turn, nextPlayer, finalTurn);
        }
    }
}
