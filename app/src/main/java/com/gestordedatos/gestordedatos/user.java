package com.gestordedatos.gestordedatos;

import android.os.Parcel;
import android.os.Parcelable;

public class user implements Parcelable{
    String nombreUsuario;
    String nombre;
    String primerApellido;
    String segundoApellido;
    String edad;
    String dni;
    String genero;
    String tipoDeMiembro;

    public user(String nombreUsuario,String nombre,String primerApellido,String segundoApellido,String edad,String dni,String genero,String tipoDeMiembro) {
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.edad = edad;
        this.dni = dni;
        this.genero = genero;
        this.tipoDeMiembro = tipoDeMiembro;
    }

    protected user(Parcel in) {
        nombreUsuario = in.readString();
    }

    public static final Creator<user> CREATOR = new Creator<user>() {
        @Override
        public user createFromParcel(Parcel in) {
            return new user(in);
        }

        @Override
        public user[] newArray(int size) {
            return new user[size];
        }
    };

    public String getNombreUsuario() {
        return nombreUsuario;
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
