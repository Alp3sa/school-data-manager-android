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

public class FormListClassrooms extends AppCompatActivity {
    Context contexto;
    EditText editTextClassroom;
    String classroom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_list_classrooms);

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
        editTextClassroom = (EditText) findViewById(R.id.editTextClassroom);

        editTextClassroom.setError(null);

        classroom = editTextClassroom.getText().toString();

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
