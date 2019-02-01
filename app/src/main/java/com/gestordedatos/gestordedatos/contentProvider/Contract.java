package com.gestordedatos.gestordedatos.contentProvider;

import android.net.Uri;
import android.provider.BaseColumns;

public class Contract {
    public static final String AUTHORITY = "com.gestordedatos.gestordedatos.contentProvider.MyContentProvider";

    /*public static final class Classroom implements BaseColumns{
        public static final Uri CONTENT_URI = Uri
                .parse("content://"+AUTHORITY+"/Classrooms");
        // Table columns
        public static final String classroomName = "classroomName";
        public static final String subject = "Subject";
    }*/

    public static final class Classroom implements BaseColumns{
        public static final Uri CONTENT_URI = Uri
                .parse("content://"+AUTHORITY+"/Classrooms");
        // Table columns
        public static final String classroomName = "classroomName";
        public static final String details = "details";
        public static final String image_url = "image";
    }

    public static final class Subject implements BaseColumns{
        public static final Uri CONTENT_URI = Uri
                .parse("content://"+AUTHORITY+"/Subjects");
        // Table columns
        public static final String name = "name";
        public static final String teacher = "teacher";
        public static final String classroom = "classroom";
        public static final String startTime = "startTime";
        public static final String endingTime = "endingTime";
        public static final String classroomID = "classroomID";
    }

    public static final class Tutorship implements BaseColumns{
        public static final Uri CONTENT_URI = Uri
                .parse("content://"+AUTHORITY+"/Tutorships");
        // Table columns
        public static final String name = "name";
        public static final String classroom = "classroom";
        public static final String startTime = "startTime";
        public static final String endingTime = "endingTime";
        public static final String classroomID = "classroomID";
    }

    public static final class Enrollment implements BaseColumns{
        public static final Uri CONTENT_URI = Uri
                .parse("content://"+AUTHORITY+"/Enrollments");
        // Table columns
        public static final String userID = "userID";
        public static final String subjectID = "subjectID";
    }

    public static final class User implements BaseColumns{
        public static final Uri CONTENT_URI = Uri
                .parse("content://"+AUTHORITY+"/Users");
        // Table columns
        public static final String nombreUsuario = "nombreUsuario";
        public static final String nombre = "nombre";
        public static final String primerApellido = "primerApellido";
        public static final String segundoApellido = "segundoApellido";
        public static final String edad = "edad";
        public static final String dni = "dni";
        public static final String genero = "genero";
        public static final String tipoDeMiembro = "tipoDeMiembro";
        public static final String password = "password";
    }

    public static final class Bitacora implements BaseColumns {

        public static final Uri CONTENT_URI = Uri
                .parse("content://"+AUTHORITY+"/Bitacora");

        // Table column
        public static final String CLASS_ELEMENT = "CLASS_Element";
        public static final String ID_ELEMENT = "ID_Element";
        public static final String OPERACION = "Operacion";
    }
}