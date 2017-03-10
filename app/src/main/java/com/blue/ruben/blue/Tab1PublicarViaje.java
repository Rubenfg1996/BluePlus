package com.blue.ruben.blue;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

/**
 * Created by ruben on 10/02/2017.
 */

public class Tab1PublicarViaje extends Fragment {
    private static final String TAG = Tab2BuscarViaje.class.getSimpleName();
    public static final String KEY = "key";
    Button btnPublicar, btnCambiar;
    EditText etPrecio, etNumPlazas;
    AutoCompleteTextView etOrigen, etDestino;
    static EditText etFecha, etHora;
    String uid, origen, destino, username;
    private DatabaseReference myRef;
    private FirebaseUser user;
    public static String[] CAMPUS = {
            "Campus Alcobendas",
            "Campus Villaviciosa de Odón",
            "Campus La Orotava",
            "Campus Valencia"
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.publicarviaje, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        myRef = database.getInstance().getReference();
        btnPublicar = (Button) view.findViewById(R.id.btnPublicar);
        btnCambiar = (Button) view.findViewById(R.id.btnCambiar);
        etOrigen = (AutoCompleteTextView) view.findViewById(R.id.etOrigen);
        etDestino = (AutoCompleteTextView) view.findViewById(R.id.etDestino);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, CAMPUS);
        etOrigen.setAdapter(adaptador);
        etDestino.setAdapter(adaptador);
        etFecha = (EditText) view.findViewById(R.id.etFecha);
        etHora = (EditText) view.findViewById(R.id.etHora);
        etPrecio = (EditText) view.findViewById(R.id.etPrecio);
        etNumPlazas = (EditText) view.findViewById(R.id.etNumPlazas);
        etFecha.setKeyListener(null);
        etFecha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (etFecha.isFocused()) {
                    new DateDialog().show(getFragmentManager(),"DatePickerInFull");
                }
            }
        });
        etHora.setKeyListener(null);
        etHora.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (etHora.isFocused()) {
                    new TimeDialog().show(getFragmentManager(),"TimePickerInFull");
                }
            }
        });
        if (user != null) {
            uid = user.getUid();
            username = user.getDisplayName();
        }
        btnPublicar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String origen = etOrigen.getText().toString();
                String destino= etDestino.getText().toString();
                String fecha = etFecha.getText().toString();
                String hora = etHora.getText().toString();
                String numPlazas = etNumPlazas.getText().toString();
                String precio = etPrecio.getText().toString();
                String key = myRef.child("Viaje").push().getKey();
                if(origen.equals("")){
                    Toast.makeText(getActivity(), getString(R.string.toastOrigenVacio), Toast.LENGTH_LONG).show();
                }else if(destino.equals("")){
                    Toast.makeText(getActivity(), getString(R.string.toastDestinoVacio), Toast.LENGTH_SHORT).show();
                }else if(fecha.equals("")){
                    Toast.makeText(getActivity(), getString(R.string.toastFechaVacia), Toast.LENGTH_SHORT).show();
                }else if(hora.equals("")){
                    Toast.makeText(getActivity(), getString(R.string.toastHoraVacia), Toast.LENGTH_SHORT).show();
                }else if(numPlazas.equals("")){
                    Toast.makeText(getActivity(), getString(R.string.toastNumPlazasVacia), Toast.LENGTH_SHORT).show();
                }else if(Integer.parseInt(numPlazas)>6){
                    Toast.makeText(getActivity(), getString(R.string.toastNumPlazasMayor), Toast.LENGTH_SHORT).show();
                }else if(precio.equals("")) {
                    Toast.makeText(getActivity(), getString(R.string.toastPrecioVacia), Toast.LENGTH_SHORT).show();
                }
                else {
                    publicarViaje(origen, destino,fecha,hora,numPlazas ,precio , key);
                    etOrigen.clearFocus();
                    etDestino.clearFocus();
                    etFecha.clearFocus();
                    etHora.clearFocus();
                    etNumPlazas.clearFocus();
                    etPrecio.clearFocus();
                }
            }
        });

        btnCambiar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                origen = etOrigen.getText().toString();
                destino = etDestino.getText().toString();
                Button iv = (Button) view.findViewById(R.id.btnCambiar);
                Animation rotation = AnimationUtils.loadAnimation(getContext(), R.anim.rotation);
                rotation.setRepeatCount(Animation.RESTART);
                iv.startAnimation(rotation);
                int contador = 0;
                if (contador ==0){
                    etOrigen.setText(destino);
                    etDestino.setText(origen);
                    contador++;

                }
                else if (contador>0){
                    etDestino.setText(origen);
                    etOrigen.setText(destino);


                }

            }
        });


    }

    private void publicarViaje(String origen, String destino,String fecha,String hora,String numPlazas,String precio ,String key){
        Viaje viaje = new Viaje();
        viaje.setOrigen(origen);
        etOrigen.setText("");
        viaje.setDestino(destino);
        etDestino.setText("");
        viaje.setFecha(fecha);
        etFecha.setText("");
        viaje.setHora(hora);
        etHora.setText("");
        viaje.setPlazas(numPlazas);
        etNumPlazas.setText("");
        viaje.setPrecio(precio);
        etPrecio.setText("");
        viaje.setKey(key);
        viaje.setKeyReserva("");
        viaje.setUid(uid);
        viaje.setConductor(username);
        Log.d(TAG,viaje.getUid());
        myRef.child("Viaje").child(key).setValue(viaje);
        Toast.makeText(getContext(), getString(R.string.viajePublicado), Toast.LENGTH_LONG).show();
    }

    public static class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Obtener fecha actual
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            // Retornar en nueva instancia del dialogo selector de fecha
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            if(month<10){
                etFecha.setText(day+"/0"+(month +1) +"/"+year);
            }else{
                etFecha.setText(day+"/"+(month +1) +"/"+year);
            }
        }
    }
    public static class TimeDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Iniciar con el tiempo actual
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Retornar en nueva instancia del dialogo selector de tiempo
            return new TimePickerDialog(getActivity(),this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if(minute<10){
                etHora.setText(hourOfDay+":0"+minute);
            }else{
                etHora.setText(hourOfDay+":"+minute);
            }
        }
    }


}
