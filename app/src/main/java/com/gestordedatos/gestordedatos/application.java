package com.gestordedatos.gestordedatos;

import android.app.Application;

import com.gestordedatos.gestordedatos.pojos.User;

public class application extends android.app.Application {
    public User User;

    public static final int SIN_VALOR_INT = -1;
    public static final String SIN_VALOR_STRING = "";

    public static final int INSERTAR = 1;
    public static final int GUARDAR = 2;

    public static String CLASSROOM_TABLE_NAME = "Classrooms";
    public static int LAST_TAB = 0;

    public static final int TAKE_PHOTO = 1;
    public static final int UPLOAD_PHOTO = 2;
}
