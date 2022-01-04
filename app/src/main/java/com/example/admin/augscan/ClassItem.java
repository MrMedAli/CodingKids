package com.example.admin.augscan;

public class ClassItem {
    public ClassItem(long classID, String className, String subjectName) {
        this.classID = classID;
        this.className = className;
        this.subjectName = subjectName;
    }

    private long classID;

    public long getClassID() {
        return classID;
    }

    public void setClassID(long classID) {
        this.classID = classID;
    }

    private String className;
    private String subjectName;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public ClassItem(String className, String subjectName) {
        this.className = className;
        this.subjectName = subjectName;
    }
}
