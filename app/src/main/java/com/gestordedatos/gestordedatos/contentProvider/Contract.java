package com.gestordedatos.gestordedatos.contentProvider;

import android.net.Uri;
import android.provider.BaseColumns;

public class Contract {
    public static final String AUTHORITY = "com.gestordedatos.gestordedatos.contentProvider.MyContentProvider";

    public static final class Classroom implements BaseColumns{
        public static final Uri CONTENT_URI = Uri
                .parse("content://"+AUTHORITY+"/Classrooms");
        // Table columns
        public static final String classroomName = "classroomName";
        public static final String subject = "Subject";
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
    }

    public static final class Tutorship implements BaseColumns{
        public static final Uri CONTENT_URI = Uri
                .parse("content://"+AUTHORITY+"/Tutorships");
        // Table columns
        public static final String name = "name";
        public static final String classroom = "classroom";
        public static final String startTime = "startTime";
        public static final String endingTime = "endingTime";
    }
}