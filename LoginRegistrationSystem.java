// Source code is decompiled from a .class file using FernFlower decompiler.
package orgsystem;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class LoginRegistrationSystem {
   private static HashMap<String, Student> studentDatabase = new HashMap();
   private static HashMap<String, Organization> organizationDatabase = new HashMap();
   private static JFrame mainFrame;
   private static CardLayout cardLayout;
   private static JPanel mainPanel;
   private static Student currentStudent = null;

   public LoginRegistrationSystem() {
   }

   public static void main(String[] var0) {
      loadOrganizationsFromFile();
      mainFrame = new JFrame("OLFU Organization System");
      mainFrame.setDefaultCloseOperation(3);
      mainFrame.setSize(600, 450);
      cardLayout = new CardLayout();
      mainPanel = new JPanel(cardLayout);
      Color var1 = Color.BLUE;
      Color var2 = Color.BLUE;
      Color var3 = Color.BLUE;
      Color var4 = Color.RED;
      mainPanel.add(createLoginPanel(var1, var2), "login");
      mainPanel.add(createRegistrationPanel(var3, var4), "register");
      mainPanel.add(createDashboardPanel(), "dashboard");
      mainFrame.add(mainPanel);
      mainFrame.setLocationRelativeTo((Component)null);
      mainFrame.setVisible(true);
   }

   private static JPanel createLoginPanel(Color var0, Color var1) {
      JPanel var2 = new JPanel(new GridBagLayout());
      var2.setBackground(Color.WHITE);
      GridBagConstraints var3 = new GridBagConstraints();
      var3.insets = new Insets(10, 10, 10, 10);
      JLabel var4 = new JLabel("OLFU Login");
      var4.setFont(new Font("SansSerif", 1, 26));
      var4.setForeground(Color.BLACK);
      JTextField var5 = new JTextField(20);
      JPasswordField var6 = new JPasswordField(20);
      JButton var7 = new JButton("Login");
      JButton var8 = new JButton("Register");
      var3.gridx = 0;
      var3.gridy = 0;
      var3.gridwidth = 2;
      var2.add(var4, var3);
      var3.gridwidth = 1;
      ++var3.gridy;
      var2.add(new JLabel("Student ID:"), var3);
      var3.gridx = 1;
      var2.add(var5, var3);
      var3.gridx = 0;
      ++var3.gridy;
      var2.add(new JLabel("Password:"), var3);
      var3.gridx = 1;
      var2.add(var6, var3);
      var3.gridx = 0;
      ++var3.gridy;
      var3.gridwidth = 2;
      var2.add(var7, var3);
      ++var3.gridy;
      var2.add(var8, var3);
      var7.addActionListener((var2x) -> {
         String var3 = var5.getText();
         String var4 = new String(var6.getPassword());
         if (studentDatabase.containsKey(var3) && ((Student)studentDatabase.get(var3)).getPassword().equals(var4)) {
            currentStudent = (Student)studentDatabase.get(var3);
            updateDashboard();
            cardLayout.show(mainPanel, "dashboard");
         } else {
            JOptionPane.showMessageDialog(mainFrame, "Invalid ID or Password");
         }

      });
      var8.addActionListener((var0x) -> {
         cardLayout.show(mainPanel, "register");
      });
      return var2;
   }

   private static JPanel createRegistrationPanel(Color var0, Color var1) {
      JPanel var2 = new JPanel(new GridBagLayout());
      var2.setBackground(Color.WHITE);
      GridBagConstraints var3 = new GridBagConstraints();
      var3.insets = new Insets(10, 10, 10, 10);
      JLabel var4 = new JLabel("Register");
      var4.setFont(new Font("SansSerif", 1, 26));
      var4.setForeground(Color.BLACK);
      JTextField var5 = new JTextField(20);
      JTextField var6 = new JTextField(20);
      JPasswordField var7 = new JPasswordField(20);
      JPasswordField var8 = new JPasswordField(20);
      JButton var9 = new JButton("Register");
      JButton var10 = new JButton("Back");
      var3.gridx = 0;
      var3.gridy = 0;
      var3.gridwidth = 2;
      var2.add(var4, var3);
      var3.gridwidth = 1;
      ++var3.gridy;
      var2.add(new JLabel("Student ID:"), var3);
      var3.gridx = 1;
      var2.add(var5, var3);
      var3.gridx = 0;
      ++var3.gridy;
      var2.add(new JLabel("Name:"), var3);
      var3.gridx = 1;
      var2.add(var6, var3);
      var3.gridx = 0;
      ++var3.gridy;
      var2.add(new JLabel("Password:"), var3);
      var3.gridx = 1;
      var2.add(var7, var3);
      var3.gridx = 0;
      ++var3.gridy;
      var2.add(new JLabel("Confirm Password:"), var3);
      var3.gridx = 1;
      var2.add(var8, var3);
      var3.gridx = 0;
      ++var3.gridy;
      var3.gridwidth = 2;
      var2.add(var9, var3);
      ++var3.gridy;
      var2.add(var10, var3);
      var9.addActionListener((var4x) -> {
         String var5x = var5.getText();
         String var6x = var6.getText();
         String var7x = new String(var7.getPassword());
         String var8x = new String(var8.getPassword());
         if (!var5x.isEmpty() && !var6x.isEmpty() && !var7x.isEmpty() && !var8x.isEmpty()) {
            if (!var7x.equals(var8x)) {
               JOptionPane.showMessageDialog(mainFrame, "Passwords do not match.");
            } else if (studentDatabase.containsKey(var5x)) {
               JOptionPane.showMessageDialog(mainFrame, "Student ID already exists.");
            } else {
               studentDatabase.put(var5x, new Student(var5x, var6x, var7x));
               JOptionPane.showMessageDialog(mainFrame, "Registration successful!");
               cardLayout.show(mainPanel, "login");
            }
         } else {
            JOptionPane.showMessageDialog(mainFrame, "Please fill out all fields.");
         }

      });
      var10.addActionListener((var0x) -> {
         cardLayout.show(mainPanel, "login");
      });
      return var2;
   }

   private static JPanel createDashboardPanel() {
      JPanel var0 = new JPanel(new BorderLayout(15, 15));
      var0.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
      var0.setBackground(Color.LIGHT_GRAY);
      JPanel var1 = new JPanel(new BorderLayout(10, 10));
      var1.setOpaque(false);
      JLabel var2 = new JLabel();
      var2.setFont(new Font("Segoe UI", 1, 24));
      var2.setForeground(Color.BLACK);
      var1.add(var2, "West");
      JPanel var3 = new JPanel(new FlowLayout(2, 10, 0));
      var3.setOpaque(false);
      JButton var4 = new JButton("Create Organization");
      var4.setPreferredSize(new Dimension(180, 38));
      JButton var5 = new JButton("Logout");
      var5.setPreferredSize(new Dimension(100, 38));
      var3.add(var4);
      var3.add(var5);
      var1.add(var3, "East");
      var0.add(var1, "North");
      JSplitPane var6 = new JSplitPane(1);
      var6.setDividerLocation(300);
      JPanel var7 = new JPanel(new BorderLayout());
      DefaultListModel var8 = new DefaultListModel();
      JList var9 = new JList(var8);
      var9.setFont(new Font("Segoe UI", 0, 16));
      var9.setSelectionBackground(Color.BLUE);
      var9.setSelectionForeground(Color.WHITE);
      var9.setFixedCellHeight(30);
      JScrollPane var10 = new JScrollPane(var9);
      var10.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Available Organizations", 0, 0, new Font("Segoe UI", 1, 16), Color.BLACK));
      var10.getViewport().setBackground(Color.WHITE);
      var7.add(var10, "Center");
      JTabbedPane var11 = new JTabbedPane();
      JPanel var12 = new JPanel(new BorderLayout());
      DefaultListModel var13 = new DefaultListModel();
      JList var14 = new JList(var13);
      var14.setFont(new Font("Segoe UI", 0, 14));
      JScrollPane var15 = new JScrollPane(var14);
      var15.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Organization Updates", 0, 0, new Font("Segoe UI", 1, 16), Color.BLACK));
      var15.getViewport().setBackground(Color.WHITE);
      var12.add(var15, "Center");
      JButton var16 = new JButton("Post Update");
      var16.setPreferredSize(new Dimension(120, 30));
      var16.setEnabled(false);
      JPanel var17 = new JPanel(new FlowLayout(2));
      var17.setOpaque(false);
      var17.add(var16);
      var12.add(var17, "South");
      JPanel var18 = new JPanel(new BorderLayout());
      DefaultListModel var19 = new DefaultListModel();
      JList var20 = new JList(var19);
      var20.setFont(new Font("Segoe UI", 0, 14));
      JScrollPane var21 = new JScrollPane(var20);
      var21.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Organization Members", 0, 0, new Font("Segoe UI", 1, 16), Color.BLACK));
      var21.getViewport().setBackground(Color.WHITE);
      var18.add(var21, "Center");
      var11.addTab("Updates", var12);
      var11.addTab("Members", var18);
      var6.setLeftComponent(var7);
      var6.setRightComponent(var11);
      var0.add(var6, "Center");
      JButton var22 = new JButton("Join Selected Organization");
      var22.setPreferredSize(new Dimension(240, 42));
      JPanel var23 = new JPanel(new FlowLayout(1));
      var23.setOpaque(false);
      var23.add(var22);
      var0.add(var23, "South");
      var0.putClientProperty("welcomeLabel", var2);
      var0.putClientProperty("orgList", var9);
      var0.putClientProperty("updatesList", var14);
      var0.putClientProperty("membersList", var20);
      var0.putClientProperty("postUpdateButton", var16);
      var0.putClientProperty("rightPanel", var11);
      var4.addActionListener((var0x) -> {
         showCreateOrganizationDialog();
      });
      var5.addActionListener((var0x) -> {
         currentStudent = null;
         cardLayout.show(mainPanel, "login");
      });
      var22.addActionListener((var1x) -> {
         String var2 = (String)var9.getSelectedValue();
         if (var2 != null && currentStudent != null) {
            Organization var3 = (Organization)organizationDatabase.get(var2);
            if (!var3.getMembers().contains(currentStudent.getStudentId())) {
               var3.addMember(currentStudent.getStudentId());
               currentStudent.joinOrganization(var2);
               saveOrganizationToFile(var3);
               JOptionPane.showMessageDialog(mainFrame, "You joined " + var2 + "!");
               updateDashboard();
            } else {
               JOptionPane.showMessageDialog(mainFrame, "You are already a member.");
            }
         }

      });
      var9.addListSelectionListener((var6x) -> {
         if (!var6x.getValueIsAdjusting()) {
            String var7 = (String)var9.getSelectedValue();
            if (var7 != null) {
               Organization var8 = (Organization)organizationDatabase.get(var7);
               boolean var9x = var8.isFounder(currentStudent.getStudentId());
               var16.setEnabled(var9x);
               updateUpdatesList(var8, var13);
               updateMembersList(var8, var19);
               JTabbedPane var10 = (JTabbedPane)var0.getClientProperty("rightPanel");
               if (var9x) {
                  if (var10.indexOfTab("Members") == -1) {
                     var10.addTab("Members", var18);
                  }
               } else {
                  int var11 = var10.indexOfTab("Members");
                  if (var11 != -1) {
                     var10.removeTabAt(var11);
                  }
               }
            }
         }

      });
      var16.addActionListener((var1x) -> {
         String var2 = (String)var9.getSelectedValue();
         if (var2 != null) {
            showPostUpdateDialog(var2);
         }

      });
      return var0;
   }

   private static void showPostUpdateDialog(String var0) {
      JTextArea var1 = new JTextArea(4, 30);
      var1.setLineWrap(true);
      var1.setWrapStyleWord(true);
      JScrollPane var2 = new JScrollPane(var1);
      int var3 = JOptionPane.showConfirmDialog(mainFrame, var2, "Post Update for " + var0, 2);
      if (var3 == 0) {
         String var4 = var1.getText().trim();
         if (!var4.isEmpty()) {
            Organization var5 = (Organization)organizationDatabase.get(var0);
            var5.addUpdate(var4);
            saveOrganizationToFile(var5);
            updateDashboard();
         }
      }

   }

   private static void updateUpdatesList(Organization var0, DefaultListModel<String> var1) {
      var1.clear();
      Iterator var2 = var0.getUpdates().entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry var3 = (Map.Entry)var2.next();
         String var4 = (String)var3.getKey();
         String var5 = (String)var3.getValue();
         var1.addElement(String.format("[%s] %s", var4, var5));
      }

   }

   private static void updateMembersList(Organization var0, DefaultListModel<String> var1) {
      var1.clear();
      Iterator var2 = var0.getMembers().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         String var4 = "Unknown";
         if (studentDatabase.containsKey(var3)) {
            var4 = ((Student)studentDatabase.get(var3)).getName();
         }

         String var5 = var3.equals(var0.getFounderStudentId()) ? " (Founder)" : " (Member)";
         var1.addElement(var4 + var5);
      }

   }

   private static void updateDashboard() {
      JPanel var0 = (JPanel)mainPanel.getComponent(2);
      JLabel var1 = (JLabel)var0.getClientProperty("welcomeLabel");
      JList var2 = (JList)var0.getClientProperty("orgList");
      JList var3 = (JList)var0.getClientProperty("updatesList");
      JList var4 = (JList)var0.getClientProperty("membersList");
      JButton var5 = (JButton)var0.getClientProperty("postUpdateButton");
      var1.setText("Welcome, " + currentStudent.getName() + "!");
      DefaultListModel var6 = (DefaultListModel)var2.getModel();
      var6.clear();
      Iterator var7 = organizationDatabase.keySet().iterator();

      while(var7.hasNext()) {
         String var8 = (String)var7.next();
         var6.addElement(var8);
      }

      String var10 = (String)var2.getSelectedValue();
      if (var10 != null) {
         Organization var11 = (Organization)organizationDatabase.get(var10);
         boolean var9 = var11.isFounder(currentStudent.getStudentId());
         var5.setEnabled(var9);
         updateUpdatesList(var11, (DefaultListModel)var3.getModel());
         updateMembersList(var11, (DefaultListModel)var4.getModel());
      }

   }

   private static void showCreateOrganizationDialog() {
      JTextField var0 = new JTextField(20);
      JTextArea var1 = new JTextArea(4, 20);
      var1.setLineWrap(true);
      var1.setWrapStyleWord(true);
      JPanel var2 = new JPanel(new GridLayout(4, 1));
      var2.add(new JLabel("Organization Name:"));
      var2.add(var0);
      var2.add(new JLabel("Description:"));
      var2.add(new JScrollPane(var1));
      int var3 = JOptionPane.showConfirmDialog(mainFrame, var2, "Create Organization", 2);
      if (var3 == 0) {
         String var4 = var0.getText().trim();
         String var5 = var1.getText().trim();
         if (!var4.isEmpty() && !var5.isEmpty()) {
            if (organizationDatabase.containsKey(var4)) {
               JOptionPane.showMessageDialog(mainFrame, "Organization already exists.");
            } else {
               Organization var6 = new Organization(var4, var5, currentStudent.getStudentId());
               organizationDatabase.put(var4, var6);
               currentStudent.joinOrganization(var4);
               saveOrganizationToFile(var6);
               JOptionPane.showMessageDialog(mainFrame, "Organization created successfully!");
               updateDashboard();
            }
         } else {
            JOptionPane.showMessageDialog(mainFrame, "Fields cannot be empty.");
         }
      }

   }

   private static void saveOrganizationToFile(Organization var0) {
      try {
         BufferedWriter var1 = new BufferedWriter(new FileWriter("organizations.txt", true));

         try {
            String var10000 = var0.getName();
            String var2 = var10000 + "|" + var0.getDescription() + "|" + var0.getFounderStudentId() + "|" + String.join(",", var0.getMembers()) + "|" + String.join("||", (CharSequence[])var0.getUpdates().entrySet().stream().map((var0x) -> {
               String var10000 = (String)var0x.getKey();
               return var10000 + "::" + (String)var0x.getValue();
            }).toArray((var0x) -> {
               return new String[var0x];
            }));
            var1.write(var2);
            var1.newLine();
         } catch (Throwable var5) {
            try {
               var1.close();
            } catch (Throwable var4) {
               var5.addSuppressed(var4);
            }

            throw var5;
         }

         var1.close();
      } catch (IOException var6) {
         JOptionPane.showMessageDialog(mainFrame, "Error saving organization to file.");
         var6.printStackTrace();
      }

   }

   private static void loadOrganizationsFromFile() {
      File var0 = new File("organizations.txt");
      if (var0.exists()) {
         try {
            BufferedReader var1 = new BufferedReader(new FileReader(var0));

            try {
               label63:
               while(true) {
                  String[] var3;
                  do {
                     String var2;
                     if ((var2 = var1.readLine()) == null) {
                        break label63;
                     }

                     var3 = var2.split("\\|");
                  } while(var3.length < 4);

                  String var4 = var3[0];
                  String var5 = var3[1];
                  String var6 = var3[2];
                  String[] var7 = var3[3].split(",");
                  Organization var8 = new Organization(var4, var5, var6);
                  String[] var9 = var7;
                  int var10 = var7.length;

                  int var11;
                  for(var11 = 0; var11 < var10; ++var11) {
                     String var12 = var9[var11];
                     if (!var12.equals(var6)) {
                        var8.addMember(var12);
                     }
                  }

                  if (var3.length > 4) {
                     var9 = var3[4].split("\\|\\|");
                     String[] var18 = var9;
                     var11 = var9.length;

                     for(int var19 = 0; var19 < var11; ++var19) {
                        String var13 = var18[var19];
                        String[] var14 = var13.split("::");
                        if (var14.length == 2) {
                           var8.getUpdates().put(var14[0], var14[1]);
                        }
                     }
                  }

                  organizationDatabase.put(var4, var8);
               }
            } catch (Throwable var16) {
               try {
                  var1.close();
               } catch (Throwable var15) {
                  var16.addSuppressed(var15);
               }

               throw var16;
            }

            var1.close();
         } catch (IOException var17) {
            System.err.println("Error loading organizations: " + var17.getMessage());
         }

      }
   }
}
