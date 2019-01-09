package be.reactiveprogramming.demos.stockgameserver.shared.registration;

import java.io.Serializable;

public class UserRegistration implements Serializable {

    private static final long serialVersionUID = -1091070646138821048L;

    private String username;

    private UserRegistration() {
    }

    public UserRegistration(final String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
