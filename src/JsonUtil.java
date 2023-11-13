import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonUtil {
    public static UserPermission loadUserPermission(String filePath) {
        List<User> users = new ArrayList<>();

        try {
            JSONTokener tokener = new JSONTokener(new FileReader(filePath));
            JSONObject jsonObject = new JSONObject(tokener);

            JSONArray usersArray = jsonObject.getJSONArray("users");

            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject userObject = usersArray.getJSONObject(i);
                String username = userObject.getString("username");
                String role = userObject.getString("role");
                List<String> permissions = jsonArrayToList(userObject.getJSONArray("permissions"));

                User user = new User(username, role, permissions);
                users.add(user);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return new UserPermission(users);
    }

    private static List<String> jsonArrayToList(JSONArray jsonArray) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }
}
