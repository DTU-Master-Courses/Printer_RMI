import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends ImplExample {
    public Server() throws RemoteException {
        super();
    }

    public static void main(String[] args) {
        try {
            // Instantiating the implementation class
            ImplExample obj = new ImplExample();

            // Exporting the object of implementation class
            // (here we are exporting the remote object to representative stub)
            // Operations stub = (Operations) UnicastRemoteObject.exportObject(obj, 0);

            // Binding the remote object (stub) in the registry
            Registry registry = LocateRegistry.createRegistry(5099);

            // The stub's primary purpose is to enable method calls on the client side to be forwarded to the remote object on the server side.
            registry.rebind("print", new ImplExample());
            System.err.println("Server ready");
        } catch (RemoteException e) {
            System.err.println("Server exception: " + e);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            e.printStackTrace();
        }
    }
}