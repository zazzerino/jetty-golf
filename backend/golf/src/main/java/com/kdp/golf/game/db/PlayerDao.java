package com.kdp.golf.game.db;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.BindMethods;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

/**
 * An interface for doing crud with PlayerRow objects.
 */
public interface PlayerDao
{
    @SqlQuery("SELECT * FROM player WHERE person = ?")
    @RegisterRowMapper(PlayerRow.Mapper.class)
    Optional<PlayerRow> findById(Long id);

    @SqlQuery("""
        SELECT * FROM player
        JOIN person ON player.person = person.id
        WHERE game = ?""")
    @RegisterRowMapper(PlayerRow.Mapper.class)
    List<PlayerRow> findPlayers(Long gameId);

    @SqlQuery("""
    SELECT p.name FROM person p
    WHERE p.id = ?""")
    Optional<String> findName(Long playerId);

    @SqlUpdate("""
        INSERT INTO player
        (game, person, cards, uncovered, held_card)
        VALUES
        (:game, :person, :cards, :uncovered, :heldCard)""")
    void create(@BindMethods PlayerRow playerRow);

    @SqlUpdate("""
        UPDATE player
        SET cards = :cards, uncovered = :uncovered, held_card = :heldCard
        WHERE game = :game AND person = :person""")
    void update(@BindMethods PlayerRow playerRow);

    @SqlUpdate("DELETE FROM player WHERE person = ?")
    void delete(Long playerId);

    @SqlUpdate("DELETE FROM player WHERE game = ?")
    void deletePlayers(Long gameId);
}
