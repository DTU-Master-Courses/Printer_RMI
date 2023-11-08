import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private Connection databaseConnection;
  


    // public DatabaseManager(String jdbcUrl, String dbUsername, String dbPassword) throws SQLException {
    //     // databaseConnection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
    //     try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
    //         System.out.println("Connected to Azure SQL Database.");
            
    //         // Perform database operations here.
    //     } catch (SQLException e) {
    //         System.err.println("Connection failed. Error: " + e.getMessage());
    //     }
    // }
    public DatabaseManager(String jdbcUrl) throws SQLException {
        // databaseConnection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
        try (Connection databaseConnection = DriverManager.getConnection(jdbcUrl)) {
            System.out.println("Connected to Azure SQL Database.");
            
            // Perform database operations here.
        } catch (SQLException e) {
            System.err.println("databaseConnection failed. Error: " + e.getMessage());
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
