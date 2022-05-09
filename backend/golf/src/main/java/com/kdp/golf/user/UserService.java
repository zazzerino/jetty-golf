package com.kdp.golf.user;

import com.kdp.golf.DatabaseConnection;
import com.kdp.golf.user.db.UserDao;
import com.kdp.golf.user.db.UserRow;
import org.jdbi.v3.sqlobject.transaction.Transaction;

import java.util.Optional;

public class UserService
{
    private final UserDao userDao;

    public UserService(DatabaseConnection dbConn)
    {
        this.userDao = dbConn.jdbi().onDemand(UserDao.class);
    }

    public Optional<User> findById(Long id)
    {
        return userDao.findById(id)
                .map(UserRow::toUser);
    }

    public Optional<User> findBySessionId(Long sessionId)
    {
        return userDao.findBySessionId(sessionId)
                .map(UserRow::toUser);
    }

    public Optional<Long> findUserId(Long sessionId)
    {
        return userDao.findBySessionId(sessionId)
                .map(UserRow::id);
    }

    @Transaction
    public User createUser(Long sessionId)
    {
        var name = User.DEFAULT_NAME;
        var userRow = new UserRow(null, name, sessionId);
        var id = userDao.insert(userRow);

        return new User(id, name, sessionId);
    }

    @Transaction
    public void deleteUser(Long sessionId)
    {
        var userId = findUserId(sessionId).orElseThrow();
        userDao.delete(userId);
    }

    @Transaction
    public User updateName(Long sessionId, String newName)
    {
        var userRow = userDao.findBySessionId(sessionId)
                .map(u -> new UserRow(u.id(), newName, u.session()))
                .orElseThrow();

        userDao.update(userRow);
        return userRow.toUser();
    }
}
