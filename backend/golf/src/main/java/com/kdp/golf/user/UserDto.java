package com.kdp.golf.user;

/**
 * Represents the user data that will be sent to the client.
 */
public record UserDto(Long id, String name)
{
    public static UserDto from(User u)
    {
        return new UserDto(u.id(), u.name());
    }
}
