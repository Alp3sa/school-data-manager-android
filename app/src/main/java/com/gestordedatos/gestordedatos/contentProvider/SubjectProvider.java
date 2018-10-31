package com.gestordedatos.gestordedatos.contentProvider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.gestordedatos.gestordedatos.application;
import com.gestordedatos.gestordedatos.pojos.Subject;

public class SubjectProvider {
    public static void insertRecord(ContentResolver resolver, Subject subject){
        Uri uri = Contract.Subject.CONTENT_URI;

        ContentValues values = new ContentValues();
        values.put(Contract.Subject.name,subject.getName());
        values.put(Contract.Subject.teacher,subject.getTeacher());
        values.put(Contract.Subject.classroom,subject.getClassroom());
        values.put(Contract.Subject.startTime,subject.getStartTime());
        values.put(Contract.Subject.endingTime,subject.getEndingTime());

        application.CLASSROOM_TABLE_NAME = "Subjects";

        resolver.insert(uri,values);
    }

    public static void deleteRecord(ContentResolver resolver, int subjectId){
        Uri uri = Uri.parse(Contract.Subject.CONTENT_URI+"/"+subjectId);

        application.CLASSROOM_TABLE_NAME = "Subjects";

        resolver.delete(uri, null, null);
    }

    public static void updateRecord(ContentResolver resolver, Subject subject){
        Uri uri = Uri.parse(Contract.Subject.CONTENT_URI+"/"+subject.getID());

        ContentValues values = new ContentValues();
        values.put(Contract.Subject.name,subject.getName());
        values.put(Contract.Subject.teacher,subject.getTeacher());
        values.put(Contract.Subject.classroom,subject.getClassroom());
        values.put(Contract.Subject.startTime,subject.getStartTime());
        values.put(Contract.Subject.endingTime,subject.getEndingTime());

        application.CLASSROOM_TABLE_NAME = "Subjects";

        resolver.update(uri, values, null, null);
    }

    public static Subject readRecord(ContentResolver resolver, int subjectId){
        Uri uri = Uri.parse(Contract.Subject.CONTENT_URI+"/"+subjectId);

        String[] projection = {
                Contract.Subject._ID,
                Contract.Subject.name,
                Contract.Subject.teacher,
                Contract.Subject.classroom,
                Contract.Subject.startTime,
                Contract.Subject.endingTime
        };

        application.CLASSROOM_TABLE_NAME = "Subjects";

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if(cursor.moveToFirst()){
            Subject subject = new Subject();
            subject.setID(subjectId);
            subject.setName(cursor.getString(cursor.getColumnIndex(Contract.Subject.name)));
            subject.setTeacher(cursor.getString(cursor.getColumnIndex(Contract.Subject.teacher)));
            subject.setClassroom(cursor.getString(cursor.getColumnIndex(Contract.Subject.classroom)));
            subject.setStartTime(cursor.getString(cursor.getColumnIndex(Contract.Subject.startTime)));
            subject.setEndingTime(cursor.getString(cursor.getColumnIndex(Contract.Subject.endingTime)));
            return subject;
        }

        return null;
    }
}
