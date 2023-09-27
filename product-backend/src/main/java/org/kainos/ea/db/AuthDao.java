package org.kainos.ea.db;

import org.kainos.ea.cli.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

public class AuthDao {
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    public String registerUser(User user) throws SQLException {
        Connection c = databaseConnector.getConnection();
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
