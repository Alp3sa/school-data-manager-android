package com.gestordedatos.gestordedatos.pojos;

import android.graphics.Bitmap;

import com.gestordedatos.gestordedatos.Globals;

import org.json.JSONException;
import org.json.JSONObject;

public class Classroom {
    int ID;
    String classroomName;
    String subject;
    Bitmap image;

    String details;
    String image_url;

    public Classroom() {
        this.ID = Globals.SIN_VALOR_INT;
        this.classroomName = Globals.SIN_VALOR_STRING;
        this.details = Globals.SIN_VALOR_STRING;
        this.image_url = Globals.SIN_VALOR_STRING;
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

    public Classroom(int ID, String classroomName, String details, String image_url) {
        this.ID=ID;
        this.classroomName = classroomName;
        this.details = details;
        this.image_url = image_url;
    }

    public Classroom(String classroomName, String details, String image_url) {
        this.classroomName = classroomName;
        this.details = details;
        this.image_url = image_url;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(final String details) {
        this.details = details;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(final String image_url) {
        this.image_url = image_url;
    }

    public String toJSON(){
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("ID", this.getID());
            jsonObject.put("classroomName", this.getClassroomName());
            jsonObject.put("details", this.getDetails());
            jsonObject.put("image", this.getImage_url());

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
