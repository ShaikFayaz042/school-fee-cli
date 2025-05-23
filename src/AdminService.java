import Models.Student;
import Models.Payment;
import Models.FeeStructure;

import java.util.*;

public class AdminService {
    private List <Student> students;
    private List <Payment> payments;
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
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

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

    private int getTotalFee(String className) {
        for (FeeStructure f : feeStructures) {
            if (f.getClassName().equals(className)) {
                return f.getTotalFees();
            }
        }
        return 0;
    }
}
