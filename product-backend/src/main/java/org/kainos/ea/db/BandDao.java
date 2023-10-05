package org.kainos.ea.db;

import org.kainos.ea.model.Band;
import org.kainos.ea.model.BandRequest;

import java.sql.*;
import java.util.Optional;

public class BandDao {
    private static DatabaseConnector databaseConnector = new DatabaseConnector();

    public Optional<Band> createNewBand(BandRequest band) throws SQLException {
        Connection c = databaseConnector.getConnection();

        String insertStatement = "INSERT INTO Band (Name, Level) VALUES (?,?)";

        PreparedStatement st = c.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);

        st.setString(1, band.getName());
        st.setInt(2, band.getLevel());

        st.executeUpdate();

        ResultSet rs = st.getGeneratedKeys();

        if (rs.next()) {
            return Optional.of(
                    new Band(
                            rs.getInt(1),
                            band.getName(),
                            band.getLevel()));
        }
        return Optional.empty();
    }

    public Optional<Band> getBandByName(String name) throws SQLException {
        Connection c = databaseConnector.getConnection();
        String getStatement = "SELECT * FROM `Band` WHERE `Name`=?;";
        PreparedStatement st = c.prepareStatement(getStatement);
        st.setString(1, name);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            return Optional.of(new Band(
                    rs.getInt("Id"),
                    rs.getString("Name"),
                    rs.getInt("Level")
            ));
        }

        return Optional.empty();
    }

}
