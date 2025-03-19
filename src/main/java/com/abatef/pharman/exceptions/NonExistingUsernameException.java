package com.abatef.pharman.exceptions;

import com.abatef.pharman.utils.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class NonExistingUsernameException extends RuntimeException {
    private final String username;
    public NonExistingUsernameException(String username) {
        super(Constants.Messages.NON_EXISTING_USERNAME_MSG);
        this.username = username;
    }
}
