package com.kdp.golf.user.db;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.BindMethods;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Optional;

/**
 * Crud operations for UserRow objects.
 */
public interface UserDao
{
    @SqlQuery("SELECT * FROM person WHERE id = ?")
    @RegisterRowMapper(UserRow.Mapper.class)
    Optional<UserRow> findById(Long id);

    @SqlQuery("SELECT * FROM person WHERE session = ?")
    @RegisterRowMapper(UserRow.Mapper.class)
    Optional<UserRow> findBySessionId(Long sessionId);

    @SqlQuery("SELECT p.name FROM person p WHERE p.id = ?")
    Optional<String> findName(Long playerId);

    @SqlUpdate("""
    INSERT INTO person (name, session)
    VALUES (:name, :session)""")
    @GetGeneratedKeys
    Long create(@BindMethods UserRow u);

    @SqlUpdate("""
        UPDATE person
        SET name = :name, session = :session
        WHERE id = :id""")
    void update(@BindMethods UserRow u);

    @SqlUpdate("DELETE FROM person WHERE id = ?")
    void delete(Long id);
}
