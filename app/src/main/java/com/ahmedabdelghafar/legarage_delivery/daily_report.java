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

public class daily_report extends Activity implements download_complete {
    public ListView listView;
    public ArrayList<Countries> countries = new ArrayList<Countries>();
    public ListAdapter_Daily_report adapter;
    public ProgressDialog pd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_report);

        pd = new ProgressDialog(daily_report.this);
        pd.setMessage("لا حول ولا قوة إلا بالله");
        pd.setIndeterminate(true);
        pd.setProgress(0);
        pd.show();

        listView = (ListView) findViewById(R.id.ListView_dr);
        adapter = new ListAdapter_Daily_report(this);
        listView.setAdapter(adapter);


        SharedPreferences sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);


        Download_data download_data = new Download_data((Download_data.download_complete) this);
        download_data.download_data_from_link("http://197.51.253.242:1000/api/dr/"+sharedPreferences.getString("runner_code", "0"));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                int itemPosition = position ;

                String dr_no = (String) listView.getItemAtPosition(position);
                // String itemText = (String) listView.getSelectedItem("category_id"));

                String selected = ((TextView) view.findViewById(R.id.dr_no)).getText().toString();

                //Toast.makeText(getApplicationContext(),"Position :" + itemPosition + " ListItem : " + selected , Toast.LENGTH_LONG).show();

                Intent i = new Intent(daily_report.this, shipment.class);
                i.putExtra("dr_no",selected);
                finish();
                startActivity(i);



            }


        });

    }
    public void onBackPressed() {
        Intent i = new Intent(daily_report.this, Home.class);
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
                add.dr_delivery_no = obj.getString("dr_delivery_no");
                //add.G_Descriptions = obj.getString("G_Descriptions");

                countries.add(add);

            }

            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        pd.dismiss();


    }

}
