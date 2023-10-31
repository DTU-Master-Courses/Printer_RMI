import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SystemFileAuthenticationProvider implements AuthenticationProvider {

    private final String passwordFile = "passwords.txt"; // File to store usernames and hashed passwords

    @Override
    public boolean authenticate(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(passwordFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String storedUsername = parts[0];
                    String storedHashedPassword = parts[1];
                    String inputHashedPassword = hashPassword(password);

                    if (username.equals(storedUsername) && inputHashedPassword.equals(storedHashedPassword)) {
                        return true; // Authentication successful
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Authentication failed
    }

    @Override
    public boolean createUser(String username, String password) {
        try (FileWriter writer = new FileWriter(passwordFile, true)) {
            String hashedPassword = hashPassword(password);
            writer.write(username + ":" + hashedPassword + "\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateUserPassword(String username, String newPassword) {
        // Implement logic to update the password
        return false;
    }

    @Override
    public boolean deleteUser(String username) {
        // Implement logic to delete a user
        return false;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());

            StringBuilder hexHash = new StringBuilder();
            for (byte b : hashBytes) {
                hexHash.append(String.format("%02x", b));
            }

            return hexHash.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
