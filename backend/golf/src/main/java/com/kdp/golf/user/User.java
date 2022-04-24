package com.kdp.golf.user;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public record User(Long id,
                   String name,
                   Long sessionId) {
    public static final String DEFAULT_NAME = "anon";

    public User withName(String name) {
        return new User(id, name, sessionId);
    }

    public static class Mapper implements RowMapper<User> {
        @Override
        public User map(ResultSet rs, StatementContext _ctx) throws SQLException {
            var id = rs.getLong("id");
            var name = rs.getString("name");
            var sessionId = rs.getLong("session_id");
            return new User(id, name, sessionId);
        }
    }

    public record Dto(Long id, String name) {
        public static Dto from(User user) {
            return new Dto(user.id(), user.name());
        }
    }
}
