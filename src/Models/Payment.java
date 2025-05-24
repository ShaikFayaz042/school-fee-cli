package Models;

public class Payment {
    private int studentId;
    private int amount;
    private String date;
    private String transactionId;

    // Updated constructor to include transactionId
    public Payment(int studentId, String date, int amount, String transactionId) {
        this.studentId = studentId;
        this.date = date;
        this.amount = amount;
        this.transactionId = transactionId;
    }

    // Overloaded constructor (for backward compatibility, if needed)
    public Payment(int studentId, String date, int amount) {
        this(studentId, date, amount, generateTransactionId());
    }

    public int getStudentId() {
        return studentId;
    }

    public int getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    // Converts payment to string format for file storage
    public String toFileString() {
        return studentId + "," + amount + "," + date + "," + transactionId;
    }

    // Parses a payment from file string
    public static Payment fromFileString(String line) {
        String[] parts = line.split(",");
        int studentId = Integer.parseInt(parts[0]);
        int amount = Integer.parseInt(parts[1]);
        String date = parts[2];
        String transactionId = parts.length > 3 ? parts[3] : generateTransactionId(); // fallback for older entries
        return new Payment(studentId, date, amount, transactionId);
    }

    // Generates a random transaction ID
    private static String generateTransactionId() {
        int randomNum = 100000 + (int)(Math.random() * 900000); // 6-digit
        return "TXN" + randomNum;
    }
}
