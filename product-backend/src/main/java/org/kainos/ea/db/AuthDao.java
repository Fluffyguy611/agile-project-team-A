package org.kainos.ea.db;

import org.kainos.ea.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuthDao {
    public String registerUser(User user, Connection c) throws SQLException {
        String insertStatement = "INSERT INTO User (Email, Password, RoleId) VALUES (?,?,?)";

        PreparedStatement st = c.prepareStatement(insertStatement);

        st.setString(1, user.getEmail());
        st.setString(2, user.getPassword());
        st.setInt(3, user.getRoleId());
        st.executeUpdate();

        return user.getEmail();
    }
}
