package org.kainos.ea.db;

import org.kainos.ea.cli.JobRole;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JobRolesDao {
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    public List<JobRole> getAllJobRoles() throws SQLException {
        Connection c = databaseConnector.getConnection();
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("SELECT JobRole.Id, JobRole.Name, JobRole.Description, JobRole.SharePointLink\n" + "FROM JobRole");

        List<JobRole> jobRoleList = new ArrayList<>();
        while (rs.next()) {
            JobRole jobRole = new JobRole(
                    rs.getInt("Id"),
                    rs.getString("Name"),
                    rs.getString("Description"),
                    rs.getString("SharePointLink")
            );
            jobRoleList.add(jobRole);
        }
        return jobRoleList;
    }
}
