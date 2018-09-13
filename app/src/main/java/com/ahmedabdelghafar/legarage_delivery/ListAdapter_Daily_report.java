package com.ahmedabdelghafar.legarage_delivery;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by orcl on 20/06/2017.
 */

public class ListAdapter_Daily_report extends BaseAdapter {
    daily_report daily_reports;


    ListAdapter_Daily_report(daily_report daily_reports) { this.daily_reports = daily_reports;}

    @Override
    public int getCount() {
        return daily_reports.countries.size();
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
        TextView dr_delivery_no;
       // TextView G_Descriptions;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListAdapter_Daily_report.ViewHolderItem holder = new ListAdapter_Daily_report.ViewHolderItem();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) daily_reports.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cell_daily_report, null);

            holder.dr_delivery_no = (TextView) convertView.findViewById(R.id.dr_no);
           // holder.G_Descriptions = (TextView) convertView.findViewById(R.id.G_Descriptions);


            convertView.setTag(holder);
        } else {
            holder = (ListAdapter_Daily_report.ViewHolderItem) convertView.getTag();
        }


        holder.dr_delivery_no.setText(this.daily_reports.countries.get(position).dr_delivery_no);
        //holder.G_Descriptions.setText(this.daily_reports.countries.get(position).G_Descriptions);


        return convertView;
    }

}