package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class doctorbooking3 extends AppCompatActivity {
    TextView e1,e2,e3,e4,e5;
    EditText e6;
    Button b1,b2;
    SharedPreferences sh;
    String doctor,specification,days,fromtime,totime,date,did;
    DatePickerDialog datepicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        setContentView(R.layout.doctorbooking3);
        e1=findViewById(R.id.textView28);
        e2=findViewById(R.id.textView29);
        e3=findViewById(R.id.textView30);
        e4=findViewById(R.id.textView31);
        e5=findViewById(R.id.textView32);
        e6=findViewById(R.id.editTextTextPersonName);
        doctor=getIntent().getStringExtra("name");
        did=getIntent().getStringExtra("doctor_id");
        specification=getIntent().getStringExtra("specification");
        days=getIntent().getStringExtra("days");
        fromtime=getIntent().getStringExtra("from");
        totime=getIntent().getStringExtra("totime");

        e1.setText(doctor);
        e2.setText(specification);
        e3.setText(days);
        e4.setText(fromtime);
        e5.setText(totime);
        e6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datepicker = new DatePickerDialog(doctorbooking3.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                e6.setText( year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                datepicker.show();
            }
        });
        b1=findViewById(R.id.button10);
        b2=findViewById(R.id.button11);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date=e6.getText().toString();
                if(date.equalsIgnoreCase(""))
                {
                    e6.setError("Enter Date");
                }
                else {


                    RequestQueue queue = Volley.newRequestQueue(doctorbooking3.this);
                    String url ="http://"+sh.getString("ip", "")+":5000/booking";
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

                                    Toast.makeText(getApplicationContext(), "Booking request has been sent", Toast.LENGTH_LONG).show();
                                    Intent in=new Intent(getApplicationContext(),homepage.class);

                                    startActivity(in);

                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext()," failed",Toast.LENGTH_LONG).show();

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


                            params.put("u_id", sh.getString("lid",""));
                            params.put("doctor_id",did);
                            params.put("date",date);



                            return params;
                        }
                    };
                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);

                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),bookdoctor.class);
                startActivity(i);
            }
        });
    }
}