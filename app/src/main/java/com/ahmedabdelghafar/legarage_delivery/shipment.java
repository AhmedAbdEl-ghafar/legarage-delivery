package com.ahmedabdelghafar.legarage_delivery;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ahmedabdelghafar.legarage_delivery.Download_data.download_complete;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class shipment extends Activity implements download_complete {
    public ListView listView;
    public ArrayList<Countries> countries = new ArrayList<Countries>();
    public ListAdapter_shipment adapter;
    public ProgressDialog pd ;
    String dr_no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment);



        pd = new ProgressDialog(shipment.this);
        pd.setMessage("لا حول ولا قوة الا بالله");
        pd.setIndeterminate(true);
        pd.setProgress(0);
        pd.show();

        listView = (ListView) findViewById(R.id.ListView_shipment);
        adapter = new ListAdapter_shipment(this);
        listView.setAdapter(adapter);

        SharedPreferences sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);

        Download_data download_data = new Download_data((Download_data.download_complete) this);
        download_data.download_data_from_link("http://197.51.253.242:1000/api/shipment/"+sharedPreferences.getString("runner_code", "0")+","+getIntent().getStringExtra("dr_no"));

        //Toast.makeText(this, "Welcome " + getIntent().getStringExtra("dr_no") + " In Le Garage System", Toast.LENGTH_LONG).show();

        //dr_no = getIntent().getStringExtra("dr_no");


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                int itemPosition = position ;

                String itemValue = (String) listView.getItemAtPosition(position);
                // String itemText = (String) listView.getSelectedItem("category_id"));

              String selected = ((TextView) view.findViewById(R.id.tracking_no)).getText().toString();
                //Toast.makeText(getApplicationContext(),"Position :" + itemPosition + " ListItem : " + selected , Toast.LENGTH_LONG).show();

               // Toast.makeText(this, "Welcome " +  + " In Le Garage System", Toast.LENGTH_LONG).show();


                Intent i = new Intent(shipment.this, event_shipment.class);
                i.putExtra("dr_no",getIntent().getStringExtra("dr_no"));
                i.putExtra("tracking_no",selected.toString());
                finish();
                //i.putExtra("G_Code",selected);
                startActivity(i);



           }


        }
        );
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(shipment.this, daily_report.class);
        //i.putExtra("dr_no",getIntent().getStringExtra("dr_no"));
        finish();
        startActivity(i);
    }
    public void get_data(String data)
    {

        try {
            JSONArray data_array=new JSONArray(data);

            for (int i = 0 ; i < data_array.length() ; i++)
            {
                JSONObject obj=new JSONObject(data_array.get(i).toString());

                Countries add=new Countries();
                add.id_id = obj.getString("id_id");
                add.seller_name = obj.getString("seller_name");
                add.phone1 = obj.getString("phone1");
                add.phone2 = obj.getString("phone2");
                add.governorate_name = obj.getString("governorate_name");
                add.area_name = obj.getString("area_name");
                add.address = obj.getString("address");
                add.country_name = obj.getString("country_name");
                add.cod = obj.getString("cod");
                add.price = obj.getString("price");
                add.total = obj.getString("total");


                countries.add(add);

            }

            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        pd.dismiss();


    }


}
