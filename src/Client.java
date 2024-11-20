import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.io.File;
import javax.crypto.SecretKey;


public class Client {
    private static String username;
    private static String password;
    private static String sessionToken;

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(5099);
            Operations operation = (Operations) registry.lookup("print");
            //Operations queue = (Operations) registry.lookup("queue");
            //Operations toQueue = (Operations) registry.lookup("toQueue");
            
            // Prompt the user to select an action
            Scanner scanner = new Scanner(System.in);
            

            // Prompt the user for credentials
            promptForCredentials(scanner);

            // Authenticate and get session token
            sessionToken = operation.authenticate(username, password);
            System.out.println("Authenticated with session token: " + sessionToken);

            // Perform operations
            while (true) {
                
                // System.out.println("Enter operation (or 'exit' to quit): ");
                // String operation_n = scanner.next();
                // if (operation_n.equals("exit")) {
                //     break;
                try {
                    int choice = displayActionMenu(scanner);
                    switch (choice) {
                        case 1:
                            // Print a file
                            System.out.print("Enter the filename: ");
                            String filename = scanner.next();
                            System.out.print("Enter the printer name: ");
                            String printer = scanner.next();
                            operation.print(sessionToken,filename, printer);                            
                            break;
                            case 2:
                            // List the print queue
                            System.out.print("Enter the printer name: ");
                            String queuePrinter = scanner.next();;
                            operation.queue(sessionToken, queuePrinter);
                            break;
                        case 3:
                            // Move a job to the top of the queue
                            System.out.print("Enter the printer name: ");
                            String topQueuePrinter = scanner.next();
                            System.out.print("Enter the job number: ");
                            int jobNumber = scanner.nextInt();
                            operation.topQueue(sessionToken,topQueuePrinter, jobNumber);
                            break;
                        case 4:
                        // starts the print server
                            System.out.println("The print server is starting, pleae wait...");
                            operation.start(sessionToken);
                            break;
                        case 5:
                            // stops the print server
                            System.out.println("The print server is shutting down, please wait...");
                            operation.stop(sessionToken);
                            break;
                        case 6:
                         // stops the print server, clears the print queue and starts the print server again
                            System.out.println("The print server is restarting, please wait...");
                            operation.restart(sessionToken);
                            break;
                        case 7:
                            // prints status of printer on the user’s display
                            System.out.print("Enter the printer name: ");
                            String printer_ = scanner.next();
                            operation.status(sessionToken, printer_);
                            break;
                        case 8:
                        // prints the value of the parameter on the print server to the user’s display
                            System.out.print("Enter the parameter name: "); // only take the parameter name port
                            String parameter = scanner.next();
                            operation.readConfig(sessionToken, parameter);
                            break;
                        case 9:
                             // sets the parameter on the print server to value
                            System.out.print("Enter the parameter name: "); // only take the parameter name port
                            String port = scanner.next();
                            System.out.print("Enter the " +port+ " new value: "); // only take the parameter name port
                            String value = scanner.next();
                            operation.setConfig(sessionToken, port, value);
                            break;
                        default:
                            System.out.println("Invalid choice.");
                            break;
                    }
                    // operation.performOperation(sessionToken, operation_n);
                    // scanner.close();
                } catch (RemoteException e) {
                    System.err.println("Operation failed: " + e.getMessage());
                    if (e.getMessage().contains("Session expired")) {
                        System.out.println("Session expired. Please log in again.");
                        System.out.print("Enter username: ");
                        username = scanner.next();
                        System.out.print("Enter password: ");
                        password = scanner.next();
                        sessionToken = operation.authenticate(username, password);
                        System.out.println("Authenticated with session token: " + sessionToken);
                    }
                }
            }

            // Generate a secret key based on the password
            // SecretKey secretKey = CryptoUtil.generateAESKey(password);
            // String encryptedUsername = CryptoUtil.encrypt(username, secretKey);
            // String hashedPassword = CryptoUtil.hashPassword(password);
            // System.out.println("secretKey: " + secretKey);
            // System.out.println("Hashed Password: " + hashedPassword);
            // SecretKey secretKey = CryptoUtil.generateKey();

            // String encryptedPassword = CryptoUtil.encrypt(password);
            // String encryptedUsername = CryptoUtil.encrypt(username);
            // String decryptedPassword = CryptoUtil.decrypt(encryptedPassword);
            // String decryptedUsername = CryptoUtil.decrypt(encryptedUsername);
    
            // System.out.println("Encrypted Password: " + encryptedPassword);
            // System.out.println("Decrypted Password: " + decryptedPassword);

            // if (authenticateUser(encryptedUsername, encryptedPassword)) {
            //     // User is authenticated, present a list of actions
            //     switch (choice) {
            //         case 1:
            //             // Print a file
            //             System.out.print("Enter the filename: ");
            //             String filename = scanner.next();
            //             System.out.print("Enter the printer name: ");
            //             String printer = scanner.next();
            //             operation.print(filename, printer);
            //             break;
            //         case 2:
            //             // List the print queue
            //             System.out.print("Enter the printer name: ");
            //             String queuePrinter = scanner.next();;
            //             operation.queue(queuePrinter);
            //             break;
            //         case 3:
            //             // Move a job to the top of the queue
            //             System.out.print("Enter the printer name: ");
            //             String topQueuePrinter = scanner.next();
            //             System.out.print("Enter the job number: ");
            //             int jobNumber = scanner.nextInt();
            //             operation.topQueue(topQueuePrinter, jobNumber);
            //             break;
            //         case 4:
            //         // starts the print server
            //             System.out.println("The print server is starting, pleae wait...");
            //             operation.start();
            //             break;
            //         case 5:
            //             // stops the print server
            //             System.out.println("The print server is shutting down, please wait...");
            //             operation.stop();
            //             break;
            //         case 6:
            //          // stops the print server, clears the print queue and starts the print server again
            //             System.out.println("The print server is restarting, please wait...");
            //             operation.restart();
            //             break;
            //         case 7:
            //             // prints status of printer on the user’s display
            //             System.out.print("Enter the printer name: ");
            //             String printer_ = scanner.next();
            //             operation.status(printer_);
            //             break;
            //         case 8:
            //         // prints the value of the parameter on the print server to the user’s display
            //             System.out.print("Enter the parameter name: "); // only take the parameter name port
            //             String parameter = scanner.next();
            //             operation.readConfig(parameter);
            //             break;
            //         case 9:
            //              // sets the parameter on the print server to value
            //             System.out.print("Enter the parameter name: "); // only take the parameter name port
            //             String port = scanner.next();
            //             System.out.print("Enter the " +port+ " new value: "); // only take the parameter name port
            //             String value = scanner.next();
            //             operation.setConfig( port, value);
            //             break;
            //         default:
            //             System.out.println("Invalid choice.");
            //             break;
            //     }
            // } else {
            //     System.out.println("Authentication failed. Invalid username or password.");
            // }
            // scanner.close();
        } catch (Exception e) {
            System.err.println("Client exception: " + e);
            e.printStackTrace();
        }
    }

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

    // private static boolean authenticateUser(String encryptedUsername, String encryptedPassword) {
    //     try {
    //         // Read the password file and check for a matching username and password
    //         Scanner fileScanner = new Scanner(new File("passwords.txt"));
    //         String username = CryptoUtil.decrypt(encryptedUsername);
    //         //String password = CryptoUtil.decrypt(encryptedPassword, secretKey);
    //         while (fileScanner.hasNextLine()) {
    //             String line = fileScanner.nextLine();
    //             String[] parts = line.split(":");
    //             if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(encryptedPassword)) {
    //                 return true; // Authentication successful
    //             }
    //         }
    //         fileScanner.close();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }

    //     return false; // Authentication failed
    // }

    private static int displayActionMenu(Scanner scanner) {
        System.out.println("Choose an action:");
        System.out.println("1. Print a file");
        System.out.println("2. List the print queue");
        System.out.println("3. Move a job to the top of the queue");
        System.out.println("4. Starts the print server");
        System.out.println("5. Stops the print server");
        System.out.println("6. Restarts the print server");
        System.out.println("7. Prints status of printer on the user’s display");
        System.out.println("8. Prints the value of the parameter on the print server");
        System.out.println("9. Sets the parameter on the print server to value, only available parameter: port");

        int choice = -1; // Initialize to an invalid choice

        while (choice < 1 || choice > 9) {
            System.out.print("Enter your choice: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice < 1 || choice > 9) {
                    System.out.println("Invalid choice. Please select a valid option (1-9).");
                }
            } else {
                scanner.next(); // Consume the non-integer token
                System.out.println("Invalid input. Please enter a valid option (1-9).");
            }
        }

        return choice;
    }

}
