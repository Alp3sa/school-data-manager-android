package com.gestordedatos.gestordedatos.pojos;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable{
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        parcel.writeString(nombreUsuario);
    }
}
