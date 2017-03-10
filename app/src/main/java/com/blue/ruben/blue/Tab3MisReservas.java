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
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by ruben on 10/02/2017.
 */

public class Tab3MisReservas extends Fragment implements AdapterView.OnItemClickListener{
    private FirebaseUser user;
    private static final String TAG = Tab3MisReservas.class.getSimpleName();
    public static final String RESERVA = "reserva";
    private ListView listViewMisReservas;
    private Viaje viaje;
    private Reserva reserva;
    TextView tvNoMisViajes1;
    String uid;
    private ArrayList<Reserva> reservas = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.misreservas, container, false);
        reservas=new ArrayList<>();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvNoMisViajes1 = (TextView)view.findViewById(R.id.tvNoMisViajes1);
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid=user.getUid();
        listViewMisReservas = (ListView) view.findViewById(R.id.listViewMisReservas);
        Log.d(TAG,"UID USUARIO  EL BUENO "+uid);
        Query q1 = FirebaseDatabase.getInstance().getReference("Reservas").orderByChild("uid").equalTo(uid);
        ChildEventListener cv = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                reserva = dataSnapshot.getValue(Reserva.class);
                if (reserva.getKeyReserva().equals("")) {
                }else{
                    tvNoMisViajes1.setVisibility(View.INVISIBLE);
                }
                reservas.add(reserva);
                ListAdapterMisReservas adapter2 = new ListAdapterMisReservas(getContext(),reservas);
                listViewMisReservas.setAdapter(adapter2);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                reserva = dataSnapshot.getValue(Reserva.class);
                reservas.add(reserva);
                ListAdapterMisReservas adapter2 = new ListAdapterMisReservas(getContext(),reservas);
                listViewMisReservas.setAdapter(adapter2);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                reserva = dataSnapshot.getValue(Reserva.class);
                int pos = cogerPosicion(reservas, reserva);
                if (pos !=-1) {
                    reservas.remove(pos);
                    ListAdapterMisReservas adapter = new ListAdapterMisReservas(getContext(), reservas);
                    listViewMisReservas.setAdapter(adapter);
                    if (reservas.size()==0){
                        tvNoMisViajes1.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        q1.addChildEventListener(cv);
        listViewMisReservas.setOnItemClickListener(Tab3MisReservas.this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent();
        intent.putExtra(RESERVA, reservas.get(i));
        createCancelarReservaDialogo(intent);
    }
    public void createCancelarReservaDialogo(Intent intent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference myRef = database.getInstance().getReference();

        reserva = intent.getParcelableExtra(Tab3MisReservas.RESERVA);

        builder.setTitle(getString(R.string.tituloCancelarReserva)).setPositiveButton(getString(R.string.btnDiaSI),new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final int plazasSumar = reserva.getPlazasReservadas();
                final String keyViaje = reserva.getKeyViaje();
                myRef.child("Reservas").child(reserva.getKeyReserva()).removeValue();
                database.getReference("Viaje").orderByChild("key").equalTo(keyViaje).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                            viaje = dataSnapshot1.getValue(Viaje.class);
                            Log.d("PLAZAS DEL VIAJEkd", dataSnapshot1.getKey() + "");
                            Log.d("PLAZAS DEL VIAJE", String.valueOf(viaje.getPlazas()) + "");
                            Log.d("PLAZAS DEL VIAJE", String.valueOf(viaje.getDestino()) + "");
                            viaje.setPlazas(String.valueOf(Integer.parseInt(viaje.getPlazas()) + plazasSumar));
                            myRef.child("Viaje").child(keyViaje).setValue(viaje);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        builder.setNegativeButton(getString(R.string.btnDiaNO),new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }
    //método para coger la posicion
    public int cogerPosicion(ArrayList<Reserva> array, Reserva data) {
        int pos = -1;
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).getKeyViaje().equals(data.getKeyViaje())) {
                pos = i;
            }
        }
        return pos;
    }
}
