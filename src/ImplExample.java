import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// Implementing the remote interface
public class ImplExample extends UnicastRemoteObject implements Operations {

    // Implementing the interface method
    private List<String> printQueue;
    private static Map<String, String> userPasswords = new HashMap<>();
    private static Map<String, Session> sessions = new HashMap<>();
    private static final long SESSION_TIMEOUT = 300000; // 5 minutes

    protected ImplExample() throws RemoteException {
        super();
        printQueue = new ArrayList<>();
        userPasswords.put("user1", "password1");
        userPasswords.put("user2", "password2");
        userPasswords.put("Ran", "0119");
    }

    
    public String authenticate(String username, String password) throws RemoteException {
        if (userPasswords.containsKey(username) && userPasswords.get(username).equals(password)) {
            String sessionToken = UUID.randomUUID().toString();
            sessions.put(sessionToken, new Session(username, System.currentTimeMillis()));
            return sessionToken;
        } else {
            throw new RemoteException("Invalid credentials");
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

    
    public void performOperation(String sessionToken, String operation) throws RemoteException {
        if (isSessionValid(sessionToken)) {
            String username = sessions.get(sessionToken).getUsername();
            System.out.println("Performing operation for user: " + username);
            // Perform the operation
            // Example operation: add to print queue
            
        } else {
            throw new RemoteException("Session expired or invalid");
        }
    }

    public void print(String sessionToken, String file, String printer) throws RemoteException {
        if (isSessionValid(sessionToken)) {
            String username = sessions.get(sessionToken).getUsername();
            System.out.println("Performing operation for user: " + username);
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
        System.out.println("Performing operation for user: " + username);
        // Perform the operation
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
    } else {
        throw new RemoteException("Session expired or invalid");
    }
}


    public void topQueue(String sessionToken, String printer, int job) throws RemoteException {
    if (isSessionValid(sessionToken)) {
        String username = sessions.get(sessionToken).getUsername();
        System.out.println("Performing operation for user: " + username);
        // Perform the operation
        // Implement the topQueue method here
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
        
    } else {
        throw new RemoteException("Session expired or invalid");
    }
}

    public void start(String sessionToken) throws RemoteException {
    if (isSessionValid(sessionToken)) {
        String username = sessions.get(sessionToken).getUsername();
        System.out.println("Performing operation for user: " + username);
        // Perform the operation
        // Implement the start method here
        try {
            Thread.sleep(3000);
            System.out.println("start(): The print server is ready.");
        } catch (InterruptedException e) {
            System.out.println("start(): Interrupted while sleeping");
            Thread.currentThread().interrupt();
        }
        
    } else {
        throw new RemoteException("Session expired or invalid");
    }
}

    public void stop(String sessionToken) throws RemoteException {
    if (isSessionValid(sessionToken)) {
        String username = sessions.get(sessionToken).getUsername();
        System.out.println("Performing operation for user: " + username);
        // Perform the operation
        // Implement the start method here
        try {
            Thread.sleep(3000);
            System.out.println("stop(): The print server is stopped.");
        } catch (InterruptedException e) {
            System.out.println("stop(): Interrupted while sleeping");
            Thread.currentThread().interrupt();
        }  
        
    } else {
        throw new RemoteException("Session expired or invalid");
    }
}
    public void restart(String sessionToken) throws RemoteException {
    if (isSessionValid(sessionToken)) {
        String username = sessions.get(sessionToken).getUsername();
        System.out.println("Performing operation for user: " + username);
        // Perform the operation
         // Implement the start method here
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("restart(): Interrupted while sleeping");
            Thread.currentThread().interrupt();
        }
        printQueue.clear();
        System.out.println("restart(): The print queue is clean, the print server is restarted.");     
    } else {
        throw new RemoteException("Session expired or invalid");
    }
}

    public void status(String sessionToken, String printer) throws RemoteException {    
        if (isSessionValid(sessionToken)) {
            String username = sessions.get(sessionToken).getUsername();
            System.out.println("Performing operation for user: " + username);
            // Perform the operation
            // Check the status of the given printer
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
            
        } else {
            throw new RemoteException("Session expired or invalid");
        }
    }
        

    public void readConfig(String sessionToken,String parameter) throws RemoteException {
        if (isSessionValid(sessionToken)) {
            String username = sessions.get(sessionToken).getUsername();
            System.out.println("Performing operation for user: " + username);
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
            
        } else {
            throw new RemoteException("Session expired or invalid");
        }
    }
    public void setConfig(String sessionToken, String parameter, String value) throws RemoteException {
        if (isSessionValid(sessionToken)) {
            String username = sessions.get(sessionToken).getUsername();
            System.out.println("Performing operation for user: " + username);
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
            
        } else {
            throw new RemoteException("Session expired or invalid");
        }
    }

}