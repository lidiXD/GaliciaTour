package com.example.navigationdrawer;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;


public class ArrayAdapterLocalidades_ListView extends ArrayAdapter<QueverDondeComer> {

    public interface OnButtonClickListener {
        void onQueVerClick(int position);
        void onDondeComerClick(int position);
    }
    private OnButtonClickListener buttonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.buttonClickListener = listener;
    }
    Activity activity;
    int idlayout;
    List <QueverDondeComer> lugares_galicia;

    public ArrayAdapterLocalidades_ListView(@NonNull Context context, int resource, @NonNull List<QueverDondeComer> lugares_galicia) {
        super(context, resource, lugares_galicia);
        activity= (Activity) context;
        idlayout=resource;
        this.lugares_galicia=lugares_galicia;
    }

    static class Ref{
        TextView nombreLocalidad;
        ImageButton que_ver;
        ImageButton donde_comer;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ArrayAdapterLocalidades_ListView.Ref ref;
        View layout;
        if(convertView==null){
            layout=activity.getLayoutInflater().inflate(idlayout,null);
            TextView nombreLocalidad=layout.findViewById(R.id.lugar_galicia);
            ImageButton que_ver = layout.findViewById(R.id.que_ver);
            ImageButton donde_comer = layout.findViewById(R.id.donde_comer);

            ref=new ArrayAdapterLocalidades_ListView.Ref();
            ref.nombreLocalidad=nombreLocalidad;
            ref.que_ver=que_ver;
            ref.donde_comer=donde_comer;

            layout.setTag(ref);
        }
        else{
            layout=convertView;
            ref= (ArrayAdapterLocalidades_ListView.Ref) layout.getTag();
        }
        QueverDondeComer lugar_galicia=lugares_galicia.get(position);
        ref.nombreLocalidad.setText(lugar_galicia.getNombreLocalidad());

        ref.que_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonClickListener != null) {
                    buttonClickListener.onQueVerClick(position);
                }
            }
        });

        ref.donde_comer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonClickListener != null) {
                    buttonClickListener.onDondeComerClick(position);
                }
            }
        });

        return layout;
    }
}
