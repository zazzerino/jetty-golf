package com.kdp.golf.game.db;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.BindMethods;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Optional;

/**
 * A GameRow data access object.
 */
public interface GameDao
{
    @SqlQuery("SELECT * FROM game WHERE id = ?")
    @RegisterRowMapper(GameRow.Mapper.class)
    Optional<GameRow> findById(Long id);

    @SqlUpdate("""
    INSERT INTO game
    (deck, table_cards, players, host, state, turn, next_player, final_turn)
    VALUES (:deck, :tableCards, :players, :host, :state, :turn, :nextPlayer, :finalTurn)""")
    @GetGeneratedKeys
    Long insert(@BindMethods GameRow g);

    @SqlUpdate("""
    UPDATE game
    SET
    deck = :deck, table_cards = :tableCards, players = :players, host = :host,
    state = :state, turn = :turn, next_player = :nextPlayer, final_turn = :finalTurn
    WHERE id = :id""")
    void update(@BindMethods GameRow g);

    @SqlUpdate("DELETE FROM game WHERE id = ?")
    void delete(Long id);
}
