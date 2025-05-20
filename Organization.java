package orgsystem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Organization {
    private String name;
    private String description;
    private String founderStudentId;
    private ArrayList<String> members;
    private LinkedHashMap<String, String> updates; // Store updates with timestamps

    public Organization(String name, String description, String founderStudentId) {
        this.name = name;
        this.description = description;
        this.founderStudentId = founderStudentId;
        this.members = new ArrayList<>();
        this.updates = new LinkedHashMap<>();
        this.members.add(founderStudentId);
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getFounderStudentId() { return founderStudentId; }
    public ArrayList<String> getMembers() { return members; }
    public Map<String, String> getUpdates() { return updates; }
    
    public void addMember(String studentId) { members.add(studentId); }
    
    public void addUpdate(String update) {
        String timestamp = java.time.LocalDateTime.now().toString();
        updates.put(timestamp, update);
    }
    
    public boolean isFounder(String studentId) {
        return studentId.equals(founderStudentId);
    }
} 