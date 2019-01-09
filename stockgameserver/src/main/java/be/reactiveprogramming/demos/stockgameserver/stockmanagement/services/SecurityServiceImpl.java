package be.reactiveprogramming.demos.stockgameserver.stockmanagement.services;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kristof on 12/01/2017.
 */
public class SecurityServiceImpl {

    private SecureRandom random = new SecureRandom();
    private Map<String, String> overview = new HashMap<>();

    public String nextPassword() {
        return new BigInteger(130, random).toString(32);
    }

    boolean isValidPair(String username, String password) {
        if(! overview.containsKey(username)) {
            return false;
        }

        System.out.println("Comparing " + rotate(overview.get(username)) + " to " + password);

        return overview.get(username).equals(password);
    }

    private String rotate(String password) {
        String result = "";
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if       (c >= 'a' && c <= 'm') c += 13;
            else if  (c >= 'A' && c <= 'M') c += 13;
            else if  (c >= 'n' && c <= 'z') c -= 13;
            else if  (c >= 'N' && c <= 'Z') c -= 13;
            result += c;
        }
        return result;
    }

    public String generatePassword(String username) {
        if (overview.containsKey(username)) {
            throw new IllegalArgumentException("Username already exists in security");
        }

        String password = nextPassword();

        overview.put(username, password);

        return password;
    }
}
