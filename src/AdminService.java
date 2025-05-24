import Models.FeeStructure;
import Models.Payment;
import Models.Student;
import java.util.*;

public class AdminService {
    private List<Student> students;
    private List<Payment> payments;
    private List<FeeStructure> feeStructures;

    public AdminService() {
        this.students = FeeManager.loadStudents();
        this.payments = FeeManager.loadPayments();
        this.feeStructures = FeeManager.loadFeeStructures();
    }

    public void showMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View All Students Fee Status");
            System.out.println("2. View Pending Payments");
            System.out.println("3. View Completed Payments");
            System.out.println("4. Send Payment Reminders");
            System.out.println("5. Add Student");
            System.out.println("6. Remove Student");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();  // Clear buffer

            switch (choice) {
                case 1:
                    viewAllStatus();
                    break;
                case 2:
                    viewPending();
                    break;
                case 3:
                    viewCompleted();
                    break;
                case 4:
                    sendReminders();
                    break;
                case 5:
                    addStudent(sc);
                    break;
                case 6:
                    removeStudent(sc);
                    break;
                case 0:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    private void viewAllStatus() {
        System.out.println("\n--- Student Fee Status ---");
        for (Student s : students) {
            int total = getTotalFee(s.getClassName());
            int paid = s.getFeesPaid();
            int due = total - paid;
            System.out.println("ID: " + s.getId() + ", Name: " + s.getName()
                    + ", Class: " + s.getClassName()
                    + ", Paid: " + paid + ", Due: " + due);
        }
    }

    private void viewPending() {
        System.out.println("\n--- Pending Payments ---");
        for (Student s : students) {
            int total = getTotalFee(s.getClassName());
            int paid = s.getFeesPaid();
            if (paid < total) {
                System.out.println("ID: " + s.getId() + ", Name: " + s.getName() + ", Due: " + (total - paid));
            }
        }
    }

    private void viewCompleted() {
        System.out.println("\n--- Completed Payments ---");
        for (Student s : students) {
            int total = getTotalFee(s.getClassName());
            int paid = s.getFeesPaid();
            if (paid >= total) {
                System.out.println("ID: " + s.getId() + ", Name: " + s.getName() + ", Status: PAID");
            }
        }
    }

    private void sendReminders() {
        System.out.println("\n--- Sending Reminders ---");
        for (Student s : students) {
            int total = getTotalFee(s.getClassName());
            int paid = s.getFeesPaid();
            if (paid < total) {
                System.out.println("Reminder sent to: " + s.getName() + " (Due: " + (total - paid) + ")");
            }
        }
    }

    private void addStudent(Scanner sc) {
        System.out.println("\n--- Add Student ---");
        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();  // clear buffer

        for (Student s : students) {
            if (s.getId() == id) {
                System.out.println("Student ID already exists.");
                return;
            }
        }

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Class Name: ");
        String className = sc.nextLine();

        Student newStudent = new Student(id, name, className, 0);
        students.add(newStudent);
        FeeManager.saveStudents(students);
        System.out.println("Student added successfully.");
    }

    private void removeStudent(Scanner sc) {
        System.out.println("\n--- Remove Student ---");
        System.out.print("Enter Student ID to remove: ");
        int id = sc.nextInt();

        Student toRemove = null;
        for (Student s : students) {
            if (s.getId() == id) {
                toRemove = s;
                break;
            }
        }

        if (toRemove == null) {
            System.out.println("Student not found.");
        } else {
            students.remove(toRemove);
            FeeManager.saveStudents(students);
            System.out.println("Student removed successfully.");
        }
    }

    private int getTotalFee(String className) {
        for (FeeStructure f : feeStructures) {
            if (f.getClassName().equals(className)) {
                return f.getTotalFees();
            }
        }
        return 0;
    }
}
