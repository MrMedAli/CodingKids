package com.example.admin.augscan;

public class StudentItem {
    private String studentid;
    private String studentname;
    private String studentlastname;
    private String studentqrcode;
    private String roll;
    private String status;


public StudentItem() {

}

public StudentItem(String studentid, String studentname, String studentlastname, String studentqrcode){

    this.studentid=studentid;
    this.studentname=studentname;
    this.studentlastname=studentlastname;
    this.studentqrcode= studentqrcode;
    this.roll="";
    this.status="";

}

    public String getstudentid() {
        return studentid;
    }

    public String getstudentname() {
        return studentname;
    }

    public String getstudentlastname() {
        return studentlastname;
    }

    public String getstudentqrcode() {
        return studentqrcode;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}