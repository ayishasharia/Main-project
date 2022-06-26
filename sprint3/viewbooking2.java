package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class viewbooking2 extends AppCompatActivity {
    TextView e1,e2,e3;
    Button b1,b2;
    SharedPreferences sh;
    String name,gender,date,bid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewbooking2);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=findViewById(R.id.textView28);
        e2=findViewById(R.id.textView29);
        e3=findViewById(R.id.textView30);
        bid=getIntent().getStringExtra("booking_id");

        name=getIntent().getStringExtra("name");
        gender=getIntent().getStringExtra("gender");
        date=getIntent().getStringExtra("date");

        e1.setText(name);
        e2.setText(gender);
        e3.setText(date);

        b1=findViewById(R.id.button10);
        b2=findViewById(R.id.button11);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(viewbooking2.this);
                String url ="http://"+sh.getString("ip", "")+":5000/bookapprove";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++",response);
                        try {
                            JSONObject json=new JSONObject(response);
                            String res=json.getString("task");

                            if(res.equalsIgnoreCase("success"))
                            {

                                    Toast.makeText(getApplicationContext(), "Approved", Toast.LENGTH_LONG).show();
                                    Intent in = new Intent(getApplicationContext(), doctor_home.class);

                                    startActivity(in);
                                }



                            else
                            {
                                Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_LONG).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),"Error"+error,Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();


                        params.put("bid", bid);


                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);


            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(viewbooking2.this);
                String url ="http://"+sh.getString("ip", "")+":5000/bookreject";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++",response);
                        try {
                            JSONObject json=new JSONObject(response);
                            String res=json.getString("task");

                            if(res.equalsIgnoreCase("success"))
                            {

                                Toast.makeText(getApplicationContext(), "Rejected", Toast.LENGTH_LONG).show();
                                Intent in = new Intent(getApplicationContext(), doctor_home.class);

                                startActivity(in);
                            }



                            else
                            {
                                Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_LONG).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),"Error"+error,Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();


                        params.put("bid", bid);


                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);


            }
        });
    }
}