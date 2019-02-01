package com.gestordedatos.gestordedatos.rest;

import android.util.Log;

import com.gestordedatos.gestordedatos.Globals;
import com.gestordedatos.gestordedatos.pojos.Classroom;
import com.gestordedatos.gestordedatos.pojos.Enrollment;
import com.gestordedatos.gestordedatos.pojos.Subject;
import com.gestordedatos.gestordedatos.pojos.Tutorship;
import com.gestordedatos.gestordedatos.pojos.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Rest {
    final static String ruta = Globals.RUTA_SERVIDOR_TOMCAT +"/"+Globals.SERVER_TABLE_NAME;

    public static ArrayList<Classroom> getAllClassroom(){
        ArrayList<Classroom> registros = new ArrayList<>();
        try {
            URL url = new URL(ruta);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET");
            conexion.setDoInput(true);

            InputStream is = conexion.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder respuesta = new StringBuilder();
            String datos;

            while((datos = br.readLine()) != null){
                respuesta.append(datos);
            }

            JSONArray jsonArray = new JSONArray(respuesta.toString());

            Log.i("WS: ", respuesta.toString());

            for (int i = 0; i < jsonArray.length(); i++ ){
                JSONObject obj = jsonArray.getJSONObject(i);
                registros.add(new Classroom(obj.getInt("ID"), obj.getString("classroomName"), obj.getString("details"), obj.getString("image")));
            }

            return registros;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Subject> getAllSubject(){
        ArrayList<Subject> registros = new ArrayList<>();

        try {
            URL url = new URL(ruta);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET");
            conexion.setDoInput(true);

            InputStream is = conexion.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder respuesta = new StringBuilder();
            String datos;

            while((datos = br.readLine()) != null){
                respuesta.append(datos);
            }

            JSONArray jsonArray = new JSONArray(respuesta.toString());

            Log.i("WS: ", respuesta.toString());

            for (int i = 0; i < jsonArray.length(); i++ ){
                JSONObject obj = jsonArray.getJSONObject(i);
                registros.add(new Subject(obj.getInt("ID"), obj.getString("name"), obj.getString("teacher"), obj.getString("classroom"), obj.getString("startTime"), obj.getString("endingTime"), obj.getInt("classroomID")));
            }

            return registros;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Tutorship> getAllTutorship(){
        ArrayList<Tutorship> registros = new ArrayList<>();

        try {
            URL url = new URL(ruta);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET");
            conexion.setDoInput(true);

            InputStream is = conexion.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder respuesta = new StringBuilder();
            String datos;

            while((datos = br.readLine()) != null){
                respuesta.append(datos);
            }

            JSONArray jsonArray = new JSONArray(respuesta.toString());

            Log.i("WS: ", respuesta.toString());

            for (int i = 0; i < jsonArray.length(); i++ ){
                    JSONObject obj = jsonArray.getJSONObject(i);
                    registros.add(new Tutorship(obj.getInt("ID"), obj.getString("name"), obj.getString("classroom"), obj.getString("startTime"), obj.getString("endingTime"), obj.getInt("classroomID")));
            }

            return registros;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Enrollment> getAllEnrollment(){
        ArrayList<Enrollment> registros = new ArrayList<>();

        try {
            URL url = new URL(ruta);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET");
            conexion.setDoInput(true);

            InputStream is = conexion.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder respuesta = new StringBuilder();
            String datos;

            while((datos = br.readLine()) != null){
                respuesta.append(datos);
            }

            JSONArray jsonArray = new JSONArray(respuesta.toString());

            Log.i("WS: ", respuesta.toString());


            for (int i = 0; i < jsonArray.length(); i++ ){
                JSONObject obj = jsonArray.getJSONObject(i);
                registros.add(new Enrollment(obj.getInt("ID"), obj.getInt("userID"), obj.getInt("subjectID")));
            }

            return registros;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<User> getAllUser(){
        ArrayList<User> registros = new ArrayList<>();

        try {
            URL url = new URL(ruta);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET");
            conexion.setDoInput(true);

            InputStream is = conexion.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder respuesta = new StringBuilder();
            String datos;

            while((datos = br.readLine()) != null){
                respuesta.append(datos);
            }

            JSONArray jsonArray = new JSONArray(respuesta.toString());

            Log.i("WS: ", respuesta.toString());


            for (int i = 0; i < jsonArray.length(); i++ ){
                JSONObject obj = jsonArray.getJSONObject(i);
                registros.add(new User(obj.getInt("userID"), obj.getString("nombreUsuario"), obj.getString("nombre"), obj.getString("primerApellido"), obj.getString("segundoApellido"), obj.getString("edad"), obj.getString("dni"), obj.getString("genero"), obj.getString("tipoDeMiembro"), obj.getString("password")));
            }

            return registros;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean addClassroom(Classroom registro){

        try {
            URL url = new URL(ruta);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setDoInput(true);
            conexion.setDoOutput(false);
            conexion.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            //conexion.setRequestProperty("Host", "192.168.1.12");
            conexion.connect();

            //OutputStream os = conexion.getOutputStream();
            //OutputStreamWriter osw = new OutputStreamWriter(os);
            //BufferedWriter bw = new BufferedWriter(osw);

            DataOutputStream printout = new DataOutputStream(conexion.getOutputStream ());
            printout.writeBytes(URLEncoder.encode(registro.toJSON(),"UTF-8"));
            printout.flush ();
            printout.close ();
            //http://stackoverflow.com/questions/13911993/sending-a-json-http-post-request-from-android

            //Se envía petición
            //bw.write(registro.toJSON());
            //bw.flush();
            //bw.close();

            //Se recibe respuesta
/*            InputStream is = conexion.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder respuesta = new StringBuilder();
            String datos;

            while((datos = br.readLine()) != null){
                respuesta.append(datos);
            }

            Log.i("Respuesta: ", respuesta.toString());
*/
            Log.i("Respuesta: ", "hola");
            return true;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } //catch (JSONException e) {
        //    e.printStackTrace();
        //}

        return false;
    }

}