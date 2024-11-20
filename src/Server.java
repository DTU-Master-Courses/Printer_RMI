import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// public class Server extends ImplExample {
//     public Server() throws RemoteException {
//         super();
//     }

//     public static void main(String[] args) {
//         try {
//             // Instantiating the implementation class
//             ImplExample obj = new ImplExample();

//             // Exporting the object of implementation class
//             // (here we are exporting the remote object to representative stub)
//             // Operations stub = (Operations) UnicastRemoteObject.exportObject(obj, 0);

//             // Binding the remote object (stub) in the registry
//             Registry registry = LocateRegistry.createRegistry(5099);

//             // The stub's primary purpose is to enable method calls on the client side to be forwarded to the remote object on the server side.

//             registry.rebind("print", new ImplExample());
//             System.err.println("Server ready");
//         } catch (RemoteException e) {
//             System.err.println("Server exception: " + e);
//             e.printStackTrace();
//         } catch (Exception e) {
//             System.err.println("Unexpected exception: " + e);
//             e.printStackTrace();
//         }
//     }
// }


public class Server extends ImplExample {
    private static Map<String, String> userPasswords = new HashMap<>();
    private static Map<String, Session> sessions = new HashMap<>();
    private static final long SESSION_TIMEOUT = 300000; // 5 minutes

    public Server() throws RemoteException {
        super();
        // Initialize user passwords (in a real application, use a secure storage)
        userPasswords.put("user1", "password1");
        userPasswords.put("user2", "password2");
        userPasswords.put("Ran", "0119");
    }

    public static void main(String[] args) {
        try {
            ImplExample obj = new ImplExample();
            // Operations stub = (Operations) UnicastRemoteObject.exportObject(obj, 0);
            Registry registry = LocateRegistry.createRegistry(5099);
            registry.rebind("print", new ImplExample());
            System.err.println("Server ready");
        } catch (RemoteException e) {
            System.err.println("Server exception: " + e);
            e.printStackTrace();
        }
    }

    public String authenticate(String username, String password) throws RemoteException {
        if (userPasswords.containsKey(username) && userPasswords.get(username).equals(password)) {
            String sessionToken = UUID.randomUUID().toString();
            sessions.put(sessionToken, new Session(username, System.currentTimeMillis()));
            return sessionToken;
        } else {
            throw new RemoteException("Invalid credentials");
        }
    }

    private boolean isSessionValid(String sessionToken) {
        Session session = sessions.get(sessionToken);
        if (session == null) {
            return false;
        }
        long currentTime = System.currentTimeMillis();
        if (currentTime - session.getTimestamp() > SESSION_TIMEOUT) {
            sessions.remove(sessionToken);
            return false;
        }
        session.setTimestamp(currentTime); // Refresh session timestamp
        return true;
    }

    public void performOperation(String sessionToken, String operation) throws RemoteException {
        if (isSessionValid(sessionToken)) {
            String username = sessions.get(sessionToken).getUsername();
            System.out.println("Performing operation for user: " + username);
            // Perform the operation
        } else {
            throw new RemoteException("Session expired or invalid");
        }
    }
}

class Session {
    private String username;
    private long timestamp;

    public Session(String username, long timestamp) {
        this.username = username;
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}