package com.blue.ruben.blue;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ruben on 01/03/2017.
 */

public class Reserva implements Parcelable{
    private String origen;
    private String destino;
    private String fecha;
    private String hora;
    private String precio;
    private int plazasReservadas;
    private String keyViaje;
    private String keyReserva;
    private String uid;
    private String conductor;

    public Reserva(String origen, String destino, String fecha, String hora, String precio, int plazasReservadas, String keyViaje, String keyReserva, String uid, String conductor) {
        this.origen = origen;
        this.destino = destino;
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
        this.plazasReservadas = plazasReservadas;
        this.keyViaje = keyViaje;
        this.keyReserva = keyReserva;
        this.uid = uid;
        this.conductor = conductor;
    }

    public Reserva() {
    }

    protected Reserva(Parcel in) {
        origen = in.readString();
        destino = in.readString();
        fecha = in.readString();
        hora = in.readString();
        precio = in.readString();
        plazasReservadas = in.readInt();
        keyViaje = in.readString();
        keyReserva = in.readString();
        uid = in.readString();
        conductor = in.readString();
    }

    public static final Creator<Reserva> CREATOR = new Creator<Reserva>() {
        @Override
        public Reserva createFromParcel(Parcel in) {
            return new Reserva(in);
        }

        @Override
        public Reserva[] newArray(int size) {
            return new Reserva[size];
        }
    };

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public int getPlazasReservadas() {
        return plazasReservadas;
    }

    public void setPlazasReservadas(int plazasReservadas) {
        this.plazasReservadas = plazasReservadas;
    }

    public String getKeyViaje() {
        return keyViaje;
    }

    public void setKeyViaje(String keyViaje) {
        this.keyViaje = keyViaje;
    }

    public String getKeyReserva() {
        return keyReserva;
    }

    public void setKeyReserva(String keyReserva) {
        this.keyReserva = keyReserva;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getConductor() {
        return conductor;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(origen);
        parcel.writeString(destino);
        parcel.writeString(fecha);
        parcel.writeString(hora);
        parcel.writeString(precio);
        parcel.writeInt(plazasReservadas);
        parcel.writeString(keyViaje);
        parcel.writeString(keyReserva);
        parcel.writeString(uid);
        parcel.writeString(conductor);
    }
}
