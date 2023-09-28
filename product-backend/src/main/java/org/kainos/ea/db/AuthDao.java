package org.kainos.ea.db;

import org.kainos.ea.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuthDao {

    public String registerUser(User user, Connection c) throws SQLException {
        String insertStatement = "INSERT INTO User (Email, Password, RoleId) VALUES (?,?,?)";
        PreparedStatement st = c.prepareStatement(insertStatement);
        st.setString(1, user.getEmail());

        String hashedPassword = hashPassword(user.getPassword());
        st.setString(2, hashedPassword);

        st.setInt(3, user.getRoleId());
        st.executeUpdate();

        return user.getEmail();
    }

    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
}
