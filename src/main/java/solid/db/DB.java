package solid.db;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.*;

public class DB implements AutoCloseable {
    private Connection conn;
    private final String dbPath;

    public DB() {
        this.dbPath = "db/fbs.db";
    }

    public void open() {
        // System.out.println("Working Directory = " + System.getProperty("user.dir"));
        if (conn != null) {
            System.out.println("Connection already open.");
            return; // Connection is already open
        }

        try {
            // Load SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            System.out.println("SQLite JDBC Driver loaded.");
            // Establish the connection
            this.conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            System.out.println("Connection established to the database.");
        } catch(Exception e) {
            System.err.println("Error opening database connection: " + e.getMessage());
            System.err.println("SQLite JDBC Driver not found.");
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            conn.close();
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            System.err.println("Error closing DB connection: " + e.getMessage());
        }
    }

    // Ensure the connection is open before preparing the statement
    public PreparedStatement prepare(String sql, Object... params) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not open.");
        }

        PreparedStatement stmt = conn.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
        return stmt;
    }

    // Ensure the connection is open before querying
    public ResultSet query(String sql, Object... params) throws SQLException {
        return prepare(sql, params).executeQuery();
    }

    // Ensure the connection is open before updating
    public void update(String sql, Object... params) throws SQLException {
        prepare(sql, params).executeUpdate();
    }

    // Getter for the connection (optional)
    public Connection getConnection() {
        return conn;
    }
}
