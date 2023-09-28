package org.kainos.ea.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthValidator {
    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]{1,53}@kainos\\.com$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isValidPassword(String password) {
        if (password.length() < 8 || password.length() > 64) {
            return false;
        }

        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        if (!password.matches(".*[a-z].*")) {
            return false;
        }

        if (!password.matches(".*[!@#$%^&*()-_+=<>?].*")) {
            return false;
        }

        return true;
    }

    public boolean isRoleIdValid(int id) {
        return id == 1 || id == 2;
    }
}
