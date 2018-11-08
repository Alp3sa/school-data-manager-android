package com.gestordedatos.gestordedatos.contentProvider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.widget.Toast;

import com.gestordedatos.gestordedatos.application;
import com.gestordedatos.gestordedatos.R;
import com.gestordedatos.gestordedatos.Utilities;
import com.gestordedatos.gestordedatos.pojos.Classroom;

import java.io.IOException;

public class ClassroomProvider {
    public static void insertRecord(ContentResolver resolver, Classroom classroom,Context context){
        Uri uri = Contract.Classroom.CONTENT_URI;

        ContentValues values = new ContentValues();
        values.put(Contract.Classroom.classroomName,classroom.getClassroomName());
        values.put(Contract.Classroom.subject,classroom.getSubject());

        application.CLASSROOM_TABLE_NAME = "Classrooms";

        Uri uriResult = resolver.insert(uri,values);

        String classroomId = uriResult.getLastPathSegment();

        if(classroom.getImage()!=null){
            try {
                Utilities.storeImage(classroom.getImage(), context, "img_" + classroomId + ".jpg");
                Utilities.storeImageInExternelMemory(classroom.getImage(), context, "img_" + classroomId + ".jpg");
            }
            catch (IOException e){
                String toast = context.getResources().getString(R.string.errorImage);
                SpannableStringBuilder biggerText = new SpannableStringBuilder(toast);
                biggerText.setSpan(new RelativeSizeSpan(1.5f), 0, toast.length(), 0);
                Toast errorImage = Toast.makeText(context,biggerText,Toast.LENGTH_LONG);
                errorImage.setGravity(Gravity.BOTTOM, 0, 40);
                errorImage.show();
            }
        }


    }

    public static void deleteRecord(ContentResolver resolver, int classroomId, Context context){
        Uri uri = Uri.parse(Contract.Classroom.CONTENT_URI+"/"+classroomId);
        Utilities.deleteImage(context, "img_" + classroomId + ".jpg");

        application.CLASSROOM_TABLE_NAME = "Classrooms";

        resolver.delete(uri, null, null);
    }

    public static void updateRecord(ContentResolver resolver, Classroom classroom, Context context){
        Uri uri = Uri.parse(Contract.Classroom.CONTENT_URI+"/"+classroom.getID());

        ContentValues values = new ContentValues();
        values.put(Contract.Classroom.classroomName,classroom.getClassroomName());
        values.put(Contract.Classroom.subject,classroom.getSubject());

        application.CLASSROOM_TABLE_NAME = "Classrooms";

        resolver.update(uri, values, null, null);

        if(classroom.getImage()!=null){
            try {
                Utilities.storeImage(classroom.getImage(), context, "img_" + classroom.getID() + ".jpg");
                Utilities.storeImageInExternelMemory(classroom.getImage(), context, "img_" + classroom.getID() + ".jpg");
            }
            catch (IOException e){
                String toast = context.getResources().getString(R.string.errorImage);
                SpannableStringBuilder biggerText = new SpannableStringBuilder(toast);
                biggerText.setSpan(new RelativeSizeSpan(1.5f), 0, toast.length(), 0);
                Toast errorImage = Toast.makeText(context,biggerText,Toast.LENGTH_LONG);
                errorImage.setGravity(Gravity.BOTTOM, 0, 40);
                errorImage.show();
            }
        }
    }

    public static Classroom readRecord(ContentResolver resolver, int classroomId){
        Uri uri = Uri.parse(Contract.Classroom.CONTENT_URI+"/"+classroomId);

        String[] projection = {
                Contract.Classroom._ID,
                Contract.Classroom.classroomName,
                Contract.Classroom.subject
        };

        application.CLASSROOM_TABLE_NAME = "Classrooms";

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if(cursor.moveToFirst()){
            Classroom classroom = new Classroom();
            classroom.setID(classroomId);
            classroom.setClassroomName(cursor.getString(cursor.getColumnIndex(Contract.Classroom.classroomName)));
            classroom.setSubject(cursor.getString(cursor.getColumnIndex(Contract.Classroom.subject)));
            return classroom;
        }

        return null;
    }
}
