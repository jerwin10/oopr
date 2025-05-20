package orgsystem;

import java.util.ArrayList;
import java.util.List;

public class Organization {
    private static int nextId = 1; // For auto-incrementing IDs
    private int organizationId;
    private String organizationName;
    private String description;
    private String founderStudentId;
    private ArrayList<String> members;
    private List<OrganizationContent> contents;

    public Organization(String organizationName, String description, String founderStudentId) {
        this.organizationId = nextId++;
        this.organizationName = organizationName;
        this.description = description;
        this.founderStudentId = founderStudentId;
        this.members = new ArrayList<>();
        this.contents = new ArrayList<>();
        this.members.add(founderStudentId);
    }

    // Getters
    public int getOrganizationId() { return organizationId; }
    public String getOrganizationName() { return organizationName; }
    public String getDescription() { return description; }
    public String getFounderStudentId() { return founderStudentId; }
    public ArrayList<String> getMembers() { return members; }
    public List<OrganizationContent> getContents() { return contents; }
    public int getMemberCount() { return members.size(); }
    
    // Methods
    public void addMember(String studentId) { members.add(studentId); }
    
    public void addContent(String content, int postedBy) {
        OrganizationContent newContent = new OrganizationContent(content, postedBy, this.organizationId);
        contents.add(newContent);
    }
    
    public boolean isFounder(String studentId) {
        return studentId.equals(founderStudentId);
    }

    // For file storage
    public String toFileString() {
        return organizationId + "|" + organizationName + "|" + description + "|" +
               founderStudentId + "|" + String.join(",", members) + "|" +
               String.join("||", contents.stream()
                   .map(OrganizationContent::toFileString)
                   .toArray(String[]::new));
    }

    // For file loading
    public static Organization fromFileString(String line) {
        String[] parts = line.split("\\|");
        
        // Handle old format (without organization ID)
        if (parts.length >= 3 && !parts[0].matches("\\d+")) {
            String name = parts[0];
            String desc = parts[1];
            String founderId = parts[2];
            Organization org = new Organization(name, desc, founderId);
            
            // Add members if they exist
            if (parts.length > 3) {
                String[] memberArray = parts[3].split(",");
                for (String member : memberArray) {
                    if (!member.equals(founderId)) {
                        org.addMember(member);
                    }
                }
            }
            
            // Load contents if they exist
            if (parts.length > 4) {
                String[] contents = parts[4].split("\\|\\|");
                for (String content : contents) {
                    OrganizationContent orgContent = OrganizationContent.fromFileString(content);
                    if (orgContent != null) {
                        org.contents.add(orgContent);
                    }
                }
            }
            
            return org;
        }
        
        // Handle new format (with organization ID)
        if (parts.length >= 5) {
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            String desc = parts[2];
            String founderId = parts[3];
            String[] memberArray = parts[4].split(",");
            
            Organization org = new Organization(name, desc, founderId);
            org.organizationId = id;
            if (id >= nextId) {
                nextId = id + 1;
            }
            
            for (String member : memberArray) {
                if (!member.equals(founderId)) {
                    org.addMember(member);
                }
            }
            
            // Load contents if they exist
            if (parts.length > 5) {
                String[] contents = parts[5].split("\\|\\|");
                for (String content : contents) {
                    OrganizationContent orgContent = OrganizationContent.fromFileString(content);
                    if (orgContent != null) {
                        org.contents.add(orgContent);
                    }
                }
            }
            
            return org;
        }
        return null;
    }
} 