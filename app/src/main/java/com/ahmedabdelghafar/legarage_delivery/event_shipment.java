package com.ahmedabdelghafar.legarage_delivery;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ahmedabdelghafar.legarage_delivery.Download_data.download_complete;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

//import java.lang.ref.ReferenceQueue;

public class event_shipment extends Activity implements download_complete {
    public ArrayList<Countries> countries = new ArrayList<Countries>();
    public ProgressDialog pd ;
    public TextView textView1;
    public TextView textView2;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_shipment);

        //Toast.makeText(this, "Welcome " + getIntent().getStringExtra("tracking_no") + " In Le Garage System", Toast.LENGTH_LONG).show();


        pd = new ProgressDialog(event_shipment.this);
        pd.setMessage("لا حول ولا قوة الا بالله");
        pd.setIndeterminate(true);
        pd.setProgress(0);
        pd.show();

        sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        Download_data download_data = new Download_data((Download_data.download_complete) this);
        download_data.download_data_from_link("http://197.51.253.242:1000/api/event_shipment/"+sharedPreferences.getString("runner_code", "0")+","+getIntent().getStringExtra("dr_no")+","+getIntent().getStringExtra("tracking_no"));

    }

    public void updated_shipment(View view){

        NukeSSLCerts.nuke();

        String clinet_name = textView1.getText().toString();
        String[] clinet_s = clinet_name.split(" ");
        //Toast.makeText(this, clinet_s[0], Toast.LENGTH_SHORT).show();

        String sms = sharedPreferences.getString("sms", "Null");
        String sms_edit = sms.replaceAll("seller_name", clinet_s[0]);

        String utf8String ="";
        try {
            utf8String = URLEncoder.encode(sms_edit.toString(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //Toast.makeText(this, textView2.getText(), Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://smsmisr.com/api/send/?username=6F6WUAAK&password=6F6WUA&language=2&sender=Le%20Garage&mobile="+textView2.getText()+"&message="+utf8String;
     //String url = "http://www.google.com";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(event_shipment.this, textView2.getText(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(event_shipment.this, "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                //Log.e(TAG, "Error : " + error.getMessage());
            }
        });
        queue.add(stringRequest);

        Download_data download_data = new Download_data((Download_data.download_complete) this);
        download_data.download_data_from_link("http://197.51.253.242:1000/api/update_deliveryss/3000,"+ getIntent().getStringExtra("dr_no") + "," + getIntent().getStringExtra("tracking_no")+",1,0,NULL,"+sharedPreferences.getString("runner_code", "0"));

        Intent i = new Intent(event_shipment.this, shipment.class);
        i.putExtra("dr_no",getIntent().getStringExtra("dr_no"));
        finish();
        startActivity(i);

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(event_shipment.this, shipment.class);
        i.putExtra("dr_no",getIntent().getStringExtra("dr_no"));
        finish();
        startActivity(i);
    }

    @Override
    public void get_data(String data) {

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

                TextView textView = (TextView) findViewById(R.id.tracking_no_event);
                textView.setText(obj.getString("id_id"));

                textView1 = (TextView) findViewById(R.id.client_name_event);
                textView1.setText(obj.getString("seller_name"));

                textView2 = (TextView) findViewById(R.id.phone1_event);
                textView2.setText(obj.getString("phone1"));

                TextView textView3 = (TextView) findViewById(R.id.phone2_event);
                textView3.setText(obj.getString("phone2"));

                TextView textView4 = (TextView) findViewById(R.id.gov_event);
                textView4.setText(obj.getString("governorate_name"));

                TextView textView5 = (TextView) findViewById(R.id.areas_event);
                textView5.setText(obj.getString("area_name"));

                TextView textView6 = (TextView) findViewById(R.id.address_event);
                textView6.setText(obj.getString("address"));

                TextView textView7 = (TextView) findViewById(R.id.contry_event);
                textView7.setText(obj.getString("country_name"));

                //TextView textView8 = (TextView) findViewById(R.id.cod_event);
                //textView8.setText(obj.getString("cod"));

                //TextView textView9 = (TextView) findViewById(R.id.prices_event);
                //textView9.setText(obj.getString("price"));

                TextView textView10 = (TextView) findViewById(R.id.Total_event);
                textView10.setText(obj.getString("total"));

            }

           // adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        pd.dismiss();


    }
}
