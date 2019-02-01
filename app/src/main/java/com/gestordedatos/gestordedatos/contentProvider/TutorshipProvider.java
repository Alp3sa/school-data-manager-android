package com.gestordedatos.gestordedatos.contentProvider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.gestordedatos.gestordedatos.Globals;
import com.gestordedatos.gestordedatos.pojos.Bitacora;
import com.gestordedatos.gestordedatos.pojos.Tutorship;
import com.gestordedatos.gestordedatos.pojos.Tutorship;
import com.gestordedatos.gestordedatos.sync.Sincronizacion;

import java.util.ArrayList;

public class TutorshipProvider {

    static public Uri insert(ContentResolver resolvedor, Tutorship tutorship) throws Exception {
        Uri uri = Contract.Tutorship.CONTENT_URI;

        ContentValues values = new ContentValues();
        if(tutorship.getID() != Globals.SIN_VALOR_INT) values.put(Contract.Tutorship._ID, tutorship.getID());
        values.put(Contract.Tutorship.name,tutorship.getName());
        values.put(Contract.Tutorship.classroom,tutorship.getClassroom());
        values.put(Contract.Tutorship.startTime,tutorship.getStartTime());
        values.put(Contract.Tutorship.endingTime,tutorship.getEndingTime());
        values.put(Contract.Tutorship.classroomID,tutorship.getClassroomID());

        return resolvedor.insert(uri, values);
    }
    
    public static Uri insertRecord(ContentResolver resolver, Tutorship tutorship, Context context){
        Uri uri = Contract.Tutorship.CONTENT_URI;

        ContentValues values = new ContentValues();
        values.put(Contract.Tutorship.name,tutorship.getName());
        values.put(Contract.Tutorship.classroom,tutorship.getClassroom());
        values.put(Contract.Tutorship.startTime,tutorship.getStartTime());
        values.put(Contract.Tutorship.endingTime,tutorship.getEndingTime());
        values.put(Contract.Tutorship.classroomID,tutorship.getClassroomID());

        System.out.println("CHECK TUTOR "+uri+" "+tutorship.getName()+" "+tutorship.getClassroom()+" "+tutorship.getStartTime()+" "+tutorship.getEndingTime()+" "+tutorship.getClassroomID());

        Globals.CLASSROOM_TABLE_NAME = "Tutorships";

        Uri uriResult = resolver.insert(uri,values);
        System.out.println("CHECK TUTOR "+uriResult);
        return uriResult;
    }

    public static void insertConBitacora(ContentResolver resolvedor, Tutorship registro, Context context) throws Exception {
        Uri uri = TutorshipProvider.insertRecord(resolvedor, registro, context);

        int id = Integer.valueOf(uri.getLastPathSegment());

        Bitacora bitacora = new Bitacora();
        bitacora.setID_Element(id);
        //bitacora.setClass_element(Globals.CLASE_TUTORSHIP);
        bitacora.setOperacion(Globals.OPERACION_INSERTAR);
        BitacoraProveedor.insert(resolvedor,bitacora);
        Globals.STATUS_OPERATION=true;
        Sincronizacion.refresh();
        //return uri;
    }

    static public void delete(ContentResolver resolver, int ElementID)  throws Exception{
        Uri uri = Uri.parse(Contract.Tutorship.CONTENT_URI + "/" + ElementID);
        resolver.delete(uri, null, null);
    }

    public static void deleteRecord(ContentResolver resolver, int tutorshipId){
        Uri uri = Uri.parse(Contract.Tutorship.CONTENT_URI+"/"+tutorshipId);

        Globals.CLASSROOM_TABLE_NAME = "Tutorships";

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

    static public void update(ContentResolver resolver, Tutorship tutorship, boolean form)  throws Exception{
        Uri uri = Uri.parse(Contract.Tutorship.CONTENT_URI + "/" + tutorship.getID());

        ContentValues values = new ContentValues();
        values.put(Contract.Tutorship.name,tutorship.getName());
        values.put(Contract.Tutorship.classroom,tutorship.getClassroom());
        values.put(Contract.Tutorship.startTime,tutorship.getStartTime());
        values.put(Contract.Tutorship.endingTime,tutorship.getEndingTime());
        values.put(Contract.Tutorship.classroomID,tutorship.getClassroomID());

        resolver.update(uri, values, null, null);

        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
    }

    public static void updateRecord(ContentResolver resolver, Tutorship tutorship){
        Uri uri = Uri.parse(Contract.Tutorship.CONTENT_URI+"/"+tutorship.getID());

        ContentValues values = new ContentValues();
        values.put(Contract.Tutorship.name,tutorship.getName());
        values.put(Contract.Tutorship.classroom,tutorship.getClassroom());
        values.put(Contract.Tutorship.startTime,tutorship.getStartTime());
        values.put(Contract.Tutorship.endingTime,tutorship.getEndingTime());
        values.put(Contract.Tutorship.classroomID,tutorship.getClassroomID());

        Globals.CLASSROOM_TABLE_NAME = "Tutorships";

        resolver.update(uri, values, null, null);
    }

    public static void updateConBitacora(ContentResolver resolvedor, Tutorship registro) throws Exception {
        update(resolvedor, registro,true);

        Bitacora bitacora = new Bitacora();
        bitacora.setID_Element(registro.getID());
        //bitacora.setClass_element(Globals.CLASE_SUBJECT);
        bitacora.setOperacion(Globals.OPERACION_MODIFICAR);
        BitacoraProveedor.insert(resolvedor, bitacora);

        Globals.STATUS_OPERATION=true;
        Sincronizacion.refresh();
    }

    static public Tutorship read(ContentResolver resolver, int ElementID)  throws Exception{
        Uri uri = Uri.parse(Contract.Tutorship.CONTENT_URI + "/" + ElementID);

        String[] projection = {Contract.Tutorship._ID,
                Contract.Tutorship.name,
                Contract.Tutorship.classroom,
                Contract.Tutorship.startTime,
                Contract.Tutorship.endingTime,
                Contract.Tutorship.classroomID};

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if (cursor.moveToFirst()){
            Tutorship tutorship = new Tutorship();
            tutorship.setID(cursor.getInt(cursor.getColumnIndex(Contract.Tutorship._ID)));
            tutorship.setName(cursor.getString(cursor.getColumnIndex(Contract.Tutorship.name)));
            tutorship.setClassroom(cursor.getString(cursor.getColumnIndex(Contract.Tutorship.classroom)));
            tutorship.setStartTime(cursor.getString(cursor.getColumnIndex(Contract.Tutorship.startTime)));
            tutorship.setEndingTime(cursor.getString(cursor.getColumnIndex(Contract.Tutorship.endingTime)));
            tutorship.setClassroomID(cursor.getInt(cursor.getColumnIndex(Contract.Tutorship.classroomID)));
            return tutorship;
        }

        return null;

    }

    public static Tutorship readRecord(ContentResolver resolver, int tutorshipId){
        Uri uri = Uri.parse(Contract.Tutorship.CONTENT_URI+"/"+tutorshipId);

        String[] projection = {
                Contract.Tutorship._ID,
                Contract.Tutorship.name,
                Contract.Tutorship.classroom,
                Contract.Tutorship.startTime,
                Contract.Tutorship.endingTime,
                Contract.Tutorship.classroomID
        };

        Globals.CLASSROOM_TABLE_NAME = "Tutorships";

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if(cursor.moveToFirst()){
            Tutorship tutorship = new Tutorship();
            tutorship.setID(tutorshipId);
            tutorship.setName(cursor.getString(cursor.getColumnIndex(Contract.Tutorship.name)));
            tutorship.setClassroom(cursor.getString(cursor.getColumnIndex(Contract.Tutorship.classroom)));
            tutorship.setStartTime(cursor.getString(cursor.getColumnIndex(Contract.Tutorship.startTime)));
            tutorship.setEndingTime(cursor.getString(cursor.getColumnIndex(Contract.Tutorship.endingTime)));
            tutorship.setClassroomID(cursor.getInt(cursor.getColumnIndex(Contract.Tutorship.classroomID)));
            return tutorship;
        }

        return null;
    }

    public static int readRecordFromClassrooms(ContentResolver resolver, String classroomName){
        //GET SUBJECTS FROM CLASSROOM NAME

        if(classroomName!=null) {
            Globals.CLASSROOM_TABLE_NAME="Tutorships";
            Uri uri = Uri.parse("content://" + Contract.AUTHORITY + "/Tutorships");

            String[] projection = {
                    Contract.Tutorship._ID,
                    Contract.Tutorship.name,
                    Contract.Tutorship.classroom,
                    Contract.Tutorship.startTime,
                    Contract.Tutorship.endingTime,
                    Contract.Tutorship.classroomID
            };

            Cursor cursor = resolver.query(uri, projection, null, null, null);

            int numRows = 0;

            try {
                while (cursor.moveToNext()) {
                    if (classroomName.equals(cursor.getString(cursor.getColumnIndex(Contract.Tutorship.classroom)))) {
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

    static public ArrayList<Tutorship> readAll(ContentResolver resolver)  throws Exception{
        Uri uri = Contract.Tutorship.CONTENT_URI;

        String[] projection = {
                Contract.Tutorship._ID,
                Contract.Tutorship.name,
                Contract.Tutorship.classroom,
                Contract.Tutorship.startTime,
                Contract.Tutorship.endingTime,
                Contract.Tutorship.classroomID
        };

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        ArrayList<Tutorship> registros = new ArrayList<>();

        while (cursor.moveToNext()){
            Tutorship tutorship = new Tutorship();
            tutorship.setID(cursor.getInt(cursor.getColumnIndex(Contract.Tutorship._ID)));
            tutorship.setName(cursor.getString(cursor.getColumnIndex(Contract.Tutorship.name)));
            tutorship.setClassroom(cursor.getString(cursor.getColumnIndex(Contract.Tutorship.classroom)));
            tutorship.setStartTime(cursor.getString(cursor.getColumnIndex(Contract.Tutorship.startTime)));
            tutorship.setEndingTime(cursor.getString(cursor.getColumnIndex(Contract.Tutorship.endingTime)));
            tutorship.setClassroomID(cursor.getInt(cursor.getColumnIndex(Contract.Tutorship.classroomID)));
            registros.add(tutorship);
        }

        return registros;
    }
}