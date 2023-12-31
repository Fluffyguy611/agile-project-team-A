package org.kainos.ea.db;

import org.kainos.ea.exception.FailedToCreateCapabilityLeadException;
import org.kainos.ea.model.Capability;
import org.kainos.ea.model.CapabilityRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CapabilityDao {


    public List<Capability> getEveryCapabilityLead(Connection c) throws SQLException {
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
                    rs.getString("Message"));

            capabilityList.add(capability);
        }
        return capabilityList;
    }

    public void createCapabilityLead(CapabilityRequest capability, Connection c) throws SQLException, FailedToCreateCapabilityLeadException {

        String insertStatement = "INSERT INTO Capability (Capability, Name, Photo, Message) VALUES (?,?,?,?)";

        PreparedStatement st = c.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);

        st.setString(1, capability.getCapability());
        st.setString(2, capability.getName());
        st.setString(3, capability.getPhoto());
        st.setString(4, capability.getMessage());

        st.executeUpdate();

        ResultSet rs = st.getGeneratedKeys();
        if (!rs.next()) {
            throw new FailedToCreateCapabilityLeadException();
        }
    }
}
