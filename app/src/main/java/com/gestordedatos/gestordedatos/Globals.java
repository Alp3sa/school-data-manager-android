package com.gestordedatos.gestordedatos;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.gestordedatos.gestordedatos.pojos.Classroom;
import com.gestordedatos.gestordedatos.pojos.Subject;
import com.gestordedatos.gestordedatos.pojos.Tutorship;
import com.gestordedatos.gestordedatos.pojos.User;

public class Globals extends Application {
    public User User;
    public static User user;

    public static final int SIN_VALOR_INT = -1;
    public static final String SIN_VALOR_STRING = "";

    public static String CLASSROOM_TABLE_NAME = "Classrooms";
    public static String SERVER_TABLE_NAME = "classroom";
    public static int LAST_TAB = 0;
    public static boolean STATUS_OPERATION = false;
    public static boolean SUBQUERY_OPERATION = false; //Lo usaremos para chequear si se está realizando una subconsulta
    public static boolean LOGIN = false;// Si es verdadero, si el usuario está logueado, inicia la sincronización

    public static int subquery = -1;// La utilizaremos como variable de control en subconsultas
    public static Classroom classroomsSubquery = null;
    public static Subject classrooms1Subquery = null;
    public static Tutorship classrooms2Subquery = null;
    public static String tutorshipSearchBox = null;

    public static String connectionUsers = null;

    public static final int TAKE_PHOTO = 1;
    public static final int UPLOAD_PHOTO = 2;

    public static final long SYNC_INTERVAL = 60; // Cada 60 segundos
    public static final boolean VERSION_ADMINISTRADOR = false;

    public static final String RUTA_SERVIDOR_TOMCAT = "http://192.168.1.33:8080/school-data-manager/webresources";

    public static final int INSERTAR = 1;
    public static final int GUARDAR = 2;

    //Clases de objetos
    public static final int CLASE_CLASSROOM = 1;
    public static final int CLASE_ENROLLMENT = 2;
    public static final int CLASE_SUBJECT = 3;
    public static final int CLASE_TUTORSHIP = 4;
    public static final int CLASE_USER = 5;

    //Operaciones a guardar en la Bitácora
    public static final int OPERACION_INSERTAR = 0;
    public static final int OPERACION_MODIFICAR = 1;
    public static final int OPERACION_BORRAR = 2;
}
