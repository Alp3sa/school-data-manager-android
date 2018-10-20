package com.gestordedatos.gestordedatos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

public class SignUpPasswords extends AppCompatActivity {
    Activity contexto;
    user user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_passwords);

        contexto=this;

        //user = getIntent().getExtras().getParcelable("user");
        user = ((application) getApplicationContext()).user;

        final EditText editTextCodigoActivacion = (EditText) findViewById(R.id.editTextCodigoActivacion);
        final EditText editTextContraseña = (EditText) findViewById(R.id.editTextContraseña);
        final EditText editTextRepetirContraseña = (EditText) findViewById(R.id.editTextRepetirContraseña);

        Button buttonVolver = (Button) findViewById(R.id.buttonVolver);

        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent = new Intent(contexto,SignUpTypeMember.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        Button buttonCrearCuenta = (Button) findViewById(R.id.buttonCrearCuenta);

        buttonCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String codigoActivacion = editTextCodigoActivacion.getText().toString();
                String contraseña = editTextContraseña.getText().toString();
                String repetirContraseña = editTextRepetirContraseña.getText().toString();

                int numMensajesError = 0;

                boolean checkCodigoActivacion=false;
                boolean checkContraseña=false;
                boolean checkRepetirContraseña=false;

                int posicion = 40;
                String toast="";

                if(codigoActivacion.trim().equals("") || codigoActivacion.trim().length()!=4){
                    if(codigoActivacion.trim().equals("")){toast = getResources().getString(R.string.toastErrorEmptyActivationCode);}
                    else if(codigoActivacion.trim().length()!=4){toast = getResources().getString(R.string.toastErrorInvalidActivationCode);}
                    //Dar formato al texto
                    SpannableStringBuilder biggerText = new SpannableStringBuilder(toast);
                    biggerText.setSpan(new RelativeSizeSpan(1.5f), 0, toast.length(), 0);

                    Toast errorPassword = Toast.makeText(getApplicationContext(),biggerText,Toast.LENGTH_LONG);
                    errorPassword.setGravity(Gravity.BOTTOM, 0, posicion);
                    
                    errorPassword.show();

                    return;
                }
                else{
                    checkCodigoActivacion=true;
                }

                if(contraseña.trim().equals("") || contraseña.trim().length()<8){
                    if(contraseña.trim().equals("")){toast = getResources().getString(R.string.toastErrorEmptyNewPassword);}
                    else if(contraseña.trim().length()<8){toast = getResources().getString(R.string.toastErrorInvalidNewPassword);}
                    //Dar formato al texto
                    SpannableStringBuilder biggerText = new SpannableStringBuilder(toast);
                    biggerText.setSpan(new RelativeSizeSpan(1.5f), 0, toast.length(), 0);

                    Toast errorPassword = Toast.makeText(getApplicationContext(),biggerText,Toast.LENGTH_LONG);
                    errorPassword.setGravity(Gravity.BOTTOM, 0, posicion);
                    
                    errorPassword.show();

                    return;
                }
                else{
                    checkContraseña=true;
                }

                if(repetirContraseña.trim().equals("") || !repetirContraseña.equals(contraseña)){
                    if(repetirContraseña.trim().equals("")){toast = getResources().getString(R.string.toastErrorEmptyRepeatPassword);}
                    else if(!repetirContraseña.equals(contraseña)){toast = getResources().getString(R.string.toastErrorInvalidRepeatPassword);}
                    //Dar formato al texto
                    SpannableStringBuilder biggerText = new SpannableStringBuilder(toast);
                    biggerText.setSpan(new RelativeSizeSpan(1.5f), 0, toast.length(), 0);

                    Toast errorPassword = Toast.makeText(getApplicationContext(),biggerText,Toast.LENGTH_LONG);
                    errorPassword.setGravity(Gravity.BOTTOM, 0, posicion);
                    errorPassword.show();

                    return;
                }
                else{
                    checkRepetirContraseña=true;
                }

                if(checkCodigoActivacion==true && checkContraseña==true && checkRepetirContraseña==true) {
                    Intent intent = new Intent(contexto, MainMenu.class);
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
        new AlertDialog.Builder(SignUpPasswords.this)
                .setTitle(getResources().getString(R.string.helpTitle))
                .setMessage(getResources().getString(R.string.helpSignUpPassword))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }
}
