package com.example.registration;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class searchplants extends AppCompatActivity {

    Button b1,b2;
    EditText e1;
    TextView t1;
    SharedPreferences sh;
    String PathHolder = "";
    byte[] filedt = null;
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchplants);
        b1 = findViewById(R.id.button17);
        b2=findViewById(R.id.button18);
        e1=findViewById(R.id.editTextTextPersonName8);
        t1=findViewById(R.id.textView12);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url ="http://"+sh.getString("ip", "")+":5000/searchmed";
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
//            intent.setType("application/pdf");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 7);

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadBitmap("cvvv");
            }
        });


    }
        ProgressDialog pd;

        private void uploadBitmap(final String title) {
            pd = new ProgressDialog(searchplants.this);
            pd.setMessage("Uploading....");
            pd.show();
            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,url,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response1) {
                            pd.dismiss();
                            String x = new String(response1.data);
                            try {
                                JSONObject obj = new JSONObject(new String(response1.data));
                                String res=obj.getString("resultt");
//                        Toast.makeText(Upload_agreement.this, "Report Sent Successfully", Toast.LENGTH_LONG).show();
                                if (obj.getString("task").equalsIgnoreCase("success")) {

                                    Toast.makeText(searchplants.this, "Successfully uploaded", Toast.LENGTH_LONG).show();
                                    t1.setText(res);

                                } else {
                                    Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();
                                }

                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();



                    params.put("bid", getIntent().getStringExtra("bid"));

                    return params;
                }

                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();
                    long imagename = System.currentTimeMillis();
                    params.put("file", new DataPart(PathHolder, filedt));


                    return params;
                }
            };

            Volley.newRequestQueue(this).add(volleyMultipartRequest);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode) {
                case 7:
                    if (resultCode == RESULT_OK) {
                        Uri uri = data.getData();
                        Log.d("File Uri", "File Uri: " + uri.toString());
                        // Get the path
                        try {
                            PathHolder = FileUtils.getPathFromURI(this, uri);
//                        PathHolder = data.getData().getPath();
//                        Toast.makeText(this, PathHolder, Toast.LENGTH_SHORT).show();

                            filedt = getbyteData(PathHolder);
                            Log.d("filedataaa", filedt + "");
//                        Toast.makeText(this, filedt+"", Toast.LENGTH_SHORT).show();
                            e1.setText(PathHolder);
                        } catch (Exception e) {
                            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
            }
        }

        private byte[] getbyteData(String pathHolder) {
            Log.d("path", pathHolder);
            File fil = new File(pathHolder);
            int fln = (int) fil.length();
            byte[] byteArray = null;
            try {
                InputStream inputStream = new FileInputStream(fil);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] b = new byte[fln];
                int bytesRead = 0;

                while ((bytesRead = inputStream.read(b)) != -1) {
                    bos.write(b, 0, bytesRead);
                }
                byteArray = bos.toByteArray();
                inputStream.close();
            } catch (Exception e) {
            }
            return byteArray;


        }

    }








