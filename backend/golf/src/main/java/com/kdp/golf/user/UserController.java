package com.kdp.golf.user;

import com.kdp.golf.websocket.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class UserController
{
    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    public Response.User createUser(Long sessionId)
    {
        var user = userService.createUser(sessionId);
        log.info("user created: " + user);
        var userDto = UserDto.from(user);
        return new Response.User(userDto);
    }

    public Response.User updateName(Long sessionId, String newName)
    {
        var user = userService.updateName(sessionId, newName);
        log.info("user updated: " + user);
        var userDto = UserDto.from(user);
        return new Response.User(userDto);
    }

    public void deleteUser(Long sessionId)
    {
        var user = userService.findBySessionId(sessionId).orElseThrow();
        userService.deleteUser(sessionId);
        log.info("user deleted: " + user);
    }
}
