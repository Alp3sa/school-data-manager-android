package com.gestordedatos.gestordedatos.pojos;

import android.graphics.Bitmap;

public class Classroom {
    int ID;
    String classroomName;
    String subject;
    Bitmap image;

    public Classroom() {
    }

    public Classroom(String classroomName,String subject,Bitmap image) {
        this.classroomName = classroomName;
        this.subject = subject;
        this.image = image;
    }

    public Classroom(int ID, String classroomName, String subject,Bitmap image) {
        this.ID=ID;
        this.classroomName = classroomName;
        this.subject = subject;
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(final Bitmap image) {
        this.image = image;
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
