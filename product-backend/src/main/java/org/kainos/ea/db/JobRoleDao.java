package org.kainos.ea.db;

import org.kainos.ea.cli.JobRole;
import org.kainos.ea.client.DatabaseConnectionException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JobRoleDao {
    private DatabaseConnector databaseConnector;

    public JobRoleDao() {
        databaseConnector = new DatabaseConnector();
    }

    public List<JobRole> getAllJobRoles() throws SQLException, DatabaseConnectionException {
        Connection c = databaseConnector.getConnection();
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("SELECT JobRole.Id, JobRole.Name, JobRole.Description, JobRole.SharePointLink\n" + "FROM JobRole");

        List<JobRole> jobRoleList = new ArrayList<>();
        while (rs.next()) {
            JobRole jobRole = new JobRole(
                    rs.getInt("Id"),
                    rs.getString("Name"),
                    rs.getString("Description"),
                    rs.getString("SharePointLink"));
            jobRoleList.add(jobRole);
        }
        return jobRoleList;
    }


    public Optional<JobRole> getJobRoleById(int id, Connection c) throws SQLException {
        String getStatement = "SELECT * FROM `JobRole` WHERE `Id`=?;";
        PreparedStatement st = c.prepareStatement(getStatement);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            return Optional.of(new JobRole(
                    rs.getInt("Id"),
                    rs.getString("Name"),
                    rs.getString("Description"),
                    rs.getString("SharePointLink")
            ));

        }

        return Optional.empty();
    }

}
