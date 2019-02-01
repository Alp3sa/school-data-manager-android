package com.gestordedatos.gestordedatos.contentProvider;

import android.accounts.AccountManager;
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
import com.gestordedatos.gestordedatos.application.AppController;
import com.gestordedatos.gestordedatos.pojos.Bitacora;
import com.gestordedatos.gestordedatos.pojos.Classroom;
import com.gestordedatos.gestordedatos.sync.Sincronizacion;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class ClassroomProvider {
    static public Uri insert(ContentResolver resolvedor, Classroom classroom) throws Exception {
        Uri uri = Contract.Classroom.CONTENT_URI;

        ContentValues values = new ContentValues();
        if(classroom.getID() != Globals.SIN_VALOR_INT) values.put(Contract.Classroom._ID, classroom.getID());
        values.put(Contract.Classroom.classroomName, classroom.getClassroomName());
        //values.put(Contract.Classroom.subject, classroom.getSubject());
        values.put(Contract.Classroom.details, classroom.getDetails());
        values.put(Contract.Classroom.image_url, classroom.getImage_url());

        return resolvedor.insert(uri, values);
    }

    public static Uri insertRecord(ContentResolver resolver, Classroom classroom,Context context){
        String imageExist="false";
        if(classroom.getImage()!=null){imageExist="true";}

        Uri uri = Contract.Classroom.CONTENT_URI;
        ContentValues values = new ContentValues();
        values.put(Contract.Classroom.classroomName,classroom.getClassroomName());
        //values.put(Contract.Classroom.subject,classroom.getSubject());
        values.put(Contract.Classroom.details,classroom.getSubject());
        values.put(Contract.Classroom.image_url,imageExist);

        Globals.CLASSROOM_TABLE_NAME = "Classrooms";

        Uri uriResult = resolver.insert(uri,values);

        String classroomId = uriResult.getLastPathSegment();

        if(classroom.getImage()!=null){
            System.out.println("check image img_" + classroomId + ".jpg "+classroom.getClassroomName()+" ");
            try {
                Utilities.storeImage(classroom.getImage(), context, "img_" + classroomId + ".jpg");
                Utilities.storeImageInExternelMemory(classroom.getImage(), context, "img_" + classroomId + ".jpg");
            }
            catch (IOException e){
                String toast = context.getResources().getString(R.string.errorImage);
                SpannableStringBuilder biggerText = new SpannableStringBuilder(toast);
                biggerText.setSpan(new RelativeSizeSpan(1.5f), 0, toast.length(), 0);
                Toast errorImage = Toast.makeText(context,biggerText,Toast.LENGTH_LONG);
                errorImage.setGravity(Gravity.BOTTOM, 0, 40);
                errorImage.show();
            }
        }

        return uriResult;
    }

    public static void insertConBitacora(ContentResolver resolvedor, Classroom registro, Context context) throws Exception {
        Uri uri = ClassroomProvider.insertRecord(resolvedor, registro, context);

        int id = Integer.valueOf(uri.getLastPathSegment());

        Bitacora bitacora = new Bitacora();
        bitacora.setID_Element(id);
        bitacora.setOperacion(Globals.OPERACION_INSERTAR);
        BitacoraProveedor.insert(resolvedor,bitacora);
        Globals.STATUS_OPERATION=true;
        Sincronizacion.refresh();
        //return uri;
    }

    static public void delete(ContentResolver resolver, int ElementID, Context context)  throws Exception{
        Uri uri = Uri.parse(Contract.Classroom.CONTENT_URI + "/" + ElementID);
        Utilities.deleteImage(context, "img_" + ElementID + ".jpg");
        resolver.delete(uri, null, null);
    }

    public static void deleteRecord(ContentResolver resolver, int classroomId, Context context){
        Uri uri = Uri.parse(Contract.Classroom.CONTENT_URI+"/"+classroomId);
        Utilities.deleteImage(context, "img_" + classroomId + ".jpg");

        Globals.CLASSROOM_TABLE_NAME = "Classrooms";

        resolver.delete(uri, null, null);
    }

    public static void deleteConBitacora(ContentResolver resolvedor, int id,Context context) throws Exception {
        delete(resolvedor, id, context);

        Bitacora bitacora = new Bitacora();
        bitacora.setID_Element(id);
        bitacora.setOperacion(Globals.OPERACION_BORRAR);
        BitacoraProveedor.insert(resolvedor, bitacora);
        Globals.STATUS_OPERATION=true;
        Sincronizacion.refresh();
    }

    static public void update(ContentResolver resolver, Classroom classroom, boolean form, Context context)  throws Exception{
        Uri uri = Uri.parse(Contract.Classroom.CONTENT_URI + "/" + classroom.getID());

        String imageExist="false";
        if(classroom.getImage()!=null){imageExist="true";}

        ContentValues values = new ContentValues();
        values.put(Contract.Classroom.classroomName, classroom.getClassroomName());
        //values.put(Contract.Classroom.subject, classroom.getSubject());
        if(form==false) {
            values.put(Contract.Classroom.details, classroom.getDetails());
        }
        else{
            values.put(Contract.Classroom.details, classroom.getSubject());
        }
        values.put(Contract.Classroom.image_url, imageExist);

        resolver.update(uri, values, null, null);

        if(classroom.getImage()!=null){
            try {
                Utilities.storeImage(classroom.getImage(), context, "img_" + classroom.getID() + ".jpg");
                Utilities.storeImageInExternelMemory(classroom.getImage(), context, "img_" + classroom.getID() + ".jpg");
            }
            catch (IOException e){
                String toast = context.getResources().getString(R.string.errorImage);
                SpannableStringBuilder biggerText = new SpannableStringBuilder(toast);
                biggerText.setSpan(new RelativeSizeSpan(1.5f), 0, toast.length(), 0);
                Toast errorImage = Toast.makeText(context,biggerText,Toast.LENGTH_LONG);
                errorImage.setGravity(Gravity.BOTTOM, 0, 40);
                errorImage.show();
            }
        }
    }

    public static void updateRecord(ContentResolver resolver, Classroom classroom, Context context){
        Uri uri = Uri.parse(Contract.Classroom.CONTENT_URI+"/"+classroom.getID());

        ContentValues values = new ContentValues();
        values.put(Contract.Classroom.classroomName,classroom.getClassroomName());
        //values.put(Contract.Classroom.subject,classroom.getSubject());
        values.put(Contract.Classroom.details,classroom.getDetails());
        values.put(Contract.Classroom.image_url,classroom.getImage_url());

        Globals.CLASSROOM_TABLE_NAME = "Classrooms";

        resolver.update(uri, values, null, null);

        if(classroom.getImage()!=null){
            try {
                Utilities.storeImage(classroom.getImage(), context, "img_" + classroom.getID() + ".jpg");
                Utilities.storeImageInExternelMemory(classroom.getImage(), context, "img_" + classroom.getID() + ".jpg");
            }
            catch (IOException e){
                String toast = context.getResources().getString(R.string.errorImage);
                SpannableStringBuilder biggerText = new SpannableStringBuilder(toast);
                biggerText.setSpan(new RelativeSizeSpan(1.5f), 0, toast.length(), 0);
                Toast errorImage = Toast.makeText(context,biggerText,Toast.LENGTH_LONG);
                errorImage.setGravity(Gravity.BOTTOM, 0, 40);
                errorImage.show();
            }
        }
    }

    public static void updateConBitacora(ContentResolver resolvedor, Classroom registro, Context context) throws Exception {
        update(resolvedor, registro,true, context);

        Bitacora bitacora = new Bitacora();
        bitacora.setID_Element(registro.getID());
        bitacora.setOperacion(Globals.OPERACION_MODIFICAR);
        BitacoraProveedor.insert(resolvedor, bitacora);
        Globals.STATUS_OPERATION=true;
        Sincronizacion.refresh();
    }

    static public Classroom read(ContentResolver resolver, int ElementID)  throws Exception{
        Uri uri = Uri.parse(Contract.Classroom.CONTENT_URI + "/" + ElementID);

        /*String[] projection = {Contract.Classroom._ID,
                Contract.Classroom.classroomName,
                Contract.Classroom.subject};*/
        String[] projection = {Contract.Classroom._ID,
                Contract.Classroom.classroomName,
                Contract.Classroom.details,
                Contract.Classroom.image_url};

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if (cursor.moveToFirst()){
            Classroom classroom = new Classroom();
            classroom.setID(cursor.getInt(cursor.getColumnIndex(Contract.Classroom._ID)));
            classroom.setClassroomName(cursor.getString(cursor.getColumnIndex(Contract.Classroom.classroomName)));
            //classroom.setSubject(cursor.getString(cursor.getColumnIndex(Contract.Classroom.subject)));
            classroom.setDetails(cursor.getString(cursor.getColumnIndex(Contract.Classroom.details)));
            classroom.setImage_url(cursor.getString(cursor.getColumnIndex(Contract.Classroom.image_url)));
            return classroom;
        }

        return null;

    }

    public static Classroom readRecord(ContentResolver resolver, int classroomId){
        Uri uri = Uri.parse(Contract.Classroom.CONTENT_URI+"/"+classroomId);

        String[] projection = {
                Contract.Classroom._ID,
                Contract.Classroom.classroomName,
                //Contract.Classroom.subject
                Contract.Classroom.details,
                Contract.Classroom.image_url
        };

        Globals.CLASSROOM_TABLE_NAME = "Classrooms";

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if(cursor.moveToFirst()){
            Classroom classroom = new Classroom();
            classroom.setID(classroomId);
            classroom.setClassroomName(cursor.getString(cursor.getColumnIndex(Contract.Classroom.classroomName)));
            //classroom.setSubject(cursor.getString(cursor.getColumnIndex(Contract.Classroom.subject)));
            classroom.setDetails(cursor.getString(cursor.getColumnIndex(Contract.Classroom.details)));
            classroom.setImage_url(cursor.getString(cursor.getColumnIndex(Contract.Classroom.image_url)));
            return classroom;
        }

        return null;
    }

    static public ArrayList<Classroom> readAll(ContentResolver resolver)  throws Exception{
        Uri uri = Contract.Classroom.CONTENT_URI;

        String[] projection = {Contract.Classroom._ID,
                Contract.Classroom.classroomName,
                //Contract.Classroom.subject};
                Contract.Classroom.details,
                Contract.Classroom.image_url};

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        ArrayList<Classroom> registros = new ArrayList<>();

        while (cursor.moveToNext()){
            Classroom classroom = new Classroom();
            classroom.setID(cursor.getInt(cursor.getColumnIndex(Contract.Classroom._ID)));
            classroom.setClassroomName(cursor.getString(cursor.getColumnIndex(Contract.Classroom.classroomName)));
            //classroom.setSubject(cursor.getString(cursor.getColumnIndex(Contract.Classroom.subject)));
            classroom.setDetails(cursor.getString(cursor.getColumnIndex(Contract.Classroom.details)));
            classroom.setImage_url(cursor.getString(cursor.getColumnIndex(Contract.Classroom.image_url)));
            registros.add(classroom);
        }

        return registros;

    }
}
