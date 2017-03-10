package com.blue.ruben.blue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

/**
 * Created by Ruben on 01/03/2017.
 */

public class MisViajes extends AppCompatActivity {
    private FirebaseUser user;
    private ListView listViewMisViajes;
    private TextView tvNoMisViajes;
    private Viaje viaje;
    String uid;
    private ArrayList<Viaje> viajes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.misviajes);
        viajes = new ArrayList<>();
        tvNoMisViajes = (TextView) findViewById(R.id.tvNoMisViajes);
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid=user.getUid();
        Query q = FirebaseDatabase.getInstance().getReference("Viaje").orderByChild("uid").equalTo(uid);
        listViewMisViajes = (ListView) findViewById(R.id.listViewMisViajes);
        ChildEventListener cv = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                viaje = dataSnapshot.getValue(Viaje.class);
                if (viaje.getOrigen() != null) {
                    tvNoMisViajes.setVisibility(View.INVISIBLE);
                }
                viajes.add(viaje);
                ListAdapterMisViajes adapter2 = new ListAdapterMisViajes(MisViajes.this,viajes);
                listViewMisViajes.setAdapter(adapter2);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                viaje = dataSnapshot.getValue(Viaje.class);
                viajes.add(viaje);
                ListAdapterMisViajes adapter2 = new ListAdapterMisViajes(MisViajes.this,viajes);
                listViewMisViajes.setAdapter(adapter2);
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
        q.addChildEventListener(cv);
    }
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
