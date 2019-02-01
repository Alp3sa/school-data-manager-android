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
import com.gestordedatos.gestordedatos.pojos.Enrollment;

import java.io.IOException;
import java.util.ArrayList;

public class EnrollmentProvider {
    static public Uri insert(ContentResolver resolvedor, Enrollment enrollment) throws Exception {
        Uri uri = Contract.Enrollment.CONTENT_URI;

        ContentValues values = new ContentValues();
        if(enrollment.getID() != Globals.SIN_VALOR_INT) values.put(Contract.Enrollment._ID, enrollment.getID());
        values.put(Contract.Enrollment.userID, enrollment.getUserID());
        values.put(Contract.Enrollment.subjectID, enrollment.getSubjectID());

        return resolvedor.insert(uri, values);
    }

    public static Uri insertRecord(ContentResolver resolver, Enrollment enrollment,Context context){
        Uri uri = Contract.Enrollment.CONTENT_URI;
        ContentValues values = new ContentValues();
        values.put(Contract.Enrollment.userID, enrollment.getUserID());
        values.put(Contract.Enrollment.subjectID, enrollment.getSubjectID());

        Globals.CLASSROOM_TABLE_NAME = "Enrollments";

        Uri uriResult = resolver.insert(uri,values);

        return uriResult;
    }

    public static void insertConBitacora(ContentResolver resolvedor, Enrollment registro, Context context) throws Exception {
        Uri uri = EnrollmentProvider.insertRecord(resolvedor, registro, context);

        int id = Integer.valueOf(uri.getLastPathSegment());

        Bitacora bitacora = new Bitacora();
        bitacora.setID_Element(id);
        bitacora.setOperacion(Globals.OPERACION_INSERTAR);
        BitacoraProveedor.insert(resolvedor,bitacora);
        //return uri;
    }

    static public void delete(ContentResolver resolver, int ElementID)  throws Exception{
        Uri uri = Uri.parse(Contract.Enrollment.CONTENT_URI + "/" + ElementID);
        resolver.delete(uri, null, null);
    }

    public static void deleteRecord(ContentResolver resolver, int enrollmentId, Context context){
        Uri uri = Uri.parse(Contract.Enrollment.CONTENT_URI+"/"+enrollmentId);

        Globals.CLASSROOM_TABLE_NAME = "Enrollments";

        resolver.delete(uri, null, null);
    }

    public static void deleteConBitacora(ContentResolver resolvedor, int id) throws Exception {
        delete(resolvedor, id);

        Bitacora bitacora = new Bitacora();
        bitacora.setID_Element(id);
        bitacora.setOperacion(Globals.OPERACION_BORRAR);
        BitacoraProveedor.insert(resolvedor, bitacora);
    }

    static public void update(ContentResolver resolver, Enrollment enrollment, boolean form)  throws Exception{
        Uri uri = Uri.parse(Contract.Enrollment.CONTENT_URI + "/" + enrollment.getID());

        ContentValues values = new ContentValues();
        values.put(Contract.Enrollment.userID, enrollment.getUserID());
        values.put(Contract.Enrollment.subjectID, enrollment.getSubjectID());

        resolver.update(uri, values, null, null);

        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
    }

    public static void updateRecord(ContentResolver resolver, Enrollment enrollment, Context context){
        Uri uri = Uri.parse(Contract.Enrollment.CONTENT_URI+"/"+enrollment.getID());

        ContentValues values = new ContentValues();
        values.put(Contract.Enrollment.userID, enrollment.getUserID());
        values.put(Contract.Enrollment.subjectID, enrollment.getSubjectID());

        Globals.CLASSROOM_TABLE_NAME = "Enrollments";

        resolver.update(uri, values, null, null);
    }

    public static void updateConBitacora(ContentResolver resolvedor, Enrollment registro) throws Exception {
        update(resolvedor, registro,true);

        Bitacora bitacora = new Bitacora();
        bitacora.setID_Element(registro.getID());
        bitacora.setOperacion(Globals.OPERACION_MODIFICAR);
        BitacoraProveedor.insert(resolvedor, bitacora);
    }

    static public Enrollment read(ContentResolver resolver, int ElementID)  throws Exception{
        Uri uri = Uri.parse(Contract.Enrollment.CONTENT_URI + "/" + ElementID);

        /*String[] projection = {Contract.Enrollment._ID,
                Contract.Enrollment.enrollmentName,
                Contract.Enrollment.subject};*/
        String[] projection = {Contract.Enrollment._ID,
                Contract.Enrollment.userID,
                Contract.Enrollment.subjectID};

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if (cursor.moveToFirst()){
            Enrollment enrollment = new Enrollment();
            enrollment.setID(cursor.getInt(cursor.getColumnIndex(Contract.Enrollment._ID)));
            enrollment.setUserID(cursor.getInt(cursor.getColumnIndex(Contract.Enrollment.userID)));
            enrollment.setSubjectID(cursor.getInt(cursor.getColumnIndex(Contract.Enrollment.subjectID)));
            return enrollment;
        }

        return null;

    }

    public static Enrollment readRecord(ContentResolver resolver, int enrollmentId){
        Uri uri = Uri.parse(Contract.Enrollment.CONTENT_URI+"/"+enrollmentId);

        String[] projection = {
                Contract.Enrollment._ID,
                Contract.Enrollment.userID,
                Contract.Enrollment.subjectID
        };

        Globals.CLASSROOM_TABLE_NAME = "Enrollments";

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if(cursor.moveToFirst()){
            Enrollment enrollment = new Enrollment();
            enrollment.setID(enrollmentId);
            enrollment.setUserID(cursor.getInt(cursor.getColumnIndex(Contract.Enrollment.userID)));
            enrollment.setSubjectID(cursor.getInt(cursor.getColumnIndex(Contract.Enrollment.subjectID)));
            return enrollment;
        }

        return null;
    }

    static public ArrayList<Enrollment> readAll(ContentResolver resolver)  throws Exception{
        Uri uri = Contract.Enrollment.CONTENT_URI;

        String[] projection = {Contract.Enrollment._ID,
                Contract.Enrollment.userID,
                Contract.Enrollment.subjectID};

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        ArrayList<Enrollment> registros = new ArrayList<>();

        while (cursor.moveToNext()){
            Enrollment enrollment = new Enrollment();
            enrollment.setID(cursor.getInt(cursor.getColumnIndex(Contract.Enrollment._ID)));
            enrollment.setUserID(cursor.getInt(cursor.getColumnIndex(Contract.Enrollment.userID)));
            enrollment.setSubjectID(cursor.getInt(cursor.getColumnIndex(Contract.Enrollment.subjectID)));
            registros.add(enrollment);
        }

        return registros;
    }
}