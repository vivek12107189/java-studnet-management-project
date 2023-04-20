import java.util.ArrayList;
import java.util.Scanner;

public class StudentManagementSystem {
    
    static ArrayList<Student> studentList = new ArrayList<Student>();
    
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        
        do {
            System.out.println("\nStudent Management System");
            System.out.println("1. Add new student");
            System.out.println("2. Edit student information");
            System.out.println("3. Delete student");
            System.out.println("4. Display all students");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            
            switch(choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    editStudent();
                    break;
                case 3:
                    deleteStudent();
                    break;
                case 4:
                    displayAllStudents();
                    break;
                case 5:
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while(!quit);
        
        scanner.close();
    }
    
    public static void addStudent() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter student ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter student email: ");
        String email = scanner.nextLine();
        System.out.print("Enter student phone number: ");
        String phoneNumber = scanner.nextLine();
        Student student = new Student(id, name, email, phoneNumber);
        studentList.add(student);
        System.out.println("Student added successfully!");
    }
    
    public static void editStudent() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter student ID to edit: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Student student = getStudentById(id);
        if(student != null) {
            System.out.print("Enter new student name: ");
            String name = scanner.nextLine();
            System.out.print("Enter new student email: ");
            String email = scanner.nextLine();
            System.out.print("Enter new student phone number: ");
            String phoneNumber = scanner.nextLine();
            student.setName(name);
            student.setEmail(email);
            student.setPhoneNumber(phoneNumber);
            System.out.println("Student information updated successfully!");
        } else {
            System.out.println("Student not found!");
        }
    }
    
    public static void deleteStudent() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter student ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Student student = getStudentById(id);
        if(student != null) {
            studentList.remove(student);
            System.out.println("Student deleted successfully!");
        } else {
            System.out.println("Student not found!");
        }
    }
    
    public static void displayAllStudents() {
        for(Student student : studentList) {
            System.out.println(student);
        }
    }
    
    public static Student getStudentById(int id) {
        for(Student student : studentList) {
            if(student.getId() == id) {
                return student;
            }
        }
        return null;
    }

}

class Student {
    
    public int id;
    public String name;
    public String email;
    public String phoneNumber;
    
    public Student(int id, String name, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Email: " + email + ", Phone Number: " + phoneNumber;
    }
    
    
}

