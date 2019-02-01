package com.gestordedatos.gestordedatos.contentProvider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.gestordedatos.gestordedatos.Globals;
import com.gestordedatos.gestordedatos.pojos.Bitacora;

import java.util.ArrayList;

public class BitacoraProveedor {
    static public void insert(ContentResolver resolvedor, Bitacora bitacora){
        Uri uri = Contract.Bitacora.CONTENT_URI;
        ContentValues values = new ContentValues();
        values.put(Contract.Bitacora.CLASS_ELEMENT, bitacora.getClass_element());
        values.put(Contract.Bitacora.ID_ELEMENT, bitacora.getID_Element());
        values.put(Contract.Bitacora.OPERACION, bitacora.getOperacion());

        resolvedor.insert(uri, values);
    }

    static public void delete(ContentResolver resolver, int bitacoraId){
        Uri uri = Uri.parse(Contract.Bitacora.CONTENT_URI + "/" + bitacoraId);
        resolver.delete(uri, null, null);
    }

    static public void update(ContentResolver resolver, Bitacora bitacora){
        Uri uri = Uri.parse(Contract.Bitacora.CONTENT_URI + "/" + bitacora.getID());

        ContentValues values = new ContentValues();
        values.put(Contract.Bitacora.CLASS_ELEMENT, bitacora.getClass_element());
        values.put(Contract.Bitacora.ID_ELEMENT, bitacora.getID_Element());
        values.put(Contract.Bitacora.OPERACION, bitacora.getOperacion());

        resolver.update(uri, values, null, null);
    }


    static public Bitacora read(ContentResolver resolver, int bitacoraId) {
        Uri uri = Uri.parse(Contract.Bitacora.CONTENT_URI + "/" + bitacoraId);

        String[] projection = {Contract.Bitacora._ID,
                Contract.Bitacora.CLASS_ELEMENT,
                Contract.Bitacora.ID_ELEMENT,
                Contract.Bitacora.OPERACION};

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if (cursor.moveToFirst()){
            Bitacora bitacora = new Bitacora();
            bitacora.setID(cursor.getInt(cursor.getColumnIndex(Contract.Bitacora._ID)));
            bitacora.setClass_element(cursor.getInt(cursor.getColumnIndex(Contract.Bitacora.CLASS_ELEMENT)));
            bitacora.setID_Element(cursor.getInt(cursor.getColumnIndex(Contract.Bitacora.ID_ELEMENT)));
            bitacora.setOperacion(cursor.getInt(cursor.getColumnIndex(Contract.Bitacora.OPERACION)));
            return bitacora;
        }

        return null;

    }

    static public ArrayList<Bitacora> readAll(ContentResolver resolver) {
        Uri uri = Contract.Bitacora.CONTENT_URI;

        String[] projection = {Contract.Bitacora._ID,
                Contract.Bitacora.CLASS_ELEMENT,
                Contract.Bitacora.ID_ELEMENT,
                Contract.Bitacora.OPERACION};

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        ArrayList<Bitacora> bitacoras = new ArrayList<>();

        while (cursor.moveToNext()){
            Bitacora bitacora = new Bitacora();
            bitacora.setID(cursor.getInt(cursor.getColumnIndex(Contract.Bitacora._ID)));
            bitacora.setClass_element(cursor.getInt(cursor.getColumnIndex(Contract.Bitacora.CLASS_ELEMENT)));
            bitacora.setID_Element(cursor.getInt(cursor.getColumnIndex(Contract.Bitacora.ID_ELEMENT)));
            bitacora.setOperacion(cursor.getInt(cursor.getColumnIndex(Contract.Bitacora.OPERACION)));
            bitacoras.add(bitacora);
        }

        return bitacoras;

    }
}