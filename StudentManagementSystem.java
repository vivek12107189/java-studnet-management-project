import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.io.FileReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;

public class StudentManagementSystemGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final String[] TABLE_COLUMNS = { "ID", "Name", "Email", "Phone Number" };

    private JPanel contentPane;
    private JTextField idField;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneNumberField;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JTable studentTable;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    StudentManagementSystemGUI frame = new StudentManagementSystemGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public StudentManagementSystemGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel inputPanel = new JPanel();
        contentPane.add(inputPanel, BorderLayout.NORTH);
        inputPanel.setLayout(new BorderLayout(0, 0));

        JPanel formPanel = new JPanel();
        inputPanel.add(formPanel, BorderLayout.CENTER);
        formPanel.setLayout(new BorderLayout(0, 0));

        JPanel labelPanel = new JPanel();
        formPanel.add(labelPanel, BorderLayout.WEST);
        labelPanel.setLayout(new BorderLayout(0, 0));

        JLabel idLabel = new JLabel("ID:");
        labelPanel.add(idLabel, BorderLayout.NORTH);

        JLabel nameLabel = new JLabel("Name:");
        labelPanel.add(nameLabel, BorderLayout.CENTER);

        JLabel emailLabel = new JLabel("Email:");
        labelPanel.add(emailLabel, BorderLayout.SOUTH);

        JPanel fieldPanel = new JPanel();
        formPanel.add(fieldPanel, BorderLayout.CENTER);
        fieldPanel.setLayout(new BorderLayout(0, 0));

        idField = new JTextField();
        fieldPanel.add(idField, BorderLayout.NORTH);
        idField.setColumns(10);

        nameField = new JTextField();
        fieldPanel.add(nameField, BorderLayout.CENTER);
        nameField.setColumns(10);

        emailField = new JTextField();
        fieldPanel.add(emailField, BorderLayout.SOUTH);
        emailField.setColumns(10);

        JPanel phonePanel = new JPanel();
        inputPanel.add(phonePanel, BorderLayout.EAST);
        phonePanel.setLayout(new BorderLayout(0, 0));

        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        phonePanel.add(phoneNumberLabel, BorderLayout.NORTH);

        phoneNumberField = new JTextField();
        phonePanel.add(phoneNumberField, BorderLayout.SOUTH);
        phoneNumberField.setColumns(10);

        JPanel buttonPanel = new JPanel();
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        addButton = new JButton("Add");
        addButton.setBackground(Color.green);
        buttonPanel.add(addButton);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });
        editButton = new JButton("Edit");
        editButton.setBackground(Color.green);
        buttonPanel.add(editButton);
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editStudent();
            }
        });

        deleteButton = new JButton("Delete");
        deleteButton.setBackground(Color.red);
        buttonPanel.add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });
        JButton searchButton = new JButton("Search");
        buttonPanel.add(searchButton);
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchStudent();
            }
        });

        JScrollPane tableScrollPane = new JScrollPane();
        contentPane.add(tableScrollPane, BorderLayout.CENTER);

        studentTable = new JTable();
        tableScrollPane.setViewportView(studentTable);

        DefaultTableModel tableModel = new DefaultTableModel(TABLE_COLUMNS, 0);
        studentTable.setModel(tableModel);
    }

    private void addStudent() {
        String id = idField.getText();
        String name = nameField.getText();
        String email = emailField.getText();
        String phoneNumber = phoneNumberField.getText();

        Object[] rowData = { id, name, email, phoneNumber };
        DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
        model.addRow(rowData);

        PrintWriter writer = null;
        File file = new File("studentdata.txt");
        try {
            writer = new PrintWriter(new FileWriter(file, true));
            file.setWritable(true);

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        writer.println(id + "," + name + "," + email + "," + phoneNumber);
        writer.close();

        clearFields();
    }

    private void editStudent() {
        int rowIndex = studentTable.getSelectedRow();

        if (rowIndex != -1) {
            String id = idField.getText();
            String name = nameField.getText();
            String email = emailField.getText();
            String phoneNumber = phoneNumberField.getText();

            DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
            model.setValueAt(id, rowIndex, 0);
            model.setValueAt(name, rowIndex, 1);
            model.setValueAt(email, rowIndex, 2);
            model.setValueAt(phoneNumber, rowIndex, 3);
            clearFields();
        }
    }

    private void deleteStudent() {
        DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this student?",
                    "Delete Student", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                model.removeRow(selectedRow);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteStudentData(int idField) {
        // read the data from the file
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("studentdata.txt"))) {
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // remove the data with the given student ID
        List<String> updatedLines = new ArrayList<>();
        for (String line : lines) {
            String[] fields = line.split(",");
            int id = Integer.parseInt(fields[0]);
            if (id != idField) {
                updatedLines.add(line);
            }
        }

        // write the updated data back to the file
        try (PrintWriter writer = new PrintWriter(new FileWriter("studentdata.txt"))) {
            for (String line : updatedLines) {
                writer.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void searchStudent() {
        String searchText = JOptionPane.showInputDialog(this, "Enter student ID to search:");
        if (searchText != null && !searchText.isEmpty()) {
            DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
            boolean found = false;
            for (int i = 0; i < model.getRowCount(); i++) {
                String id = (String) model.getValueAt(i, 0);
                if (id.equalsIgnoreCase(searchText)) {
                    found = true;
                    studentTable.setRowSelectionInterval(i, i);
                    break;
                }
            }
            if (!found) {
                JOptionPane.showMessageDialog(this, "Student not found.", "Search Result",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        phoneNumberField.setText("");
    }
}
