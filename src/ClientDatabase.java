import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.Scanner;
import javax.crypto.SecretKey;


public class ClientDatabase {
    private static String username;
    private static String password;

    public static void main(String[] args) throws SQLException {
        // Prompt the user to select an action
        Scanner scanner = new Scanner(System.in);
        int choice = displayActionMenu(scanner);

        // Prompt the user for credentials
        promptForCredentials(scanner);

        // Create a DatabaseManager instance using your database connection details
        

        String jdbcUrl = "jdbc:sqlserver://ranwang.database.windows.net:1433;database=ranwang;user=ran-adm@ranwang;password=R@n12345678;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        // String dbUsername = "ran-adm@ranwang";
        // String dbPassword = "R@n12345678";
        // DatabaseManager databaseManager = new DatabaseManager(jdbcUrl, dbUsername, dbPassword);
        DatabaseManager databaseManager = new DatabaseManager(jdbcUrl);

        // Use the databaseManager for database operations, including authentication
        DatabaseAuthenticationProvider authenticationProvider = new DatabaseAuthenticationProvider(databaseManager);

        try {
            Registry registry = LocateRegistry.getRegistry(null);
            Operations operation = (Operations) registry.lookup("print");

            SecretKey secretKey = CryptoUtil.generateAESKey(password);
            String encryptedUsername = CryptoUtil.encrypt(username, secretKey);
            String hashedPassword = CryptoUtil.hashPassword(password);
            System.out.println("Hashed Password: " + hashedPassword);

            if (authenticateUser(authenticationProvider, encryptedUsername, hashedPassword)) {
                // User is authenticated, present a list of actions
                switch (choice) {
                    case 1:
                        // Print a file
                        System.out.print("Enter the filename: ");
                        String filename = scanner.next();
                        System.out.print("Enter the printer name: ");
                        String printer = scanner.next();
                        operation.print(filename, printer);
                        break;
                    case 2:
                        // List the print queue
                        System.out.print("Enter the printer name: ");
                        String queuePrinter = scanner.next();
                        operation.queue(queuePrinter);
                        break;
                    case 3:
                        // Move a job to the top of the queue
                        System.out.print("Enter the printer name: ");
                        String topQueuePrinter = scanner.next();
                        System.out.print("Enter the job number: ");
                        int jobNumber = scanner.nextInt();
                        operation.topQueue(topQueuePrinter, jobNumber);
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            } else {
                System.out.println("Authentication failed. Invalid username or password.");
            }
            scanner.close();
        } catch (Exception e) {
            System.err.println("Client exception: " + e);
            e.printStackTrace();
        }
    }

    private static void promptForCredentials(Scanner scanner) {
        System.out.print("Enter your username: ");
        username = scanner.next();
        System.out.print("Enter your password: ");
        password = scanner.next();
        if (password == null) {
            password = "";
        }
    }

    private static boolean authenticateUser(DatabaseAuthenticationProvider authenticationProvider, String encryptedUsername, String hashedPassword) {
        return authenticationProvider.authenticate(encryptedUsername, hashedPassword);
    }

    private static int displayActionMenu(Scanner scanner) {
        System.out.println("Choose an action:");
        System.out.println("1. Print a file");
        System.out.println("2. List the print queue");
        System.out.println("3. Move a job to the top of the queue");
        System.out.println("4. Exit");

        int choice = -1; // Initialize to an invalid choice

        while (choice < 1 || choice > 4) {
            System.out.print("Enter your choice: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice < 1 || choice > 4) {
                    System.out.println("Invalid choice. Please select a valid option (1-4).");
                }
            } else {
                scanner.next(); // Consume the non-integer token
                System.out.println("Invalid input. Please enter a valid option (1-4).");
            }
        }

        return choice;
    }
}
