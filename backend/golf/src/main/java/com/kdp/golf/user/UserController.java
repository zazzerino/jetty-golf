package com.kdp.golf.user;

import com.kdp.golf.websocket.Response;
import com.kdp.golf.websocket.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create a Response.User from a Request.
 */
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

    public Response.User updateName(Request.UpdateName request)
    {
        var user = userService.updateName(request.sessionId(), request.name());
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
