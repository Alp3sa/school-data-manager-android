package com.gestordedatos.gestordedatos.contentProvider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.widget.Toast;

import com.gestordedatos.gestordedatos.Globals;
import com.gestordedatos.gestordedatos.R;
import com.gestordedatos.gestordedatos.Utilities;
import com.gestordedatos.gestordedatos.pojos.Bitacora;
import com.gestordedatos.gestordedatos.pojos.Classroom;
import com.gestordedatos.gestordedatos.pojos.Subject;
import com.gestordedatos.gestordedatos.sync.Sincronizacion;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class SubjectProvider {
    static public Uri insert(ContentResolver resolvedor, Subject subject) throws Exception {
        Uri uri = Contract.Subject.CONTENT_URI;

        ContentValues values = new ContentValues();
        if(subject.getID() != Globals.SIN_VALOR_INT) values.put(Contract.Subject._ID, subject.getID());
        values.put(Contract.Subject.name,subject.getName());
        values.put(Contract.Subject.teacher,subject.getTeacher());
        values.put(Contract.Subject.classroom,subject.getClassroom());
        values.put(Contract.Subject.startTime,subject.getStartTime());
        values.put(Contract.Subject.endingTime,subject.getEndingTime());
        values.put(Contract.Subject.classroomID,subject.getClassroomID());

        return resolvedor.insert(uri, values);
    }

    public static Uri insertRecord(ContentResolver resolver, Subject subject, Context context){
        Uri uri = Contract.Subject.CONTENT_URI;
        ContentValues values = new ContentValues();
        values.put(Contract.Subject.name,subject.getName());
        values.put(Contract.Subject.teacher,subject.getTeacher());
        values.put(Contract.Subject.classroom,subject.getClassroom());
        values.put(Contract.Subject.startTime,subject.getStartTime());
        values.put(Contract.Subject.endingTime,subject.getEndingTime());
        values.put(Contract.Subject.classroomID,subject.getClassroomID());

        Globals.CLASSROOM_TABLE_NAME = "Subjects";

        Uri uriResult = resolver.insert(uri,values);

        return uriResult;
    }

    public static void insertConBitacora(ContentResolver resolvedor, Subject registro, Context context) throws Exception {
        Uri uri = SubjectProvider.insertRecord(resolvedor, registro, context);

        int id = Integer.valueOf(uri.getLastPathSegment());

        Bitacora bitacora = new Bitacora();
        bitacora.setID_Element(id);
        //bitacora.setClass_element(Globals.CLASE_SUBJECT);
        bitacora.setOperacion(Globals.OPERACION_INSERTAR);
        BitacoraProveedor.insert(resolvedor,bitacora);
        Globals.STATUS_OPERATION=true;
        Sincronizacion.refresh();
        //return uri;
    }

    static public void delete(ContentResolver resolver, int ElementID)  throws Exception{
        Uri uri = Uri.parse(Contract.Subject.CONTENT_URI + "/" + ElementID);
        resolver.delete(uri, null, null);
    }

    public static void deleteRecord(ContentResolver resolver, int subjectId){
        Uri uri = Uri.parse(Contract.Subject.CONTENT_URI+"/"+subjectId);

        Globals.CLASSROOM_TABLE_NAME = "Subjects";

        resolver.delete(uri, null, null);
    }

    public static void deleteConBitacora(ContentResolver resolvedor, int id, Context context) throws Exception {
        delete(resolvedor, id);

        Bitacora bitacora = new Bitacora();
        bitacora.setID_Element(id);
        bitacora.setOperacion(Globals.OPERACION_BORRAR);
        BitacoraProveedor.insert(resolvedor, bitacora);
        Globals.STATUS_OPERATION=true;
        Sincronizacion.refresh();
    }

    static public void update(ContentResolver resolver, Subject subject, boolean form)  throws Exception{
        Uri uri = Uri.parse(Contract.Subject.CONTENT_URI + "/" + subject.getID());

        ContentValues values = new ContentValues();
        values.put(Contract.Subject.name,subject.getName());
        values.put(Contract.Subject.teacher,subject.getTeacher());
        values.put(Contract.Subject.classroom,subject.getClassroom());
        values.put(Contract.Subject.startTime,subject.getStartTime());
        values.put(Contract.Subject.endingTime,subject.getEndingTime());
        values.put(Contract.Subject.classroomID,subject.getClassroomID());

        resolver.update(uri, values, null, null);
    }

    public static void updateRecord(ContentResolver resolver, Subject subject){
        Uri uri = Uri.parse(Contract.Subject.CONTENT_URI+"/"+subject.getID());

        ContentValues values = new ContentValues();
        values.put(Contract.Subject.name,subject.getName());
        values.put(Contract.Subject.teacher,subject.getTeacher());
        values.put(Contract.Subject.classroom,subject.getClassroom());
        values.put(Contract.Subject.startTime,subject.getStartTime());
        values.put(Contract.Subject.endingTime,subject.getEndingTime());
        values.put(Contract.Subject.classroomID,subject.getClassroomID());

        Globals.CLASSROOM_TABLE_NAME = "Subjects";

        resolver.update(uri, values, null, null);
    }

    public static void updateConBitacora(ContentResolver resolvedor, Subject registro) throws Exception {
        update(resolvedor, registro,true);

        Bitacora bitacora = new Bitacora();
        bitacora.setID_Element(registro.getID());
        bitacora.setClass_element(Globals.CLASE_SUBJECT);
        bitacora.setOperacion(Globals.OPERACION_MODIFICAR);
        BitacoraProveedor.insert(resolvedor, bitacora);
        Globals.STATUS_OPERATION=true;
        Sincronizacion.refresh();
    }

    static public Subject read(ContentResolver resolver, int ElementID)  throws Exception{
        Uri uri = Uri.parse(Contract.Subject.CONTENT_URI + "/" + ElementID);

        /*String[] projection = {Contract.Classroom._ID,
                Contract.Classroom.classroomName,
                Contract.Classroom.subject};*/
        String[] projection = {Contract.Subject._ID,
                Contract.Subject.name,
                Contract.Subject.teacher,
                Contract.Subject.classroom,
                Contract.Subject.startTime,
                Contract.Subject.endingTime,
                Contract.Subject.classroomID};

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if (cursor.moveToFirst()){
            Subject subject = new Subject();
            subject.setID(cursor.getInt(cursor.getColumnIndex(Contract.Subject._ID)));
            subject.setName(cursor.getString(cursor.getColumnIndex(Contract.Subject.name)));
            subject.setTeacher(cursor.getString(cursor.getColumnIndex(Contract.Subject.teacher)));
            subject.setClassroom(cursor.getString(cursor.getColumnIndex(Contract.Subject.classroom)));
            subject.setStartTime(cursor.getString(cursor.getColumnIndex(Contract.Subject.startTime)));
            subject.setEndingTime(cursor.getString(cursor.getColumnIndex(Contract.Subject.endingTime)));
            subject.setClassroomID(cursor.getInt(cursor.getColumnIndex(Contract.Subject.classroomID)));
            return subject;
        }

        return null;

    }

    public static Subject readRecord(ContentResolver resolver, int subjectId){
        Uri uri = Uri.parse(Contract.Subject.CONTENT_URI+"/"+subjectId);

        String[] projection = {
                Contract.Subject._ID,
                Contract.Subject.name,
                Contract.Subject.teacher,
                Contract.Subject.classroom,
                Contract.Subject.startTime,
                Contract.Subject.endingTime,
                Contract.Subject.classroomID
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
            subject.setClassroomID(cursor.getInt(cursor.getColumnIndex(Contract.Subject.classroomID)));

            return subject;
        }

        return null;
    }

    public static int readRecordFromClassrooms(ContentResolver resolver, int classroomID){
        //GET SUBJECTS FROM CLASSROOM ID
        if(classroomID!=-1) {
            Globals.CLASSROOM_TABLE_NAME="Subjects";
            Uri uri = Uri.parse("content://" + Contract.AUTHORITY + "/Subjects");

            String[] projection = {
                    Contract.Subject._ID,
                    Contract.Subject.name,
                    Contract.Subject.teacher,
                    Contract.Subject.classroom,
                    Contract.Subject.startTime,
                    Contract.Subject.endingTime,
                    Contract.Subject.classroomID
            };

            Cursor cursor = resolver.query(uri, projection, null, null, null);

            int numRows = 0;

            try {
                while (cursor.moveToNext()) {
                    if (classroomID==cursor.getInt(cursor.getColumnIndex(Contract.Subject.classroomID))) {
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

    static public ArrayList<Subject> readAll(ContentResolver resolver)  throws Exception{
        Uri uri = Contract.Subject.CONTENT_URI;

        String[] projection = {
                Contract.Subject._ID,
                Contract.Subject.name,
                Contract.Subject.teacher,
                Contract.Subject.classroom,
                Contract.Subject.startTime,
                Contract.Subject.endingTime,
                Contract.Subject.classroomID
        };

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        ArrayList<Subject> registros = new ArrayList<>();

        while (cursor.moveToNext()){
            Subject subject = new Subject();
            subject.setID(cursor.getInt(cursor.getColumnIndex(Contract.Subject._ID)));
            subject.setName(cursor.getString(cursor.getColumnIndex(Contract.Subject.name)));
            subject.setTeacher(cursor.getString(cursor.getColumnIndex(Contract.Subject.teacher)));
            subject.setClassroom(cursor.getString(cursor.getColumnIndex(Contract.Subject.classroom)));
            subject.setStartTime(cursor.getString(cursor.getColumnIndex(Contract.Subject.startTime)));
            subject.setEndingTime(cursor.getString(cursor.getColumnIndex(Contract.Subject.endingTime)));
            subject.setClassroomID(cursor.getInt(cursor.getColumnIndex(Contract.Subject.classroomID)));
            registros.add(subject);
        }

        return registros;

    }
}
