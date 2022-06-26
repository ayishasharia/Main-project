package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class viewbookaccept extends AppCompatActivity implements  AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sp;
    String url="",ip="";
    ArrayList<String> name,gender,date,status,bid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewbookaccept);
        l1=(ListView) findViewById(R.id.list);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        url ="http://"+sp.getString("ip", "") + ":5000/viewbookaccept";
        RequestQueue queue = Volley.newRequestQueue(viewbookaccept.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    name= new ArrayList<>();
                    gender= new ArrayList<>();
                    date=new ArrayList<>();
                    status=new ArrayList<>();
                    bid=new ArrayList<>();



                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        name.add(jo.getString("name"));
                        gender.add(jo.getString("gender"));
                        date.add(jo.getString("date"));
                        status.add(jo.getString("status"));
                        bid.add(jo.getString("booking_id"));



                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new custom3(viewbookaccept.this,name,gender,date,status));
                    l1.setOnItemClickListener(viewbookaccept.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(viewbookaccept.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lid",sp.getString("lid",""));

                return params;
            }
        };
        queue.add(stringRequest);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long id){
        Intent ik=new Intent(getApplicationContext(),addprescription.class);
        ik.putExtra("bid",bid.get(i));
        startActivity(ik);


    }
}