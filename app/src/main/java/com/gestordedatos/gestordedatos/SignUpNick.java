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

public class SignUpNick extends AppCompatActivity {
    Activity contexto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_nick);

        contexto=this;

        final EditText editTextNombreUsuario = (EditText) findViewById(R.id.editTextNombreUsuario);

        Button buttonVolver = (Button) findViewById(R.id.buttonVolver);

        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent = new Intent(contexto,MainActivity.class);
                startActivity(intent);
            }
        });

        Button buttonContinuar = (Button) findViewById(R.id.buttonContinuar);

        buttonContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String nombreUsuario = editTextNombreUsuario.getText().toString();

                int numMensajesError = 0;

                if(nombreUsuario.trim().equals("") || nombreUsuario.trim().length()<4){
                    numMensajesError++;
                }

                boolean checkNombreUsuario=false;

                int posicion = 40;
                String toast="";

                if(nombreUsuario.trim().equals("") || nombreUsuario.trim().length()<4){
                    if(nombreUsuario.trim().equals("")){toast = "Introduzca un nombre de usuario";}
                    else if(nombreUsuario.trim().length()<4){toast = "Introduzca un nombre de usuario de al menos 4 caracteres";}
                    //Dar formato al texto
                    SpannableStringBuilder biggerText = new SpannableStringBuilder(toast);
                    biggerText.setSpan(new RelativeSizeSpan(1.5f), 0, toast.length(), 0);

                    Toast errorNombreUsuario = Toast.makeText(getApplicationContext(),biggerText,Toast.LENGTH_LONG);
                    errorNombreUsuario.setGravity(Gravity.BOTTOM, 0, posicion);
                    
                    errorNombreUsuario.show();

                    return;
                }
                else{
                    checkNombreUsuario=true;
                }

                if(checkNombreUsuario==true) {
                    user user = new user(nombreUsuario,null,null,null,null,null,null,null);
                    Intent intent = new Intent(contexto, SignUpPersonalData.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
            }
        });
    }
}
