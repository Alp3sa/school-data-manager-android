package com.gestordedatos.gestordedatos.contentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.SparseArray;

import com.gestordedatos.gestordedatos.Globals;
import com.gestordedatos.gestordedatos.sync.Sincronizacion;

public class MyContentProvider extends ContentProvider {
    private static final int CLASSROOM_ONE_REG=1;
    private static final int CLASSROOM_ALL_REGS=2;

    private static final int BITACORA_ONE_REG = 3;
    private static final int BITACORA_ALL_REGS = 4;

    private SQLiteDatabase sqlDB;
    public DatabaseHelper dbHelper;
    private static final String DATABASE_NAME = "school.db";
    private static final int DATABASE_VERSION = 27;

    private static final String BITACORA_TABLE_NAME = "Bitacora";

    //Indicates an invalid content URI
    public static final int INVALID_URI = -1;

    //Defines a helper object that matches content
    private static final UriMatcher sUriMatcher;

    //Stores the MIME served by this provider
    private static final SparseArray<String> sMimeTypes;

    static{
        //Creates an object that associates content URIs with numeric codes
        sUriMatcher = new UriMatcher(0);

        /*
         * Sets up an array that maps content URIs to MIME types, via a mapping between the
         * URIs and an integer code. These are custom MIME types that apply to tables and rows
         * in this particular provider.
         */


        sMimeTypes = new SparseArray<String>();

        //Adds a URI "match" entry that maps picture URL content URIs to a numeric code

        sUriMatcher.addURI(
                Contract.AUTHORITY,
                "Classrooms",
                CLASSROOM_ALL_REGS);

        sUriMatcher.addURI(
                Contract.AUTHORITY,
                "Classrooms/#",
                CLASSROOM_ONE_REG);

        sUriMatcher.addURI(
                Contract.AUTHORITY,
                "Subjects",
                CLASSROOM_ALL_REGS);

        sUriMatcher.addURI(
                Contract.AUTHORITY,
                "Subjects/#",
                CLASSROOM_ONE_REG);

        sUriMatcher.addURI(
                Contract.AUTHORITY,
                "Tutorships",
                CLASSROOM_ALL_REGS);

        sUriMatcher.addURI(
                Contract.AUTHORITY,
                "Tutorships/#",
                CLASSROOM_ONE_REG);
        sUriMatcher.addURI(
                Contract.AUTHORITY,
                "Enrollments",
                CLASSROOM_ALL_REGS);

        sUriMatcher.addURI(
                Contract.AUTHORITY,
                "Enrollments/#",
                CLASSROOM_ONE_REG);
        sUriMatcher.addURI(
                Contract.AUTHORITY,
                "Users",
                CLASSROOM_ALL_REGS);

        sUriMatcher.addURI(
                Contract.AUTHORITY,
                "Users/#",
                CLASSROOM_ONE_REG);
        sUriMatcher.addURI(
                Contract.AUTHORITY,
                "Bitacora",
                BITACORA_ALL_REGS);
        sUriMatcher.addURI(
                Contract.AUTHORITY,
                "Bitacora/#",
                BITACORA_ONE_REG);
        /*sUriMatcher.addURI(
                Contract.AUTHORITY,
                Globals.CLASSROOM_TABLE_NAME,
                CLASSROOM_ALL_REGS);

        sUriMatcher.addURI(
                Contract.AUTHORITY,
                Globals.CLASSROOM_TABLE_NAME+"/#",
                CLASSROOM_ONE_REG);*/

        // Specifies a custom MIME type for the picture URL table text/html

        sMimeTypes.put(CLASSROOM_ONE_REG,
                "vnd.android.cursor.dir/vnd."+
                        Contract.AUTHORITY+".Classrooms");
        sMimeTypes.put(CLASSROOM_ALL_REGS,
                "vnd.android.cursor.dir/vnd."+
                        Contract.AUTHORITY+".Classrooms");
        sMimeTypes.put(CLASSROOM_ONE_REG,
                "vnd.android.cursor.dir/vnd."+
                        Contract.AUTHORITY+".Subjects");
        sMimeTypes.put(CLASSROOM_ALL_REGS,
                "vnd.android.cursor.dir/vnd."+
                        Contract.AUTHORITY+".Subjects");
        sMimeTypes.put(CLASSROOM_ONE_REG,
                "vnd.android.cursor.dir/vnd."+
                        Contract.AUTHORITY+".Tutorships");
        sMimeTypes.put(CLASSROOM_ALL_REGS,
                "vnd.android.cursor.dir/vnd."+
                        Contract.AUTHORITY+".Tutorships");
        sMimeTypes.put(CLASSROOM_ONE_REG,
                "vnd.android.cursor.dir/vnd."+
                        Contract.AUTHORITY+".Enrollments");
        sMimeTypes.put(CLASSROOM_ALL_REGS,
                "vnd.android.cursor.dir/vnd."+
                        Contract.AUTHORITY+".Enrollments");
        sMimeTypes.put(CLASSROOM_ONE_REG,
                "vnd.android.cursor.dir/vnd."+
                        Contract.AUTHORITY+".Users");
        sMimeTypes.put(CLASSROOM_ALL_REGS,
                "vnd.android.cursor.dir/vnd."+
                        Contract.AUTHORITY+".Users");
        sMimeTypes.put(BITACORA_ONE_REG,
                "vnd.android.cursor.dir/vnd."+
                        Contract.AUTHORITY+".Bitacora");
        sMimeTypes.put(BITACORA_ALL_REGS,
                "vnd.android.cursor.dir/vnd."+
                        Contract.AUTHORITY+".Bitacora");
        /*sMimeTypes.put(CLASSROOM_ONE_REG,
                "vnd.android.cursor.dir/vnd."+
                Contract.AUTHORITY+"."+Globals.CLASSROOM_TABLE_NAME);
        sMimeTypes.put(CLASSROOM_ALL_REGS,
                "vnd.android.cursor.dir/vnd."+
                        Contract.AUTHORITY+"."+Globals.CLASSROOM_TABLE_NAME);*/
    }

    public static class DatabaseHelper extends SQLiteOpenHelper{
        public DatabaseHelper(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onOpen(final SQLiteDatabase db) {
            super.onOpen(db);
            db.execSQL("PRAGMA foreign_key=ON;");
/*
            db.execSQL("DROP TABLE IF EXISTS Tutorships");
            db.execSQL("DROP TABLE IF EXISTS Subjects");
            db.execSQL("DROP TABLE IF EXISTS Classrooms");
            db.execSQL("DROP TABLE IF EXISTS Users");
            db.execSQL("DROP TABLE IF EXISTS Enrollments");

            db.execSQL("Create table "
                    +"Classrooms "
                    +"(_id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                    +Contract.Classroom.classroomName +" TEXT UNIQUE, "
                    +Contract.Classroom.details+" TEXT,"
                    +Contract.Classroom.image_url+" TEXT);");

            db.execSQL("Create table "
                    +"Subjects "
                    +"(_id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                    +Contract.Subject.name +" TEXT, "
                    +Contract.Subject.teacher +" TEXT, "
                    +Contract.Subject.classroom +" TEXT, "
                    +Contract.Subject.startTime +" TEXT, "
                    +Contract.Subject.endingTime+" TEXT, "
                    +Contract.Subject.classroomID+" INTEGER, "
                    +"FOREIGN KEY(classroomID) REFERENCES Classrooms(_id));");

            db.execSQL("Create table "
                    +"Tutorships "
                    +"(_id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                    +Contract.Tutorship.name +" TEXT, "
                    +Contract.Tutorship.classroom +" TEXT, "
                    +Contract.Tutorship.startTime +" TEXT, "
                    +Contract.Tutorship.endingTime+" TEXT, "
                    +Contract.Tutorship.classroomID+" INTEGER, "
                    +"FOREIGN KEY(classroomID) REFERENCES Classrooms(_id));");


            db.execSQL("Create table "
                    +"Enrollments "
                    +"(_id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                    +Contract.Enrollment.userID +" INTEGER, "
                    +Contract.Enrollment.subjectID +" INTEGER);");

            db.execSQL("Create table "
                    +"Users "
                    +"(_id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                    +Contract.User.nombreUsuario +" TEXT, "
                    +Contract.User.nombre +" TEXT, "
                    +Contract.User.primerApellido +" TEXT, "
                    +Contract.User.segundoApellido+" TEXT, "
                    +Contract.User.edad+" TEXT, "
                    +Contract.User.dni+" TEXT, "
                    +Contract.User.genero+" TEXT, "
                    +Contract.User.tipoDeMiembro+" TEXT, "
                    +Contract.User.password+" TEXT);");*/
        }

        @Override
        public void onConfigure(SQLiteDatabase db) {
            db.setForeignKeyConstraintsEnabled(true);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            /*db.execSQL("Create table "
                +"Classrooms "
                    +"(_id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                    +Contract.Classroom.classroomName +" TEXT UNIQUE, "
                    +Contract.Classroom.subject+" TEXT);");
*/
            db.execSQL("Create table "
                    +"Classrooms "
                    +"(_id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                    +Contract.Classroom.classroomName +" TEXT UNIQUE, "
                    +Contract.Classroom.details+" TEXT,"
                    +Contract.Classroom.image_url+" TEXT);");

            /*db.execSQL("Create table "
                    +"Subjects "
                    +"(_id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                    +Contract.Subject.name +" TEXT, "
                    +Contract.Subject.teacher +" TEXT, "
                    +Contract.Subject.classroom +" TEXT, "
                    +Contract.Subject.startTime +" TEXT, "
                    +Contract.Subject.endingTime+" TEXT, "
                    +Contract.Subject.classroomID+" INT, "
                    +"FOREIGN KEY(classroom) REFERENCES Classrooms(classroomName));");
            */
            db.execSQL("Create table "
                    +"Subjects "
                    +"(_id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                    +Contract.Subject.name +" TEXT, "
                    +Contract.Subject.teacher +" TEXT, "
                    +Contract.Subject.classroom +" TEXT, "
                    +Contract.Subject.startTime +" TEXT, "
                    +Contract.Subject.endingTime+" TEXT, "
                    +Contract.Subject.classroomID+" INT, "
                    +"FOREIGN KEY(classroomID) REFERENCES Classrooms(_id));");

            db.execSQL("Create table "
                    +"Tutorships "
                    +"(_id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                    +Contract.Tutorship.name +" TEXT, "
                    +Contract.Tutorship.classroom +" TEXT, "
                    +Contract.Tutorship.startTime +" TEXT, "
                    +Contract.Tutorship.endingTime+" TEXT, "
                    +Contract.Tutorship.classroomID+" INTEGER, "
                    +"FOREIGN KEY(classroomID) REFERENCES Classrooms(_id));");

            db.execSQL("Create table "
                    +"Enrollments "
                    +"(_id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                    +Contract.Enrollment.userID +" INTEGER, "
                    +Contract.Enrollment.subjectID +" INTEGER);");

            db.execSQL("Create table "
                    +"Users "
                    +"(_id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                    +Contract.User.nombreUsuario +" TEXT, "
                    +Contract.User.nombre +" TEXT, "
                    +Contract.User.primerApellido +" TEXT, "
                    +Contract.User.segundoApellido+" TEXT, "
                    +Contract.User.edad+" TEXT, "
                    +Contract.User.dni+" TEXT, "
                    +Contract.User.genero+" TEXT, "
                    +Contract.User.tipoDeMiembro+" TEXT, "
                    +Contract.User.password+" TEXT);");

            db.execSQL("Create table "
                    + BITACORA_TABLE_NAME
                    + "( _id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                    + Contract.Bitacora.CLASS_ELEMENT + " INTEGER , "
                    + Contract.Bitacora.ID_ELEMENT + " INTEGER , "
                    + Contract.Bitacora.OPERACION + " INTEGER ); "
            );

            inicializarDatos(db);
        }

        void inicializarDatos(SQLiteDatabase db){
            /*db.execSQL("INSERT INTO Classrooms ("+Contract.Classroom._ID+","+Contract.Classroom.classroomName +","+Contract.Classroom.details+")"+
                    " VALUES (1,'101','INGLÉS')");
            db.execSQL("INSERT INTO Classrooms ("+Contract.Classroom._ID+","+Contract.Classroom.classroomName +","+Contract.Classroom.details+")"+
                    " VALUES (2,'102','MATES')");
            db.execSQL("INSERT INTO Classrooms ("+Contract.Classroom._ID+","+Contract.Classroom.classroomName +","+Contract.Classroom.details+")"+
                    " VALUES (3,'103','LENGUA')");
            */
            /*db.execSQL("INSERT INTO Classrooms ("+Contract.Classroom._ID+","+Contract.Classroom.classroomName +","+Contract.Classroom.subject+")"+
                    " VALUES (1,'101','INGLÉS')");
            db.execSQL("INSERT INTO Classrooms ("+Contract.Classroom._ID+","+Contract.Classroom.classroomName +","+Contract.Classroom.subject+")"+
                    " VALUES (2,'102','MATES')");
            db.execSQL("INSERT INTO Classrooms ("+Contract.Classroom._ID+","+Contract.Classroom.classroomName +","+Contract.Classroom.subject+")"+
                    " VALUES (3,'103','LENGUA')");

            db.execSQL("INSERT INTO Subjects ("+Contract.Subject._ID+","+Contract.Subject.name +","+Contract.Subject.teacher+","+Contract.Subject.classroom+","+Contract.Subject.startTime+","+Contract.Subject.endingTime+")"+
                    " VALUES (1,'INGLÉS','JUAN','102','11:00','11:30')");
            db.execSQL("INSERT INTO Subjects ("+Contract.Subject._ID+","+Contract.Subject.name +","+Contract.Subject.teacher+","+Contract.Subject.classroom+","+Contract.Subject.startTime+","+Contract.Subject.endingTime+")"+
                    " VALUES (2,'MATES','LUISA','101','10:30','11:00')");
            db.execSQL("INSERT INTO Subjects ("+Contract.Subject._ID+","+Contract.Subject.name +","+Contract.Subject.teacher+","+Contract.Subject.classroom+","+Contract.Subject.startTime+","+Contract.Subject.endingTime+")"+
                    " VALUES (3,'LENGUA','LUIS','103','08:30','09:30')");

            db.execSQL("INSERT INTO Tutorships ("+Contract.Tutorship._ID+","+Contract.Tutorship.name +","+Contract.Tutorship.classroom+","+Contract.Tutorship.startTime+","+Contract.Tutorship.endingTime+")"+
                    " VALUES (1,'LUISA','102','16:00','17:00')");
            db.execSQL("INSERT INTO Tutorships ("+Contract.Tutorship._ID+","+Contract.Tutorship.name +","+Contract.Tutorship.classroom+","+Contract.Tutorship.startTime+","+Contract.Tutorship.endingTime+")"+
                    " VALUES (2,'JUAN','102','16:00','17:00')");
            db.execSQL("INSERT INTO Tutorships ("+Contract.Tutorship._ID+","+Contract.Tutorship.name +","+Contract.Tutorship.classroom+","+Contract.Tutorship.startTime+","+Contract.Tutorship.endingTime+")"+
                    " VALUES (3,'LUIS','102','16:00','17:00')");*/

            db.execSQL("INSERT INTO " + BITACORA_TABLE_NAME + " (" + Contract.Bitacora._ID + "," + Contract.Bitacora.CLASS_ELEMENT + "," + Contract.Bitacora.ID_ELEMENT + "," + Contract.Bitacora.OPERACION + ") " +
                    "VALUES (1,1,1," + Globals.OPERACION_INSERTAR + ")");
            db.execSQL("INSERT INTO " + BITACORA_TABLE_NAME + " (" + Contract.Bitacora._ID + "," + Contract.Bitacora.CLASS_ELEMENT + "," + Contract.Bitacora.ID_ELEMENT + "," + Contract.Bitacora.OPERACION + ") " +
                    "VALUES (2,1,2," + Globals.OPERACION_INSERTAR + ")");
            db.execSQL("INSERT INTO " + BITACORA_TABLE_NAME + " (" + Contract.Bitacora._ID + "," + Contract.Bitacora.CLASS_ELEMENT + "," + Contract.Bitacora.ID_ELEMENT + "," + Contract.Bitacora.OPERACION + ") " +
                    "VALUES (3,1,3," + Globals.OPERACION_INSERTAR + ")");
            db.execSQL("INSERT INTO " + BITACORA_TABLE_NAME + " (" + Contract.Bitacora._ID + "," + Contract.Bitacora.CLASS_ELEMENT + "," + Contract.Bitacora.ID_ELEMENT + "," + Contract.Bitacora.OPERACION + ") " +
                    "VALUES (4,1,4," + Globals.OPERACION_INSERTAR + ")");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS Tutorships");
            db.execSQL("DROP TABLE IF EXISTS Subjects");
            db.execSQL("DROP TABLE IF EXISTS Classrooms");
            db.execSQL("DROP TABLE IF EXISTS Enrollments");
            db.execSQL("DROP TABLE IF EXISTS Users");
            db.execSQL("DROP TABLE IF EXISTS " + BITACORA_TABLE_NAME);

            onCreate(db);
        }
    }

    public MyContentProvider(){
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        sqlDB = dbHelper.getWritableDatabase();
        //insertRecord record in User table and get the row classroomName of record

        String table="";
        switch(sUriMatcher.match(uri)){
            case CLASSROOM_ONE_REG:
                if(null == selection) selection = "";
                selection += Contract.Classroom._ID+"="
                        +uri.getLastPathSegment();
                table=Globals.CLASSROOM_TABLE_NAME;
                break;
            case CLASSROOM_ALL_REGS:
                table=Globals.CLASSROOM_TABLE_NAME;
                break;
            case BITACORA_ONE_REG:
                if (null == selection) selection = "";
                selection += Contract.Bitacora._ID + " = "
                        + uri.getLastPathSegment();
                table = BITACORA_TABLE_NAME;
                break;
            case BITACORA_ALL_REGS:
                table = BITACORA_TABLE_NAME;
                break;
        }

        int rows = sqlDB.delete(table, selection, selectionArgs);

        if (rows > 0) {
            //getContext().getContentResolver().notifyChange(uri,null);
            if (table != BITACORA_TABLE_NAME)
                getContext().getContentResolver().notifyChange(uri, null);
            return rows;
        }
        return -1;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        sqlDB = dbHelper.getWritableDatabase();
        String table="";
        switch(sUriMatcher.match(uri)){
            case CLASSROOM_ALL_REGS:
                table=Globals.CLASSROOM_TABLE_NAME;
                break;
            case BITACORA_ALL_REGS:
                table = BITACORA_TABLE_NAME;
                break;
        }

        long rowId = sqlDB.insert(table, "", values);

        if (rowId > 0) {
            Uri rowUri = ContentUris.appendId(uri.buildUpon(), rowId).build();
            //getContext().getContentResolver().notifyChange(rowUri,null);
            if (table != BITACORA_TABLE_NAME)
                getContext().getContentResolver().notifyChange(rowUri, null);
            return rowUri;
        }

        return null;
    }


    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return (dbHelper==null) ? false : true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        System.out.println("check table: "+Globals.CLASSROOM_TABLE_NAME);
        qb.setTables(Globals.CLASSROOM_TABLE_NAME);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        switch(sUriMatcher.match(uri)){
            case CLASSROOM_ONE_REG:
                if(null == selection) selection = "";
                selection += Contract.Classroom._ID+"="
                        +uri.getLastPathSegment();
                qb.setTables(Globals.CLASSROOM_TABLE_NAME);
                break;
            case CLASSROOM_ALL_REGS:
                if(TextUtils.isEmpty(sortOrder)) sortOrder = Contract.Classroom._ID+" ASC";
                qb.setTables(Globals.CLASSROOM_TABLE_NAME);
                break;
            case BITACORA_ONE_REG:
                if (null == selection) selection = "";
                selection += Contract.Bitacora._ID + " = "
                        + uri.getLastPathSegment();
                qb.setTables(BITACORA_TABLE_NAME);
                break;
            case BITACORA_ALL_REGS:
                if (TextUtils.isEmpty(sortOrder)) sortOrder =
                        Contract.Bitacora._ID + " ASC";
                qb.setTables(BITACORA_TABLE_NAME);
                break;
        }
        System.out.println("check subqueries: ("+Globals.classroomsSubquery+":"+Globals.classrooms1Subquery+":"+Globals.classrooms2Subquery+":"+Globals.tutorshipSearchBox+") "+Globals.LAST_TAB);
        if(Globals.classroomsSubquery!=null && Globals.LAST_TAB==1){
            String arg[] = {Integer.toString(Globals.classroomsSubquery.getID())};
            System.out.println("HOLA "+arg[0]);
            selectionArgs=arg;
            selection="classroomID=?";
        }
        else if(Globals.classrooms1Subquery!=null && Globals.LAST_TAB==0){
            String arg[] = {Integer.toString(Globals.classrooms1Subquery.getClassroomID())};
            selectionArgs=arg;
            selection="_id=?";
        }
        else if(Globals.classrooms2Subquery!=null && Globals.LAST_TAB==0){
            String arg[] = {Integer.toString(Globals.classrooms2Subquery.getClassroomID())};
            selectionArgs=arg;
            selection="_id=?";
        }
        else if(Globals.tutorshipSearchBox!=null && Globals.LAST_TAB==2){
            String arg[] = {"%"+Globals.tutorshipSearchBox+"%","%"+Globals.tutorshipSearchBox+"%","%"+Globals.tutorshipSearchBox+"%","%"+Globals.tutorshipSearchBox+"%"};
            selectionArgs=arg;
            selection="name LIKE ? COLLATE NOCASE OR classroom LIKE ? COLLATE NOCASE OR startTime LIKE ? COLLATE NOCASE OR endingTime LIKE ? COLLATE NOCASE";
            System.out.println(selection);
            Globals.tutorshipSearchBox=null;
        }

        Cursor c;

        c=qb.query(db,projection,selection,selectionArgs,null,null,sortOrder);
        c.setNotificationUri(getContext().getContentResolver(),uri);

        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        sqlDB = dbHelper.getWritableDatabase();

        String table = "";
        switch(sUriMatcher.match(uri)){
            case CLASSROOM_ONE_REG:
                if(null == selection) selection = "";
                selection += Contract.Classroom._ID+"="
                        +uri.getLastPathSegment();
                table=Globals.CLASSROOM_TABLE_NAME;
                break;
            case CLASSROOM_ALL_REGS:
                table=Globals.CLASSROOM_TABLE_NAME;
                break;
            case BITACORA_ONE_REG:
                if (null == selection) selection = "";
                selection += Contract.Bitacora._ID + " = "
                        + uri.getLastPathSegment();
                table = BITACORA_TABLE_NAME;
                break;
            case BITACORA_ALL_REGS:
                table = BITACORA_TABLE_NAME;
                break;
        }

        int rows = sqlDB.update(table, values, selection, selectionArgs);
        if (rows > 0) {
            //getContext().getContentResolver().notifyChange(uri,null);
            if (table != BITACORA_TABLE_NAME)
                getContext().getContentResolver().notifyChange(uri, null);
            return rows;
        }
        return -1;
    }
}