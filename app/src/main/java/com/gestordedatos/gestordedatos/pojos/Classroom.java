package com.gestordedatos.gestordedatos.pojos;

public class Classroom {
    int ID;
    String classroomName;
    String subject;

    public Classroom() {
    }

    public Classroom(String classroomName,String subject) {
        this.classroomName = classroomName;
        this.subject = subject;
    }

    public Classroom(int ID, String classroomName, String subject) {
        this.ID=ID;
        this.classroomName = classroomName;
        this.subject = subject;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(final String classroomName) {
        this.classroomName = classroomName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public int getID() {
        return ID;
    }

    public void setID(final int ID) {
        this.ID = ID;
    }
}
