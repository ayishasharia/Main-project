package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ip extends AppCompatActivity {
    Button b1;
    EditText e1;
    String ip;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ip);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b1=findViewById(R.id.button12);
        e1=findViewById(R.id.editTextTextPersonName4);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ip=e1.getText().toString();

                SharedPreferences.Editor ed = sh.edit();
                ed.putString("ip", ip);
                ed.commit();


                Intent i=new Intent(getApplicationContext(),login.class);
                startActivity(i);


            }
        });
    }
}