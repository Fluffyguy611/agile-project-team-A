package org.kainos.ea.db;

import org.kainos.ea.exception.FailedToCreateProductException;
import org.kainos.ea.model.Product;
import org.kainos.ea.model.ProductRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDao {

    public List<Product> getAllProducts() throws SQLException {
        Connection c = DatabaseConnector.getConnection();

        Statement st = c.createStatement();

        ResultSet rs = st.executeQuery("SELECT ProductID, Name, Description, Price FROM Product;");

        List<Product> productList = new ArrayList<>();

        while (rs.next()) {
            Product product = new Product(
                    rs.getInt("ProductID"),
                    rs.getString("Name"),
                    rs.getString("Description"),
                    rs.getDouble("Price")
            );

            productList.add(product);
        }

        return productList;
    }

    public Optional<Product> getProductById(int id) throws SQLException {
        Connection c = DatabaseConnector.getConnection();

        Statement st = c.createStatement();

        ResultSet rs = st.executeQuery("SELECT ProductID, Name, Description, Price" +
                " FROM Product where ProductID=" + id);

        while (rs.next()) {
            return Optional.of(new Product(
                    rs.getInt("ProductID"),
                    rs.getString("Name"),
                    rs.getString("Description"),
                    rs.getDouble("Price")
            ));

        }

        return Optional.empty();
    }

    public Product createProduct(ProductRequest product) throws SQLException, FailedToCreateProductException {
        Connection c = DatabaseConnector.getConnection();

        String insertStatement = "INSERT INTO Product (Name, Description, Price) VALUES (?,?,?)";

        PreparedStatement st = c.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);

        st.setString(1, product.getName());
        st.setString(2, product.getDescription());
        st.setDouble(3, product.getPrice());

        st.executeUpdate();

        ResultSet rs = st.getGeneratedKeys();

        if (!rs.next()) {
            throw new FailedToCreateProductException("No product id have been returned");
        }
        return new Product(rs.getInt(1),
                product.getName(),
                product.getDescription(),
                product.getPrice());
    }
}
