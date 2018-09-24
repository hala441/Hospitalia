package com.example.boody_laptop.hospitalia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextName , editTextUserName ,editTextPassword , editTextEmail , editTextCity ,editTextMobile,editTextTelephone , editTextCategory;
    private Button buttonRegister;
    private ProgressDialog progressDialog;
    private TextView textViewlogin;
    private TextView radiotextview;

    private DatabaseReference mDatabase;

    //ProgressDialog
    private ProgressDialog mRegProgress;

    //Firebase Auth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextName = (EditText)findViewById(R.id.editTextName);
        editTextUserName = (EditText)findViewById(R.id.editTextUsername);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextCity = (EditText)findViewById(R.id.editTextcity);
        editTextMobile = (EditText)findViewById(R.id.editTextMobile);
        editTextTelephone = (EditText)findViewById(R.id.editTextTelephone);
        editTextCategory = (EditText)findViewById(R.id.editTextCategory);
        textViewlogin = (TextView) findViewById(R.id.textViewlogin);
        buttonRegister = (Button)findViewById(R.id.buttonRegister);
        radiotextview = (TextView) findViewById(R.id.RadioTextView);
        progressDialog = new ProgressDialog(this);
        buttonRegister.setOnClickListener(this);
        textViewlogin.setOnClickListener(this);
        // Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }


    private void registerUser(){
        final String name = editTextName.getText().toString().trim();
        final String username = editTextUserName.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String city= editTextCity.getText().toString().trim();
        final String mobile = editTextMobile.getText().toString().trim();
        final String telephone = editTextTelephone.getText().toString().trim();
        final String category = editTextCategory.getText().toString().trim();
        final String gender = radiotextview.getText().toString().trim();
        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER,
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
                params.put("fullname",name);
                params.put("username",username);
                params.put("password",password);
                params.put("email",email);
                params.put("city",city);
                params.put("Mobile",mobile);
                params.put("category",category);
                params.put("gender",gender);
                params.put("Telephone",telephone);
//                params.put("mydoctor",mydoctor);

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
                                Intent mainIntent = new Intent(LoginActivity.this, LoginDoctorActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                                finish();
                            }
                        }
                    });
                } else {

                    Toast.makeText(LoginActivity.this, "Cannot Sign in. Please check the form and try again.", Toast.LENGTH_LONG).show();

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
        if(view == textViewlogin)
            startActivity(new Intent(this,LoginDoctorActivity.class));
    }
}

