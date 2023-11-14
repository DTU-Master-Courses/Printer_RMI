import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

// Implementing the remote interface
public class ImplExample implements Operations {

    // Implementing the interface method
   /* public void printMsg() {
        System.out.println("This is an example RMI program");
    }*/
    private static List<PrintJob> printQueue = new ArrayList<>();
    private static UserPermission userPermission;
    static {
        // Load user permissions from a JSON file
        userPermission = JsonUtil.loadUserPermission("user_permissions.json");
    }
    

     @Override
    public void print(String file, String printer, String username) throws RemoteException {
        checkPermission(username, "print");
        int jobNumber = printQueue.size() + 1;
        PrintJob printJob = new PrintJob(jobNumber, file, printer);
        printQueue.add(printJob);
        System.out.println("print(): Job number " + jobNumber +", File name "+file+" printer "+printer+ " added to the queue");
    }
    

    @Override
    public void queue(String printer, String username) throws RemoteException {
        checkPermission(username, "queue");
        System.out.println("queue(): Print queue for printer " + printer + ":");
        for (PrintJob job : printQueue) {
            if (job.getPrinter().equals(printer)) {
                System.out.println("Job number: " + job.getJobNumber() + ", File name: " + job.getFile());
            }
        }
    }

    @Override
    public void topQueue(String printer, int job, String username) throws RemoteException {
        checkPermission(username, "topQueue");
        if (job <= 0 || job > printQueue.size()) {
            System.out.println("topQueue(): Invalid job number");
            return;
        }

        PrintJob selectedJob = printQueue.get(job - 1);
        printQueue.remove(selectedJob);
        printQueue.add(0, selectedJob);

        System.out.println("topQueue(): Moved job " + job + " to the top of the queue for printer: " + printer);
        System.out.println("The new list of queue: "+ printQueue.toString() );
    }

     @Override
    public void start(String username) throws RemoteException {
        checkPermission(username, "start");
        System.out.println("start(): Starting printer for " + username);
        // Add your implementation to start print jobs
    }

    @Override
    public void stop(String username) throws RemoteException {
        checkPermission(username, "stop");
        printQueue.clear();
        System.out.println("stop(): Stopping printer for " + username);
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