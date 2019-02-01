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
import com.gestordedatos.gestordedatos.pojos.User;

import java.io.IOException;
import java.util.ArrayList;

public class UserProvider {
    static public Uri insert(ContentResolver resolvedor, User user) throws Exception {
        Uri uri = Contract.User.CONTENT_URI;

        ContentValues values = new ContentValues();
        if(user.getID() != Globals.SIN_VALOR_INT) values.put(Contract.User._ID, user.getID());
        values.put(Contract.User.nombreUsuario, user.getNombreUsuario());
        values.put(Contract.User.nombre, user.getNombre());
        values.put(Contract.User.primerApellido, user.getPrimerApellido());
        values.put(Contract.User.segundoApellido, user.getSegundoApellido());
        values.put(Contract.User.edad, user.getEdad());
        values.put(Contract.User.dni, user.getDni());
        values.put(Contract.User.genero, user.getGenero());
        values.put(Contract.User.tipoDeMiembro, user.getTipoDeMiembro());
        values.put(Contract.User.password, user.getPassword());

        return resolvedor.insert(uri, values);
    }

    public static Uri insertRecord(ContentResolver resolver, User user,Context context){
        Uri uri = Contract.User.CONTENT_URI;
        ContentValues values = new ContentValues();
        values.put(Contract.User.nombreUsuario, user.getNombreUsuario());
        values.put(Contract.User.nombre, user.getNombre());
        values.put(Contract.User.primerApellido, user.getPrimerApellido());
        values.put(Contract.User.segundoApellido, user.getSegundoApellido());
        values.put(Contract.User.edad, user.getEdad());
        values.put(Contract.User.dni, user.getDni());
        values.put(Contract.User.genero, user.getGenero());
        values.put(Contract.User.tipoDeMiembro, user.getTipoDeMiembro());
        values.put(Contract.User.password, user.getPassword());

        Globals.CLASSROOM_TABLE_NAME = "Users";

        Uri uriResult = resolver.insert(uri,values);

        return uriResult;
    }

    public static void insertConBitacora(ContentResolver resolvedor, User registro, Context context) throws Exception {
        Uri uri = UserProvider.insertRecord(resolvedor, registro, context);

        int id = Integer.valueOf(uri.getLastPathSegment());

        Bitacora bitacora = new Bitacora();
        bitacora.setID_Element(id);
        bitacora.setOperacion(Globals.OPERACION_INSERTAR);
        BitacoraProveedor.insert(resolvedor,bitacora);
        //return uri;
    }

    static public void delete(ContentResolver resolver, int ElementID)  throws Exception{
        Uri uri = Uri.parse(Contract.User.CONTENT_URI + "/" + ElementID);
        resolver.delete(uri, null, null);
    }

    public static void deleteRecord(ContentResolver resolver, int userId, Context context){
        Uri uri = Uri.parse(Contract.User.CONTENT_URI+"/"+userId);

        Globals.CLASSROOM_TABLE_NAME = "Users";

        resolver.delete(uri, null, null);
    }

    public static void deleteConBitacora(ContentResolver resolvedor, int id) throws Exception {
        delete(resolvedor, id);

        Bitacora bitacora = new Bitacora();
        bitacora.setID_Element(id);
        bitacora.setOperacion(Globals.OPERACION_BORRAR);
        BitacoraProveedor.insert(resolvedor, bitacora);
    }

    static public void update(ContentResolver resolver, User user, boolean form)  throws Exception{
        Uri uri = Uri.parse(Contract.User.CONTENT_URI + "/" + user.getID());

        ContentValues values = new ContentValues();
        values.put(Contract.User.nombreUsuario, user.getNombreUsuario());
        values.put(Contract.User.nombre, user.getNombre());
        values.put(Contract.User.primerApellido, user.getPrimerApellido());
        values.put(Contract.User.segundoApellido, user.getSegundoApellido());
        values.put(Contract.User.edad, user.getEdad());
        values.put(Contract.User.dni, user.getDni());
        values.put(Contract.User.genero, user.getGenero());
        values.put(Contract.User.tipoDeMiembro, user.getTipoDeMiembro());
        values.put(Contract.User.password, user.getPassword());

        resolver.update(uri, values, null, null);

        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
    }

    public static void updateRecord(ContentResolver resolver, User user, Context context){
        Uri uri = Uri.parse(Contract.User.CONTENT_URI+"/"+user.getID());

        ContentValues values = new ContentValues();
        values.put(Contract.User.nombreUsuario, user.getNombreUsuario());
        values.put(Contract.User.nombre, user.getNombre());
        values.put(Contract.User.primerApellido, user.getPrimerApellido());
        values.put(Contract.User.segundoApellido, user.getSegundoApellido());
        values.put(Contract.User.edad, user.getEdad());
        values.put(Contract.User.dni, user.getDni());
        values.put(Contract.User.genero, user.getGenero());
        values.put(Contract.User.tipoDeMiembro, user.getTipoDeMiembro());
        values.put(Contract.User.password, user.getPassword());

        Globals.CLASSROOM_TABLE_NAME = "Users";

        resolver.update(uri, values, null, null);
    }

    public static void updateConBitacora(ContentResolver resolvedor, User registro) throws Exception {
        update(resolvedor, registro,true);

        Bitacora bitacora = new Bitacora();
        bitacora.setID_Element(registro.getID());
        bitacora.setOperacion(Globals.OPERACION_MODIFICAR);
        BitacoraProveedor.insert(resolvedor, bitacora);
    }

    static public User read(ContentResolver resolver, int ElementID)  throws Exception{
        Uri uri = Uri.parse(Contract.User.CONTENT_URI + "/" + ElementID);

        String[] projection = {Contract.User._ID,
                Contract.User.nombreUsuario,
                Contract.User.nombre,
                Contract.User.primerApellido,
                Contract.User.segundoApellido,
                Contract.User.edad,
                Contract.User.dni,
                Contract.User.genero,
                Contract.User.tipoDeMiembro,
                Contract.User.password};

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if (cursor.moveToFirst()){
            User user = new User();
            user.setID(cursor.getInt(cursor.getColumnIndex(Contract.User._ID)));
            user.setNombreUsuario(cursor.getString(cursor.getColumnIndex(Contract.User.nombreUsuario)));
            user.setNombre(cursor.getString(cursor.getColumnIndex(Contract.User.nombre)));
            user.setPrimerApellido(cursor.getString(cursor.getColumnIndex(Contract.User.primerApellido)));
            user.setSegundoApellido(cursor.getString(cursor.getColumnIndex(Contract.User.segundoApellido)));
            user.setEdad(cursor.getString(cursor.getColumnIndex(Contract.User.edad)));
            user.setDni(cursor.getString(cursor.getColumnIndex(Contract.User.dni)));
            user.setGenero(cursor.getString(cursor.getColumnIndex(Contract.User.genero)));
            user.setTipoDeMiembro(cursor.getString(cursor.getColumnIndex(Contract.User.tipoDeMiembro)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(Contract.User.password)));

            return user;
        }

        return null;
    }

    public static User readRecord(ContentResolver resolver, int userId){
        Uri uri = Uri.parse(Contract.User.CONTENT_URI+"/"+userId);

        String[] projection = {Contract.User._ID,
                Contract.User.nombreUsuario,
                Contract.User.nombre,
                Contract.User.primerApellido,
                Contract.User.segundoApellido,
                Contract.User.edad,
                Contract.User.dni,
                Contract.User.genero,
                Contract.User.tipoDeMiembro,
                Contract.User.password
        };

        Globals.CLASSROOM_TABLE_NAME = "Users";

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if(cursor.moveToFirst()){
            User user = new User();
            user.setID(userId);
            user.setNombreUsuario(cursor.getString(cursor.getColumnIndex(Contract.User.nombreUsuario)));
            user.setNombre(cursor.getString(cursor.getColumnIndex(Contract.User.nombre)));
            user.setPrimerApellido(cursor.getString(cursor.getColumnIndex(Contract.User.primerApellido)));
            user.setSegundoApellido(cursor.getString(cursor.getColumnIndex(Contract.User.segundoApellido)));
            user.setEdad(cursor.getString(cursor.getColumnIndex(Contract.User.edad)));
            user.setDni(cursor.getString(cursor.getColumnIndex(Contract.User.dni)));
            user.setGenero(cursor.getString(cursor.getColumnIndex(Contract.User.genero)));
            user.setTipoDeMiembro(cursor.getString(cursor.getColumnIndex(Contract.User.tipoDeMiembro)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(Contract.User.password)));
            return user;
        }

        return null;
    }

    static public ArrayList<User> readAll(ContentResolver resolver)  throws Exception{
        Uri uri = Contract.User.CONTENT_URI;

        String[] projection = {Contract.User._ID,
                Contract.User.nombreUsuario,
                Contract.User.nombre,
                Contract.User.primerApellido,
                Contract.User.segundoApellido,
                Contract.User.edad,
                Contract.User.dni,
                Contract.User.genero,
                Contract.User.tipoDeMiembro,
                Contract.User.password
        };

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        ArrayList<User> registros = new ArrayList<>();

        while (cursor.moveToNext()){
            User user = new User();
            user.setID(cursor.getInt(cursor.getColumnIndex(Contract.User._ID)));
            user.setNombreUsuario(cursor.getString(cursor.getColumnIndex(Contract.User.nombreUsuario)));
            user.setNombre(cursor.getString(cursor.getColumnIndex(Contract.User.nombre)));
            user.setPrimerApellido(cursor.getString(cursor.getColumnIndex(Contract.User.primerApellido)));
            user.setSegundoApellido(cursor.getString(cursor.getColumnIndex(Contract.User.segundoApellido)));
            user.setEdad(cursor.getString(cursor.getColumnIndex(Contract.User.edad)));
            user.setDni(cursor.getString(cursor.getColumnIndex(Contract.User.dni)));
            user.setGenero(cursor.getString(cursor.getColumnIndex(Contract.User.genero)));
            user.setTipoDeMiembro(cursor.getString(cursor.getColumnIndex(Contract.User.tipoDeMiembro)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(Contract.User.password)));

            registros.add(user);
        }

        return registros;
    }
}
