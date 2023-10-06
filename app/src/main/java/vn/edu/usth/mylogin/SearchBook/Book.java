package vn.edu.usth.mylogin.SearchBook;

public class Book {
    String title;
    String key;
    String description;

    Create created;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Create getCreated() {
        return created;
    }

    public void setCreated(Create created) {
        this.created = created;
    }
}
