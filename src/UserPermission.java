import java.util.List;

// UserPermission class to represent the overall user permissions stored in the JSON file
public class UserPermission {
    private List<User> users;

    // Constructors
    public UserPermission(List<User> users) {
        this.users = users;
    }

    // Getter for users
    public List<User> getUsers() {
        return users;
    }

    // Setter for users
    public void setUsers(List<User> users) {
        this.users = users;
    }
}
