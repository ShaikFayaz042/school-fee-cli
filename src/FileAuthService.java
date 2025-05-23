import java.io.BufferedReader;
import java.io.FileReader;

public class FileAuthService {
    public String login(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("../data/users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String fileUser = parts[0];
                    String filePass = parts[1];
                    String role = parts[2];
                    if (fileUser.equals(username) && filePass.equals(password)) {
                        return role;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading users.txt: " + e.getMessage());
        }
        return null;
    }
}
