import java.rmi.Remote;
import java.rmi.RemoteException;

// Creating Remote interface for our application 
public interface Hello extends Remote {
//    void printMsg() throws RemoteException;
    void print(String filename, String printer) throws RemoteException;
} 