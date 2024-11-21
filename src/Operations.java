import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Operations extends Remote {
    void print(String sessionToken, String filename, String printer) throws RemoteException;
    void queue(String sessionToken, String printer) throws RemoteException;
    void topQueue(String sessionToken, String printer, int job) throws RemoteException;
    void start(String sessionToken) throws RemoteException;
    void stop(String sessionToken) throws RemoteException;
    void restart(String sessionToken) throws RemoteException;
    void status(String sessionToken, String printer) throws RemoteException;
    void readConfig(String sessionToken, String parameter) throws RemoteException;
    void setConfig(String sessionToken, String parameter, String value) throws RemoteException;
    String authenticate(String username, String password) throws RemoteException;
    // void performOperation(String sessionToken, String operation) throws RemoteException;
    // String hashPassword(String password);
}
