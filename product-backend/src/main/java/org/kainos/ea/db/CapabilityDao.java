package org.kainos.ea.db;


import org.kainos.ea.model.Capability;
import org.kainos.ea.model.CapabilityRequest;
import org.kainos.ea.model.JobRole;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CapabilityDao {


    public Optional<List<Capability>> getEveryCapabilityLead(Connection c) throws SQLException {
        String getStatement = "SELECT Id, Capability, Name, Photo, Message FROM `Capability`";
        PreparedStatement st = c.prepareStatement(getStatement);
        ResultSet rs = st.executeQuery();

        List<Capability> capabilityList = new ArrayList<>();

        while (rs.next()) {
            Capability capability = new Capability(
                    rs.getInt("Id"),
                    rs.getString("Capability"),
                    rs.getString("Name"),
                    rs.getString("Photo"),
                    rs.getString("Message")
                    );
            capabilityList.add(capability);
        }
        return Optional.of(capabilityList);
    }



    public int createCapabilityLead (CapabilityRequest capability, Connection c) throws SQLException {
        // String insertStatement = to be filled
                /*

        PreparedStatement st = c.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);

        st.setString(1, employee.getName());
        st.setInt(2, employee.getSalary());
        st.setString(3, employee.getBankAccount());
        st.setString(4, employee.getInsuranceNumber());

        st.executeUpdate();

        ResultSet rs = st.getGeneratedKeys();

        if (rs.next()) {
            return rs.getInt(1);
        }
        */
        return -1;

    }

}
