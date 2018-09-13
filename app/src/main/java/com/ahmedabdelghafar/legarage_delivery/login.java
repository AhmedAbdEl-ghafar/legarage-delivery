package com.ahmedabdelghafar.legarage_delivery;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ahmedabdelghafar.legarage_delivery.Download_data.download_complete;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class login extends Activity implements download_complete {
    public ArrayList<Countries> countries = new ArrayList<Countries>();
    EditText username, password,kk;
    Button button_login;
    private ProgressDialog pDialog;
    public ProgressDialog pd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);

        if (!sharedPreferences.getString("runner_code", "0").equals("0")) {
            Intent intent = new Intent(this, Home.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        }

        button_login = (Button)findViewById(R.id.signbtn);
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.viewbised);
        button_login.startAnimation(animation);
    }

    public void onClick(View view) {

        //ProgressDialog pd = new ProgressDialog(login.this);
        //pd.setMessage("لا حول ولا قوة الا بالله");
        //pd.show();

        pd = new ProgressDialog(login.this);
        pd.setMessage("لا حول ولا قوة الا بالله");
        //pd.setIndeterminate(true);
        //pd.setProgress(0);
        pd.show();

        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);

        Download_data download_data = new Download_data((Download_data.download_complete) this);
        download_data.download_data_from_link("http://197.51.253.242:1000/api/logginon/"+username.getText().toString()+","+password.getText().toString());


    }



    @Override
    public void get_data(String data)
    {

        try {
            JSONArray data_array=new JSONArray(data);

            for (int i = 0 ; i < data_array.length() ; i++)
            {
                JSONObject obj=new JSONObject(data_array.get(i).toString());

                Countries add=new Countries();

                add.user_name = obj.getString("user_name");
                add.user_pass = obj.getString("user_pass");
                add.runner_code = obj.getString("runner_code");
                add.sms = obj.getString("sms");

                countries.add(add);
                if (username.getText().toString().equalsIgnoreCase(obj.getString("user_name")) && (password.getText().toString().equalsIgnoreCase(obj.getString("user_pass")))){
                    //GithubUser user = response.body();
                    //Toast.makeText(this, "Got the user: " + user.name, Toast.LENGTH_LONG).show();
                    Toast.makeText(this, "Welcome " + username.getText().toString() + " In Le Garage System", Toast.LENGTH_LONG).show();

                    SharedPreferences sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("runner_code", obj.getString("runner_code"));
                    editor.putString("sms", obj.getString("sms"));
                    editor.commit();

//                    Toast.makeText(this, sharedPreferences.getString("runner_code", "0"), Toast.LENGTH_LONG).show();

                    Intent is = new Intent(login.this, Home.class);
                    startActivity(is);

                } else {
                    Toast.makeText(this, "You Not Working In Team Le Garage", Toast.LENGTH_LONG).show();
                }
            }

            //adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        pd.dismiss();
    }
}
