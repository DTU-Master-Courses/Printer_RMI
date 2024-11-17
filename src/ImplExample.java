import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

// Implementing the remote interface
public class ImplExample extends UnicastRemoteObject implements Operations {

    // Implementing the interface method
   /* public void printMsg() {
        System.out.println("This is an example RMI program");
    }*/
    // Constructor
    protected ImplExample() throws RemoteException {
        super();
    }

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
            queue(printer);
        } else {
            System.out.println("Job " + job + " does not belong to printer: " + printer);
        }
        
    }
    public void start() throws RemoteException {
        // Implement the start method here
        try {
            Thread.sleep(3000);
            System.out.println("start(): The print server is ready.");
        } catch (InterruptedException e) {
            System.out.println("start(): Interrupted while sleeping");
            Thread.currentThread().interrupt();
        }
       
        
    }
    public void stop() throws RemoteException {
        // Implement the start method here
        try {
            Thread.sleep(3000);
            System.out.println("stop(): The print server is stopped.");
        } catch (InterruptedException e) {
            System.out.println("stop(): Interrupted while sleeping");
            Thread.currentThread().interrupt();
        }
        
        
    }
    public void restart() throws RemoteException {
        // Implement the start method here
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("restart(): Interrupted while sleeping");
            Thread.currentThread().interrupt();
        }
        printQueue.clear();
        System.out.println("restart(): The print queue is clean, the print server is restarted.");
        
    }
    public void status(String printer) throws RemoteException {
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
    }

    public void readConfig(String parameter) throws RemoteException {
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
    }
    public void setConfig(String parameter, String value) throws RemoteException {
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
    }

}