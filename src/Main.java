import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        FileAuthService auth = new FileAuthService();  // Ya FileAuthService

        System.out.println("=== School Fee Management System ===");
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        String role = auth.login(username, password);

        if (role == null) {
            System.out.println("Invalid credentials.");
            return;
        }

        System.out.println("Login successful! Role: " + role);

        if (role.equals("admin")) {
            AdminService admin = new AdminService();
            admin.showMenu();
        } 
        else if (role.startsWith("student_")) {
            // Extract studentId from role string
            int studentId = Integer.parseInt(role.split("_")[1]);
            StudentService student = new StudentService(studentId);
            student.showMenu();
        } 
        else {
            System.out.println("No menu implemented for role: " + role);
        }
    }
}
