package com.example.registration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
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

import androidx.appcompat.app.AppCompatActivity;


public class prediction extends AppCompatActivity {
    Spinner s1;
    Button b1,b2,b3;
    ListView l1;
    String url="";
    SharedPreferences sh;
    ArrayList<String> results,symptoms;
    TextView t1,t2;
    String info="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);
        s1=(Spinner) findViewById(R.id.spinner);
        t1=(TextView)findViewById(R.id.textView8);
//        t2=(TextView)findViewById(R.id.textView9);



        b3=(Button) findViewById(R.id.button13);

        b1=(Button) findViewById(R.id.button15);
        b2=(Button) findViewById(R.id.button16);
        l1=(ListView) findViewById(R.id.list1);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String val=s1.getSelectedItem().toString();
                results.add(val);
                ArrayAdapter<String>ad=new ArrayAdapter<String>(prediction.this,android.R.layout.simple_list_item_1,results);
                l1.setAdapter(ad);

//             l1.setAdapter(new customseach(search_medicine.this,result));

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(results.size()==0)
                {
                    Toast.makeText(getApplicationContext(),"pls select symptom",Toast.LENGTH_LONG).show();
                }
                else {
                    url = "http://" + sh.getString("ip", "") + ":5000/disease_predict";

                    for (int i = 0; i < results.size(); i++)
                        info += results.get(i) + "#";
                    RequestQueue queue = Volley.newRequestQueue(prediction.this);

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.

                            try {
                                JSONObject jo = new JSONObject(response);
                                String result = jo.getString("task");

                                info="";
                                t1.setText(result);
                                t2.setText(jo.getString("med"));

                            } catch (Exception e) {
                                Log.d("=========", e.toString());
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(prediction.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("symptoms", info);

                            return params;
                        }
                    };
                    // Add the request to the RequestQueue.


                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    queue.add(stringRequest);


                }}
        });


        b1.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View view) {
                                      results.clear();
                                      info = "";
                                      startActivity(new Intent(getApplicationContext(), prediction.class));
                                  }
                              });



                sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                results = new ArrayList<String>();
                String url1 = "http://" + sh.getString("ip", "") + ":5000/view_symptom";

                RequestQueue queue = Volley.newRequestQueue(prediction.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++", response);
                        try {

                            JSONArray ar = new JSONArray(response);

                            symptoms = new ArrayList<String>();

                            for (int i = 0; i < ar.length(); i++) {
                                JSONObject jo = ar.getJSONObject(i);
                                symptoms.add(jo.getString("symptom"));


                            }

                            ArrayAdapter<String> ad = new ArrayAdapter<String>(prediction.this, android.R.layout.simple_spinner_item, symptoms);
                            s1.setAdapter(ad);

                            // l1.setAdapter(new custom2(Monitoring_signal.this,notification,date));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(stringRequest);

            }
        }