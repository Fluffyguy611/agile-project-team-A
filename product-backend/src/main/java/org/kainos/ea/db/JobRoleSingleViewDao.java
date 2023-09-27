package org.kainos.ea.db;


import org.kainos.ea.cli.JobRoleSingleView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JobRoleSingleViewDao {

    public JobRoleSingleView getJobRoleById(int id, Connection c) throws SQLException {
        String getStatement = "SELECT * FROM `JobRole` WHERE `Id`=" + id + ";";
        PreparedStatement st = c.prepareStatement(getStatement);
        ResultSet rs = st.executeQuery();

        while (rs.next()){
            return new JobRoleSingleView(
                    rs.getInt("Id"),
                    rs.getString("Name"),
                    rs.getString("Description"),
                    rs.getString("SharePointLink")
            );

        }

        return null;
    }

}
