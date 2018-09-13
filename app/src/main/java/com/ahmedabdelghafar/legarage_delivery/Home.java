package com.ahmedabdelghafar.legarage_delivery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity implements View.OnClickListener{
    private ProgressDialog pDialog;
    public ProgressDialog pd;
    Button dr_button;
    Button signoutBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dr_button = (Button) findViewById(R.id.dr_button);
        signoutBTN = (Button) findViewById(R.id.signoutBTN);

        dr_button.setOnClickListener(this);
        signoutBTN.setOnClickListener(this);

    }



    public void onBackPressed() {
//        Intent i = new Intent(Home.this, login.class);
//        startActivity(i);
        finish();
    }

    public void onClick(View view) {

        //ProgressDialog pd = new ProgressDialog(login.this);
        //pd.setMessage("لا حول ولا قوة الا بالله");
        //pd.show();
switch (view.getId()) {
    case R.id.dr_button:
        pd = new ProgressDialog(Home.this);
        pd.setMessage("لا حول ولا قوة الا بالله");
        pd.show();

        Intent is = new Intent(Home.this, daily_report.class);
        finish();
        startActivity(is);
        break;

    case R.id.signoutBTN:
        SharedPreferences sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("runner_code", "0");
        editor.commit();

        Intent i = new Intent(Home.this, login.class);
        startActivity(i);
        finish();

        break;

}




    }
}

