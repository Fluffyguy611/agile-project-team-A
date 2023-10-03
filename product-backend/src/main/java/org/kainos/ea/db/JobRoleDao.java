package org.kainos.ea.db;


import org.kainos.ea.cli.JobRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JobRoleDao {

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
