package com.gestordedatos.gestordedatos.pojos;

import com.gestordedatos.gestordedatos.Globals;

import org.json.JSONException;
import org.json.JSONObject;

public class Subject {
    int ID;
    String name;
    String teacher;
    String classroom;
    String startTime;
    String endingTime;

    int classroomID;

    public Subject() {
        this.ID = Globals.SIN_VALOR_INT;
        this.name = Globals.SIN_VALOR_STRING;
        this.teacher = Globals.SIN_VALOR_STRING;
        this.classroom = Globals.SIN_VALOR_STRING;
        this.startTime = Globals.SIN_VALOR_STRING;
        this.endingTime = Globals.SIN_VALOR_STRING;
        this.classroomID = Globals.SIN_VALOR_INT;
    }

    public Subject(final String name, final String teacher, final String classroom, final String startTime, final String endingTime) {
        this.name = name;
        this.teacher = teacher;
        this.classroom = classroom;
        this.startTime = startTime;
        this.endingTime = endingTime;
    }

    /*public Subject(int ID, final String name, final String teacher, final String classroom, final String startTime, final String endingTime) {
        this.ID = ID;
        this.name = name;
        this.teacher = teacher;
        this.classroom = classroom;
        this.startTime = startTime;
        this.endingTime = endingTime;
    }*/

    public Subject(int ID, final String name, final String teacher, final String classroom, final String startTime, final String endingTime, final int classroomID) {
        this.ID = ID;
        this.name = name;
        this.teacher = teacher;
        this.classroom = classroom;
        this.startTime = startTime;
        this.endingTime = endingTime;
        this.classroomID = classroomID;
    }

    public Subject(final String name, final String teacher, final String classroom, final String startTime, final String endingTime, final int classroomID) {
        this.name = name;
        this.teacher = teacher;
        this.classroom = classroom;
        this.startTime = startTime;
        this.endingTime = endingTime;
        this.classroomID = classroomID;
    }

    public int getID() {
        return ID;
    }

    public void setID(final int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(final String teacher) {
        this.teacher = teacher;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(final String classroom) {
        this.classroom = classroom;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(final String startTime) {
        this.startTime = startTime;
    }

    public String getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(final String endingTime) {
        this.endingTime = endingTime;
    }

    public int getClassroomID() {
        return classroomID;
    }

    public void setClassroomID(final int classroomID) {
        this.classroomID = classroomID;
    }

    public String toJSON(){
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("ID", this.getID());
            jsonObject.put("name", this.getName());
            jsonObject.put("teacher", this.getTeacher());
            jsonObject.put("classroom", this.getClassroom());
            jsonObject.put("startTime", this.getStartTime());
            jsonObject.put("endingTime", this.getEndingTime());
            jsonObject.put("classroomID", this.getClassroomID());

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
