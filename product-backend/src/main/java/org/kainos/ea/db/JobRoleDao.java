package org.kainos.ea.db;

import org.kainos.ea.exception.DatabaseConnectionException;
import org.kainos.ea.model.JobRole;
import org.kainos.ea.model.JobRoleRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JobRoleDao {

    private static DatabaseConnector databaseConnector = new DatabaseConnector();

    public JobRoleDao() {
        databaseConnector = new DatabaseConnector();
    }

    public List<JobRole> getAllJobRoles() throws SQLException, DatabaseConnectionException {
        Connection c = databaseConnector.getConnection();
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("SELECT JobRole.Id, JobRole.Name, JobRole.Description, JobRole.SharePointLink, JobRole.CapabilityId, Capability.Name, JobRole.BandId, Band.Name, Band.Level" +
                " FROM JobRole " +
                "INNER JOIN Band " +
                "ON Band.Id = JobRole.BandId " +
                "INNER JOIN Capability " +
                "ON Capability.Id = JobRole.CapabilityId " +
                "ORDER BY JobRole.Name ASC");
        List<JobRole> jobRoleList = new ArrayList<>();
        while (rs.next()) {

            JobRole jobRole = new JobRole(
                    rs.getInt("Id"),
                    rs.getString("Name"),
                    rs.getString("Description"),
                    rs.getString("SharePointLink"),
                    rs.getInt("CapabilityId"),
                    rs.getString("Capability.Name"),
                    rs.getInt("BandId"),
                    rs.getString("Band.Name"),
                    rs.getInt("Band.Level"));

            jobRoleList.add(jobRole);

        }
        return jobRoleList;
    }


    public Optional<JobRole> createNewJobRole(JobRoleRequest jobRole) throws SQLException {
        Connection c = databaseConnector.getConnection();

        String insertStatement = "INSERT INTO JobRole (Name, Description, SharePointLink, CapabilityId, BandId) VALUES (?,?,?,?,?)";

        PreparedStatement st = c.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);

        st.setString(1, jobRole.getName());
        st.setString(2, jobRole.getDescription());
        st.setString(3, jobRole.getSharePointLink());
        st.setInt(4, jobRole.getCapabilityId());
        st.setInt(5, jobRole.getBandId());

        st.executeUpdate();

        ResultSet rs = st.getGeneratedKeys();

        if (rs.next()) {
            return Optional.of(
                    new JobRole(
                            rs.getInt(1),
                            jobRole.getName(),
                            jobRole.getDescription(),
                            jobRole.getSharePointLink(),
                            jobRole.getCapabilityId(),
                            jobRole.getBandId()));
        }
        return Optional.empty();
    }

    public Optional<JobRole> getJobRoleById(int id) throws SQLException {
        Connection c = databaseConnector.getConnection();
        String getStatement = "SELECT JobRole.Id, JobRole.Name, JobRole.Description, JobRole.SharePointLink, JobRole.CapabilityId, Capability.Name, JobRole.BandId, Band.Name, Band.Level" +
                " FROM JobRole " +
                "INNER JOIN Band " +
                "ON Band.Id = JobRole.BandId " +
                "INNER JOIN Capability " +
                "ON Capability.Id = JobRole.CapabilityId " +
                "WHERE JobRole.Id = ?; ";
        PreparedStatement st = c.prepareStatement(getStatement);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            return Optional.of(new JobRole(
                    rs.getInt("Id"),
                    rs.getString("Name"),
                    rs.getString("Description"),
                    rs.getString("SharePointLink"),
                    rs.getInt("CapabilityId"),
                    rs.getString("Capability.Name"),
                    rs.getInt("BandId"),
                    rs.getString("Band.Name"),
                    rs.getInt("Band.Level")
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
                    rs.getInt("CapabilityId"),
                    rs.getInt("BandId")
            ));
        }
        return Optional.empty();
    }
}

