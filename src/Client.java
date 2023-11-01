import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.io.File;
import javax.crypto.SecretKey;

public class Client {
    private static String username;
    private static String password;

    /*public static void main(String[] args) {
        // Prompt the user for credentials
        promptForCredentials();

        try {
            // Getting the registry
            Registry registry = LocateRegistry.getRegistry(null);
            // Looking up the registry for the remote object
            Operations print = (Operations) registry.lookup("print");
            // Generate a secret key based on the password
            SecretKey secretKey = CryptoUtil.generateAESKey(password);
            // Hash the password before sending it to the server
            String hashedPassword = CryptoUtil.hashPassword(password);
            System.out.println("Hashed Password: " + hashedPassword);

            // Encrypt the username and password
            String encryptedUsername = CryptoUtil.encrypt(username, secretKey);
            //String encryptedPassword = CryptoUtil.encrypt(password, secretKey);

            // Call the remote method with encrypted credentials
            // String result = hello.printWithCredentials("file01", "p1", encryptedUsername, encryptedPassword);

            // Authenticate the user based on the provided credentials
            if (authenticateUser(encryptedUsername, hashedPassword, secretKey)) {
                // User is authenticated, make the print request
                print.print("file01", "p1");
            } else {
                System.out.println("Authentication failed. Invalid username or password.");
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e);
            e.printStackTrace();
        }
    }
*/
    private static void promptForCredentials(Scanner scanner) {
        //Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username: ");
        username = scanner.next();
        System.out.print("Enter your password: ");
        password = scanner.next();
        // Check if password is null and initialize it if necessary
        if (password == null) {
            password = "";
        }
        //scanner.close();
    }

    private static boolean authenticateUser(String encryptedUsername, String hashedPassword ,SecretKey secretKey) {
        try {
            // Read the password file and check for a matching username and password
            Scanner fileScanner = new Scanner(new File("passwords.txt"));
            String username = CryptoUtil.decrypt(encryptedUsername, secretKey);
            //String password = CryptoUtil.decrypt(encryptedPassword, secretKey);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(hashedPassword)) {
                    return true; // Authentication successful
                }
            }
            fileScanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false; // Authentication failed
    }


    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(null);
            Operations operation = (Operations) registry.lookup("print");
            //Operations queue = (Operations) registry.lookup("queue");
            //Operations toQueue = (Operations) registry.lookup("toQueue");
            // Prompt the user to select an action
            Scanner scanner = new Scanner(System.in);
            int choice = displayActionMenu(scanner);

            // Prompt the user for credentials
            promptForCredentials(scanner);

            // Generate a secret key based on the password
            SecretKey secretKey = CryptoUtil.generateAESKey(password);
            String encryptedUsername = CryptoUtil.encrypt(username, secretKey);
            String hashedPassword = CryptoUtil.hashPassword(password);



            if (authenticateUser(encryptedUsername, hashedPassword, secretKey)) {
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
                        String queuePrinter = scanner.next();;
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
