package com.blue.ruben.blue;

import android.support.v7.app.AppCompatActivity;

public class ModificarViajes extends AppCompatActivity {
    /*private static final String TAG = ModificarViajes.class.getSimpleName();
    public static final String KEY = "key";
    Button btnModificar;
    EditText etOrigen, etDestino, etPrecio, etNumPlazas;
    static EditText etFecha, etHora;
    String uid;
    private DatabaseReference myRef;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_viajes);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        myRef = database.getInstance().getReference();
        btnPublicar = (Button) view.findViewById(R.id.btnPublicar);
        etOrigen = (EditText) view.findViewById(R.id.etOrigen);
        etDestino = (EditText) view.findViewById(R.id.etDestino);
        etFecha = (EditText) view.findViewById(R.id.etFecha);
        etHora = (EditText) view.findViewById(R.id.etHora);
        etPrecio = (EditText) view.findViewById(R.id.etPrecio);
        etNumPlazas = (EditText) view.findViewById(R.id.etNumPlazas);
        etFecha.setKeyListener(null);
        etFecha.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                new Tab1PublicarViaje.DateDialog().show(getFragmentManager(),"DatePickerInFull");
            }
        });
        etHora.setKeyListener(null);
        etHora.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                new Tab1PublicarViaje.TimeDialog().show(getFragmentManager(),"TimePickerInFull");
            }
        });
        if (user != null) {
            uid = user.getUid();

            // Name, email address, and profile photo Url
            //String name = profile.getDisplayName();
        }
        btnPublicar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String origen = etOrigen.getText().toString();
                String destino= etDestino.getText().toString();
                String fecha = etFecha.getText().toString();
                String hora = etHora.getText().toString();
                int numPlazas = Integer.parseInt(etNumPlazas.getText().toString());
                String precio = etPrecio.getText().toString();
                String key = myRef.child("Viaje").push().getKey();
                if(origen.equals("")){
                    Toast.makeText(getActivity(), getString(R.string.toastOrigenVacio), Toast.LENGTH_LONG).show();
                }else if(destino.equals("")){
                    Toast.makeText(getActivity(), getString(R.string.toastDestinoVacio), Toast.LENGTH_SHORT).show();
                }else if(fecha.equals("")){
                    Toast.makeText(getActivity(), getString(R.string.toastFechaVacia), Toast.LENGTH_SHORT).show();
                }else if(numPlazas==0){
                    Toast.makeText(getActivity(), getString(R.string.toastNumPlazasVacia), Toast.LENGTH_SHORT).show();
                }else if(precio.equals("")){
                    Toast.makeText(getActivity(), getString(R.string.toastPrecioVacia), Toast.LENGTH_SHORT).show();
                }
                else {
                    publicarViaje(origen, destino,fecha,hora,numPlazas ,precio , key);
                }
            }
        });
    }*/
}
