public interface AuthenticationProvider {
    boolean authenticate(String username, String password);
    boolean createUser(String username, String password);
    boolean updateUserPassword(String username, String newPassword);
    boolean deleteUser(String username);
}
