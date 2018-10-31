package com.gestordedatos.gestordedatos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.gestordedatos.gestordedatos.classrooms.ListClassrooms;
import com.gestordedatos.gestordedatos.pojos.User;
import com.gestordedatos.gestordedatos.subjects.ListSubjects;


public class MainActivity extends AppCompatActivity {
    Activity contexto;

    TextView textView;

    EditText editTextNombreUsuario;
    EditText editTextPassword;

    String nombreUsuario;
    String password;
    ImageView imagenFondo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contexto=this;
        //BEGIN TEST - SAVE TIME TO SKIP THE LOGIN SYSTEM
        //Intent intent = new Intent(contexto,MainMenu.class);
        //startActivity(intent);
        //END TEST

        textView = (TextView) findViewById(R.id.textViewIniciarSesion);

        editTextNombreUsuario = (EditText) findViewById(R.id.editTextNombreUsuario);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        Button buttonSignup = (Button) findViewById(R.id.botonSignup);
        Button buttonLogin = (Button) findViewById(R.id.botonLogin);

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent = new Intent(contexto,SignUpNick.class);
                startActivity(intent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                validar();
                /*Alternativa de validación usando Toast

                String nombreUsuario = editTextNombreUsuario.getText().toString();
                String password = editTextPassword.getText().toString();

                int numMensajesError = 0;


                if(nombreUsuario.trim().equals("") || nombreUsuario.trim().length()<4){
                    numMensajesError++;
                }
                if(password.trim().equals("") || password.trim().length()<8){
                    numMensajesError++;
                }

                boolean checkNombre=false;
                boolean checkPassword=false;
                int posicion = 40;
                String toast="";
                if(password.trim().equals("") || password.trim().length()<8){
                    if(password.trim().equals("")){toast = "Introduzca su contraseña";}
                    else if(password.trim().length()<8){toast = "Contraseña inválida";}
                    //Dar formato al texto
                    SpannableStringBuilder biggerText = new SpannableStringBuilder(toast);
                    biggerText.setSpan(new RelativeSizeSpan(1.5f), 0, toast.length(), 0);

                    Toast errorPassword = Toast.makeText(getApplicationContext(),biggerText,Toast.LENGTH_LONG);
                    errorPassword.setGravity(Gravity.BOTTOM, 0, posicion);
                    posicion=posicion+180;
                    errorPassword.show();
                }
                else{
                    checkPassword=true;
                }

                if(nombreUsuario.trim().equals("") || nombreUsuario.trim().length()<4){
                    if(nombreUsuario.trim().equals("")){toast = "Introduzca su nombre de usuario";}
                    else if(nombreUsuario.trim().length()<4){toast = "Nombre inválido";}
                    //Dar formato al texto
                    SpannableStringBuilder biggerText = new SpannableStringBuilder(toast);
                    biggerText.setSpan(new RelativeSizeSpan(1.5f), 0, toast.length(), 0);

                    Toast errorNombreUsuario = Toast.makeText(getApplicationContext(),biggerText,Toast.LENGTH_LONG);
                    errorNombreUsuario.setGravity(Gravity.BOTTOM, 0, posicion);
                    errorNombreUsuario.show();
                }
                else {
                    checkNombre=true;
                }

                if(checkNombre==true && checkPassword==true){
                    Intent intent = new Intent(contexto, Menu.class);
                    intent.putExtra("nombreUsuario", nombreUsuario);
                    intent.putExtra("password", password);

                    connection con = new connection(contexto);
                    con.execute(nombreUsuario,password);
                    //startActivity(intent);


                    //Intent intent = this.getIntent();
                    //String nombreUsuario = intent.getExtras().getString("nombreUsuario");
                    //String password = intent.getExtras().getString("password");

                }
                */
            }
        });

        imagenFondo = (ImageView) findViewById(R.id.imagenFondo);
        RadioGroup opcionesFondoLogueo = (RadioGroup)findViewById(R.id.fondoPantallaLogueo);

        opcionesFondoLogueo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if (checkedId==R.id.radioButtonFondoBlanco){
                    imagenFondo.setColorFilter(getBaseContext().getResources().getColor(R.color.colorWhite));
                }
                else if (checkedId==R.id.radioButtonFondoOriginal){
                    imagenFondo.clearColorFilter();
                    imagenFondo.setImageResource(R.mipmap.ic_launcher_foreground);
                    imagenFondo.setScaleType(ImageView.ScaleType.FIT_XY);
                }
            }
        });
    }

    public void validar(){
        editTextNombreUsuario.setError(null);
        editTextPassword.setError(null);

        nombreUsuario = editTextNombreUsuario.getText().toString();
        password = editTextPassword.getText().toString();

        if(nombreUsuario.trim().equals("") || nombreUsuario.trim().length()<4){
            if (nombreUsuario.trim().equals("")) {
                editTextNombreUsuario.setError(getString(R.string.errorEmptyUsername));
            }
            else if (nombreUsuario.trim().length() < 4) {
                editTextNombreUsuario.setError(getString(R.string.errorInvalidUsername));
            }
            editTextNombreUsuario.requestFocus();
            return;
        }
        if(password.trim().equals("") || password.trim().length()<8){
            if(password.trim().equals("")){
                editTextPassword.setError(getString(R.string.errorEmptyPassword));
            }
            else if(password.trim().length()<8){
                editTextPassword.setError(getString(R.string.errorInvalidPassword));
            }
            editTextPassword.requestFocus();
            return;
        }

        String toast = "Los datos son válidos";
        //Dar formato al texto
        SpannableStringBuilder biggerText = new SpannableStringBuilder(toast);
        biggerText.setSpan(new RelativeSizeSpan(1.5f), 0, toast.length(), 0);

        Toast mensajeValidacion = Toast.makeText(getApplicationContext(),biggerText,Toast.LENGTH_LONG);
        mensajeValidacion.setGravity(Gravity.CENTER, 0, 0);
        mensajeValidacion.show();
        /*Futura conexión con mysql

        connection con = new connection(contexto);
        con.execute(nombreUsuario,password);
        */
        //Ejemplo de pasar objeto entre actividades
        User User = new User(nombreUsuario,null,null,null,null,null,null,null);
        Intent intent = new Intent(contexto, MainMenu.class);
        //intent.putExtra("User", User);
        ((application) getApplicationContext()).User = User;

        startActivity(intent);

        //Añadir en destino
        //User User = getIntent().getExtras().getParcelable("User");
        //...o si pasamos la variable nombreUsuario en vez de un objeto
        //Intent intent = this.getIntent();
        //String nombreUsuario = intent.getExtras().getString("nombreUsuario");
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
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(getResources().getString(R.string.helpTitle))
                .setMessage(getResources().getString(R.string.helpLogin))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }
}
