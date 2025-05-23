import Models.Student;
import Models.Payment;
import Models.FeeStructure;

import java.io.*;
import java.util.*;

public class FeeManager {
    public static List<Student> loadStudents() {
        List<Student> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("../data/students.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                list.add(new Student(
                    Integer.parseInt(parts[0]),
                    parts[1],
                    parts[2],
                    Integer.parseInt(parts[3])
                ));
            }
        } catch (Exception e) {
            System.out.println("Error loading students: " + e.getMessage());
        }
        return list;
    }

    public static List<Payment> loadPayments() {
        List<Payment> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("../data/payments.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                list.add(new Payment(
                    Integer.parseInt(parts[0]),
                    parts[1],
                    Integer.parseInt(parts[2])
                ));
            }
        } catch (Exception e) {
            System.out.println("Error loading payments: " + e.getMessage());
        }
        return list;
    }

    public static List<FeeStructure> loadFeeStructures() {
        List<FeeStructure> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("../data/fee_structure.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                list.add(new FeeStructure(parts[0], Integer.parseInt(parts[1])));
            }
        } catch (Exception e) {
            System.out.println("Error loading fee structure: " + e.getMessage());
        }
        return list;
    }

    public static void saveStudents(List<Student> students) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("../data/students.txt"))) {
            for (Student s : students) {
                bw.write(s.toString());
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }

    public static void savePayments(List<Payment> payments) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("../data/payments.txt", true))) {
            for (Payment p : payments) {
                bw.write(p.toString());
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error saving payments: " + e.getMessage());
        }
    }
    public static Student getStudentById(int id) {
        List<Student> students = loadStudents();
        for (Student s : students) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }
    public static void updateStudent(Student updatedStudent) {
        List<Student> students = loadStudents();

        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == updatedStudent.getId()) {
                students.set(i, updatedStudent);
                break;
            }
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter("..\\data\\students.txt", false))) {
            for (Student s : students) {
                pw.println(s.getId() + "," + s.getName() + "," + s.getClassName() + "," + s.getFeesPaid());
            }
        } catch (IOException e) {
            System.out.println("Error updating students file: " + e.getMessage());
        }
    }

}
