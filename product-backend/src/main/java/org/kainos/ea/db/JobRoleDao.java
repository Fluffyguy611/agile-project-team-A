package org.kainos.ea.db;

import org.kainos.ea.model.JobRole;
import org.kainos.ea.model.JobRoleRequest;

import java.sql.*;
import java.util.Optional;

public class JobRoleDao {

    private static DatabaseConnector databaseConnector = new DatabaseConnector();

    public Optional<JobRole> createNewJobRole(JobRoleRequest jobRole) throws SQLException {
        Connection c = databaseConnector.getConnection();

        String insertStatement = "INSERT INTO JobRole (Name, Description, SharePointLink, BandId) VALUES (?,?,?,?);";

        PreparedStatement st = c.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);

        st.setString(1, jobRole.getName());
        st.setString(2, jobRole.getDescription());
        st.setString(3, jobRole.getSharePointLink());
        st.setInt(4, jobRole.getBandId());

        st.executeUpdate();

        ResultSet rs = st.getGeneratedKeys();

        if (rs.next()) {
            return Optional.of(
                    new JobRole(
                            rs.getInt(1),
                            jobRole.getName(),
                            jobRole.getDescription(),
                            jobRole.getSharePointLink(),
                            jobRole.getBandId()));
        }
        return Optional.empty();
    }

    public Optional<JobRole> getJobRoleById(int id) throws SQLException {
        Connection c = databaseConnector.getConnection();
        String getStatement = "SELECT * FROM `JobRole` WHERE `Id`=?;";
        PreparedStatement st = c.prepareStatement(getStatement);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            return Optional.of(new JobRole(
                    rs.getInt("Id"),
                    rs.getString("Name"),
                    rs.getString("Description"),
                    rs.getString("SharePointLink"),
                    rs.getInt("BandId")
            ));

        }

        return Optional.empty();
    }

    public Optional<JobRole> getJobRoleByName(String name) throws SQLException {
        Connection c = databaseConnector.getConnection();
        String getStatement = "SELECT * FROM `JobRole` WHERE `Name`=?;";
        PreparedStatement st = c.prepareStatement(getStatement);
        st.setString(1, name);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            return Optional.of(new JobRole(
                    rs.getInt("Id"),
                    rs.getString("Name"),
                    rs.getString("Description"),
                    rs.getString("SharePointLink"),
                    rs.getInt("BandId")
            ));

        }

        return Optional.empty();
    }
}
