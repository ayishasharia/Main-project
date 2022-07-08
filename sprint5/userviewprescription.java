package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

public class userviewprescription extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sp;
    String url="",ip="";

    ArrayList<String> date,doctor,pr_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userviewprescription);
        l1=(ListView) findViewById(R.id.list);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url ="http://"+sp.getString("ip", "") + ":5000/userviewprescription";
        RequestQueue queue = Volley.newRequestQueue(userviewprescription.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    date= new ArrayList<>();
                    doctor= new ArrayList<>();
                    pr_id= new ArrayList<>();



                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        doctor.add(jo.getString("first_name")+jo.getString("second_name"));
//                        time.add(jo.getString("from_time")+"To"+jo.getString("to_time"));
                        date.add(jo.getString("date"));
                        pr_id.add(jo.getString("pre_id"));
//                        bid.add(jo.getString("booking_id"));


                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new custom6(userviewprescription.this,doctor,date,pr_id));
                    l1.setOnItemClickListener(userviewprescription.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(userviewprescription.this, "err"+error, Toast.LENGTH_SHORT).show();
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


//        Toast.makeText(this, "aaa"+id, Toast.LENGTH_SHORT).show();
        Intent j=new Intent(getApplicationContext(),imageview.class);
        startActivity(j);
    }
}