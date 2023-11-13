import java.util.List;

// User class to represent individual users
public class User {
    private String username;
    private String role;
    private List<String> permissions;

    // Constructor
    public User(String username, String role, List<String> permissions) {
        this.username = username;
        this.role = role;
        this.permissions = permissions;
    }

    // Getter and setter methods
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
