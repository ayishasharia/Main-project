package com.example.registration;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.widget.AdapterView.*;

import androidx.appcompat.app.AppCompatActivity;

public class bookdoctor extends AppCompatActivity implements AdapterView.OnItemClickListener  {
    ListView l1;
    SharedPreferences sp;
    String url="",ip="";
    ArrayList<String> name,specialisation,gender,days,fromtime,totime,did;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookdoctor);
        l1=(ListView) findViewById(R.id.list);
        sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



        url ="http://"+sp.getString("ip", "") + ":5000/viewdoctors";
        RequestQueue queue = Volley.newRequestQueue(bookdoctor.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    name= new ArrayList<>();
                    specialisation= new ArrayList<>();
                    gender= new ArrayList<>();
                    days=new ArrayList<>();
                    fromtime=new ArrayList<>();
                    totime=new ArrayList<>();
                    did=new ArrayList<>();

                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        name.add(jo.getString("first_name")+" "+jo.getString("second_name"));
                        specialisation.add(jo.getString("specification"));
                        days.add(jo.getString("days_available"));
                        gender.add(jo.getString("gender"));
                        fromtime.add(jo.getString("from_time"));
                        totime.add(jo.getString("to_time"));
                        did.add(jo.getString("login_id"));

                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new custom(bookdoctor.this,name,specialisation,gender,days,fromtime,totime));
                    l1.setOnItemClickListener(bookdoctor.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(bookdoctor.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };
        queue.add(stringRequest);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long id){
        Intent in = new Intent(getApplicationContext(), doctorbooking3.class);
        in.putExtra("doctor_id", did.get(i));
        in.putExtra("name", name.get(i));
        in.putExtra("specification", specialisation.get(i));
        in.putExtra("gender", gender.get(i));
        in.putExtra("days", days.get(i));
        in.putExtra("from", fromtime.get(i));
        in.putExtra("totime",totime.get(i));
        startActivity(in);

    }
}
