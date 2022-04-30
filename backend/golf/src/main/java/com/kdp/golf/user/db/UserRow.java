package com.kdp.golf.user.db;

import com.kdp.golf.user.User;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A row in the `person` table.
 */
public record UserRow(Long id,
                      String name,
                      Long session)
{
    public static UserRow from(User u)
    {
        return new UserRow(u.id(), u.name(), u.sessionId());
    }

    public User toUser()
    {
        return new User(id, name, session);
    }

    public static class Mapper implements RowMapper<UserRow>
    {
        @Override
        public UserRow map(ResultSet rs, StatementContext ctx) throws SQLException
        {
            var id = rs.getLong("id");
            var name = rs.getString("name");
            var session = rs.getLong("session");
            return new UserRow(id, name, session);
        }
    }
}
