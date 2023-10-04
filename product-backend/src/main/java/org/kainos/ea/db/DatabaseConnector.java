package org.kainos.ea.db;

import org.kainos.ea.exception.DatabaseConnectionException;
import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private final static Logger logger = LoggerFactory.getLogger(DatabaseConnector.class);
    private static Connection conn;

    public Connection getConnection() throws DatabaseConnectionException, SQLException {
        String user, password, host, database;

        if (conn != null && !conn.isClosed()) {
            return conn;
        }

        try {
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
            user = dotenv.get("DB_USERNAME");
            password = dotenv.get("DB_PASSWORD");
            host = dotenv.get("DB_HOST");
            name = dotenv.get("DB_NAME");

            if (user == null || password == null || host == null || name == null)
                throw new IllegalArgumentException("Properties file must exist " +
                        "and must contain user, password, name and host properties.");

            conn = DriverManager.getConnection("jdbc:mysql://" + host + "/" + name + "?allowPublicKeyRetrieval=true&useSSL=false", user, password);
            return conn;

        } catch (SQLException exception) {
            logger.error("Couldn't connect to the database! Error: {}", exception.getMessage());
            throw exception;
        }
    }
}
