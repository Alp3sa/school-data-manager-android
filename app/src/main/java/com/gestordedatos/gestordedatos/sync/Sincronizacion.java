package com.gestordedatos.gestordedatos.sync;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.util.Log;

import com.gestordedatos.gestordedatos.Globals;
import com.gestordedatos.gestordedatos.MainMenu;
import com.gestordedatos.gestordedatos.R;
import com.gestordedatos.gestordedatos.application.AppController;
import com.gestordedatos.gestordedatos.contentProvider.Contract;
import com.gestordedatos.gestordedatos.contentProvider.EnrollmentProvider;
import com.gestordedatos.gestordedatos.contentProvider.SubjectProvider;
import com.gestordedatos.gestordedatos.contentProvider.TutorshipProvider;
import com.gestordedatos.gestordedatos.contentProvider.UserProvider;
import com.gestordedatos.gestordedatos.pojos.Bitacora;
import com.gestordedatos.gestordedatos.contentProvider.BitacoraProveedor;
import com.gestordedatos.gestordedatos.contentProvider.ClassroomProvider;
import com.gestordedatos.gestordedatos.pojos.Classroom;
import com.gestordedatos.gestordedatos.pojos.Enrollment;
import com.gestordedatos.gestordedatos.pojos.Subject;
import com.gestordedatos.gestordedatos.pojos.Tutorship;
import com.gestordedatos.gestordedatos.pojos.User;
import com.gestordedatos.gestordedatos.volley.Volley1;

import junit.framework.Test;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Tiburcio on 28/10/2015.
 */
public class Sincronizacion {
    public static final String LOGTAG = "Sincronizacion";
    public static ContentResolver resolvedor;
    public static Context contexto;
    public static boolean esperandoRespuestaDeServidor = false;

    public Sincronizacion(Context contexto){
        this.resolvedor = contexto.getContentResolver();
        this.contexto = contexto;
        //recibirActualizacionesDelServidor(); //La primera vez se cargan los datos siempre
    }

    public synchronized static boolean isEsperandoRespuestaDeServidor() {
        return esperandoRespuestaDeServidor;
    }

    public synchronized static void setEsperandoRespuestaDeServidor(boolean esperandoRespuestaDeServidor) {
        Sincronizacion.esperandoRespuestaDeServidor = esperandoRespuestaDeServidor;
    }

    public static synchronized boolean sincronizar(){
        Log.i("sincronizacion","SINCRONIZAR");

        if(isEsperandoRespuestaDeServidor()){
            return true;
        }

        /*if(Globals.VERSION_ADMINISTRADOR){
            enviarActualizacionesAlServidor();
        } else {
            recibirActualizacionesDelServidor();
        }*/

        if(Globals.subquery==-1 && Globals.SUBQUERY_OPERATION==false){
            if (Globals.STATUS_OPERATION == false) {
                recibirActualizacionesDelServidor();
            }
            enviarActualizacionesAlServidor();
            Globals.STATUS_OPERATION = false;
        }

        return true;
    }



    public static void enviarActualizacionesAlServidor(){
        ArrayList<Bitacora> registrosBitacora = BitacoraProveedor.readAll(resolvedor);
        for(Bitacora bitacora : registrosBitacora){
            Classroom classroom = null;
            Subject subject = null;
            Tutorship tutorship = null;
            Enrollment enrollment = null;
            User user = null;

            switch(bitacora.getOperacion()){
                case Globals.OPERACION_INSERTAR:
                    try {
                        if(Globals.SERVER_TABLE_NAME.equals("classroom")) {
                            classroom = ClassroomProvider.readRecord(resolvedor, bitacora.getID_Element());
                            if(classroom!=null) {
                                Volley1.addClassroom(classroom, true, bitacora.getID());
                            }
                        }
                        else if(Globals.SERVER_TABLE_NAME.equals("subject")) {
                            subject = SubjectProvider.readRecord(resolvedor, bitacora.getID_Element());
                            if(subject!=null) {
                                Volley1.addSubject(subject, true, bitacora.getID());
                            }
                        }
                        else if(Globals.SERVER_TABLE_NAME.equals("tutorship")) {
                            tutorship = TutorshipProvider.readRecord(resolvedor, bitacora.getID_Element());
                            if(tutorship!=null) {
                                Volley1.addTutorship(tutorship, true, bitacora.getID());
                            }
                        }
                        else if(Globals.SERVER_TABLE_NAME.equals("enrollment")) {
                            enrollment = EnrollmentProvider.readRecord(resolvedor, bitacora.getID_Element());
                            if(enrollment!=null) {
                                Volley1.addEnrollment(enrollment, true, bitacora.getID());
                            }
                        }
                        else if(Globals.SERVER_TABLE_NAME.equals("user")) {
                             user = UserProvider.readRecord(resolvedor, bitacora.getID_Element());
                             if(user!=null) {
                                 Volley1.addUser(user, true, bitacora.getID());
                             }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case Globals.OPERACION_MODIFICAR:
                    try {
                        if(Globals.SERVER_TABLE_NAME.equals("classroom")) {
                            classroom = ClassroomProvider.readRecord(resolvedor, bitacora.getID_Element());
                            if(classroom!=null) {
                                Volley1.updateClassroom(classroom, true, bitacora.getID());
                            }
                        }
                        else if(Globals.SERVER_TABLE_NAME.equals("subject")) {
                            subject = SubjectProvider.readRecord(resolvedor, bitacora.getID_Element());
                            if(subject!=null) {
                                Volley1.updateSubject(subject, true, bitacora.getID());
                            }
                        }
                        else if(Globals.SERVER_TABLE_NAME.equals("tutorship")) {
                            tutorship = TutorshipProvider.readRecord(resolvedor, bitacora.getID_Element());
                            if(tutorship!=null) {
                                Volley1.updateTutorship(tutorship, true, bitacora.getID());
                            }
                        }
                        else if(Globals.SERVER_TABLE_NAME.equals("enrollment")) {
                            enrollment = EnrollmentProvider.readRecord(resolvedor, bitacora.getID_Element());
                            if(enrollment!=null) {
                                Volley1.updateEnrollment(enrollment, true, bitacora.getID());
                            }
                        }
                        else if(Globals.SERVER_TABLE_NAME.equals("user")) {
                            user = UserProvider.readRecord(resolvedor, bitacora.getID_Element());
                            if(user!=null) {
                                Volley1.updateUser(user, true, bitacora.getID());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case Globals.OPERACION_BORRAR:
                    if(Globals.SERVER_TABLE_NAME.equals("classroom")) {
                        Volley1.delClassroom(bitacora.getID_Element(), true, bitacora.getID());
                    }
                    else if(Globals.SERVER_TABLE_NAME.equals("subject")) {
                        Volley1.delSubject(bitacora.getID_Element(), true, bitacora.getID());
                    }
                    else if(Globals.SERVER_TABLE_NAME.equals("tutorship")) {
                        Volley1.delTutorship(bitacora.getID_Element(), true, bitacora.getID());
                    }
                    else if(Globals.SERVER_TABLE_NAME.equals("enrollment")) {
                        Volley1.delEnrollment(bitacora.getID_Element(), true, bitacora.getID());
                    }
                    else if(Globals.SERVER_TABLE_NAME.equals("user")) {
                        Volley1.delUser(bitacora.getID_Element(), true, bitacora.getID());
                    }
                    break;
            }
            Log.i("sincronizacion", "acabo de enviar");
        }
    }

    public static void recibirActualizacionesDelServidor(){
        Volley1.getAll();
    }

    public static void realizarActualizacionesDelServidorUnaVezRecibidas(JSONArray jsonArray){
        Log.i("sincronizacion", "recibirActualizacionesDelServidor");

        try {
            if(Globals.SERVER_TABLE_NAME.equals("classroom")) {
                ArrayList<Integer> identificadoresDeRegistrosActualizados = new ArrayList<Integer>();
                ArrayList<Classroom> registrosNuevos = new ArrayList<>();
                ArrayList<Classroom> registrosViejos = ClassroomProvider.readAll(resolvedor);
                ArrayList<Integer> identificadoresDeRegistrosViejos = new ArrayList<Integer>();
                for(Classroom i : registrosViejos) identificadoresDeRegistrosViejos.add(i.getID());

                JSONObject obj = null;
                for (int i = 0; i < jsonArray.length(); i++ ){
                    obj = jsonArray.getJSONObject(i);

                    int ID = obj.getInt("ID");
                    String classroomName = obj.getString("classroomName");
                    String details = obj.getString("details");
                    String image="null";

                    try {
                        image = obj.getString("image");
                    }
                    catch(Exception e){System.out.println(e);}

                    registrosNuevos.add(new Classroom(ID,classroomName, details, image));
                }


                for(Classroom classroom: registrosNuevos) {
                    try {
                        if(identificadoresDeRegistrosViejos.contains(classroom.getID())) {
                            ClassroomProvider.update(resolvedor, classroom,false, contexto);
                        } else {
                            ClassroomProvider.insert(resolvedor, classroom);
                        }
                        identificadoresDeRegistrosActualizados.add(classroom.getID());
                    } catch (Exception e){
                        Log.i("sincronizacion",
                                "Probablemente el registro ya existía en la BD."+"" +
                                        " Esto se podría controlar mejor con una Bitácora.");
                    }
                }

            /*Borrar registros viejos*/
            for(Classroom classroom: registrosViejos){
                if(!identificadoresDeRegistrosActualizados.contains(classroom.getID())){
                    try {
                        ClassroomProvider.delete(resolvedor, classroom.getID(),contexto);
                    }catch(Exception e){
                        Log.i("sincronizacion", "Error al borrar el registro con id:" + classroom.getID());
                    }
                }
            }

                //Volley1.getAll(); //Los baja y los guarda en SQLite
            }
            else if(Globals.SERVER_TABLE_NAME.equals("subject")) {
                ArrayList<Integer> identificadoresDeRegistrosActualizados = new ArrayList<Integer>();
                ArrayList<Subject> registrosNuevos = new ArrayList<>();
                ArrayList<Subject> registrosViejos = SubjectProvider.readAll(resolvedor);
                ArrayList<Integer> identificadoresDeRegistrosViejos = new ArrayList<Integer>();
                for(Subject i : registrosViejos) identificadoresDeRegistrosViejos.add(i.getID());

                ArrayList<String> nombresDeRegistrosViejos = new ArrayList<String>();
                for(Subject i : registrosViejos) nombresDeRegistrosViejos.add(i.getName());

                JSONObject obj = null;
                for (int i = 0; i < jsonArray.length(); i++ ){
                    obj = jsonArray.getJSONObject(i);

                    String[] projection = {
                            Contract.Subject._ID,
                            Contract.Subject.name,
                            Contract.Subject.teacher,
                            Contract.Subject.classroom,
                            Contract.Subject.startTime,
                            Contract.Subject.endingTime,
                            Contract.Subject.classroomID
                    };

                    int ID = obj.getInt("ID");
                    String name = obj.getString("name");
                    String teacher = obj.getString("teacher");
                    String classroom = obj.getString("classroom");
                    String startTime = obj.getString("startTime");
                    String endingTime = obj.getString("endingTime");
                    int classroomID = obj.getInt("classroomID");

                    registrosNuevos.add(new Subject(ID,name, teacher, classroom, startTime, endingTime, classroomID));
                }


                for(Subject subject: registrosNuevos) {
                    try {
                        if(identificadoresDeRegistrosViejos.contains(subject.getID())) {
                            SubjectProvider.update(resolvedor, subject,false);
                        } else {
                            SubjectProvider.insert(resolvedor, subject);
                        }
                        identificadoresDeRegistrosActualizados.add(subject.getID());
                    } catch (Exception e){
                        Log.i("sincronizacion",
                                "Probablemente el registro ya existía en la BD."+"" +
                                        " Esto se podría controlar mejor con una Bitácora.");
                    }
                }

            /*Borrar registros viejos*/
            for(Subject subject: registrosViejos){
                if(!identificadoresDeRegistrosActualizados.contains(subject.getID())){
                    try {
                        SubjectProvider.delete(resolvedor, subject.getID());
                    }catch(Exception e){
                        Log.i("sincronizacion", "Error al borrar el registro con id:" + subject.getID());
                    }
                }
            }

                //Volley1.getAll(); //Los baja y los guarda en SQLite
            }
            else if(Globals.SERVER_TABLE_NAME.equals("tutorship")) {
                ArrayList<Integer> identificadoresDeRegistrosActualizados = new ArrayList<Integer>();
                ArrayList<Tutorship> registrosNuevos = new ArrayList<>();
                ArrayList<Tutorship> registrosViejos = TutorshipProvider.readAll(resolvedor);
                ArrayList<Integer> identificadoresDeRegistrosViejos = new ArrayList<Integer>();
                for(Tutorship i : registrosViejos) identificadoresDeRegistrosViejos.add(i.getID());

                JSONObject obj = null;
                for (int i = 0; i < jsonArray.length(); i++ ){
                    obj = jsonArray.getJSONObject(i);

                    String[] projection = {
                            Contract.Tutorship._ID,
                            Contract.Tutorship.name,
                            Contract.Tutorship.classroom,
                            Contract.Tutorship.startTime,
                            Contract.Tutorship.endingTime,
                            Contract.Tutorship.classroomID
                    };


                    int ID = obj.getInt("ID");
                    String name = obj.getString("name");
                    String classroom = obj.getString("classroom");
                    String startTime = obj.getString("startTime");
                    String endingTime = obj.getString("endingTime");
                    int classroomID = obj.getInt("classroomID");

                    registrosNuevos.add(new Tutorship(ID,name, classroom, startTime, endingTime, classroomID));
                }


                for(Tutorship tutorship: registrosNuevos) {
                    try {
                        if(identificadoresDeRegistrosViejos.contains(tutorship.getID())) {
                            TutorshipProvider.update(resolvedor, tutorship,false);
                        } else {
                            TutorshipProvider.insert(resolvedor, tutorship);
                        }
                        identificadoresDeRegistrosActualizados.add(tutorship.getID());
                    } catch (Exception e){
                        Log.i("sincronizacion",
                                "Probablemente el registro ya existía en la BD."+"" +
                                        " Esto se podría controlar mejor con una Bitácora.");
                    }
                }

            /*Borrar registros viejos*/
            for(Tutorship tutorship: registrosViejos){
                if(!identificadoresDeRegistrosActualizados.contains(tutorship.getID())){
                    try {
                        TutorshipProvider.delete(resolvedor, tutorship.getID());
                    }catch(Exception e){
                        Log.i("sincronizacion", "Error al borrar el registro con id:" + tutorship.getID());
                    }
                }
            }

                //Volley1.getAll(); //Los baja y los guarda en SQLite

            }
            else if(Globals.SERVER_TABLE_NAME.equals("enrollment")) {
                /*ArrayList<Integer> identificadoresDeRegistrosActualizados = new ArrayList<Integer>();
                ArrayList<Enrollment> registrosNuevos = new ArrayList<>();
                ArrayList<Enrollment> registrosViejos = EnrollmentProvider.readAll(resolvedor);
                ArrayList<Integer> identificadoresDeRegistrosViejos = new ArrayList<Integer>();
                for(Enrollment i : registrosViejos) identificadoresDeRegistrosViejos.add(i.getID());

                ArrayList<String> nombresDeRegistrosViejos = new ArrayList<String>();
                for(Enrollment i : registrosViejos) nombresDeRegistrosViejos.add(i.getEnrollmentName());

                System.out.println("check json: "+jsonArray.length());

                JSONObject obj = null;
                for (int i = 0; i < jsonArray.length(); i++ ){System.out.println("hola registro1 "+i);
                    obj = jsonArray.getJSONObject(i);System.out.println("hola registro2");

                    int ID = obj.getInt("ID");
                    String enrollmentName = obj.getString("enrollmentName");
                    String details = obj.getString("details");
                    String image="null";

                    try {
                        image = obj.getString("image");
                    }
                    catch(Exception e){System.out.println(e);}
                    System.out.println("check image "+enrollmentName+"/"+details+"/"+image);
                    registrosNuevos.add(new Enrollment(ID,enrollmentName, details, image));

                    System.out.println("hola registro4");
                }


                for(Enrollment enrollment: registrosNuevos) {
                    try {
                        if(nombresDeRegistrosViejos.contains(enrollment.getEnrollmentName())) {
                            EnrollmentProvider.update(resolvedor, enrollment,false);
                        } else {
                            EnrollmentProvider.insert(resolvedor, enrollment);
                        }
                        //identificadoresDeRegistrosActualizados.add(enrollment.getID());
                    } catch (Exception e){
                        Log.i("sincronizacion",
                                "Probablemente el registro ya existía en la BD."+"" +
                                        " Esto se podría controlar mejor con una Bitácora.");
                    }
                }*/

            /*Borrar registros viejos
            for(Enrollment enrollment: registrosViejos){
                if(!identificadoresDeRegistrosActualizados.contains(enrollment.getID())){
                    try {
                        EnrollmentProvider.delete(resolvedor, enrollment.getID());
                    }catch(Exception e){
                        Log.i("sincronizacion", "Error al borrar el registro con id:" + enrollment.getID());
                    }
                }
            }

                //Volley1.getAll(); //Los baja y los guarda en SQLite*/
            }
            else if(Globals.SERVER_TABLE_NAME.equals("user")) {
                /*ArrayList<Integer> identificadoresDeRegistrosActualizados = new ArrayList<Integer>();
                ArrayList<User> registrosNuevos = new ArrayList<>();
                ArrayList<User> registrosViejos = UserProvider.readAll(resolvedor);
                ArrayList<Integer> identificadoresDeRegistrosViejos = new ArrayList<Integer>();
                for(User i : registrosViejos) identificadoresDeRegistrosViejos.add(i.getID());

                ArrayList<String> nombresDeRegistrosViejos = new ArrayList<String>();
                for(User i : registrosViejos) nombresDeRegistrosViejos.add(i.getUserName());

                System.out.println("check json: "+jsonArray.length());

                JSONObject obj = null;
                for (int i = 0; i < jsonArray.length(); i++ ){System.out.println("hola registro1 "+i);
                    obj = jsonArray.getJSONObject(i);System.out.println("hola registro2");

                    int ID = obj.getInt("ID");
                    String userName = obj.getString("userName");
                    String details = obj.getString("details");
                    String image="null";

                    try {
                        image = obj.getString("image");
                    }
                    catch(Exception e){System.out.println(e);}
                    System.out.println("check image "+userName+"/"+details+"/"+image);
                    registrosNuevos.add(new User(ID,userName, details, image));

                    System.out.println("hola registro4");
                }


                for(User user: registrosNuevos) {
                    try {
                        if(nombresDeRegistrosViejos.contains(user.getUserName())) {
                            UserProvider.update(resolvedor, user,false);
                        } else {
                            UserProvider.insert(resolvedor, user);
                        }
                        //identificadoresDeRegistrosActualizados.add(user.getID());
                    } catch (Exception e){
                        Log.i("sincronizacion",
                                "Probablemente el registro ya existía en la BD."+"" +
                                        " Esto se podría controlar mejor con una Bitácora.");
                    }
                }*/

            /*Borrar registros viejos
            for(User user: registrosViejos){
                if(!identificadoresDeRegistrosActualizados.contains(user.getID())){
                    try {
                        UserProvider.delete(resolvedor, user.getID());
                    }catch(Exception e){
                        Log.i("sincronizacion", "Error al borrar el registro con id:" + user.getID());
                    }
                }
            }

                //Volley1.getAll(); //Los baja y los guarda en SQLite*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void refresh(){
        System.out.println("HOLA HOLITA VECINO");
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(AppController.mAccount,Contract.AUTHORITY,bundle);
        ContentResolver.setSyncAutomatically(AppController.mAccount,Contract.AUTHORITY,true);
    }
}