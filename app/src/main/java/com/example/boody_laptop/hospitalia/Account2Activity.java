package com.example.boody_laptop.hospitalia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Account2Activity extends AppCompatActivity implements View.OnClickListener {
    TextView Uname,Gender;
    EditText Name, Email, City, Mobile, Telephone, Category, Password;
    Button UpdateStudent;
    String  name, city, mobile, email, telephone, category, gender, password,username;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account2);
        Uname = (TextView)findViewById(R.id.editDUNAME);
        Name = (EditText) findViewById(R.id.editDNAME);
        City = (EditText) findViewById(R.id.editDCITY);
        Email = (EditText) findViewById(R.id.editDEMAIL);
        Mobile = (EditText) findViewById(R.id.editDMOBILE);
        Gender = (TextView) findViewById(R.id.editDGender);
        Category = (EditText) findViewById(R.id.editDCATEGORY);
        Telephone = (EditText) findViewById(R.id.editDTELEPHONE);
        Password = (EditText) findViewById(R.id.editDPassword);
        UpdateStudent = (Button) findViewById(R.id.UpdateButton);
        progressDialog = new ProgressDialog(this);

        // Setting Received Student Name, Phone Number, Class into EditText.
        Uname.setText(SharedPrefManager.getInstance(this).getUsername());
        Name.setText(SharedPrefManager.getInstance(this).getname());
        City.setText(SharedPrefManager.getInstance(this).getCity());
        Telephone.setText(SharedPrefManager.getInstance(this).getTelephone());
        Gender.setText(SharedPrefManager.getInstance(this).getGender());
        Mobile.setText(SharedPrefManager.getInstance(this).getMobile());
        Email.setText(SharedPrefManager.getInstance(this).getEmail());
        Gender.setText(SharedPrefManager.getInstance(this).getGender());
        Password.setText(SharedPrefManager.getInstance(this).getPassword());
        Category.setText(SharedPrefManager.getInstance(this).getCategory());
        UpdateStudent.setOnClickListener(this);



    }
    private void UpdateUser(){
        final String name = Name.getText().toString().trim();
        final String username = Uname.getText().toString().trim();
        final String password = Password.getText().toString().trim();
        final String email = Email.getText().toString().trim();
        final String city= City.getText().toString().trim();
        final String mobile = Mobile.getText().toString().trim();
        final String telephone = Telephone.getText().toString().trim();
        final String category = Category.getText().toString().trim();
        final String gender = Gender.getText().toString().trim();

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
                params.put("email",email);
                params.put("city",city);
                params.put("Mobile",mobile);
                params.put("category",category);
                params.put("gender",gender);
                params.put("Telephone",telephone);
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
            startActivity(new Intent(this,LoginDoctorActivity.class));
        }

    }
}
