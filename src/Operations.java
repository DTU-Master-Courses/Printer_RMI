import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Operations extends Remote {
    void print(String filename, String printer) throws RemoteException;
    void queue(String printer) throws RemoteException;
    void topQueue(String printer, int job) throws RemoteException;
}
