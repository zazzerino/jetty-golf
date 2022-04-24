package com.kdp.golf.user;

import com.kdp.golf.websocket.Sessions;
import com.kdp.golf.websocket.response.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserController {
    private final Sessions sessions;
    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(Sessions sessions,
                          UserService userService) {
        this.sessions = sessions;
        this.userService = userService;
    }

    public UserResponse createUser(Long sessionId) {
        var user = userService.createUser(sessionId);
        log.info("user created: " + user);
        var userDto = User.Dto.from(user);
        return new UserResponse(userDto);
    }

    public UserResponse updateName(Long sessionId, String name) {
        var user = userService.findBySessionId(sessionId)
                .orElseThrow()
                .withName(name);

        userService.updateName(user.id(), name);
        log.info("user updated: " + user);
        var userDto = User.Dto.from(user);
        return new UserResponse(userDto);
    }

    public void deleteUser(Long sessionId) {
        var user = userService.findBySessionId(sessionId).orElseThrow();
        userService.deleteUser(sessionId);
        log.info("user deleted: " + user);
    }
}
