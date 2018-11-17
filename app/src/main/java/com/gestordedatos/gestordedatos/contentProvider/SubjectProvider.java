package com.gestordedatos.gestordedatos.contentProvider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.gestordedatos.gestordedatos.Globals;
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

        Globals.CLASSROOM_TABLE_NAME = "Subjects";

        resolver.insert(uri,values);
    }

    public static void deleteRecord(ContentResolver resolver, int subjectId){
        Uri uri = Uri.parse(Contract.Subject.CONTENT_URI+"/"+subjectId);

        Globals.CLASSROOM_TABLE_NAME = "Subjects";

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

        Globals.CLASSROOM_TABLE_NAME = "Subjects";

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

        Globals.CLASSROOM_TABLE_NAME = "Subjects";

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

    public static int readRecordFromClassrooms(ContentResolver resolver, String classroomName){
        //GET SUBJECTS FROM CLASSROOM NAME

        if(classroomName!=null) {
            Globals.CLASSROOM_TABLE_NAME="Subjects";
            Uri uri = Uri.parse("content://" + Contract.AUTHORITY + "/Subjects");

            String[] projection = {
                    Contract.Subject._ID,
                    Contract.Subject.name,
                    Contract.Subject.teacher,
                    Contract.Subject.classroom,
                    Contract.Subject.startTime,
                    Contract.Subject.endingTime
            };

            Cursor cursor = resolver.query(uri, projection, null, null, null);

            int numRows = 0;

            try {
                while (cursor.moveToNext()) {
                    if (classroomName.equals(cursor.getString(cursor.getColumnIndex(Contract.Subject.classroom)))) {
                        numRows++;
                        return 1;
                    }
                }
            } finally {cursor.close();}

            if(numRows==0){
                Globals.CLASSROOM_TABLE_NAME="Classrooms";
                return 0;
            }
        }
        return -1;
    }
}
