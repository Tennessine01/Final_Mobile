package vn.edu.usth.mylogin.SearchBook;

import java.util.List;

public class Subject {
    private String key;
    private String name;
    private String subject_type;
    private int work_count;
    private List<Work> works;

    public Subject(String key, String name, String subjectType, int workCount, List<Work> works) {
        this.key = key;
        this.name = name;
        subject_type = subjectType;
        work_count = workCount;
        this.works = works;
    }

    public List<Work> getWorks() {
        return works;
    }

    // Constructors, getters, and setters
}
