package com.blue.ruben.blue;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ruben on 05/03/2017.
 */

public class Usuario implements Parcelable {
    private String keyUsu;
    private String usuario;
    private String nombre;
    private String apellidos;
    private String email;
    private String edad;
    private String añoscarnet;

    public Usuario(String keyUsu, String usuario, String nombre, String apellidos, String email, String edad, String añoscarnet) {
        this.keyUsu = keyUsu;
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.edad = edad;
        this.añoscarnet = añoscarnet;
    }

    public Usuario() {
    }

    protected Usuario(Parcel in) {
        keyUsu = in.readString();
        usuario = in.readString();
        nombre = in.readString();
        apellidos = in.readString();
        email = in.readString();
        edad = in.readString();
        añoscarnet = in.readString();
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    public String getKeyUsu() {
        return keyUsu;
    }

    public void setKeyUsu(String keyUsu) {
        this.keyUsu = keyUsu;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getAñoscarnet() {
        return añoscarnet;
    }

    public void setAñoscarnet(String añoscarnet) {
        this.añoscarnet = añoscarnet;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(keyUsu);
        parcel.writeString(usuario);
        parcel.writeString(nombre);
        parcel.writeString(apellidos);
        parcel.writeString(email);
        parcel.writeString(edad);
        parcel.writeString(añoscarnet);
    }
}
