package com.blue.ruben.blue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MiPerfil extends AppCompatActivity {
    private TextView tvUsuarioPerfil, tvNombreApePerfil, tvEmailPerfil, tvEdadPerfil, tvAniosCanertPerfil;
    private DatabaseReference myRef;
    private FirebaseUser user;
    private Usuario usuario = new Usuario();
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        tvUsuarioPerfil = (TextView) findViewById(R.id.tvUsuarioPerfil);
        tvNombreApePerfil = (TextView) findViewById(R.id.tvNombreApePerfil);
        tvEmailPerfil = (TextView) findViewById(R.id.tvEmailPerfil);
        tvEdadPerfil = (TextView) findViewById(R.id.tvEdadPerfil);
        tvAniosCanertPerfil = (TextView) findViewById(R.id.tvAniosCanertPerfil);
        if (user != null) {
            email = user.getEmail();
        }
        database.getReference("Usuarios").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    usuario = dataSnapshot1.getValue(Usuario.class);
                    Log.d("USUARIO", dataSnapshot1.getKey() + "");
                    tvUsuarioPerfil.setText(usuario.getUsuario());
                    tvNombreApePerfil.setText(usuario.getNombre()+" "+usuario.getApellidos());
                    tvEmailPerfil.setText(usuario.getEmail());
                    tvEdadPerfil.setText(usuario.getEdad()+" años");
                    tvAniosCanertPerfil.setText(usuario.getAñoscarnet()+" años de carnet de conducir");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
