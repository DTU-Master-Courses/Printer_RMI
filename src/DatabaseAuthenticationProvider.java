import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseAuthenticationProvider implements AuthenticationProvider {
    private final DatabaseManager databaseManager;

    public DatabaseAuthenticationProvider(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public boolean authenticate(String username, String password) {
        Connection databaseConnection = databaseManager.getDatabaseConnection();

        // Implement user authentication logic
        try {
            String query = "SELECT password FROM dbo.users WHERE UserName = ?";
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                // Compare the stored password with the provided password
                return storedPassword.equals(password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean createUser(String username, String password) {
        Connection databaseConnection = databaseManager.getDatabaseConnection();

        // Implement user creation logic
        try {
            String query = "INSERT INTO dbo.users (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateUserPassword(String username, String newPassword) {
        Connection databaseConnection = databaseManager.getDatabaseConnection();

        // Implement password update logic
        try {
            String query = "UPDATE dbo.users SET password = ? WHERE username = ?";
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, username);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteUser(String username) {
        Connection databaseConnection = databaseManager.getDatabaseConnection();

        // Implement user deletion logic
        try {
            String query = "DELETE FROM dbo.users WHERE username = ?";
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
            preparedStatement.setString(1, username);

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void closeConnection() {
        Connection databaseConnection = databaseManager.getDatabaseConnection();

        try {
            if (databaseConnection != null && !databaseConnection.isClosed()) {
                databaseConnection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
