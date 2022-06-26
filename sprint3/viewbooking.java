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

public class viewbooking extends AppCompatActivity implements  AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sp;
    String url="",ip="";
    ArrayList<String> name,gender,date,bid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewbooking);
        l1=(ListView) findViewById(R.id.list);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        url ="http://"+sp.getString("ip", "") + ":5000/viewbooking";
        RequestQueue queue = Volley.newRequestQueue(viewbooking.this);

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
                    bid=new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        name.add(jo.getString("name"));
                        gender.add(jo.getString("gender"));
                        date.add(jo.getString("date"));
                        bid.add(jo.getString("booking_id"));


                    }

                      // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new custom2(viewbooking.this,name,gender,date));
                    l1.setOnItemClickListener(viewbooking.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(viewbooking.this, "err"+error, Toast.LENGTH_SHORT).show();
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
        Intent in = new Intent(getApplicationContext(), viewbooking2.class);
        in.putExtra("name", name.get(i));
        in.putExtra("gender", gender.get(i));
        in.putExtra("date", date.get(i));
        in.putExtra("booking_id", bid.get(i));
        startActivity(in);


    }
}