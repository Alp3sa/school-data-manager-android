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
import android.widget.EditText;
import android.widget.Toast;

import com.gestordedatos.gestordedatos.pojos.User;

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
                    if(nombreUsuario.trim().equals("")){toast = getResources().getString(R.string.toastErrorEmptyUsername);}
                    else if(nombreUsuario.trim().length()<4){toast = getResources().getString(R.string.toastErrorShortUsername);}
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
                    User User = new User(nombreUsuario,null,null,null,null,null,null,null);
                    Intent intent = new Intent(contexto, SignUpPersonalData.class);
                    //intent.putExtra("User", User);
                    ((Globals) getApplicationContext()).User = User;
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
        new AlertDialog.Builder(SignUpNick.this)
                .setTitle(getResources().getString(R.string.helpTitle))
                .setMessage(getResources().getString(R.string.helpSignUpNick))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }
}