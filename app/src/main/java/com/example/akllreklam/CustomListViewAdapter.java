package com.example.akllreklam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListViewAdapter extends ArrayAdapter<Firmalar> {

    private final LayoutInflater inflater;
    private final Context context;
    private ViewHolder holder;
    private final ArrayList<Firmalar> firmalar;

    public CustomListViewAdapter(Context context, ArrayList<Firmalar> firmalar) {
        super(context, 0, firmalar);
        this.context = context;
        this.firmalar = firmalar;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return firmalar.size();
    }

    @Override
    public Firmalar getItem(int position) {
        return firmalar.get(position);
    }

    @Override
    public long getItemId(int position) {
        return firmalar.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.fir_layout, null);

            holder = new ViewHolder();
            //holder.firmaImage = (ImageView) convertView.findViewById(R.id.imageView);
            holder.firmaAdiLabel = (TextView) convertView.findViewById(R.id.firmaAdi);
            holder.firmaicerikLabel = (TextView) convertView.findViewById(R.id.texticerik);
            holder.firmaKategoriLabel=(TextView)convertView.findViewById(R.id.textkategori) ;
            convertView.setTag(holder);

        }
        else{
            //Get viewholder we already created
            holder = (ViewHolder)convertView.getTag();
        }

        Firmalar firma = firmalar.get(position);
        if(firma != null){
           // holder.fi.setImageResource(person.getPhotoId());
            holder.firmaAdiLabel.setText(firma.getFirma_adi());
            holder.firmaicerikLabel.setText(firma.getFirma_kampanya_icerik());
            holder.firmaKategoriLabel.setText(firma.getKagegori());

        }
        return convertView;
    }

    //View Holder Pattern for better performance
    private static class ViewHolder {
        TextView firmaAdiLabel;
        TextView firmaicerikLabel;
        TextView firmaKategoriLabel;
        ImageView firmaImage;

    }
}