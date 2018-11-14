package com.gestordedatos.gestordedatos.contentProvider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;

import com.gestordedatos.gestordedatos.application;
import com.gestordedatos.gestordedatos.pojos.Tutorship;

public class TutorshipProvider {
    public static void insertRecord(ContentResolver resolver, Tutorship tutorship){
        Uri uri = Contract.Tutorship.CONTENT_URI;

        ContentValues values = new ContentValues();
        values.put(Contract.Tutorship.name,tutorship.getName());
        values.put(Contract.Tutorship.classroom,tutorship.getClassroom());
        values.put(Contract.Tutorship.startTime,tutorship.getStartTime());
        values.put(Contract.Tutorship.endingTime,tutorship.getEndingTime());

        application.CLASSROOM_TABLE_NAME = "Tutorships";

        resolver.insert(uri, values);
    }

    public static void deleteRecord(ContentResolver resolver, int tutorshipId){
        Uri uri = Uri.parse(Contract.Tutorship.CONTENT_URI+"/"+tutorshipId);

        application.CLASSROOM_TABLE_NAME = "Tutorships";

        resolver.delete(uri, null, null);
    }

    public static void updateRecord(ContentResolver resolver, Tutorship tutorship){
        Uri uri = Uri.parse(Contract.Tutorship.CONTENT_URI+"/"+tutorship.getID());

        ContentValues values = new ContentValues();
        values.put(Contract.Tutorship.name,tutorship.getName());
        values.put(Contract.Tutorship.classroom,tutorship.getClassroom());
        values.put(Contract.Tutorship.startTime,tutorship.getStartTime());
        values.put(Contract.Tutorship.endingTime,tutorship.getEndingTime());

        application.CLASSROOM_TABLE_NAME = "Tutorships";

        resolver.update(uri, values, null, null);
    }

    public static Tutorship readRecord(ContentResolver resolver, int tutorshipId){
        Uri uri = Uri.parse(Contract.Tutorship.CONTENT_URI+"/"+tutorshipId);

        String[] projection = {
                Contract.Tutorship._ID,
                Contract.Tutorship.name,
                Contract.Tutorship.classroom,
                Contract.Tutorship.startTime,
                Contract.Tutorship.endingTime
        };

        application.CLASSROOM_TABLE_NAME = "Tutorships";

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if(cursor.moveToFirst()){
            Tutorship tutorship = new Tutorship();
            tutorship.setID(tutorshipId);
            tutorship.setName(cursor.getString(cursor.getColumnIndex(Contract.Tutorship.name)));
            tutorship.setClassroom(cursor.getString(cursor.getColumnIndex(Contract.Tutorship.classroom)));
            tutorship.setStartTime(cursor.getString(cursor.getColumnIndex(Contract.Tutorship.startTime)));
            tutorship.setEndingTime(cursor.getString(cursor.getColumnIndex(Contract.Tutorship.endingTime)));
            return tutorship;
        }

        return null;
    }
}