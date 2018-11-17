package com.gestordedatos.gestordedatos;

import android.app.Application;

import com.gestordedatos.gestordedatos.pojos.Classroom;
import com.gestordedatos.gestordedatos.pojos.Subject;
import com.gestordedatos.gestordedatos.pojos.Tutorship;
import com.gestordedatos.gestordedatos.pojos.User;

public class Globals extends android.app.Application {
    public User User;
    public static User user;

    public static final int SIN_VALOR_INT = -1;
    public static final String SIN_VALOR_STRING = "";

    public static final int INSERTAR = 1;
    public static final int GUARDAR = 2;

    public static String CLASSROOM_TABLE_NAME = "Classrooms";
    public static int LAST_TAB = 0;
    public static int subquery = -1;
    public static Classroom classroomsSubquery = null;
    public static Subject classrooms1Subquery = null;
    public static Tutorship classrooms2Subquery = null;
    public static String tutorshipSearchBox = null;

    public static String connectionUsers = null;

    public static final int TAKE_PHOTO = 1;
    public static final int UPLOAD_PHOTO = 2;
}
