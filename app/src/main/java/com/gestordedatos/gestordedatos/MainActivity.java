package com.gestordedatos.gestordedatos;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;


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

        textView = (TextView) findViewById(R.id.textViewIniciarSesion);

        editTextNombreUsuario = (EditText) findViewById(R.id.editTextNombreUsuario);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        Button buttonSignup = (Button) findViewById(R.id.botonSignup);
        Button buttonLogin = (Button) findViewById(R.id.botonLogin);

        contexto=this;

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
                editTextNombreUsuario.setError(getString(R.string.errorNombreUsuarioVacio));
            }
            else if (nombreUsuario.trim().length() < 4) {
                editTextNombreUsuario.setError(getString(R.string.errorNombreUsuarioInvalido));
            }
            editTextNombreUsuario.requestFocus();
            return;
        }
        if(password.trim().equals("") || password.trim().length()<8){
            if(password.trim().equals("")){
                editTextPassword.setError(getString(R.string.errorContraseñaVacia));
            }
            else if(password.trim().length()<8){
                editTextPassword.setError(getString(R.string.errorContraseñaInvalida));
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
        user user = new user(nombreUsuario,null,null,null,null,null,null,null);
        Intent intent = new Intent(contexto, MainMenu.class);
        intent.putExtra("user", user);


        startActivity(intent);

        //Añadir en destino
        //user user = getIntent().getExtras().getParcelable("user");
        //...o si pasamos la variable nombreUsuario en vez de un objeto
        //Intent intent = this.getIntent();
        //String nombreUsuario = intent.getExtras().getString("nombreUsuario");
    }
}
