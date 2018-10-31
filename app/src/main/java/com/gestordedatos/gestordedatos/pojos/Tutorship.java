package com.gestordedatos.gestordedatos.pojos;

public class Tutorship {
    int ID;
    String name;
    String classroom;
    String startTime;
    String endingTime;

    public Tutorship() {
    }

    public Tutorship(final String name, final String classroom, final String startTime, final String endingTime) {
        this.name = name;
        this.classroom = classroom;
        this.startTime = startTime;
        this.endingTime = endingTime;
    }

    public Tutorship(int ID, final String name, final String classroom, final String startTime, final String endingTime) {
        this.ID = ID;
        this.name = name;
        this.classroom = classroom;
        this.startTime = startTime;
        this.endingTime = endingTime;
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
}
