package com.example.boody_laptop.hospitalia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginPatientActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextUserName ,editTextPassword;
    private Button buttonLogin;
    private ProgressDialog progressDialog;

//    private ProgressDialog mLoginProgress;

    private FirebaseAuth mAuth;

    private DatabaseReference mUserDatabase;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_patient);
        if(SharedPrefManager2.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, Home2Activity.class));
            return;
        }

   //     mLoginProgress = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        editTextUserName = (EditText)findViewById(R.id.editTextUsername);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        buttonLogin = (Button)findViewById(R.id.buttonLogin);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        buttonLogin.setOnClickListener(this);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = editTextUserName.getText().toString();
                String password = editTextPassword.getText().toString();

                if(!TextUtils.isEmpty(username) || !TextUtils.isEmpty(password)){

                /*    mLoginProgress.setTitle("Logging In");
                    mLoginProgress.setMessage("Please wait while we check your credentials.");
                    mLoginProgress.setCanceledOnTouchOutside(false);
                    mLoginProgress.show();*/
                    userLogin();
                }
            }
        });
    }

    private void userLogin(){
        final String username = editTextUserName.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOGIN2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(response);
                    if(!obj.getBoolean("error")){
                        SharedPrefManager2.getInstance(getApplicationContext()).userLogin(

                                obj.getString("fullname"),
                                obj.getString("username"),
                                obj.getString("password"),
                                obj.getString("gender"),
                                obj.getString("email"),
                                obj.getString("contact"),
                                obj.getString("medicalHistory"),
                                obj.getString("age"),
                                obj.getString("weight"),
                                obj.getString("DoctorName"),
                                obj.getString("DeviceId"),
                                obj.getString("comment"),
                                obj.getString("helpnumber")

                        );
                        //password2=obj.getString("password");
                        email=SharedPrefManager2.getInstance(LoginPatientActivity.this).getEmail();
                        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                  //  if(mLoginProgress != null) {
                                    //    mLoginProgress.dismiss();
                                        //mLoginProgress = null;
                                    //}
                                    String current_user_id = mAuth.getCurrentUser().getUid();
                                    String deviceToken = FirebaseInstanceId.getInstance().getToken();

                                    mUserDatabase.child(current_user_id).child("device_token").setValue(deviceToken)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Intent mainIntent = new Intent(LoginPatientActivity.this, BasicChat.class);
                                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            //startActivity(mainIntent);
                                            //finish();
                                        }
                                    });
                                } else {
                                    //if((mLoginProgress!=null)&& mLoginProgress.isShowing()){
                                     //   mLoginProgress.hide();
                                   // }
                                    String task_result = task.getException().getMessage().toString();
                                    Toast.makeText(LoginPatientActivity.this, "Error : " + task_result,
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        startActivity(new Intent(getApplicationContext(), Home2Activity.class));
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),
                                obj.getString("message"),
                                Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                   if(progressDialog != null) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }

                Toast.makeText(getApplicationContext(),
                        error.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
////////////////////// Email ////////////////////

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View v) {

    }

  /*  @Override
    public void onClick(View view) {
        if(view == buttonLogin)
            mLoginProgress.setTitle("Logging In");
            mLoginProgress.setMessage("Please wait while we check your credentials.");
            mLoginProgress.setCanceledOnTouchOutside(false);
            mLoginProgress.show();
            userLogin();

    }*/
}

