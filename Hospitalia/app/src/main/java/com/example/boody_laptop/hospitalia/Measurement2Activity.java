package com.example.boody_laptop.hospitalia;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Measurement2Activity extends AppCompatActivity {
    TextView editText;
    private ProgressDialog pd;
    TextView result1,result2,comment;
    String uname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement2);
        editText = (TextView) findViewById(R.id.editText1);
        result1 = (TextView)findViewById(R.id.textView);
        result2 = (TextView)findViewById(R.id.textView1);
        comment = (TextView)findViewById(R.id.comment);
        comment.setText(SharedPrefManager2.getInstance(this).getcomment());
        editText.setText(SharedPrefManager2.getInstance(this).getUsername());
        uname = editText.getText().toString();
        pd = new ProgressDialog(Measurement2Activity.this);
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
                                String temperature1 =   temperature.substring(temperature.lastIndexOf(' ') + 1);

                                result1.setText(heartratel);
                                result2.setText(temperature1+" °C");


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
}
