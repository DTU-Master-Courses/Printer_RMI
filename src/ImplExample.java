import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

// Implementing the remote interface
public class ImplExample implements Operations {

    // Implementing the interface method
   /* public void printMsg() {
        System.out.println("This is an example RMI program");
    }*/
    private static List<String> printQueue = new ArrayList<>();
    private static UserPermission userPermission;
    static {
        // Load user permissions from a JSON file
        userPermission = JsonUtil.loadUserPermission("user_permissions.json");
    }
    

    @Override
    public void print(String file, String printer, String username) throws RemoteException {
        checkPermission(username, "print");
        String printJob = String.format("print(): The file name is %s on printer %s", file, printer);
        String job = String.format("%s:%s", file, printer);
        printQueue.add(job);
        System.out.println(printJob);
    }

    @Override
    public void queue(String printer, String username) throws RemoteException {
        checkPermission(username, "queue");
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
    }

    @Override
    public void topQueue(String printer, int job, String username) throws RemoteException {
        checkPermission(username, "topQueue");
        // Implement the topQueue method here
        System.out.println("topQueue():  Moving job " + job + " to the top of the queue for printer: " + printer);
        // Add your implementation here
    }

     @Override
    public void start(String username) throws RemoteException {
        checkPermission(username, "start");
        System.out.println("start(): Starting print jobs for user " + username);
        // Add your implementation to start print jobs
    }

    @Override
    public void stop(String username) throws RemoteException {
        checkPermission(username, "stop");
        System.out.println("stop(): Stopping print jobs for user " + username);
        // Add your implementation to stop print jobs
    }

    private void checkPermission(String username, String operation) {
        // Check if the user has permission for the given operation
        if (userPermission != null) {
            List<User> users = userPermission.getUsers();
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    if (user.getPermissions().contains(operation)) {
                        return; // User has permission
                    } else {
                        throw new SecurityException("User does not have permission for operation: " + operation);
                    }
                }
            }
        }
        throw new SecurityException("User not found or does not have permission for operation: " + operation);
    }

}