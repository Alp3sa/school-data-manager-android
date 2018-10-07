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

public class SignUpPersonalData extends AppCompatActivity {
    Activity contexto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_personal_data);

        contexto=this;

        final user user = getIntent().getExtras().getParcelable("user");

        final EditText editTextNombre = (EditText) findViewById(R.id.editTextNombre);
        final EditText editTextPrimerApellido = (EditText) findViewById(R.id.editTextPrimerApellido);
        final EditText editTextSegundoApellido = (EditText) findViewById(R.id.editTextSegundoApellido);
        final EditText editTextEdad = (EditText) findViewById(R.id.editTextEdad);
        final EditText editTextDNI = (EditText) findViewById(R.id.editTextDNI);

        Button buttonVolver = (Button) findViewById(R.id.buttonVolver);

        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent = new Intent(contexto,SignUpNick.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        Button buttonContinuar = (Button) findViewById(R.id.buttonContinuar);

        buttonContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String nombre = editTextNombre.getText().toString();
                String primerApellido = editTextPrimerApellido.getText().toString();
                String segundoApellido = editTextSegundoApellido.getText().toString();
                String edad = editTextEdad.getText().toString();
                String dni = editTextDNI.getText().toString();

                int numMensajesError = 0;

                if(nombre.trim().equals("")){
                    numMensajesError++;
                }
                if(primerApellido.trim().equals("")){
                    numMensajesError++;
                }
                if(segundoApellido.trim().equals("")){
                    numMensajesError++;
                }
                if(edad.trim().equals("")){
                    numMensajesError++;
                }
                if(dni.trim().equals("") || dni.trim().length()!=9){
                    numMensajesError++;
                }

                boolean checkNombre=false;
                boolean checkPrimerApellido=false;
                boolean checkSegundoApellido=false;
                boolean checkEdad=false;
                boolean checkDNI=false;

                int posicion = 40;
                String toast="";

                if(nombre.trim().equals("")){
                    if(nombre.trim().equals("")){toast = "Introduzca su nombre";}
                    //Dar formato al texto
                    SpannableStringBuilder biggerText = new SpannableStringBuilder(toast);
                    biggerText.setSpan(new RelativeSizeSpan(1.5f), 0, toast.length(), 0);

                    Toast errorNombre = Toast.makeText(getApplicationContext(),biggerText,Toast.LENGTH_LONG);
                    errorNombre.setGravity(Gravity.BOTTOM, 0, posicion);
                    
                    errorNombre.show();

                    return;
                }
                else{
                    checkNombre=true;
                }

                if(primerApellido.trim().equals("")){
                    if(primerApellido.trim().equals("")){toast = "Introduzca su primer apellido";}
                    //Dar formato al texto
                    SpannableStringBuilder biggerText = new SpannableStringBuilder(toast);
                    biggerText.setSpan(new RelativeSizeSpan(1.5f), 0, toast.length(), 0);

                    Toast errorPrimerApellido = Toast.makeText(getApplicationContext(),biggerText,Toast.LENGTH_LONG);
                    errorPrimerApellido.setGravity(Gravity.BOTTOM, 0, posicion);
                    
                    errorPrimerApellido.show();

                    return;
                }
                else{
                    checkPrimerApellido=true;
                }

                if(segundoApellido.trim().equals("")){
                    if(segundoApellido.trim().equals("")){toast = "Introduzca su segundo apellido";}
                    //Dar formato al texto
                    SpannableStringBuilder biggerText = new SpannableStringBuilder(toast);
                    biggerText.setSpan(new RelativeSizeSpan(1.5f), 0, toast.length(), 0);

                    Toast errorSegundoApellido = Toast.makeText(getApplicationContext(),biggerText,Toast.LENGTH_LONG);
                    errorSegundoApellido.setGravity(Gravity.BOTTOM, 0, posicion);

                    errorSegundoApellido.show();

                    return;
                }
                else{
                    checkSegundoApellido=true;
                }

                if (edad.trim().equals("")) {
                    toast = "Introduzca su edad";
                    //Dar formato al texto
                    SpannableStringBuilder biggerText = new SpannableStringBuilder(toast);
                    biggerText.setSpan(new RelativeSizeSpan(1.5f), 0, toast.length(), 0);

                    Toast errorEdad = Toast.makeText(getApplicationContext(), biggerText, Toast.LENGTH_LONG);
                    errorEdad.setGravity(Gravity.BOTTOM, 0, posicion);

                    errorEdad.show();

                    return;
                }

                try {
                    int edadUsuario=Integer.parseInt(edad);
                    if (edadUsuario<0 || edadUsuario>99) {
                        toast = "La edad debe estar comprendida entre 0 y 99";

                        //Dar formato al texto
                        SpannableStringBuilder biggerText = new SpannableStringBuilder(toast);
                        biggerText.setSpan(new RelativeSizeSpan(1.5f), 0, toast.length(), 0);

                        Toast errorEdad = Toast.makeText(getApplicationContext(), biggerText, Toast.LENGTH_LONG);
                        errorEdad.setGravity(Gravity.BOTTOM, 0, posicion);

                        errorEdad.show();

                        return;
                    } else {
                        checkEdad = true;
                    }
                }
                catch(Exception e){
                    toast = "La edad introducida no es un número entero";
                    //Dar formato al texto
                    SpannableStringBuilder biggerText = new SpannableStringBuilder(toast);
                    biggerText.setSpan(new RelativeSizeSpan(1.5f), 0, toast.length(), 0);

                    Toast errorEdad = Toast.makeText(getApplicationContext(), biggerText, Toast.LENGTH_LONG);
                    errorEdad.setGravity(Gravity.BOTTOM, 0, posicion);

                    errorEdad.show();
                    return;
                }

                if(dni.trim().equals("") || dni.trim().length()!=9 || !validarNIF(dni)){
                    if(dni.trim().equals("")){toast = "Introduzca su DNI";}
                    else if(dni.trim().length()!=9){toast = "El DNI está compuesto por 9 caracteres";}
                    else{toast = "El DNI introducido no es válido";}

                    //Dar formato al texto
                    SpannableStringBuilder biggerText = new SpannableStringBuilder(toast);
                    biggerText.setSpan(new RelativeSizeSpan(1.5f), 0, toast.length(), 0);

                    Toast errorDNI = Toast.makeText(getApplicationContext(),biggerText,Toast.LENGTH_LONG);
                    errorDNI.setGravity(Gravity.BOTTOM, 0, posicion);
                    
                    errorDNI.show();

                    return;
                }
                else{
                    checkDNI=true;
                }

                if(checkNombre==true && checkPrimerApellido==true && checkSegundoApellido==true && checkEdad==true && checkDNI==true) {
                    user.setNombre(nombre);
                    user.setPrimerApellido(primerApellido);
                    user.setSegundoApellido(segundoApellido);
                    user.setEdad(edad);
                    user.setDni(dni);
                    Intent intent = new Intent(contexto, SignUpGender.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
            }
        });
    }

    protected static boolean validarNIF(String nif){
        try {
            boolean nifValido = false;
            String caracter1 = nif.substring(0, 1);
            String nifValidado = null;
            String nifValidadoAux = null;

            if (caracter1.matches("[0-9]")) {
                String cadenaNumeros = nif.substring(0, 8);
                int numero = Integer.parseInt(cadenaNumeros);
                int resto = numero % 23;
                String letra = null;
                switch (resto) {
                    case 0:
                        letra = "t";
                        break;
                    case 1:
                        letra = "r";
                        break;
                    case 2:
                        letra = "w";
                        break;
                    case 3:
                        letra = "a";
                        break;
                    case 4:
                        letra = "g";
                        break;
                    case 5:
                        letra = "m";
                        break;
                    case 6:
                        letra = "y";
                        break;
                    case 7:
                        letra = "f";
                        break;
                    case 8:
                        letra = "p";
                        break;
                    case 9:
                        letra = "d";
                        break;
                    case 10:
                        letra = "x";
                        break;
                    case 11:
                        letra = "b";
                        break;
                    case 12:
                        letra = "n";
                        break;
                    case 13:
                        letra = "j";
                        break;
                    case 14:
                        letra = "z";
                        break;
                    case 15:
                        letra = "s";
                        break;
                    case 16:
                        letra = "q";
                        break;
                    case 17:
                        letra = "v";
                        break;
                    case 18:
                        letra = "h";
                        break;
                    case 19:
                        letra = "l";
                        break;
                    case 20:
                        letra = "c";
                        break;
                    case 21:
                        letra = "k";
                        break;
                    case 22:
                        letra = "e";
                        break;
                }

                nifValidado = numero + letra;

                if (nifValidado.equalsIgnoreCase(nif)) {
                    nifValido = true;
                } else {
                    nifValido = false;
                }
            } else if (caracter1.matches("[xyzXYZ]")) {
                int digito1 = 0;
                if (caracter1.equalsIgnoreCase("x")) {
                    digito1 = 0;
                } else if (caracter1.equalsIgnoreCase("y")) {
                    digito1 = 1;
                } else if (caracter1.equalsIgnoreCase("z")) {
                    digito1 = 2;
                }

                String cadenaNumeros = digito1 + nif.substring(1, 8);
                int numero = Integer.parseInt(cadenaNumeros);
                int resto = numero % 23;
                String letra = null;
                switch (resto) {
                    case 0:
                        letra = "t";
                        break;
                    case 1:
                        letra = "r";
                        break;
                    case 2:
                        letra = "w";
                        break;
                    case 3:
                        letra = "a";
                        break;
                    case 4:
                        letra = "g";
                        break;
                    case 5:
                        letra = "m";
                        break;
                    case 6:
                        letra = "y";
                        break;
                    case 7:
                        letra = "f";
                        break;
                    case 8:
                        letra = "p";
                        break;
                    case 9:
                        letra = "d";
                        break;
                    case 10:
                        letra = "x";
                        break;
                    case 11:
                        letra = "b";
                        break;
                    case 12:
                        letra = "n";
                        break;
                    case 13:
                        letra = "j";
                        break;
                    case 14:
                        letra = "z";
                        break;
                    case 15:
                        letra = "s";
                        break;
                    case 16:
                        letra = "q";
                        break;
                    case 17:
                        letra = "v";
                        break;
                    case 18:
                        letra = "h";
                        break;
                    case 19:
                        letra = "l";
                        break;
                    case 20:
                        letra = "c";
                        break;
                    case 21:
                        letra = "k";
                        break;
                    case 22:
                        letra = "e";
                        break;
                }

                nifValidado = nif.substring(0, 8) + letra;

                if (nifValidado.equalsIgnoreCase(nif)) {
                    nifValido = true;
                } else {
                    nifValido = false;
                }
            } else if (caracter1.matches("[abcdefghjnpqrsuvwABCDEFGHJNPQRSUVW]")) {
                String cadenaNumeros = nif.substring(1, 8);

                int numero = Integer.parseInt(cadenaNumeros);
                int digito1 = numero / 1000000;
                int digito2 = (numero % 1000000) / 100000;
                int digito3 = (numero % 100000) / 10000;
                int digito4 = (numero % 10000) / 1000;
                int digito5 = (numero % 1000) / 100;
                int digito6 = (numero % 100) / 10;
                int digito7 = (numero % 10);

                int A = digito2 + digito4 + digito6;
                int B = ((digito1 * 2) / 10 + (digito1 * 2) % 10) + ((digito3 * 2) / 10 + (digito3 * 2) % 10) + ((digito5 * 2) / 10 + (digito5 * 2) % 10) + ((digito7 * 2) / 10 + (digito7 * 2) % 10);
                int C = A + B;
                int E = C % 10;
                int D = 0;

                if (E > 0) {
                    D = 10 - E;
                } else if (E == 0) {
                    D = 0;
                }

                String letra = null;
                int digito = 0;

                if (caracter1.matches("[pqswPQSW]") || nif.substring(0, 2).equals("00")) {
                    switch (D) {
                        case 1:
                            letra = "a";
                            break;
                        case 2:
                            letra = "b";
                            break;
                        case 3:
                            letra = "c";
                            break;
                        case 4:
                            letra = "d";
                            break;
                        case 5:
                            letra = "e";
                            break;
                        case 6:
                            letra = "f";
                            break;
                        case 7:
                            letra = "g";
                            break;
                        case 8:
                            letra = "h";
                            break;
                        case 9:
                            letra = "i";
                            break;
                        case 0:
                            letra = "j";
                            break;
                    }
                } else if (caracter1.matches("[abehABEH]")) {
                    digito = D;
                    letra = Integer.toString(digito);
                } else {
                    switch (D) {
                        case 1:
                            letra = "a";
                            break;
                        case 2:
                            letra = "b";
                            break;
                        case 3:
                            letra = "c";
                            break;
                        case 4:
                            letra = "d";
                            break;
                        case 5:
                            letra = "e";
                            break;
                        case 6:
                            letra = "f";
                            break;
                        case 7:
                            letra = "g";
                            break;
                        case 8:
                            letra = "h";
                            break;
                        case 9:
                            letra = "i";
                            break;
                        case 0:
                            letra = "j";
                            break;
                    }
                    digito = D;
                    nifValidadoAux = nif.substring(0, 8) + digito;
                }

                nifValidado = nif.substring(0, 8) + letra;

                if (nifValidado.equalsIgnoreCase(nif)) {
                    nifValido = true;
                } else if (nifValidado.equalsIgnoreCase(nif) || nifValidadoAux.equalsIgnoreCase(nif)) {
                    nifValido = true;
                } else {
                    nifValido = false;
                }
            }
            System.out.println("check3");
            return nifValido;
        }
        catch(Exception e){
            return false;
        }
    }
}
