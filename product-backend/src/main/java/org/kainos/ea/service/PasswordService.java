package org.kainos.ea.service;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordService {
    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public boolean verifyHash(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
