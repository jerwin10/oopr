package orgsystem;

import java.util.ArrayList;

public class Student {
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