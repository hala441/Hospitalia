package com.example.boody_laptop.hospitalia;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EmergencyActivity extends AppCompatActivity {
    Button emergency;
    private ProgressDialog pd;
    String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        emergency = (Button) findViewById(R.id.emergency);
        GPStracker gpsTracker = new GPStracker(this);

        if (gpsTracker.canGetLocation())
        {
            String stringLatitude = String.valueOf(gpsTracker.latitude);

            String stringLongitude = String.valueOf(gpsTracker.longitude);

            String country = gpsTracker.getCountryName(this);

            String city = gpsTracker.getLocality(this);

            String postalCode = gpsTracker.getPostalCode(this);

            String addressLine = gpsTracker.getAddressLine(this);

            temp = stringLatitude + " " + stringLongitude ;
        }
        else
        {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings

        }

        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String dooctorname =   SharedPrefManager2.getInstance(EmergencyActivity.this).getDoctor();
                    pd = new ProgressDialog(EmergencyActivity.this);
                    pd.setMessage("loading");
                    pd.setCancelable(false);
                    pd.setCanceledOnTouchOutside(false);

                    String url= "http://wwwhealthcaresystemcom.000webhostapp.com/Android_folder/calldoctor.php?username="+dooctorname;
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

                                            String number = jsonobject.getString("Mobile");
                                            try {
                                                SmsManager smsManager = SmsManager.getDefault();
                                                smsManager.sendTextMessage(number, null, "I need help "+temp, null, null);
                                                Toast.makeText(getApplicationContext(), "Message Sent",
                                                        Toast.LENGTH_LONG).show();
                                            } catch (Exception ex) {

                                                ex.printStackTrace();
                                            }

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

                }

        });
    }
}