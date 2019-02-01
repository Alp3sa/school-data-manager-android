package com.gestordedatos.gestordedatos.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.gestordedatos.gestordedatos.Globals;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Parcelable{
    int ID;
    int validacion;
    String nombreUsuario;
    String nombre;
    String primerApellido;
    String segundoApellido;
    String edad;
    String dni;
    String genero;
    String tipoDeMiembro;
    String password;

    public User() {
        this.ID = Globals.SIN_VALOR_INT;
        this.validacion = Globals.SIN_VALOR_INT;
        this.nombreUsuario = Globals.SIN_VALOR_STRING;
        this.nombre = Globals.SIN_VALOR_STRING;
        this.primerApellido = Globals.SIN_VALOR_STRING;
        this.segundoApellido = Globals.SIN_VALOR_STRING;
        this.edad = Globals.SIN_VALOR_STRING;
        this.dni = Globals.SIN_VALOR_STRING;
        this.genero = Globals.SIN_VALOR_STRING;
        this.tipoDeMiembro = Globals.SIN_VALOR_STRING;
        this.password = Globals.SIN_VALOR_STRING;
    }

    public User(String nombreUsuario, String nombre, String primerApellido, String segundoApellido, String edad, String dni, String genero, String tipoDeMiembro) {
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.edad = edad;
        this.dni = dni;
        this.genero = genero;
        this.tipoDeMiembro = tipoDeMiembro;
    }

    public User(int validacion, String nombreUsuario, String nombre, String primerApellido, String segundoApellido, String edad, String dni, String genero, String tipoDeMiembro, String password) {
        this.validacion = validacion;
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.edad = edad;
        this.dni = dni;
        this.genero = genero;
        this.tipoDeMiembro = tipoDeMiembro;
        this.password = password;
    }

    public User(String nombreUsuario, String nombre, String primerApellido, String segundoApellido, String edad, String dni, String genero, String tipoDeMiembro, String password) {
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.edad = edad;
        this.dni = dni;
        this.genero = genero;
        this.tipoDeMiembro = tipoDeMiembro;
        this.password = password;
    }

    public User(int validacion, String nombreUsuario, String password) {
        this.validacion = validacion;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
    }

    public User(int ID, int validacion, String nombreUsuario, String nombre, String primerApellido, String segundoApellido, String edad, String dni, String genero, String tipoDeMiembro, String password) {
        this.ID = ID;
        this.validacion = validacion;
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.edad = edad;
        this.dni = dni;
        this.genero = genero;
        this.tipoDeMiembro = tipoDeMiembro;
        this.password = password;
    }

    protected User(Parcel in) {
        nombreUsuario = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getValidacion() {
        return validacion;
    }

    public void setValidacion(final int validacion) {
        this.validacion = validacion;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public String getEdad() {
        return edad;
    }

    public String getDni() {
        return dni;
    }

    public String getGenero() {
        return genero;
    }

    public String getTipoDeMiembro() {
        return tipoDeMiembro;
    }

    public void setNombreUsuario(final String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public void setPrimerApellido(final String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public void setSegundoApellido(final String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public void setEdad(final String edad) {
        this.edad = edad;
    }

    public void setDni(final String dni) {
        this.dni = dni;
    }

    public void setGenero(final String genero) {
        this.genero = genero;
    }

    public void setTipoDeMiembro(final String tipoDeMiembro) {
        this.tipoDeMiembro = tipoDeMiembro;
    }

    public int getID() {
        return ID;
    }

    public void setID(final int ID) {
        this.ID = ID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        parcel.writeString(nombreUsuario);
    }

    public String toJSON(){
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("ID", this.getID());
            jsonObject.put("nombreUsuario", this.getNombreUsuario());
            jsonObject.put("nombre", this.getNombre());
            jsonObject.put("primerApellido", this.getPrimerApellido());
            jsonObject.put("segundoApellido", this.getSegundoApellido());
            jsonObject.put("edad", this.getEdad());
            jsonObject.put("dni", this.getDni());
            jsonObject.put("genero", this.getGenero());
            jsonObject.put("tipoDeMiembro", this.getTipoDeMiembro());
            jsonObject.put("password", this.getPassword());

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
