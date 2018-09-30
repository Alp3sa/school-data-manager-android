package com.gestordedatos.gestordedatos;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    Activity contexto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        contexto=this;

        final EditText editTextNombreUsuario = (EditText) findViewById(R.id.editTextNombreUsuario);
        final EditText editTextNombre = (EditText) findViewById(R.id.editTextNombre);
        final EditText editTextPrimerApellido = (EditText) findViewById(R.id.editTextPrimerApellido);
        final EditText editTextSegundoApellido = (EditText) findViewById(R.id.editTextSegundoApellido);
        final EditText editTextDNI = (EditText) findViewById(R.id.editTextDNI);
        final EditText editTextCodigoActivacion = (EditText) findViewById(R.id.editTextCodigoActivacion);
        final EditText editTextContraseña = (EditText) findViewById(R.id.editTextContraseña);
        final EditText editTextRepetirContraseña = (EditText) findViewById(R.id.editTextRepetirContraseña);
        final CheckBox hombre = (CheckBox) findViewById(R.id.checkBoxHombre);
        final CheckBox mujer = (CheckBox) findViewById(R.id.checkBoxMujer);
        final CheckBox alumnado = (CheckBox) findViewById(R.id.checkBoxAlumnado);
        final CheckBox profesorado = (CheckBox) findViewById(R.id.checkBoxProfesorado);

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
                Intent intent = new Intent(contexto,MainActivity.class);
                startActivity(intent);
            }
        });

        Button buttonCrearCuenta = (Button) findViewById(R.id.buttonCrearCuenta);

        buttonCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String nombreUsuario = editTextNombreUsuario.getText().toString();
                String nombre = editTextNombre.getText().toString();
                String primerApellido = editTextPrimerApellido.getText().toString();
                String segundoApellido = editTextSegundoApellido.getText().toString();
                String dni = editTextDNI.getText().toString();
                String codigoActivacion = editTextCodigoActivacion.getText().toString();
                String contraseña = editTextContraseña.getText().toString();
                String repetirContraseña = editTextRepetirContraseña.getText().toString();

                int numMensajesError = 0;

                if(nombreUsuario.trim().equals("") || nombreUsuario.trim().length()<4){
                    numMensajesError++;
                }
                if(nombre.trim().equals("")){
                    numMensajesError++;
                }
                if(primerApellido.trim().equals("")){
                    numMensajesError++;
                }
                if(segundoApellido.trim().equals("")){
                    numMensajesError++;
                }
                if(dni.trim().equals("") || dni.trim().length()!=9){
                    numMensajesError++;
                }
                if(contraseña.trim().equals("") || contraseña.trim().length()<8){
                    numMensajesError++;
                }
                if(repetirContraseña.trim().equals("") || !contraseña.equals(repetirContraseña)){
                    numMensajesError++;
                }
                if(codigoActivacion.trim().equals("") || codigoActivacion.trim().length()!=4){
                    numMensajesError++;
                }

                boolean checkNombre=false;
                boolean checkNombreUsuario=false;
                boolean checkPrimerApellido=false;
                boolean checkSegundoApellido=false;
                boolean checkDNI=false;
                boolean checkGenero=false;
                boolean checkTipoMiembro=false;
                boolean checkCodigoActivacion=false;
                boolean checkContraseña=false;
                boolean checkRepetirContraseña=false;

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

                if(repetirContraseña.trim().equals("") || !repetirContraseña.equals(repetirContraseña)){
                    if(repetirContraseña.trim().equals("")){toast = "Introduzca de nuevo la contraseña";}
                    else if(!repetirContraseña.equals(repetirContraseña)){toast = "Las contraseñas introducidas no coinciden";}
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

                if(checkNombre==true && checkNombreUsuario==true && checkPrimerApellido==true && checkSegundoApellido==true && checkDNI==true
                        && checkGenero==true && checkTipoMiembro==true && checkCodigoActivacion==true
                        && checkContraseña==true && checkRepetirContraseña==true) {
                    Intent intent = new Intent(contexto, Menu.class);
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
