package org.kainos.ea.core;

import org.kainos.ea.cli.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    public static boolean isValidUser(User user){
        if(user.getPassword().length() > 64 || user.getPassword().length() < 1){
            return false;
        }

        if(user.getRoleId() != 1 && user.getRoleId() != 2) {
            return false;
        }

        return true;
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]{1,53}@kainos\\.com$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
