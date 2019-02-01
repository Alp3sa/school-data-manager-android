package com.gestordedatos.gestordedatos.pojos;

import com.gestordedatos.gestordedatos.Globals;

import org.json.JSONException;
import org.json.JSONObject;

public class Enrollment {
    int ID;
    int userID;
    int subjectID;

    public Enrollment() {
        this.ID = Globals.SIN_VALOR_INT;
        this.userID = Globals.SIN_VALOR_INT;
        this.subjectID = Globals.SIN_VALOR_INT;
    }

    public Enrollment(final int ID, final int userID, final int subjectID) {
        this.ID = ID;
        this.userID = userID;
        this.subjectID = subjectID;
    }

    public int getID() {
        return ID;
    }

    public void setID(final int ID) {
        this.ID = ID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(final int userID) {
        this.userID = userID;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(final int subjectID) {
        this.subjectID = subjectID;
    }

    public String toJSON(){
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("ID", this.getID());
            jsonObject.put("userID", this.getUserID());
            jsonObject.put("subjectID", this.getSubjectID());

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
