package com.blue.ruben.blue;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by ruben on 10/02/2017.
 */

public class Tab2BuscarViaje extends Fragment implements AdapterView.OnItemClickListener{
    private static final String TAG = Tab2BuscarViaje.class.getSimpleName();
    public static final String VIAJE = "viaje";
    private Viaje viaje;
    private ArrayList<Viaje> viajes = new ArrayList<>();
    private ListView listView;
    private DatabaseReference dbRefRoot;
    private FirebaseUser user;
    private TextView tvNoBuscar;
    String conductor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.buscarviaje, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        viajes=new ArrayList<>();
        if (user != null) {
            conductor = user.getDisplayName();
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.listViewBuscar);
        tvNoBuscar =(TextView) view.findViewById(R.id.tvNoBuscar);
        dbRefRoot = FirebaseDatabase.getInstance().getReference("Viaje");
        Log.d(TAG,"FUNCIONA 2");
        ChildEventListener cv = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                viaje = dataSnapshot.getValue(Viaje.class);
                Log.d("Viaje",viaje.getOrigen());
                if (viaje.getOrigen() != null) {
                    tvNoBuscar.setVisibility(View.INVISIBLE);
                }
                viajes.add(viaje);
                Log.d("ARRAYLIST VIAJES", String.valueOf(viajes.size()));
                ListAdapterBuscar adapter = new ListAdapterBuscar(getContext(),viajes);
                listView.setAdapter(adapter);
                Log.d(TAG,"FUNCIONA");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                viaje = dataSnapshot.getValue(Viaje.class);
                int posicion = cogerPosicion(viajes, viaje);
                if (posicion != -1) {
                    viajes.remove(posicion);
                    viajes.add(posicion, viaje);
                    ListAdapterBuscar adapter = new ListAdapterBuscar(getContext(),viajes);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        dbRefRoot.addChildEventListener(cv);
        listView.setOnItemClickListener(Tab2BuscarViaje.this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent();
        intent.putExtra(VIAJE, viajes.get(i));
        Log.d("NOMBRE USUARIO:CLICK ",conductor);
        Log.d("NOMBRE USUARIO:CLICK ",viaje.getConductor());

        Viaje viaje =viajes.get(i);
        if(Integer.parseInt(viaje.getPlazas())<1){
            Toast.makeText(getContext(), getString(R.string.toastViajeCompleto), Toast.LENGTH_LONG).show();
        }else if(viaje.getConductor().trim().equals(conductor.trim())){
            Toast.makeText(getContext(), getString(R.string.toastPropioViaje), Toast.LENGTH_LONG).show();
        }else{
            Log.d(TAG,viaje.getDestino()+" "+viaje.getPlazas()+" "+viaje.getPrecio());
            createLoginDialogo(intent);
        }
    }

    public void createLoginDialogo(Intent intent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.reserva_viaje, null);

        builder.setView(v);

        final TextView tvDestino = (TextView) v.findViewById(R.id.tvDestinoReserva);
        final TextView tvConductorReserva = (TextView) v.findViewById(R.id.tvConductorReserva);
        final TextView tvOrigen = (TextView) v.findViewById(R.id.tvOrigenReserva);
        final TextView tvPrecio = (TextView)v.findViewById(R.id.tvPrecioReserva);
        final TextView tvNumeroPlazas=(TextView)v.findViewById(R.id.tvNumeroPlazasReserva);
        final TextView tvFecha=(TextView) v.findViewById(R.id.tvFechaReserva);
        final TextView tvHora=(TextView) v.findViewById(R.id.tvHoraReserva);
        final EditText etNumPlazasReservar=(EditText) v.findViewById(R.id.etNumPlazasReservar);

        viaje = intent.getParcelableExtra(Tab2BuscarViaje.VIAJE);
        Log.d(TAG,viaje.getDestino()+" "+viaje.getPlazas()+" "+viaje.getPrecio());
        tvConductorReserva.setText(viaje.getConductor());
        tvDestino.setText(viaje.getDestino());
        tvOrigen.setText(viaje.getOrigen());
        tvPrecio.setText(viaje.getPrecio()+"€");
        tvNumeroPlazas.setText(viaje.getPlazas()+"");
        tvFecha.setText(viaje.getFecha());
        tvHora.setText(viaje.getHora());
        Log.d(TAG,viaje.getPrecio()+" "+viaje.getPlazas());

        final String plazas = tvNumeroPlazas.getText().toString();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference myRef = database.getInstance().getReference();


        builder.setPositiveButton(getString(R.string.btnDiaReservar),new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String key = myRef.child("Reserva").push().getKey();
                String resultado;
                if(etNumPlazasReservar.getText().toString().equals("")){
                    Toast.makeText(getContext(), getString(R.string.toastMenosPlazas), Toast.LENGTH_LONG).show();
                }else if (Integer.parseInt(etNumPlazasReservar.getText().toString())>Integer.parseInt(plazas)){
                    Toast.makeText(getContext(), getString(R.string.toastMuchasPlazas), Toast.LENGTH_LONG).show();
                }else if(Integer.parseInt(etNumPlazasReservar.getText().toString())<1){
                    Toast.makeText(getContext(), getString(R.string.toastMenosPlazas), Toast.LENGTH_LONG).show();
                }else{
                    Reserva reserva = new Reserva();
                    resultado = Integer.toString(Integer.parseInt(viaje.getPlazas()) - Integer.parseInt(etNumPlazasReservar.getText().toString()));
                    viaje.setPlazas(resultado);
                    myRef.child("Viaje").child(viaje.getKey()).setValue(viaje);
                    String uid=null;
                    if (user != null) {
                        uid = user.getUid();
                    }
                    viaje.setUid(uid);
                    viaje.setKeyReserva(key);

                    //rellenamos el objeto reserva
                    reserva.setOrigen(viaje.getOrigen());
                    reserva.setDestino(viaje.getDestino());
                    reserva.setFecha(viaje.getFecha());
                    reserva.setHora(viaje.getHora());
                    reserva.setPrecio(viaje.getPrecio());
                    reserva.setPlazasReservadas(Integer.parseInt(etNumPlazasReservar.getText().toString()));
                    reserva.setKeyViaje(viaje.getKey());
                    reserva.setKeyReserva(viaje.getKeyReserva());
                    reserva.setUid(viaje.getUid());
                    reserva.setConductor(viaje.getConductor());

                    myRef.child("Reservas").child(key).setValue(reserva);
                }
            }
        });
        builder.setNegativeButton(getString(R.string.btnDiaCancelar),new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }
    //método para coger la posicion
    public int cogerPosicion(ArrayList<Viaje> array, Viaje data) {
        int pos = -1;
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).getKey().equals(data.getKey())) {
                pos = i;
            }
        }
        return pos;
    }
}
