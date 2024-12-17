import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

// Implementing the remote interface
public class ImplExample extends UnicastRemoteObject implements Operations {

    // Implementing the interface method
    private List<String> printQueue;
    private static Map<String, String> userPasswords = new HashMap<>();
    private static Map<String, Session> sessions = new HashMap<>();
    private static final long SESSION_TIMEOUT = 300000; // 5 minutes
    private static Map<String, Set<String>> acl = new HashMap<>();
    // private Server server;

    protected ImplExample() throws RemoteException {
        super();
        // server = new Server();
        printQueue = new ArrayList<>();
        // userPasswords.put("user1", "password1");
        // userPasswords.put("user2", "password2");
        // userPasswords.put("Ran", "0119");
        // userPasswords.put("Alice", "alice");
        // userPasswords.put("Bob", "bob");
        // userPasswords.put("Cecilia", "cecilia");
        // userPasswords.put("David", "david");
        // userPasswords.put("Erica", "erica");
        // userPasswords.put("Fred", "fred");
        // userPasswords.put("George", "george");
        loadPolicy("policy.txt");
        loadUserPasswords("passwords.txt");
        // Print out the hashed passwords
        userPasswords.forEach((user, hashedPassword) -> {
        System.out.println("User: " + user + ", Hashed Password: " + hashedPassword);
        });
    }

    private void loadUserPasswords(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String user = parts[0].trim();
                    String hashedPassword = parts[1].trim();
                    userPasswords.put(user, hashedPassword);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public String authenticate(String username, String password) throws RemoteException {
        try {
            // String encryptedPassword = CryptoUtil.encrypt(password);
            if (userPasswords.containsKey(username) && userPasswords.get(username).equals(password)) {
                String sessionToken = UUID.randomUUID().toString();
                sessions.put(sessionToken, new Session(username, System.currentTimeMillis()));
                return sessionToken;
            } else {
                throw new RemoteException("Invalid credentials");
            }
        } catch (Exception e) {
            throw new RemoteException("Error during authentication", e);
        }
    }

    private boolean isSessionValid(String sessionToken) {
        Session session = sessions.get(sessionToken);
        if (session == null) {
            return false;
        }
        long currentTime = System.currentTimeMillis();
        if (currentTime - session.getTimestamp() > SESSION_TIMEOUT) {
            sessions.remove(sessionToken);
            return false;
        }
        session.setTimestamp(currentTime); // Refresh session timestamp
        return true;
    }

    private void loadPolicy(String policyFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(policyFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                String user = parts[0].trim();
                String[] permissions = parts[1].trim().split(",");
                acl.put(user, new HashSet<>(Arrays.asList(permissions)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected boolean hasPermission(String user, String operation) {
        Set<String> permissions = acl.get(user);
        return permissions != null && (permissions.contains("all") || permissions.contains(operation));
    }
  
    // public void performOperation(String sessionToken, String operation) throws RemoteException {
    //     if (isSessionValid(sessionToken)) {
    //         String username = sessions.get(sessionToken).getUsername();
    //         System.out.println("Performing operation for user: " + username);
    //         // Perform the operation
    //         // Example operation: add to print queue
            
    //     } else {
    //         throw new RemoteException("Session expired or invalid");
    //     }
    // }

    public void print(String sessionToken, String file, String printer) throws RemoteException {
        if (isSessionValid(sessionToken)) {
            String username = sessions.get(sessionToken).getUsername();
            System.out.println("Performing operation print for user: " + username);
            // Perform the operation
            // Example operation: add to print queue
            String printJob = String.format("print(): The file name is %s on printer %s", file, printer);
            String job = String.format("%s:%s", file, printer);
            printQueue.add(job);
            System.out.println(printJob);
        } else {
            throw new RemoteException("Session expired or invalid");
        }
    }
    

    public void queue(String sessionToken, String printer) throws RemoteException {
    if (isSessionValid(sessionToken)) {
        String username = sessions.get(sessionToken).getUsername();
        if (hasPermission(username, "restart")) {
            // Code to queue the server
            System.out.println("Performing operation queue for user: " + username);
            // Perform the operation
            // Implement the queue method here
            try {
                // Example operation: add to print queue
                //lists the print queue for a given printer on the user's display in lines of the form <job number>   <file name>
                System.out.println("queue(): Print queue for printer " + printer + ":");
                int counter = 0;
                for (int i = 0; i < printQueue.size(); i++) {
                    String[] parts = printQueue.get(i).split(":");
                    counter ++;
                    if (parts.length >= 2 && parts[1].equals(printer) && counter <= printQueue.size() ) {
                        System.out.println("Job number: " + (i + 1) + ", File name: "+ parts[0]);
                    } 
                    else if(!parts[1].equals(printer) && counter >= printQueue.size()) {
                        System.out.println(printer + " does not exist in the queue.");
                    //     // Handle invalid format
                    //     System.out.println("Invalid print queue entry: " + printQueue.get(i));
                    }
                    // 
                }                          
            } catch (Exception e) {
              
                Thread.currentThread().interrupt();
            }
          
            System.out.println("restart(): The print queue is clean, the print server is restarted.");     
        } else {
            System.out.println("Access denied for user: " + username);
        }
    } else {
        throw new RemoteException("Session expired or invalid");
    }
}


    public void topQueue(String sessionToken, String printer, int job) throws RemoteException {
    if (isSessionValid(sessionToken)) {
        String username = sessions.get(sessionToken).getUsername();
        if (hasPermission(username, "topQueue")) {
            // Code to topQueue the server
            System.out.println("Performing operation topQueue for user: " + username);
            // Perform the operation
            // Implement the topQueue method here
            try {
                System.out.println("topQueue():  Moving job " + job + " to the top of the queue for printer: " + printer);
                if (job <= 0 || job > printQueue.size()) {
                    System.out.println("Invalid job number: " + job);
                    return;
                }
                String jobEntry = printQueue.get(job - 1);
                String[] parts = jobEntry.split(":");
                if (parts.length >= 2 && parts[1].equals(printer)) {
                    printQueue.remove(job - 1);
                    printQueue.add(0, jobEntry);
                    System.out.println("Job " + job + " moved to the top of the queue for printer: " + printer);
                    queue(sessionToken,printer);
                } else {
                    System.out.println("Job " + job + " does not belong to printer: " + printer);
                }
                 
            } catch (Exception e) {
              
                Thread.currentThread().interrupt();
            }
          
            System.out.println("restart(): The print queue is clean, the print server is restarted.");     
        } else {
            System.out.println("Access denied for user: " + username);
        }
        
    } else {
        throw new RemoteException("Session expired or invalid");
    }
}

    public void start(String sessionToken) throws RemoteException {
    if (isSessionValid(sessionToken)) {
        String username = sessions.get(sessionToken).getUsername();
        if (hasPermission(username, "start")) {
            // Code to start the server
            System.out.println("Performing operation start for user: " + username);
            try {
                Thread.sleep(3000);
                System.out.println("start(): The print server is ready.");
            } catch (InterruptedException e) {
                System.out.println("start(): Interrupted while sleeping");
                Thread.currentThread().interrupt();
            }
        } else {
            System.out.println("Access denied for user: " + username);
        }
    } else {
        throw new RemoteException("Session expired or invalid");
    }
}

    public void stop(String sessionToken) throws RemoteException {
    if (isSessionValid(sessionToken)) {
        String username = sessions.get(sessionToken).getUsername();
        if (hasPermission(username, "stop")) {
            // Code to start the server
            System.out.println("Performing operation stop for user: " + username);
            // Perform the operation
            // Implement the stop method here
            try {
                Thread.sleep(3000);
                System.out.println("stop(): The print server is stopped.");
            } catch (InterruptedException e) {
                System.out.println("stop(): Interrupted while sleeping");
                Thread.currentThread().interrupt();
            }
            printQueue.clear();  
        } else {
            System.out.println("Access denied for user: " + username);
        }       
    } else {
        throw new RemoteException("Session expired or invalid");
    }
}
    public void restart(String sessionToken) throws RemoteException {
    if (isSessionValid(sessionToken)) {
        String username = sessions.get(sessionToken).getUsername();
        if (hasPermission(username, "restart")) {
            // Code to start the server
            System.out.println("Performing operation restart for user: " + username);
            // Perform the operation
            // Implement the restart method here
            try {
                Thread.sleep(3000);              
            } catch (InterruptedException e) {
                System.out.println("restart(): Interrupted while sleeping");
                Thread.currentThread().interrupt();
            }
            printQueue.clear();  
            System.out.println("restart(): The print queue is clean, the print server is restarted.");     
        } else {
            System.out.println("Access denied for user: " + username);
        }
     
    } else {
        throw new RemoteException("Session expired or invalid");
    }
}

    public void status(String sessionToken, String printer) throws RemoteException {    
        if (isSessionValid(sessionToken)) {
            String username = sessions.get(sessionToken).getUsername();
            if (hasPermission(username, "status")) {
                // Code to start the server
                System.out.println("Performing operation status for user: " + username);
                // Perform the operation
                // Implement the status method here
                try {
                    System.out.println("status(): The status of printer " + printer + " is:");
                    boolean printerExists = false;
                    int counter = 0;
                    for (int i = 0; i < printQueue.size(); i++) {
                        String[] parts = printQueue.get(i).split(":");
                        counter++;
                        if (parts.length >= 2 && parts[1].equals(printer)) {
                            System.out.println("Job number: " + (i + 1) + ", File name: " + parts[0]);
                            printerExists = true;
                        }
                    }
                    if (!printerExists) {
                        System.out.println(printer + " does not exist in the queue.");
                    }                             
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
                
            } else {
                System.out.println("Access denied for user: " + username);
            }
       
            
        } else {
            throw new RemoteException("Session expired or invalid");
        }
    }
        

    public void readConfig(String sessionToken,String parameter) throws RemoteException {
        if (isSessionValid(sessionToken)) {
            String username = sessions.get(sessionToken).getUsername();
            if (hasPermission(username, "readConfig")) {
                // Code to readConfig the server
                System.out.println("Performing operation readConfig for user: " + username);
                // Perform the operation
                // Implement the readConfig method here
                try {
                    // Perform the operation
                    // Implement the readConfig method here
                    System.out.println("readConfig(): The value of the parameter " + parameter + " is: ");
                    switch (parameter) {
                        case "port":
                            System.out.println("5099");
                            break;
                        case "queue":
                            System.out.println(printQueue);
                            break;
                        default:
                            System.out.println("Invalid parameter: " + parameter);
                            break;
            }                              
                } catch (Exception e) {
                  
                    Thread.currentThread().interrupt();
                }             
                System.out.println("restart(): The print queue is clean, the print server is restarted.");     
            } else {
                System.out.println("Access denied for user: " + username);
            }   
        } else {
            throw new RemoteException("Session expired or invalid");
        }
    }
    public void setConfig(String sessionToken, String parameter, String value) throws RemoteException {
        if (isSessionValid(sessionToken)) {
            String username = sessions.get(sessionToken).getUsername();
            if (hasPermission(username, "setConfig")) {
                // Code to setConfig the server
                System.out.println("Performing operation setConfig for user: " + username);
                // Perform the operation
                // Implement the setConfig method here
                try {
                    // Perform the operation
                    // Implement the setConfig method here
                    System.out.println("setConfig(): The parameter " + parameter + " is set to " + value);
                    switch (parameter) {
                        case "port":
                            System.out.println("The port is set to " + value);
                            break;
                        default:
                            System.out.println("Invalid parameter: " + parameter+ " , the only available parameter name is port. ");
                            break;
                    }
                               
                } catch (Exception e) {
                  
                    Thread.currentThread().interrupt();
                }
              
                System.out.println("restart(): The print queue is clean, the print server is restarted.");     
            } else {
                System.out.println("Access denied for user: " + username);
            }
   
        } else {
            throw new RemoteException("Session expired or invalid");
        }
    }

}