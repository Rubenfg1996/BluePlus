package com.blue.ruben.blue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ruben on 28/01/2017.
 */

public class ListAdapterBuscar extends BaseAdapter{
    Context contexto;
    ArrayList<Viaje> viaje;

    public ListAdapterBuscar(Context contexto, ArrayList<Viaje> viaje) {
        this.contexto = contexto;
        this.viaje = viaje;
    }

    @Override
    public int getCount() {
        return viaje.size();
    }

    @Override
    public Object getItem(int i) {
        return viaje.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if(view==null){
            view = LayoutInflater.from(contexto).inflate(R.layout.item_buscar,null);
            holder = new ViewHolder();
            holder.tvConductor = (TextView) view.findViewById(R.id.tvConductor);
            holder.tvOrigen = (TextView) view.findViewById(R.id.tvOrigen);
            holder.tvDestino = (TextView) view.findViewById(R.id.tvDestino);
            holder.txtFecha = (TextView) view.findViewById(R.id.txtFecha);
            holder.tvHora = (TextView) view.findViewById(R.id.tvHora);
            holder.tvNumPlazas = (TextView) view.findViewById(R.id.tvNumPlazas);
            holder.txtPrecio = (TextView) view.findViewById(R.id.txtPrecio);

            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        holder.tvConductor.setText(viaje.get(i).getConductor());
        holder.tvOrigen.setText(viaje.get(i).getOrigen());
        holder.tvDestino.setText(viaje.get(i).getDestino());
        holder.txtFecha.setText(viaje.get(i).getFecha());
        holder.tvHora.setText(viaje.get(i).getHora());
        holder.tvNumPlazas.setText(viaje.get(i).getPlazas()+"");
        holder.txtPrecio.setText(viaje.get(i).getPrecio()+"€");


        return view;
    }
    private static class ViewHolder{
        TextView tvConductor;
        TextView tvOrigen;
        TextView tvDestino;
        TextView txtFecha;
        TextView tvHora;
        TextView txtPrecio;
        TextView tvNumPlazas;

    }
}
