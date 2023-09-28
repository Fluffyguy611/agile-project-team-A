package org.kainos.ea.db;

import org.kainos.ea.model.JobRole;
import org.kainos.ea.model.JobRoleRequest;

import java.sql.*;
import java.util.Optional;

public class JobRoleDao {

    private static DatabaseConnector databaseConnector = new DatabaseConnector();

    public Optional<JobRole> createNewJobRole(JobRoleRequest jobRole) throws SQLException {
        Connection c = databaseConnector.getConnection();

        String insertStatement = "INSERT INTO JobRole (Name, Description, SharePointLink) VALUES (?,?,?)";

        PreparedStatement st = c.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);

        st.setString(1, jobRole.getName());
        st.setString(2, jobRole.getDescription());
        st.setString(3, jobRole.getSharePointLink());

        st.executeUpdate();

        ResultSet rs = st.getGeneratedKeys();

        if (rs.next()) {
            return Optional.of(
                    new JobRole(
                            rs.getInt(1),
                            jobRole.getName(),
                            jobRole.getDescription(),
                            jobRole.getSharePointLink()));
        }
        return Optional.empty();
    }
}
