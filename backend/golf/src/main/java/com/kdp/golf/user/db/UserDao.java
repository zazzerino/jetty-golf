package com.kdp.golf.user.db;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.BindMethods;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Optional;

public interface UserDao
{
    @SqlQuery("SELECT * FROM person WHERE id = ?")
    @RegisterRowMapper(UserRow.Mapper.class)
    Optional<UserRow> findById(Long id);

    @SqlQuery("SELECT * FROM person WHERE session = ?")
    @RegisterRowMapper(UserRow.Mapper.class)
    Optional<UserRow> findBySessionId(Long sessionId);

    @SqlUpdate("""
    INSERT INTO person (id, name, session)
    VALUES (:id, :name, :session)""")
    void create(@BindMethods UserRow u);

    @SqlUpdate("""
        UPDATE person
        SET name = :name, session = :session
        WHERE id = :id""")
    void update(@BindMethods UserRow u);

    @SqlUpdate("DELETE FROM person WHERE id = ?")
    void delete(Long id);
}
