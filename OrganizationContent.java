package orgsystem;

import java.time.LocalDateTime;

public class OrganizationContent {
    private static int nextId = 1;
    private int postId;
    private String postedContent;
    private LocalDateTime timePosted;
    private int postedBy;
    private int postedIn;

    public OrganizationContent(String postedContent, int postedBy, int postedIn) {
        this.postId = nextId++;
        this.postedContent = postedContent;
        this.timePosted = LocalDateTime.now();
        this.postedBy = postedBy;
        this.postedIn = postedIn;
    }

    // Getters
    public int getPostId() { return postId; }
    public String getPostedContent() { return postedContent; }
    public LocalDateTime getTimePosted() { return timePosted; }
    public int getPostedBy() { return postedBy; }
    public int getPostedIn() { return postedIn; }

    // For file storage
    public String toFileString() {
        return postId + "|" + postedContent + "|" + timePosted + "|" + postedBy + "|" + postedIn;
    }

    // For file loading
    public static OrganizationContent fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length >= 5) {
            int id = Integer.parseInt(parts[0]);
            String content = parts[1];
            LocalDateTime time = LocalDateTime.parse(parts[2]);
            int by = Integer.parseInt(parts[3]);
            int in = Integer.parseInt(parts[4]);

            OrganizationContent post = new OrganizationContent(content, by, in);
            post.postId = id;
            post.timePosted = time;
            if (id >= nextId) {
                nextId = id + 1;
            }
            return post;
        }
        return null;
    }
} 