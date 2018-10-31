package com.gestordedatos.gestordedatos.classrooms;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.gestordedatos.gestordedatos.R;
import com.gestordedatos.gestordedatos.contentProvider.ClassroomProvider;
import com.gestordedatos.gestordedatos.contentProvider.Contract;
import com.gestordedatos.gestordedatos.pojos.Classroom;

public class FormListClassroomsUpdate extends AppCompatActivity {
    EditText editTextClassroomName;
    EditText editTextSubject;
    String classroomName;
    String subject;
    int classroomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_list_classrooms);

        editTextClassroomName = (EditText) findViewById(R.id.editTextClassroom);
        editTextSubject = (EditText) findViewById(R.id.editTextClassroomSubject);

        classroomId = this.getIntent().getExtras().getInt(Contract.Classroom._ID);

        Classroom classroom = ClassroomProvider.readRecord(getContentResolver(),classroomId);
        System.out.println("CHECK ID2: "+classroomId+" "+classroom.getClassroomName());

        editTextClassroomName.setText(classroom.getClassroomName());
        editTextSubject.setText(classroom.getSubject());
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
        //Actualizar registro
        Classroom classroom = new Classroom(classroomId, classroomName, subject);
        ClassroomProvider.updateRecord(getContentResolver(),classroom);
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
            validar();
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
