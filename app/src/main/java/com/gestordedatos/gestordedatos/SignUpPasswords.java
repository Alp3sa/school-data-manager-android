package com.gestordedatos.gestordedatos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpPasswords extends AppCompatActivity {
    Activity contexto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_passwords);

        contexto=this;

        final user user = getIntent().getExtras().getParcelable("user");

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
                    if(codigoActivacion.trim().equals("")){toast = "Introduzca el código de activación";}
                    else if(codigoActivacion.trim().length()!=4){toast = "El código de activación está compuesto por 4 caracteres";}
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
                    if(contraseña.trim().equals("")){toast = "Introduzca su contraseña";}
                    else if(contraseña.trim().length()<8){toast = "Introduzca una contraseña de al menos 8 caracteres";}
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
                    if(repetirContraseña.trim().equals("")){toast = "Introduzca de nuevo la contraseña";}
                    else if(!repetirContraseña.equals(contraseña)){toast = "Las contraseñas introducidas no coinciden";}
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
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
            }
        });
    }
}
