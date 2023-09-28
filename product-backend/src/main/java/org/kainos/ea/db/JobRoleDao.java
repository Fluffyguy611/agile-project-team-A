package org.kainos.ea.db;


import org.kainos.ea.cli.JobRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JobRoleDao {

    public JobRole getJobRoleById(int id, Connection c) throws SQLException {
        String getStatement = "SELECT * FROM `JobRole` WHERE `Id`=?;";
        PreparedStatement st = c.prepareStatement(getStatement);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            return new JobRole(
                    rs.getInt("Id"),
                    rs.getString("Name"),
                    rs.getString("Description"),
                    rs.getString("SharePointLink")
            );

        }

        return null;
    }

}
