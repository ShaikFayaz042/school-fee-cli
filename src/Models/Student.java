package Models;

public class Student {
    private int id;
    private String name;
    private String className;
    private int feesPaid;

    public Student(int id, String name, String className, int feesPaid) {
        this.id = id;
        this.name = name;
        this.className = className;
        this.feesPaid = feesPaid;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public int getFeesPaid() {
        return feesPaid;
    }

    public void setFeesPaid(int amount) {
        this.feesPaid = amount;
    }

    @Override
    public String toString() {
        return id + "," + name + "," + className + "," + feesPaid;
    }
}
