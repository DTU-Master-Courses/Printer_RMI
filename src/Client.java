import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.io.File;

public class Client {
    private static String username;
    private static String password;

    public static void main(String[] args) {
        // Prompt the user for credentials
        promptForCredentials();

        try {
            Registry registry = LocateRegistry.getRegistry(null);
            Hello hello = (Hello) registry.lookup("Hello");

            // Authenticate the user based on the provided credentials
            if (authenticateUser(username, password)) {
                // User is authenticated, make the print request
                hello.print("file01", "p1");
            } else {
                System.out.println("Authentication failed. Invalid username or password.");
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e);
            e.printStackTrace();
        }
    }

    private static void promptForCredentials() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username: ");
        username = scanner.next();
        System.out.print("Enter your password: ");
        password = scanner.next();
        scanner.close();
    }

    private static boolean authenticateUser(String username, String password) {
        try {
            // Read the password file and check for a matching username and password
            Scanner fileScanner = new Scanner(new File("passwords.txt"));
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true; // Authentication successful
                }
            }
            fileScanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false; // Authentication failed
    }
}
