package Models;

public class Payment {
    private int studentId;
    private int amount;
    private String date;

    public Payment(int studentId, String date, int amount) {
        this.studentId = studentId;
        this.date = date;
        this.amount = amount;
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

    public String toFileString() {
        return studentId + "," + amount + "," + date;
    }

    public static Payment fromFileString(String line) {
        String[] parts = line.split(",");
        int studentId = Integer.parseInt(parts[0]);
        int amount = Integer.parseInt(parts[1]);
        String date = parts[2];
        return new Payment(studentId, date, amount);
    }
}
