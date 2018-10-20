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

public class SignUpTypeMember extends AppCompatActivity {
    Activity contexto;
    user user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_type_member);

        contexto=this;

        //user = getIntent().getExtras().getParcelable("user");
        user = ((application) getApplicationContext()).user;

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
                    tipoMiembro="0";
                    checkTipoMiembro=true;
                }
                else if(profesorado.isChecked()){
                    tipoMiembro="1";
                    checkTipoMiembro=true;
                }
                else{
                    toast = getResources().getString(R.string.toastErrorEmptyMember);

                    SpannableStringBuilder biggerText = new SpannableStringBuilder(toast);
                    biggerText.setSpan(new RelativeSizeSpan(1.5f), 0, toast.length(), 0);

                    Toast errorTipoMiembro = Toast.makeText(getApplicationContext(),biggerText,Toast.LENGTH_LONG);
                    errorTipoMiembro.setGravity(Gravity.BOTTOM, 0, posicion);

                    errorTipoMiembro.show();
                }

                if(checkTipoMiembro==true) {
                    Intent intent = new Intent(contexto, SignUpPasswords.class);
                    user.setTipoDeMiembro(tipoMiembro);
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
        else*/
        if(id == R.id.action_help){
            showHelp();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showHelp() {
        new AlertDialog.Builder(SignUpTypeMember.this)
                .setTitle(getResources().getString(R.string.helpTitle))
                .setMessage(getResources().getString(R.string.helpSignUpTypeMember))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }
}
