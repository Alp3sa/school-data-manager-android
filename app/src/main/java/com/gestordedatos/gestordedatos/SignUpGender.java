package com.gestordedatos.gestordedatos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpGender extends AppCompatActivity {
    Activity contexto;
    user user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_gender);

        contexto=this;

        //user = getIntent().getExtras().getParcelable("user");
        user = ((application) getApplicationContext()).user;

        final CheckBox hombre = (CheckBox) findViewById(R.id.checkBoxHombre);
        final CheckBox mujer = (CheckBox) findViewById(R.id.checkBoxMujer);

        hombre.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton compoundButton, boolean b) {
                if(hombre.isChecked() && mujer.isChecked()){
                    mujer.setChecked(false);
                }
            }
        });

        mujer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton compoundButton, boolean b) {
                if(hombre.isChecked() && mujer.isChecked()){
                    hombre.setChecked(false);
                }
            }
        });

        Button buttonVolver = (Button) findViewById(R.id.buttonVolver);

        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent = new Intent(contexto,SignUpPersonalData.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        Button buttonContinuar = (Button) findViewById(R.id.buttonContinuar);

        buttonContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                int numMensajesError = 0;

                String genero = "";
                boolean checkGenero=false;

                int posicion = 40;
                String toast="";

                if(hombre.isChecked()){
                    genero="1";
                    checkGenero=true;
                }
                else if(mujer.isChecked()){
                    genero="0";
                    checkGenero=true;
                }
                else{
                    toast = getResources().getString(R.string.toastErrorEmptyGender);

                    SpannableStringBuilder biggerText = new SpannableStringBuilder(toast);
                    biggerText.setSpan(new RelativeSizeSpan(1.5f), 0, toast.length(), 0);

                    Toast errorGenero = Toast.makeText(getApplicationContext(),biggerText,Toast.LENGTH_LONG);
                    errorGenero.setGravity(Gravity.BOTTOM, 0, posicion);

                    errorGenero.show();
                }

                if(checkGenero==true) {
                    Intent intent = new Intent(contexto, SignUpTypeMember.class);
                    user.setGenero(genero);
                    //intent.putExtra("user", user);
                    ((application) getApplicationContext()).user=user;
                    startActivity(intent);
                }
            }
        });
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
        new AlertDialog.Builder(SignUpGender.this)
                .setTitle(getResources().getString(R.string.helpTitle))
                .setMessage(getResources().getString(R.string.helpSignUpGender))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }
}
