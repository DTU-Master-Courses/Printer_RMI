import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private Connection databaseConnection;

    // Static initialization block to load the JDBC driver
    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC driver not found. Error: " + e.getMessage());
        }
    }

    public DatabaseManager(String jdbcUrl) {
        try {
            // Assign the value to the class field
            this.databaseConnection = DriverManager.getConnection(jdbcUrl);
            System.out.println("Connected to Azure SQL Database.");
            // Perform database operations here.
        } catch (SQLException e) {
            System.err.println("Database connection failed. Error: " + e.getMessage());
        }
    }

    public Connection getDatabaseConnection() {
        return databaseConnection;
    }

    public void closeConnection() {
        try {
            if (databaseConnection != null && !databaseConnection.isClosed()) {
                databaseConnection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
