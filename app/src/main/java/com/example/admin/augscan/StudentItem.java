package com.example.admin.augscan;

public class StudentItem {
    private long studentid;
    private String studentname;
    private String studentlastname;
    private String studentqrcode;
    private String studentphone1;
    private String studentphone2;
    private String roll;
    private String status;




    public StudentItem(long studentid, String studentname, String studentlastname, String studentqrcode, String studentphone1, String studentphone2){

    this.studentid=studentid;
    this.studentname=studentname;
    this.studentlastname=studentlastname;
    this.studentqrcode= studentqrcode;
    this.studentphone1=studentphone1;
    this.studentphone2= studentphone2;
    this.roll="";
    this.status="";

}


    public long getstudentid() {
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

    public String getPhone1() {
        return studentphone1;
    }

    public void setPhone1(String studentphone1) {
        this.studentphone1 = studentphone1;
    }

    public String getPhone2() {
        return studentphone2;
    }

    public void setPhone2(String studentphone2) {
        this.studentphone2 = studentphone2;
    }

    public void setName(String studentName) {this.studentname = studentName;
    }
    public void setLName(String studentlName) {this.studentlastname = studentlName;
    }
}