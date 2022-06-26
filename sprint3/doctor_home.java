package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class doctor_home extends AppCompatActivity {

    Button b1,b2,b3,b4,b5;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setContentView(R.layout.doctor_home);
        b1=findViewById(R.id.button3);
        b2=findViewById(R.id.button4);
        b3=findViewById(R.id.button6);
        b4=findViewById(R.id.button7);
        b5=findViewById(R.id.button8);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),viewbooking.class);
                startActivity(i);

            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),viewbookaccept.class);
                startActivity(i);

            }
        });
    }
}