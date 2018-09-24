package com.example.boody_laptop.hospitalia;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MeasurementActivity extends AppCompatActivity implements View.OnClickListener {
    TextView editText,editText1;
    private ProgressDialog pd;
    TextView result1,result2;
    EditText comment,ucomment;
    Button submit;
    private ProgressDialog progressDialog;
    String uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement);
        editText = (TextView) findViewById(R.id.editText1);
        editText1 = (TextView) findViewById(R.id.editText2);
        result1 = (TextView)findViewById(R.id.textView);
        result2 = (TextView)findViewById(R.id.textView1);
        comment = (EditText)findViewById(R.id.editcomment);
     //  ucomment = (EditText)findViewById(R.id.editcomment1);
        submit = (Button) findViewById(R.id.buttoncommentr);

        // Receiving value into activity using intent.
        String username = getIntent().getStringExtra("product");
      ///  String medicalhistory = getIntent().getStringExtra("ListViewClickedValue1");

        // Setting up received value into EditText.
        editText.setText(username);
   //     editText1.setText(medicalhistory);

           uname = editText.getText().toString().trim();

        pd = new ProgressDialog(MeasurementActivity.this);
        pd.setMessage("loading");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);

        String url= "http://wwwhealthcaresystemcom.000webhostapp.com/Android_folder/sensors.php?username="+uname;
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pd.hide();

                        try {

                            JSONArray jsonarray = new JSONArray(response);

                            for(int i=0; i < jsonarray.length(); i++) {

                                JSONObject jsonobject = jsonarray.getJSONObject(i);


//                                String id = jsonobject.getString("id");
//                                String price = jsonobject.getString("price");
                                String heartrate = jsonobject.getString("heart_rate");
                             String heartratel =   heartrate.substring(heartrate.lastIndexOf(' ') + 1);

//                                String phone = jsonobject.getString("phone");
                                String temperature = jsonobject.getString("temperature");
                                String temperaturel =   temperature.substring(temperature.lastIndexOf(' ') + 1);

                                result1.setText(heartratel);
                                result2.setText(temperaturel+" °C");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error != null){

                            Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_LONG).show();
                        }
                    }
                }

        );

        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


        progressDialog = new ProgressDialog(this);
        submit.setOnClickListener(this);
    }
    private void addcomment(){
        String url2= "http://wwwhealthcaresystemcom.000webhostapp.com/Android_folder/comment.php";

       // final String   uscomment = ucomment.getText().toString().trim();
        final String drcomment = comment.getText().toString().trim();
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(getApplicationContext(), error.getMessage() , Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
              //  params.put("username",uscomment);
                params.put("username",uname);
                params.put("comment",drcomment);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
    @Override
    public void onClick(View view) {
        if(view == submit)
            addcomment();

    }
}



