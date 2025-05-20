package orgsystem;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class LoginRegistrationSystem {
    private static HashMap<String, Student> studentDatabase = new HashMap<>();
    private static HashMap<String, Organization> organizationDatabase = new HashMap<>();

    private static JFrame mainFrame;
    private static CardLayout cardLayout;
    private static JPanel mainPanel;
    private static Student currentStudent = null;

    // Modern color scheme
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);    // Blue
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);  // Light Blue
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241); // Light Gray
    private static final Color TEXT_COLOR = new Color(44, 62, 80);         // Dark Blue
    private static final Color ACCENT_COLOR = new Color(231, 76, 60);      // Red
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);    // Green

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadOrganizationsFromFile();

        mainFrame = new JFrame("OLFU Organization System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setMinimumSize(new Dimension(800, 600));

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(BACKGROUND_COLOR);

        mainPanel.add(createLoginPanel(), "login");
        mainPanel.add(createRegistrationPanel(), "register");
        mainPanel.add(createDashboardPanel(), "dashboard");

        mainFrame.add(mainPanel);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private static JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(200, 40));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
        
        return button;
    }

    private static JTextField createStyledTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return field;
    }

    private static JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return field;
    }

    private static JTextArea createStyledTextArea() {
        JTextArea area = new JTextArea();
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return area;
    }

    private static JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(TEXT_COLOR);
        return label;
    }

    private static JPanel createStyledPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        return panel;
    }

    private static JScrollPane createStyledScrollPane(Component view) {
        JScrollPane scrollPane = new JScrollPane(view);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return scrollPane;
    }

    private static JList<String> createStyledList() {
        JList<String> list = new JList<>();
        list.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        list.setBackground(Color.WHITE);
        list.setSelectionBackground(PRIMARY_COLOR);
        list.setSelectionForeground(Color.WHITE);
        list.setFixedCellHeight(30);
        list.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return list;
    }

    private static JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel titleLabel = new JLabel("OLFU Login");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY_COLOR);

        JTextField studentIdField = createStyledTextField();
        JPasswordField passwordField = createStyledPasswordField();

        JButton loginButton = createStyledButton("Login", PRIMARY_COLOR);
        JButton registerButton = createStyledButton("Register", SECONDARY_COLOR);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        contentPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1; gbc.gridy++;
        contentPanel.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(studentIdField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        contentPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        contentPanel.add(loginButton, gbc);
        gbc.gridy++;
        contentPanel.add(registerButton, gbc);

        panel.add(contentPanel);

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

    private static JPanel createRegistrationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel titleLabel = new JLabel("Register");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY_COLOR);

        JTextField studentIdField = createStyledTextField();
        JTextField nameField = createStyledTextField();
        JPasswordField passwordField = createStyledPasswordField();
        JPasswordField confirmPasswordField = createStyledPasswordField();

        JButton registerButton = createStyledButton("Register", SUCCESS_COLOR);
        JButton backButton = createStyledButton("Back", ACCENT_COLOR);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        contentPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1; gbc.gridy++;
        contentPanel.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(studentIdField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        contentPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        contentPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        contentPanel.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(confirmPasswordField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        contentPanel.add(registerButton, gbc);
        gbc.gridy++;
        contentPanel.add(backButton, gbc);

        panel.add(contentPanel);

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

    private static JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(BACKGROUND_COLOR);

        // Top Panel with better spacing
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setOpaque(false);

        JLabel welcomeLabel = new JLabel();
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(TEXT_COLOR);
        topPanel.add(welcomeLabel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);

        JButton createOrgButton = createStyledButton("Create Organization", PRIMARY_COLOR);
        JButton logoutButton = createStyledButton("Logout", ACCENT_COLOR);
        createOrgButton.setPreferredSize(new Dimension(180, 40));
        logoutButton.setPreferredSize(new Dimension(120, 40));

        buttonPanel.add(createOrgButton);
        buttonPanel.add(logoutButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        panel.add(topPanel, BorderLayout.NORTH);

        // Main Content - Split Pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(250);
        splitPane.setBorder(null);
        splitPane.setDividerSize(5);

        // Left Panel (Organizations)
        JPanel orgPanel = new JPanel(new BorderLayout(10, 10));
        orgPanel.setBackground(Color.WHITE);
        orgPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel orgListLabel = new JLabel("Available Organizations");
        orgListLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        orgListLabel.setForeground(PRIMARY_COLOR);
        orgPanel.add(orgListLabel, BorderLayout.NORTH);

        DefaultListModel<String> orgListModel = new DefaultListModel<>();
        JList<String> orgList = new JList<>(orgListModel);
        orgList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        orgList.setFixedCellHeight(35);
        orgList.setSelectionBackground(PRIMARY_COLOR);
        orgList.setSelectionForeground(Color.WHITE);

        JScrollPane orgScrollPane = new JScrollPane(orgList);
        orgScrollPane.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        orgPanel.add(orgScrollPane, BorderLayout.CENTER);

        // Right Panel (Updates and Members)
        JTabbedPane rightPanel = new JTabbedPane();
        rightPanel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Updates Tab
        JPanel updatesPanel = new JPanel(new BorderLayout(10, 10));
        updatesPanel.setBackground(Color.WHITE);
        updatesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        DefaultListModel<String> updatesListModel = new DefaultListModel<>();
        JList<String> updatesList = new JList<>(updatesListModel);
        updatesList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        updatesList.setFixedCellHeight(35);
        updatesList.setSelectionBackground(PRIMARY_COLOR);
        updatesList.setSelectionForeground(Color.WHITE);

        // Add right-click menu
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem viewDetailsItem = new JMenuItem("View Details");
        viewDetailsItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        popupMenu.add(viewDetailsItem);
        
        updatesList.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int index = updatesList.locationToIndex(e.getPoint());
                    if (index != -1) {
                        updatesList.setSelectedIndex(index);
                        String selectedOrg = orgList.getSelectedValue();
                        if (selectedOrg != null) {
                            Organization org = organizationDatabase.get(selectedOrg);
                            OrganizationContent content = org.getContents().get(index);
                            
                            // Create details dialog
                            JDialog detailsDialog = new JDialog(mainFrame, "Post Details", true);
                            detailsDialog.setLayout(new BorderLayout());
                            
                            JPanel detailsPanel = new JPanel(new GridLayout(5, 1, 5, 5));
                            detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                            detailsPanel.setBackground(Color.WHITE);
                            
                            String postedBy = "Unknown";
                            if (studentDatabase.containsKey(String.valueOf(content.getPostedBy()))) {
                                postedBy = studentDatabase.get(String.valueOf(content.getPostedBy())).getName();
                            }
                            
                            detailsPanel.add(createStyledLabel("Content: " + content.getPostedContent()));
                            detailsPanel.add(createStyledLabel("Posted by: " + postedBy));
                            detailsPanel.add(createStyledLabel("Time posted: " + content.getTimePosted()));
                            detailsPanel.add(createStyledLabel("Post ID: " + content.getPostId()));
                            detailsPanel.add(createStyledLabel("Organization ID: " + content.getPostedIn()));
                            
                            JButton closeButton = createStyledButton("Close", PRIMARY_COLOR);
                            closeButton.addActionListener(ev -> detailsDialog.dispose());
                            
                            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                            buttonPanel.setBackground(Color.WHITE);
                            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
                            buttonPanel.add(closeButton);
                            
                            detailsDialog.add(detailsPanel, BorderLayout.CENTER);
                            detailsDialog.add(buttonPanel, BorderLayout.SOUTH);
                            
                            detailsDialog.pack();
                            detailsDialog.setLocationRelativeTo(mainFrame);
                            detailsDialog.setVisible(true);
                        }
                    }
                }
            }
        });

        JScrollPane updatesScrollPane = new JScrollPane(updatesList);
        updatesScrollPane.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        updatesPanel.add(updatesScrollPane, BorderLayout.CENTER);

        // Post Update Button Panel
        JPanel postButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        postButtonPanel.setBackground(Color.WHITE);
        JButton postUpdateButton = createStyledButton("Post Update", PRIMARY_COLOR);
        postUpdateButton.setEnabled(false);
        postButtonPanel.add(postUpdateButton);
        updatesPanel.add(postButtonPanel, BorderLayout.SOUTH);

        // Members Tab
        JPanel membersPanel = new JPanel(new BorderLayout(10, 10));
        membersPanel.setBackground(Color.WHITE);
        membersPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        DefaultListModel<String> membersListModel = new DefaultListModel<>();
        JList<String> membersList = new JList<>(membersListModel);
        membersList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        membersList.setFixedCellHeight(35);
        membersList.setSelectionBackground(PRIMARY_COLOR);
        membersList.setSelectionForeground(Color.WHITE);

        JScrollPane membersScrollPane = new JScrollPane(membersList);
        membersScrollPane.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        membersPanel.add(membersScrollPane, BorderLayout.CENTER);

        // Add tabs
        rightPanel.addTab("Updates", updatesPanel);
        rightPanel.addTab("Members", membersPanel);

        splitPane.setLeftComponent(orgPanel);
        splitPane.setRightComponent(rightPanel);
        panel.add(splitPane, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setOpaque(false);
        JButton joinOrgButton = createStyledButton("Join Selected Organization", SECONDARY_COLOR);
        joinOrgButton.setPreferredSize(new Dimension(250, 40));
        bottomPanel.add(joinOrgButton);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        // Store components for later access
        panel.putClientProperty("welcomeLabel", welcomeLabel);
        panel.putClientProperty("orgList", orgList);
        panel.putClientProperty("updatesList", updatesList);
        panel.putClientProperty("membersList", membersList);
        panel.putClientProperty("postUpdateButton", postUpdateButton);
        panel.putClientProperty("rightPanel", rightPanel);

        createOrgButton.addActionListener(e -> showCreateOrganizationDialog());

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
                    saveOrganizationToFile(org);
                    JOptionPane.showMessageDialog(mainFrame, "You joined " + selectedOrg + "!");
                    updateDashboard();
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "You are already a member.");
                }
            }
        });

        // Add selection listener for organization list
        orgList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedOrg = orgList.getSelectedValue();
                if (selectedOrg != null) {
                    Organization org = organizationDatabase.get(selectedOrg);
                    boolean isFounder = org.isFounder(currentStudent.getStudentId());
                    postUpdateButton.setEnabled(isFounder);
                    updateUpdatesList(org, updatesListModel);
                    updateMembersList(org, membersListModel);
                    
                    // Show/hide members tab based on founder status
                    JTabbedPane tabbedPane = (JTabbedPane) panel.getClientProperty("rightPanel");
                    if (isFounder) {
                        if (tabbedPane.indexOfTab("Members") == -1) {
                            tabbedPane.addTab("Members", membersPanel);
                        }
                    } else {
                        int membersTabIndex = tabbedPane.indexOfTab("Members");
                        if (membersTabIndex != -1) {
                            tabbedPane.removeTabAt(membersTabIndex);
                        }
                    }
                }
            }
        });

        // Add action listener for post update button
        postUpdateButton.addActionListener(e -> {
            String selectedOrg = orgList.getSelectedValue();
            if (selectedOrg != null) {
                showPostUpdateDialog(selectedOrg);
            }
        });

        return panel;
    }

    private static void showPostUpdateDialog(String orgName) {
        JDialog dialog = new JDialog(mainFrame, "Post Update", true);
        dialog.setLayout(new BorderLayout());
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextArea updateArea = createStyledTextArea();
        updateArea.setLineWrap(true);
        updateArea.setWrapStyleWord(true);
        JScrollPane scrollPane = createStyledScrollPane(updateArea);

        JButton postButton = createStyledButton("Post", PRIMARY_COLOR);
        JButton cancelButton = createStyledButton("Cancel", ACCENT_COLOR);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(postButton);
        buttonPanel.add(cancelButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(panel);
        
        postButton.addActionListener(e -> {
            String content = updateArea.getText().trim();
            if (!content.isEmpty()) {
                Organization org = organizationDatabase.get(orgName);
                org.addContent(content, Integer.parseInt(currentStudent.getStudentId()));
                saveOrganizationToFile(org);
                updateDashboard();
                dialog.dispose();
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.pack();
        dialog.setLocationRelativeTo(mainFrame);
        dialog.setVisible(true);
    }

    private static void updateUpdatesList(Organization org, DefaultListModel<String> updatesListModel) {
        updatesListModel.clear();
        for (OrganizationContent content : org.getContents()) {
            updatesListModel.addElement(content.getPostedContent());
        }
    }

    private static void updateMembersList(Organization org, DefaultListModel<String> membersListModel) {
        membersListModel.clear();
        for (String memberId : org.getMembers()) {
            String memberName = "Unknown";
            if (studentDatabase.containsKey(memberId)) {
                memberName = studentDatabase.get(memberId).getName();
            }
            String role = memberId.equals(org.getFounderStudentId()) ? " (Founder)" : " (Member)";
            membersListModel.addElement(memberName + " - " + memberId + role);
        }
        membersListModel.addElement("");
        membersListModel.addElement("Total Members: " + org.getMemberCount());
    }

    private static void updateDashboard() {
        JPanel dashboardPanel = (JPanel) mainPanel.getComponent(2);
        JLabel welcomeLabel = (JLabel) dashboardPanel.getClientProperty("welcomeLabel");
        JList<String> orgList = (JList<String>) dashboardPanel.getClientProperty("orgList");
        JList<String> updatesList = (JList<String>) dashboardPanel.getClientProperty("updatesList");
        JList<String> membersList = (JList<String>) dashboardPanel.getClientProperty("membersList");
        JButton postUpdateButton = (JButton) dashboardPanel.getClientProperty("postUpdateButton");

        welcomeLabel.setText("Welcome, " + currentStudent.getName() + "!");

        DefaultListModel<String> orgListModel = (DefaultListModel<String>) orgList.getModel();
        orgListModel.clear();
        for (String orgName : organizationDatabase.keySet()) {
            orgListModel.addElement(orgName);
        }

        // Update the updates list and members list if an organization is selected
        String selectedOrg = orgList.getSelectedValue();
        if (selectedOrg != null) {
            Organization org = organizationDatabase.get(selectedOrg);
            boolean isFounder = org.isFounder(currentStudent.getStudentId());
            postUpdateButton.setEnabled(isFounder);
            updateUpdatesList(org, (DefaultListModel<String>) updatesList.getModel());
            updateMembersList(org, (DefaultListModel<String>) membersList.getModel());
        }
    }

    private static void showCreateOrganizationDialog() {
        JTextField orgNameField = new JTextField(20);
        JTextArea orgDescArea = new JTextArea(4, 20);
        orgDescArea.setLineWrap(true);
        orgDescArea.setWrapStyleWord(true);

        JPanel inputPanel = new JPanel(new GridLayout(4, 1));
        inputPanel.add(new JLabel("Organization Name:"));
        inputPanel.add(orgNameField);
        inputPanel.add(new JLabel("Description:"));
        inputPanel.add(new JScrollPane(orgDescArea));

        int result = JOptionPane.showConfirmDialog(mainFrame, inputPanel, "Create Organization", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = orgNameField.getText().trim();
            String desc = orgDescArea.getText().trim();

            if (name.isEmpty() || desc.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, "Fields cannot be empty.");
            } else if (organizationDatabase.containsKey(name)) {
                JOptionPane.showMessageDialog(mainFrame, "Organization already exists.");
            } else {
                Organization newOrg = new Organization(name, desc, currentStudent.getStudentId());
                organizationDatabase.put(name, newOrg);
                currentStudent.joinOrganization(name);
                saveOrganizationToFile(newOrg);
                JOptionPane.showMessageDialog(mainFrame, "Organization created successfully!");
                updateDashboard();
            }
        }
    }

    private static void saveOrganizationToFile(Organization org) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("organizations.txt", true))) {
            writer.write(org.toFileString());
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(mainFrame, "Error saving organization to file.");
            e.printStackTrace();
        }
    }

    private static void loadOrganizationsFromFile() {
        File file = new File("organizations.txt");
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Organization org = Organization.fromFileString(line);
                if (org != null) {
                    organizationDatabase.put(org.getOrganizationName(), org);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading organizations: " + e.getMessage());
        }
    }
} 