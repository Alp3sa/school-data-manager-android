package com.gestordedatos.gestordedatos.classrooms;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.gestordedatos.gestordedatos.R;
import com.gestordedatos.gestordedatos.contentProvider.ClassroomProvider;
import com.gestordedatos.gestordedatos.pojos.Classroom;

public class FormListClassrooms extends AppCompatActivity {
    EditText editTextClassroomName;
    EditText editTextSubject;
    String classroomName;
    String subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_list_classrooms);
    }

    public void validar(){
        editTextClassroomName = (EditText) findViewById(R.id.editTextClassroom);
        editTextClassroomName.setError(null);
        classroomName = editTextClassroomName.getText().toString();

        if(classroomName.trim().equals("")){
            editTextClassroomName.setError(getString(R.string.errorEmptyClassroom));
            editTextClassroomName.requestFocus();
            return;
        }

        editTextSubject = (EditText) findViewById(R.id.editTextClassroomSubject);
        editTextSubject.setError(null);
        subject = editTextSubject.getText().toString();

        if(subject.trim().equals("")){
            editTextSubject.setError(getString(R.string.errorEmptySubject));
            editTextSubject.requestFocus();
            return;
        }

        //Insertar registro
        Classroom classroom = new Classroom(classroomName,subject);
        ClassroomProvider.insertRecord(getContentResolver(),classroom);
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
        else if(id == R.id.action_save){
            validar();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showHelp() {
        new AlertDialog.Builder(FormListClassrooms.this)
                .setTitle(getResources().getString(R.string.helpTitle))
                .setMessage(getResources().getString(R.string.helpFormListClassrooms))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }
}
