package com.example.admin.augscan;

public class Student_class {
    public Student_class(long lid, String name, int roll) {
        this.name = name;
        this.roll = roll;
        this.lid = lid;
        this.status="";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getlid() {
        return lid;
    }

    public void setlid(long lid) {
        this.lid = lid;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    long lid;
    String name;
    int roll;
    String status;
}
