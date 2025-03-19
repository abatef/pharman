package com.abatef.pharman.exceptions;

import com.abatef.pharman.utils.Constants;
import lombok.Getter;

@Getter
public class NonUniqueUsernameException extends RuntimeException {
    private final String username;
    public NonUniqueUsernameException(String username) {
        super(Constants.Messages.NON_UNIQUE_USERNAME_MSG);
        this.username = username;
    }
}
