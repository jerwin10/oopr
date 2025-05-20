import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

// --- Student class: Represents a student user ---
class Student {
    private String studentId;
    private String name;
    private String password;
    private ArrayList<String> joinedOrganizations;

    public Student(String studentId, String name, String password) {
        this.studentId = studentId;
        this.name = name;
        this.password = password;
        this.joinedOrganizations = new ArrayList<>();
    }

    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public ArrayList<String> getJoinedOrganizations() { return joinedOrganizations; }
    public void joinOrganization(String orgName) { joinedOrganizations.add(orgName); }
}

// --- Organization class: Represents an organization ---
class Organization {
    private String name;
    private String description;
    private String founderStudentId;
    private ArrayList<String> members;

    public Organization(String name, String description, String founderStudentId) {
        this.name = name;
        this.description = description;
        this.founderStudentId = founderStudentId;
        this.members = new ArrayList<>();
        this.members.add(founderStudentId); // Founder is the first member
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public ArrayList<String> getMembers() { return members; }
    public void addMember(String studentId) { members.add(studentId); }
}

// --- Main Application Class ---
public class LoginRegistrationSystem {
    // --- Databases (in-memory) ---
    private static HashMap<String, Student> studentDatabase = new HashMap<>();
    private static HashMap<String, Organization> organizationDatabase = new HashMap<>();

    // --- UI Components ---
    private static JFrame mainFrame;
    private static CardLayout cardLayout;
    private static JPanel mainPanel;
    private static Student currentStudent = null;

    // --- Main Entry Point ---
    public static void main(String[] args) {
        mainFrame = new JFrame("OLFU Organization System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(600, 450);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Define color variables for button styling using only named colors
        Color loginButtonColor = Color.BLUE;
        Color registerButtonColor = Color.BLUE;
        Color registerPanelButtonColor = Color.BLUE;
        Color backButtonColor = Color.RED;

        mainPanel.add(createLoginPanel(loginButtonColor, registerButtonColor), "login");
        mainPanel.add(createRegistrationPanel(registerPanelButtonColor, backButtonColor), "register");
        mainPanel.add(createDashboardPanel(), "dashboard");

        mainFrame.add(mainPanel);
        mainFrame.setLocationRelativeTo(null); // Center window
        mainFrame.setVisible(true);
    }

    // --- Login Panel ---
    private static JPanel createLoginPanel(Color loginButtonColor, Color registerButtonColor) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("OLFU Login");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLabel.setForeground(Color.BLACK);

        JTextField studentIdField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        // Layout components
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1; gbc.gridy++;
        panel.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1;
        panel.add(studentIdField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        panel.add(loginButton, gbc);
        gbc.gridy++;
        panel.add(registerButton, gbc);

        // --- Button Actions ---
        loginButton.addActionListener(e -> {
            String id = studentIdField.getText();
            String pass = new String(passwordField.getPassword());

            if (studentDatabase.containsKey(id) && studentDatabase.get(id).getPassword().equals(pass)) {
                currentStudent = studentDatabase.get(id);
                updateDashboard();
                cardLayout.show(mainPanel, "dashboard");
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Invalid ID or Password");
            }
        });

        registerButton.addActionListener(e -> cardLayout.show(mainPanel, "register"));

        return panel;
    }

    // --- Registration Panel ---
    private static JPanel createRegistrationPanel(Color registerButtonColor, Color backButtonColor) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Register");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLabel.setForeground(Color.BLACK);

        JTextField studentIdField = new JTextField(20);
        JTextField nameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JPasswordField confirmPasswordField = new JPasswordField(20);

        JButton registerButton = new JButton("Register");
        JButton backButton = new JButton("Back");

        // Layout components
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1; gbc.gridy++;
        panel.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1;
        panel.add(studentIdField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx = 1;
        panel.add(confirmPasswordField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        panel.add(registerButton, gbc);
        gbc.gridy++;
        panel.add(backButton, gbc);

        // --- Button Actions ---
        registerButton.addActionListener(e -> {
            String id = studentIdField.getText();
            String name = nameField.getText();
            String pass = new String(passwordField.getPassword());
            String confirm = new String(confirmPasswordField.getPassword());

            if (id.isEmpty() || name.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, "Please fill out all fields.");
            } else if (!pass.equals(confirm)) {
                JOptionPane.showMessageDialog(mainFrame, "Passwords do not match.");
            } else if (studentDatabase.containsKey(id)) {
                JOptionPane.showMessageDialog(mainFrame, "Student ID already exists.");
            } else {
                studentDatabase.put(id, new Student(id, name, pass));
                JOptionPane.showMessageDialog(mainFrame, "Registration successful!");
                cardLayout.show(mainPanel, "login");
            }
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "login"));

        return panel;
    }

    // --- Dashboard Panel ---
    private static JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.LIGHT_GRAY); // Light background

        // --- Top: Welcome Label + Buttons ---
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setOpaque(false);

        JLabel welcomeLabel = new JLabel();
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.BLACK);
        topPanel.add(welcomeLabel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);

        JButton createOrgButton = new JButton("Create Organization");
        createOrgButton.setPreferredSize(new Dimension(180, 38));

        JButton logoutButton = new JButton("Logout");
        logoutButton.setPreferredSize(new Dimension(100, 38));

        buttonPanel.add(createOrgButton);
        buttonPanel.add(logoutButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        panel.add(topPanel, BorderLayout.NORTH);

        // --- Center: List of Organizations ---
        DefaultListModel<String> orgListModel = new DefaultListModel<>();
        JList<String> orgList = new JList<>(orgListModel);
        orgList.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        orgList.setSelectionBackground(Color.BLUE);
        orgList.setSelectionForeground(Color.WHITE);
        orgList.setFixedCellHeight(30);
        JScrollPane scrollPane = new JScrollPane(orgList);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            "Available Organizations",
            0, 0,
            new Font("Segoe UI", Font.BOLD, 16),
            Color.BLACK
        ));
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        // --- Bottom: Join Button ---
        JButton joinOrgButton = new JButton("Join Selected Organization");
        joinOrgButton.setPreferredSize(new Dimension(240, 42));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);
        bottomPanel.add(joinOrgButton);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        // Store references for later updates
        panel.putClientProperty("welcomeLabel", welcomeLabel);
        panel.putClientProperty("orgList", orgList);

        // --- Button Actions ---
        createOrgButton.addActionListener(e -> JOptionPane.showMessageDialog(mainFrame, "Create Organization feature coming soon!"));
        logoutButton.addActionListener(e -> {
            currentStudent = null;
            cardLayout.show(mainPanel, "login");
        });
        joinOrgButton.addActionListener(e -> {
            String selectedOrg = orgList.getSelectedValue();
            if (selectedOrg != null && currentStudent != null) {
                Organization org = organizationDatabase.get(selectedOrg);
                if (!org.getMembers().contains(currentStudent.getStudentId())) {
                    org.addMember(currentStudent.getStudentId());
                    currentStudent.joinOrganization(selectedOrg);
                    JOptionPane.showMessageDialog(mainFrame, "You joined " + selectedOrg + "!");
                    updateDashboard();
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "You are already a member.");
                }
            }
        });

        return panel;
    }

    // --- Update Dashboard with Current Data ---
    private static void updateDashboard() {
        JPanel dashboardPanel = (JPanel) mainPanel.getComponent(2); // dashboard panel
        JLabel welcomeLabel = (JLabel) dashboardPanel.getClientProperty("welcomeLabel");
        JList<String> orgList = (JList<String>) dashboardPanel.getClientProperty("orgList");

        welcomeLabel.setText("Welcome, " + currentStudent.getName() + "!");

        DefaultListModel<String> orgListModel = (DefaultListModel<String>) orgList.getModel();
        orgListModel.clear();
        for (String orgName : organizationDatabase.keySet()) {
            orgListModel.addElement(orgName);
        }
    }

    // --- Custom Rounded Border for Buttons ---
    static class RoundedBorder extends AbstractBorder {
        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRoundRect(x + 1, y + 1, width - 3, height - 3, radius, radius);
            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius / 2, radius / 2, radius / 2, radius / 2);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.top = insets.right = insets.bottom = radius / 2;
            return insets;
        }
    }
}
