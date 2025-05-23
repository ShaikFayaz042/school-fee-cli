package Models;

public class FeeStructure {
    private String className;
    private int totalFees;

    public FeeStructure(String className, int totalFees) {
        this.className = className;
        this.totalFees = totalFees;
    }

    public String getClassName() {
        return className;
    }

    public int getTotalFees() {
        return totalFees;
    }

    @Override
    public String toString() {
        return className + "," + totalFees;
    }
}
