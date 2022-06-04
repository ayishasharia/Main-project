package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class MainActivity extends AppCompatActivity {
    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10;
    RadioButton r1,r2,r3;
    String name,gender,dob,email,phonenumber,place,pincode,username,password,confirmpass,housename;
    Button b1;
    SharedPreferences sh;
    DatePickerDialog datepicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        e1=findViewById(R.id.editTextTextPersonName6);
        e2=findViewById(R.id.editTextTextPersonName7);
        e3=findViewById(R.id.bbb);
        e4=findViewById(R.id.editTextTextPersonName9);
        e5=findViewById(R.id.editTextTextPersonName10);
        e6=findViewById(R.id.editTextTextPersonName12);
        e7=findViewById(R.id.editTextTextPersonName13);
        e8=findViewById(R.id.editTextTextPersonName14);
        e9=findViewById(R.id.editTextTextPersonName15);
        e10=findViewById(R.id.editTextTextPersonName11);
        b1=findViewById(R.id.button5);
        r1=findViewById(R.id.radioButton2);
        r2=findViewById(R.id.radioButton);
        r3=findViewById(R.id.radioButton3);
        e2.setInputType(InputType.TYPE_NULL);
        e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datepicker = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                e2.setText(year  + "-" + (monthOfYear + 1) + "-" +dayOfMonth );
                            }
                        }, year, month, day);
                datepicker.show();

            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=e1.getText().toString();
                if(r1.isChecked()){
                    gender=r1.getText().toString();
                }
                else if(r2.isChecked()){
                    gender=r2.getText().toString();
                }
                else if(r3.isChecked()){
                    gender=r3.getText().toString();
                }

                dob=e2.getText().toString();
                email=e3.getText().toString();
                phonenumber=e4.getText().toString();
                housename=e10.getText().toString();
                place=e5.getText().toString();
                pincode=e6.getText().toString();
                username=e7.getText().toString();
                password=e8.getText().toString();
                confirmpass=e9.getText().toString();
                if(name.equalsIgnoreCase(""))
                {
                    e1.setError("Enter name");
                }
                else if(!name.matches("^[a-zA-Z]*$"))
                {
                    e1.setError("characters allowed");
                    e1.requestFocus();
                }

                else if(dob.equalsIgnoreCase(""))
                {
                    e2.setError("enter your date of birth");
                }
                else if(phonenumber.length()<10)
                {
                    e4.setError("Minimum 10 nos required");
                    e4.requestFocus();
                }
                else if(email.equalsIgnoreCase(""))
                {
                    e3.setError("Enter Your Email");
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    e3.setError("Enter Valid Email");
                    e3.requestFocus();
                }
                else if(place.equalsIgnoreCase(""))
                {
                    e6.setError("Enter Your Place");
                }
                else if(pincode.equalsIgnoreCase(""))
                {
                    e7.setError("Enter Your Pin");
                }
                else if(pincode.length()!=6)
                {
                    e6.setError("invalid pin");
                    e6.requestFocus();
                }

					else if(username.equalsIgnoreCase(""))
            {
                e7.setError("Enter Your username");
            }
					else if(password.equalsIgnoreCase(""))
            {
                e8.setError("Enter Your password");
            }




                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url ="http://"+sh.getString("ip", "")+":5000/reg";
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
                                Toast.makeText(getApplicationContext(),"successfully register",Toast.LENGTH_LONG).show();
                                Intent in=new Intent(getApplicationContext(),login.class)	;

                                startActivity(in);

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"registration failed",Toast.LENGTH_LONG).show();

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

                        params.put("name", name);
                        params.put("gender", gender);
                        params.put("dob", dob);
                        params.put("place", place);
                        params.put("housename",housename);
                        params.put("pincode", pincode);

                        params.put("phonenumber", phonenumber);
                        params.put("email", email);
                        params.put("username", username);
                        params.put("password", password);
                        params.put("cpass", confirmpass);

                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);




            }
        });






    }
}