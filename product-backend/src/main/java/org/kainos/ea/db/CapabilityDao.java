package org.kainos.ea.db;


import org.kainos.ea.cli.Capability;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CapabilityDao {


    public Optional<List<Capability>> getEveryCapabilityLead(Connection c) throws SQLException {
        String getStatement = "SELECT * FROM `Capability`";
        PreparedStatement st = c.prepareStatement(getStatement);
        ResultSet rs = st.executeQuery();

        List<Capability> capabilityList = new ArrayList<>();

        while (rs.next()) {
            Capability capability = new Capability(
                    rs.getInt("Id"),
                    rs.getString("Capability"),
                    rs.getString("Name"),
                    rs.getString("Photo"),
                    rs.getString("Message"));
            capabilityList.add(capability);
        }
        return Optional.of(capabilityList);
    }


//        public int createCapabilityLead(CapabilityRequest capability, Connection c) throws SQLException {
//         String insertStatement = "zapytanie sql";
//
//
//        PreparedStatement st = c.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);
//
//        st.setString(1, capability.Capability());
//        st.setString(2, capability.Name());
//        st.setString(3, capability.setPhoto());
//        st.setString(4, capability.setMessage());
//
//        st.executeUpdate();
//
//        ResultSet rs = st.getGeneratedKeys();
//
//        if (rs.next()) {
//            return rs.getInt(1);
//        }
//
//        return -1;
//
//    }

}








