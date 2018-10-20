package com.gestordedatos.gestordedatos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class FormListSubjects extends AppCompatActivity {
    Context contexto;
    EditText editTextSubject;
    EditText editTextTeacher;
    EditText editTextClassroom;
    String subject;
    String teacher;
    String classroom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_list_subjects);

        Spinner dropdownComienzo = findViewById(R.id.spinnerComienzo);
        String[] itemsDropdownComienzo = new String[]{"08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsDropdownComienzo);
        dropdownComienzo.setAdapter(adapter1);

        Spinner dropdownFin = findViewById(R.id.spinnerFin);
        String[] itemsDropdownFin = new String[]{"08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsDropdownFin);
        dropdownFin.setAdapter(adapter2);

        Button buttonConsult = (Button) findViewById(R.id.buttonConsult);
        Button buttonInsert = (Button) findViewById(R.id.buttonInsert);
        Button buttonDelete = (Button) findViewById(R.id.buttonDelete);
        Button buttonUpdate = (Button) findViewById(R.id.buttonUpdate);

        contexto=this;

        buttonConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                validar();
            }
        });
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                validar();
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                validar();
            }
        });
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                validar();
            }
        });
    }

    public void validar(){
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextTeacher = (EditText) findViewById(R.id.editTextTeacher);
        editTextClassroom = (EditText) findViewById(R.id.editTextClassroom);

        editTextSubject.setError(null);
        editTextTeacher.setError(null);
        editTextClassroom.setError(null);

        subject = editTextSubject.getText().toString();
        teacher = editTextTeacher.getText().toString();
        classroom = editTextClassroom.getText().toString();

        if(subject.trim().equals("")){
            editTextSubject.setError(getString(R.string.errorEmptySubject));
            editTextSubject.requestFocus();
            return;
        }
        if(teacher.trim().equals("")){
            editTextTeacher.setError(getString(R.string.errorEmptyTeacher));
            editTextTeacher.requestFocus();
            return;
        }
        if(classroom.trim().equals("")){
            editTextClassroom.setError(getString(R.string.errorEmptyClassroom));
            editTextClassroom.requestFocus();
            return;
        }

        String toast = "Los datos son válidos";
        //Dar formato al texto
        SpannableStringBuilder biggerText = new SpannableStringBuilder(toast);
        biggerText.setSpan(new RelativeSizeSpan(1.5f), 0, toast.length(), 0);

        Toast mensajeValidacion = Toast.makeText(getApplicationContext(),biggerText,Toast.LENGTH_LONG);
        mensajeValidacion.setGravity(Gravity.CENTER, 0, 0);
        mensajeValidacion.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);

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

        return super.onOptionsItemSelected(item);
    }

    public void showHelp() {
        new AlertDialog.Builder(FormListSubjects.this)
                .setTitle(getResources().getString(R.string.helpTitle))
                .setMessage(getResources().getString(R.string.helpFormListSubjects))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }
}