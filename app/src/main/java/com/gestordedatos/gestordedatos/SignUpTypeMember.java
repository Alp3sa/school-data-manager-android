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

public class SignUpTypeMember extends AppCompatActivity {
    Activity contexto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_type_member);

        contexto=this;

        final user user = getIntent().getExtras().getParcelable("user");

        final CheckBox alumnado = (CheckBox) findViewById(R.id.checkBoxAlumnado);
        final CheckBox profesorado = (CheckBox) findViewById(R.id.checkBoxProfesorado);

        alumnado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton compoundButton, boolean b) {
                if(alumnado.isChecked() && profesorado.isChecked()){
                    profesorado.setChecked(false);
                }
            }
        });

        profesorado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton compoundButton, boolean b) {
                if(alumnado.isChecked() && profesorado.isChecked()){
                    alumnado.setChecked(false);
                }
            }
        });

        Button buttonVolver = (Button) findViewById(R.id.buttonVolver);

        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent = new Intent(contexto,SignUpGender.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        Button buttonContinuar = (Button) findViewById(R.id.buttonContinuar);

        buttonContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                int numMensajesError = 0;

                String tipoMiembro = "";
                boolean checkTipoMiembro=false;

                int posicion = 40;
                String toast="";

                if(alumnado.isChecked()){
                    tipoMiembro="alumno";
                    checkTipoMiembro=true;
                }
                else if(profesorado.isChecked()){
                    tipoMiembro="profesor";
                    checkTipoMiembro=true;
                }
                else{
                    toast = "Introduzca si es alumno o docente";

                    SpannableStringBuilder biggerText = new SpannableStringBuilder(toast);
                    biggerText.setSpan(new RelativeSizeSpan(1.5f), 0, toast.length(), 0);

                    Toast errorTipoMiembro = Toast.makeText(getApplicationContext(),biggerText,Toast.LENGTH_LONG);
                    errorTipoMiembro.setGravity(Gravity.BOTTOM, 0, posicion);

                    errorTipoMiembro.show();
                }

                if(checkTipoMiembro==true) {
                    Intent intent = new Intent(contexto, SignUpPasswords.class);
                    user.setTipoDeMiembro(tipoMiembro);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
            }
        });
    }
}
