package com.example.boody_laptop.hospitalia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

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

public class Login2Activity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextName , editTextUserName, editTextHelp ,editTextPassword , editTextEmail , editTextAge , editTextWeight , editTextDeviceid , editTextMedicalHistory, editTextContact ;
    private Button buttonRegister;
    private ProgressDialog progressDialog;
    private TextView textViewlogin;
    private TextView radiotextview;
    ArrayList<String> listItems=new ArrayList<>();
    ArrayAdapter<String> adapter;
    Spinner sp;

    private DatabaseReference mDatabase;

    //ProgressDialog
    private ProgressDialog mRegProgress;

    //Firebase Auth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        editTextName = (EditText)findViewById(R.id.editTextName);
        editTextUserName = (EditText)findViewById(R.id.editTextUsername);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextAge = (EditText)findViewById(R.id.editTextAge);
        editTextWeight = (EditText)findViewById(R.id.editTextWeight);
        editTextDeviceid = (EditText)findViewById(R.id.editTextDeviceid);
        editTextMedicalHistory = (EditText)findViewById(R.id.editTextMedicalHistory);
        editTextContact = (EditText)findViewById(R.id.editTextContact);
        editTextHelp = (EditText)findViewById(R.id.editTextHelp);
        textViewlogin = (TextView) findViewById(R.id.textViewlogin);
        radiotextview = (TextView) findViewById(R.id.RadioTextView);
        buttonRegister = (Button)findViewById(R.id.buttonRegister);
        progressDialog = new ProgressDialog(this);
        buttonRegister.setOnClickListener(this);
        textViewlogin.setOnClickListener(this);
        sp=(Spinner)findViewById(R.id.spinner);
        adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.txt,listItems);
        sp.setAdapter(adapter);
        //
        // Firebase Auth

        mAuth = FirebaseAuth.getInstance();
        //register_user(editTextUserName.getText().toString(), editTextEmail.getText().toString(), editTextPassword.getText().toString());
    }
    public void onStart(){
        super.onStart();
        BackTask bt=new BackTask();
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
    private void registerUser(){
        final String name = editTextName.getText().toString().trim();
        final String username = editTextUserName.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String age = editTextAge.getText().toString().trim();
        final String weight = editTextWeight.getText().toString().trim();
        final String deviceid = editTextDeviceid.getText().toString().trim();
        final String gender = radiotextview.getText().toString().trim();
        final String doctor = sp.getSelectedItem().toString();
        final String medicalhistory = editTextMedicalHistory.getText().toString().trim();
        final String help = editTextHelp.getText().toString().trim();
        final String contact = editTextContact.getText().toString().trim();

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER2,
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

        //
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = current_user.getUid();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    String device_token = FirebaseInstanceId.getInstance().getToken();

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("name", username);
                    userMap.put("status", "Hi there I'm using Chat App.");
                    userMap.put("image", "default");
                    userMap.put("thumb_image", "default");
                    userMap.put("device_token", device_token);

                    mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Intent mainIntent = new Intent(Login2Activity.this, LoginPatientActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                                finish();

                            }

                        }
                    });

                } else {

                    Toast.makeText(Login2Activity.this, "Cannot Sign in. Please check the form and try again.", Toast.LENGTH_LONG).show();

                }
            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
    public void onRadioButtonClicked (View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId())
        {
            case R.id.female:
                if (checked)
                    radiotextview.setText("female");
                break;
            case R.id.male:
                if (checked)
                    radiotextview.setText("male");
                break;
        }
    }
    @Override
    public void onClick(View view) {
        if(view == buttonRegister)
            registerUser();
            //register_user();
        if(view == textViewlogin)
            startActivity(new Intent(this,LoginPatientActivity.class));
    }

    private void register_user() {
        final String username = editTextUserName.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = current_user.getUid();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    String device_token = FirebaseInstanceId.getInstance().getToken();

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("name", username);
                    userMap.put("status", "Hi there I'm using Chat App.");
                    userMap.put("image", "default");
                    userMap.put("thumb_image", "default");
                    userMap.put("device_token", device_token);

                    mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                Intent mainIntent = new Intent(Login2Activity.this, LoginPatientActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                                finish();

                            }

                        }
                    });


                } else {

                    Toast.makeText(Login2Activity.this, "Cannot Sign in. Please check the form and try again.", Toast.LENGTH_LONG).show();

                }
            }
        });

    }
}

