package com.gestordedatos.gestordedatos.classrooms;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.gestordedatos.gestordedatos.application;
import com.gestordedatos.gestordedatos.R;
import com.gestordedatos.gestordedatos.Utilities;
import com.gestordedatos.gestordedatos.contentProvider.ClassroomProvider;
import com.gestordedatos.gestordedatos.contentProvider.Contract;
import com.gestordedatos.gestordedatos.pojos.Classroom;

public class FormListClassroomsUpdate extends AppCompatActivity {
    EditText editTextClassroomName;
    EditText editTextSubject;
    ImageView preview;
    String classroomName;
    String subject;
    Bitmap image=null;
    int classroomId;

    int checkonOptionsItemSelected=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_list_classrooms);

        editTextClassroomName = (EditText) findViewById(R.id.editTextClassroom);
        editTextSubject = (EditText) findViewById(R.id.editTextClassroomSubject);

        classroomId = this.getIntent().getExtras().getInt(Contract.Classroom._ID);

        Classroom classroom = ClassroomProvider.readRecord(getContentResolver(),classroomId);

        editTextClassroomName.setText(classroom.getClassroomName());
        editTextSubject.setText(classroom.getSubject());

        preview = findViewById(R.id.preview);
        try {
            Utilities.loadImageFromStorage(this, "img_" + classroomId + ".jpg", preview);
            image = ((BitmapDrawable) preview.getDrawable()).getBitmap();
        }catch(Exception e){}
        preview.setTag(preview);

        Button takePhoto = findViewById(R.id.takePhoto);

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                takePhoto();
            }
        });

        Button uploadPhoto = findViewById(R.id.uploadPhoto);

        uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                uploadPhoto();
            }
        });

        Button deletePhoto = findViewById(R.id.deletePhoto);

        deletePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                image=null;
                preview.setImageResource(android.R.drawable.ic_menu_camera);
            }
        });
    }

    public void takePhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, application.TAKE_PHOTO);
    }

    public void uploadPhoto(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, application.UPLOAD_PHOTO);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        switch(requestCode){
            case application.TAKE_PHOTO:
                if(resultCode==RESULT_OK){
                    image = (Bitmap) data.getExtras().get("data");
                    preview.setImageBitmap(image);

                    /*try{
                        Utilities.storeImage(image, this, "image.jpg");
                    } catch (IOException e) {
                        String toast = getResources().getString(R.string.errorImage);
                        SpannableStringBuilder biggerText = new SpannableStringBuilder(toast);
                        biggerText.setSpan(new RelativeSizeSpan(1.5f), 0, toast.length(), 0);
                        Toast errorImage = Toast.makeText(getApplicationContext(),biggerText,Toast.LENGTH_LONG);
                        errorImage.setGravity(Gravity.BOTTOM, 0, 40);
                        errorImage.show();
                    }*/
                }
                else{
                    // User canceled action
                }
                break;
            case application.UPLOAD_PHOTO:
                if(resultCode==RESULT_OK){
                    Uri uri = data.getData();
                    preview.setImageURI(uri);
                }
                else{
                    // User canceled action
                }
                break;
        }

        image = ((BitmapDrawable) preview.getDrawable()).getBitmap();

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void validar(){
        classroomName = editTextClassroomName.getText().toString();
        editTextClassroomName.setError(null);

        if(classroomName.trim().equals("")){
            editTextClassroomName.setError(getString(R.string.errorEmptyClassroom));
            editTextClassroomName.requestFocus();
            return;
        }

        subject = editTextSubject.getText().toString();
        editTextSubject.setError(null);

        if(subject.trim().equals("")){
            editTextSubject.setError(getString(R.string.errorEmptySubject));
            editTextSubject.requestFocus();
            return;
        }

        if(image==null) {
            Utilities.deleteImage(getApplicationContext(), "img_" + classroomId + ".jpg");
        }

        //Update
        Classroom classroom = new Classroom(classroomId, classroomName, subject, image);
        try{
            ClassroomProvider.updateRecord(getContentResolver(),classroom,this);
        }
        catch(SQLException e){
            editTextClassroomName.setError(getString(R.string.errorClassroomUniqueUpdate));
            editTextClassroomName.requestFocus();
            return;
        }
        checkonOptionsItemSelected = 1;
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.save_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }
        else */
        if(id == R.id.action_help){
            showHelp();
            return true;
        }
        if(id == R.id.action_save){
            if(checkonOptionsItemSelected==0) {
                validar();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showHelp() {
        new AlertDialog.Builder(FormListClassroomsUpdate.this)
                .setTitle(getResources().getString(R.string.helpTitle))
                .setMessage(getResources().getString(R.string.helpFormListClassrooms))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }
}
