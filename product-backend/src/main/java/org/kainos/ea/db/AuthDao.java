package org.kainos.ea.db;

import org.kainos.ea.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

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

    public Optional<User> getUserByEmail(String email, Connection c) throws SQLException {
        String selectStatement = "SELECT Email, Password, RoleId FROM User WHERE Email = ?;";
        PreparedStatement st = c.prepareStatement(selectStatement);
        st.setString(1, email);

        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            return Optional.of(new User(
                    rs.getString("Email"),
                    rs.getString("Password"),
                    rs.getInt("RoleId")
            ));
        }

        return Optional.empty();
    }
}
