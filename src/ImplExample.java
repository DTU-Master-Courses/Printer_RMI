import java.rmi.RemoteException;

// Implementing the remote interface
public class ImplExample implements Hello {

    // Implementing the interface method
   /* public void printMsg() {
        System.out.println("This is an example RMI program");
    }*/
    public void print(String file, String printer) throws RemoteException {
        String output = String.format("The file name is %s on printer %s", file, printer);
        System.out.println(output);
    }
} 