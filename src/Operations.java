import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Operations extends Remote {
    void print(String filename, String printer, String username) throws RemoteException;

    void queue(String printer, String username) throws RemoteException;

    void topQueue(String printer, int job, String username) throws RemoteException;
    void start(String username) throws RemoteException;
    void stop(String username) throws RemoteException;

}