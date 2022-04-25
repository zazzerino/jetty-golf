package com.kdp.golf.user;

import com.kdp.golf.websocket.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserController
{
    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    public Response.UserResponse createUser(Long sessionId)
    {
        var user = userService.createUser(sessionId);
        log.info("user created: " + user);
        var userDto = user.toDto();
        return new Response.UserResponse(userDto);
    }

    public Response.UserResponse updateName(Long sessionId, String name)
    {
        var user = userService.findBySessionId(sessionId)
                .orElseThrow()
                .withName(name);

        userService.updateName(user.id(), name);
        log.info("user updated: " + user);
        var userDto = user.toDto();
        return new Response.UserResponse(userDto);
    }

    public void deleteUser(Long sessionId)
    {
        var user = userService.findBySessionId(sessionId).orElseThrow();
        userService.deleteUser(sessionId);
        log.info("user deleted: " + user);
    }
}
