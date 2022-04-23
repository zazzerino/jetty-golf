package com.kdp.golf.user;

import com.kdp.golf.DatabaseConnection;
import org.jdbi.v3.sqlobject.transaction.Transaction;

import java.util.Optional;

public class UserService {
    private final UserDao userDao;

    public UserService(DatabaseConnection dbConn) {
        userDao = dbConn.jdbi().onDemand(UserDao.class);
    }

    public Optional<User> findById(Long id) {
        return userDao.findById(id);
    }

    public Optional<User> findBySessionId(String sessionId) {
        return userDao.findBySessionId(sessionId);
    }

    public Optional<Long> findUserId(String sessionId) {
        return findBySessionId(sessionId)
                .map(User::id);
    }

    public Optional<String> findName(Long userId) {
        return userDao.findById(userId)
                .map(User::name);
    }

    public Optional<String> findSessionId(Long userId) {
        return findById(userId)
                .map(User::sessionId);
    }

    @Transaction
    public User createUser(String sessionId) {
        var name = User.DEFAULT_NAME;
        var id = userDao.create(name, sessionId);
        return new User(id, name, sessionId);
    }

    @Transaction
    public User updateName(Long userId, String name) {
        var user = userDao.findById(userId)
                .orElseThrow()
                .withName(name);

        userDao.update(user);
        return user;
    }

    @Transaction
    public void deleteUser(String sessionId) {
        var userId = findUserId(sessionId).orElseThrow();
        userDao.delete(userId);
    }
}
