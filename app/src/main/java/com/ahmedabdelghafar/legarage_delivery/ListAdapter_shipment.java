package com.ahmedabdelghafar.legarage_delivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by orcl on 28/06/2017.
 */

public class ListAdapter_shipment extends BaseAdapter {
    shipment shipment_s;


    ListAdapter_shipment(shipment shipment_s) { this.shipment_s = shipment_s;}

    @Override
    public int getCount() {
        return shipment_s.countries.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;

    }

    static class ViewHolderItem {
        TextView client_name;
        TextView tracking_no;
        TextView phone1;
        TextView phone2;
        TextView contry;
        TextView gov;
        TextView areas;
        TextView address;
        TextView prices;
        TextView cod;
        TextView Total;



    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListAdapter_shipment.ViewHolderItem holder = new ListAdapter_shipment.ViewHolderItem();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) shipment_s.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cell_shipment, null);

            holder.client_name = (TextView) convertView.findViewById(R.id.client_name);
            holder.tracking_no = (TextView) convertView.findViewById(R.id.tracking_no);
            holder.phone1 = (TextView) convertView.findViewById(R.id.phone1);
            holder.phone2 = (TextView) convertView.findViewById(R.id.phone2);
            holder.contry = (TextView) convertView.findViewById(R.id.contry);
            holder.gov = (TextView) convertView.findViewById(R.id.gov);
            holder.areas = (TextView) convertView.findViewById(R.id.areas);
            holder.address = (TextView) convertView.findViewById(R.id.address);
            //holder.prices = (TextView) convertView.findViewById(R.id.prices);
            //holder.cod = (TextView) convertView.findViewById(R.id.cod);
            holder.Total = (TextView) convertView.findViewById(R.id.Total);



            convertView.setTag(holder);
        } else {
            holder = (ListAdapter_shipment.ViewHolderItem) convertView.getTag();
        }


        holder.client_name.setText(this.shipment_s.countries.get(position).seller_name);
        holder.tracking_no.setText(this.shipment_s.countries.get(position).id_id);
        holder.phone1.setText(this.shipment_s.countries.get(position).phone1);
        holder.phone2.setText(this.shipment_s.countries.get(position).phone2);
        holder.contry.setText(this.shipment_s.countries.get(position).country_name);
        holder.gov.setText(this.shipment_s.countries.get(position).governorate_name);
        holder.areas.setText(this.shipment_s.countries.get(position).area_name);
        holder.address.setText(this.shipment_s.countries.get(position).address);
        //holder.prices.setText(this.shipment_s.countries.get(position).price);
        //holder.cod.setText(this.shipment_s.countries.get(position).cod);
        holder.Total.setText(this.shipment_s.countries.get(position).total);



        return convertView;
    }

}