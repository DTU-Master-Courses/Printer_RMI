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

    public void print(String file, String printer) throws RemoteException {
        String printJob = String.format("print(): The file name is %s on printer %s", file, printer);
        String job = String.format("%s:%s", file, printer);
        printQueue.add(job);
        System.out.println(printJob);
    }

    public void queue(String printer) throws RemoteException {
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

    public void topQueue(String printer, int job) throws RemoteException {
        // Implement the topQueue method here
        System.out.println("topQueue():  Moving job " + job + " to the top of the queue for printer: " + printer);
        // Add your implementation here
    }

}