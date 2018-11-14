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

import com.gestordedatos.gestordedatos.application;

public class MyContentProvider extends ContentProvider {
    private static final int CLASSROOM_ONE_REG=1;
    private static final int CLASSROOM_ALL_REGS=2;

    private SQLiteDatabase sqlDB;
    public DatabaseHelper dbHelper;
    private static final String DATABASE_NAME = "school.db";
    private static final int DATABASE_VERSION = 14;

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
        /*sUriMatcher.addURI(
                Contract.AUTHORITY,
                application.CLASSROOM_TABLE_NAME,
                CLASSROOM_ALL_REGS);

        sUriMatcher.addURI(
                Contract.AUTHORITY,
                application.CLASSROOM_TABLE_NAME+"/#",
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

        /*sMimeTypes.put(CLASSROOM_ONE_REG,
                "vnd.android.cursor.dir/vnd."+
                Contract.AUTHORITY+"."+application.CLASSROOM_TABLE_NAME);
        sMimeTypes.put(CLASSROOM_ALL_REGS,
                "vnd.android.cursor.dir/vnd."+
                        Contract.AUTHORITY+"."+application.CLASSROOM_TABLE_NAME);*/
    }

    public static class DatabaseHelper extends SQLiteOpenHelper{
        public DatabaseHelper(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onOpen(final SQLiteDatabase db) {
            super.onOpen(db);
            db.execSQL("PRAGMA foreign_key=ON;");
        }

        @Override
        public void onConfigure(SQLiteDatabase db) {
            db.setForeignKeyConstraintsEnabled(true);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("Create table "
                +"Classrooms "
                    +"(_id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                    +Contract.Classroom.classroomName +" TEXT UNIQUE, "
                    +Contract.Classroom.subject+" TEXT);");

            db.execSQL("Create table "
                    +"Subjects "
                    +"(_id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                    +Contract.Subject.name +" TEXT, "
                    +Contract.Subject.teacher +" TEXT, "
                    +Contract.Subject.classroom +" TEXT, "
                    +Contract.Subject.startTime +" TEXT, "
                    +Contract.Subject.endingTime+" TEXT, "
                    +"FOREIGN KEY(classroom) REFERENCES Classrooms(classroomName));");

            db.execSQL("Create table "
                    +"Tutorships "
                    +"(_id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                    +Contract.Tutorship.name +" TEXT, "
                    +Contract.Tutorship.classroom +" TEXT, "
                    +Contract.Tutorship.startTime +" TEXT, "
                    +Contract.Tutorship.endingTime+" TEXT, "
                    +"FOREIGN KEY(classroom) REFERENCES Classrooms(classroomName));");

            inicializarDatos(db);
        }

        void inicializarDatos(SQLiteDatabase db){
            db.execSQL("INSERT INTO Classrooms ("+Contract.Classroom._ID+","+Contract.Classroom.classroomName +","+Contract.Classroom.subject+")"+
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
                    " VALUES (3,'LUIS','102','16:00','17:00')");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS Tutorships");
            db.execSQL("DROP TABLE IF EXISTS Subjects");
            db.execSQL("DROP TABLE IF EXISTS Classrooms");

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
                table=application.CLASSROOM_TABLE_NAME;
                break;
            case CLASSROOM_ALL_REGS:
                table=application.CLASSROOM_TABLE_NAME;
                break;
        }

        int rows = sqlDB.delete(table,selection,selectionArgs);
        if(rows>0){
            getContext().getContentResolver().notifyChange(uri,null);
            return rows;
        }
        throw new SQLException("Failed to deleteRecord row into "+uri);
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
                table=application.CLASSROOM_TABLE_NAME;
                break;
        }

        long rowId = sqlDB.insert(table,"",values);

        if(rowId>0){
            Uri rowUri = ContentUris.appendId(
                    uri.buildUpon(),rowId).build();
            getContext().getContentResolver().notifyChange(rowUri,null);
            return rowUri;
        }
        throw new SQLException("Failed to insertRecord row into "+uri);
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
        qb.setTables(application.CLASSROOM_TABLE_NAME);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = null;

        switch(sUriMatcher.match(uri)){
            case CLASSROOM_ONE_REG:
                if(null == selection) selection = "";
                selection += Contract.Classroom._ID+"="
                        +uri.getLastPathSegment();
                qb.setTables(application.CLASSROOM_TABLE_NAME);
                break;
            case CLASSROOM_ALL_REGS:
                if(TextUtils.isEmpty(sortOrder)) sortOrder = Contract.Classroom._ID+" ASC";
                qb.setTables(application.CLASSROOM_TABLE_NAME);
                break;
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
                table=application.CLASSROOM_TABLE_NAME;
                break;
            case CLASSROOM_ALL_REGS:
                table=application.CLASSROOM_TABLE_NAME;
                break;
        }

        int rows = sqlDB.update(table,values,selection,selectionArgs);
        if(rows>0){
            getContext().getContentResolver().notifyChange(uri,null);
            return rows;
        }
        throw new SQLException("Failed to updateRecord row into "+uri);
    }

}
