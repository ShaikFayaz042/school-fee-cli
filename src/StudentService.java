import Models.FeeStructure;
import Models.Payment;
import Models.Student;
import java.time.LocalDate;
import java.util.*;


public class StudentService {
    private int studentId;
    private Student student;
    private List<Payment> payments;
    private List<FeeStructure> feeStructures;

    public StudentService(int studentId) {
        this.studentId = studentId;
        this.student = FeeManager.getStudentById(studentId);
        this.payments = FeeManager.loadPayments();
        this.feeStructures = FeeManager.loadFeeStructures();
    }

    public void showMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n--- Student Menu ---");
            System.out.println("1. View Student Details");
            System.out.println("2. Make Payment");
            System.out.println("3. View Payment History");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    viewStudentDetails();
                    break;
                case 2:
                    makePayment(sc);
                    break;
                case 3:
                    viewPaymentHistory();
                    break;
                case 0:
                    System.out.println("Logged out.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private void viewStudentDetails() {
        int totalFee = getTotalFee(student.getClassName());
        int paid = student.getFeesPaid();
        int due = totalFee - paid;
        System.out.println("\n--- Student Details ---");
        System.out.println("ID: " + student.getId());
        System.out.println("Name: " + student.getName());
        System.out.println("Class: " + student.getClassName());
        System.out.println("Total Fees: " + totalFee);
        System.out.println("Paid: " + paid);
        System.out.println("Due: " + due);
    }

    private void makePayment(Scanner sc) {
        int totalFee = getTotalFee(student.getClassName());
        int paid = student.getFeesPaid();
        int due = totalFee - paid;

        if (due <= 0) {
            System.out.println("No dues left. All fees paid.");
            return;
        }

        System.out.print("Enter amount to pay (due: " + due + "): ");
        int amount = sc.nextInt();

        if (amount <= 0 || amount > due) {
            System.out.println("Invalid amount.");
            return;
        }

        // Update student's feesPaid
        student.setFeesPaid(paid + amount);
        FeeManager.updateStudent(student);

        // Create new payment with current date (ISO format) and random transaction ID
        String date = LocalDate.now().toString();
        String txnId = "TXN" + new Random().nextInt(999999);
        Payment payment = new Payment(student.getId(), date, amount, txnId);

        // Append this payment to file
        FeeManager.addPayment(payment);

        // Also add to local payments list for session consistency
        payments.add(payment);

        System.out.println("Payment successful! Transaction ID: " + txnId);
    }

    private void viewPaymentHistory() {
        System.out.println("\n--- Payment History ---");
        boolean found = false;
        for (Payment p : payments) {
            if (p.getStudentId() == student.getId()) {
                System.out.println("Amount: " + p.getAmount() + ", Date: " + p.getDate() + ", Transaction ID: " + p.getTransactionId());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No payments found.");
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
