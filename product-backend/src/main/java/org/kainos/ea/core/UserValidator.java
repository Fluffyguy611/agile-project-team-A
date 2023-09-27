package org.kainos.ea.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]{1,53}@kainos\\.com$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPassword(String password) {
        // Check length
        if (password.length() < 8 || password.length() > 64) {
            return false;
        }

        // Check for at least one upper case letter
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        // Check for at least one lower case letter
        if (!password.matches(".*[a-z].*")) {
            return false;
        }

        // Check for at least one special character
        if (!password.matches(".*[!@#$%^&*()-_+=<>?].*")) {
            return false;
        }

        return true;
    }
}
