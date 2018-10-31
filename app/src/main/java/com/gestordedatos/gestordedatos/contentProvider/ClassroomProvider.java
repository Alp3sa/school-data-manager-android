package com.gestordedatos.gestordedatos.contentProvider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.gestordedatos.gestordedatos.application;
import com.gestordedatos.gestordedatos.pojos.Classroom;

public class ClassroomProvider {
    public static void insertRecord(ContentResolver resolver, Classroom classroom){
        Uri uri = Contract.Classroom.CONTENT_URI;

        ContentValues values = new ContentValues();
        values.put(Contract.Classroom.classroomName,classroom.getClassroomName());
        values.put(Contract.Classroom.subject,classroom.getSubject());

        application.CLASSROOM_TABLE_NAME = "Classrooms";

        resolver.insert(uri,values);
    }

    public static void deleteRecord(ContentResolver resolver, int classroomId){
        Uri uri = Uri.parse(Contract.Classroom.CONTENT_URI+"/"+classroomId);

        application.CLASSROOM_TABLE_NAME = "Classrooms";

        resolver.delete(uri, null, null);
    }

    public static void updateRecord(ContentResolver resolver, Classroom classroom){
        Uri uri = Uri.parse(Contract.Classroom.CONTENT_URI+"/"+classroom.getID());

        ContentValues values = new ContentValues();
        values.put(Contract.Classroom.classroomName,classroom.getClassroomName());
        values.put(Contract.Classroom.subject,classroom.getSubject());

        application.CLASSROOM_TABLE_NAME = "Classrooms";

        resolver.update(uri, values, null, null);
    }

    public static Classroom readRecord(ContentResolver resolver, int classroomId){
        Uri uri = Uri.parse(Contract.Classroom.CONTENT_URI+"/"+classroomId);

        String[] projection = {
                Contract.Classroom._ID,
                Contract.Classroom.classroomName,
                Contract.Classroom.subject
        };

        application.CLASSROOM_TABLE_NAME = "Classrooms";

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if(cursor.moveToFirst()){
            Classroom classroom = new Classroom();
            classroom.setID(classroomId);
            classroom.setClassroomName(cursor.getString(cursor.getColumnIndex(Contract.Classroom.classroomName)));
            classroom.setSubject(cursor.getString(cursor.getColumnIndex(Contract.Classroom.subject)));
            return classroom;
        }

        return null;
    }
}
