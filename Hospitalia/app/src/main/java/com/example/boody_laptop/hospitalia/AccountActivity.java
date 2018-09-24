package com.example.boody_laptop.hospitalia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {
    TextView Uname, Gender;
    EditText Name, Password,  Email, Contact,MedicalHistory,Age, Weight, DeviceId,Help;
    ArrayList<String> listItems=new ArrayList<>();
    ArrayAdapter<String> adapter;
    Spinner sp;
    Button UpdateStudent;
    String  uname,fullname, password, gender, email, contact, medicalhistory, age, weight, deviceid;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Uname = (TextView)findViewById(R.id.editUNAME);
        Name = (EditText) findViewById(R.id.editNAME);
        Password = (EditText) findViewById(R.id.editPassword);
        Gender = (TextView) findViewById(R.id.editGender);
        Email = (EditText) findViewById(R.id.editEMAIL);
        Contact = (EditText) findViewById(R.id.editCONTACT);
        Help = (EditText) findViewById(R.id.editHELP);
        MedicalHistory = (EditText) findViewById(R.id.editMEDICALHISTORY);
        Age = (EditText) findViewById(R.id.editAGE);
        Weight = (EditText) findViewById(R.id.editWEIGHT);
        DeviceId = (EditText) findViewById(R.id.editDEVICEID);
        UpdateStudent = (Button) findViewById(R.id.UpdateButton);
        progressDialog = new ProgressDialog(this);

        // Setting Received Student Name, Phone Number, Class into EditText.
        Uname.setText(SharedPrefManager2.getInstance(this).getUsername());
        Name.setText(SharedPrefManager2.getInstance(this).getName());
        Password.setText(SharedPrefManager2.getInstance(this).getPassword());
        Gender.setText(SharedPrefManager2.getInstance(this).getGender());
        Email.setText(SharedPrefManager2.getInstance(this).getEmail());
        Contact.setText(SharedPrefManager2.getInstance(this).getContact());
        Help.setText(SharedPrefManager2.getInstance(this).gethelpnumber());
        MedicalHistory.setText(SharedPrefManager2.getInstance(this).getMedicalHistory());
        Age.setText(SharedPrefManager2.getInstance(this).getAge());
        Weight.setText(SharedPrefManager2.getInstance(this).getWeight());
        DeviceId.setText(SharedPrefManager2.getInstance(this).getDeviceid());
        sp=(Spinner)findViewById(R.id.spinner);
        adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.txt,listItems);
        sp.setAdapter(adapter);
        UpdateStudent.setOnClickListener(this);
    }
    public void onStart() {
        super.onStart();
        BackTask bt = new BackTask();
        bt.execute();
    }
    private class BackTask extends AsyncTask<Void,Void,Void> {
        ArrayList<String> list;
        protected void onPreExecute(){
            super.onPreExecute();
            list=new ArrayList<>();
        }
        protected Void doInBackground(Void...params){
            InputStream is=null;
            String result="";
            try{
                HttpClient httpclient=new DefaultHttpClient();
                HttpPost httppost= new HttpPost("http://wwwhealthcaresystemcom.000webhostapp.com/Android_folder/search.php");
                HttpResponse response=httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                // Get our response as a String.
                is = entity.getContent();
            }catch(IOException e){
                e.printStackTrace();
            }

            //convert response to string
            try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    result+=line;
                }
                is.close();
                //result=sb.toString();
            }catch(Exception e){
                e.printStackTrace();
            }
            // parse json data
            try{
                JSONArray jArray =new JSONArray(result);
                for(int i=0;i<jArray.length();i++){
                    JSONObject jsonObject=jArray.getJSONObject(i);
                    // add interviewee name to arraylist
                    list.add(jsonObject.getString("fullname"));
                }
            }
            catch(JSONException e){
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Void result){
            listItems.addAll(list);
            adapter.notifyDataSetChanged();
        }
    }
    private void UpdateUser(){
        final String name = Name.getText().toString().trim();
        final String username = Uname.getText().toString().trim();
        final String password = Password.getText().toString().trim();
        final String email = Email.getText().toString().trim();
        final String age = Age.getText().toString().trim();
        final String weight = Weight.getText().toString().trim();
        final String deviceid = DeviceId.getText().toString().trim();
        final String gender = Gender.getText().toString().trim();
        final String doctor = sp.getSelectedItem().toString();
        final String medicalhistory = MedicalHistory.getText().toString().trim();
        final String contact = Contact.getText().toString().trim();
        final String help = Help.getText().toString().trim();


        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_EDIT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject json = new JSONObject(response);

                            Toast.makeText(getApplicationContext(),json.getString("message"),Toast.LENGTH_LONG).show();
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
                params.put("fullname",name);
                params.put("username",username);
                params.put("password",password);
                params.put("gender",gender);
                params.put("email",email);
                params.put("contact",contact);
                params.put("medicalHistory",medicalhistory);
                params.put("age",age);
                params.put("weight",weight);
                params.put("DoctorName",doctor);
                params.put("DeviceId",deviceid);
                params.put("helpnumber",help);

                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
    @Override
    public void onClick(View view) {
        if(view == UpdateStudent)
        {
            UpdateUser();
            startActivity(new Intent(this,LoginPatientActivity.class));
        }


    }
}
