package com.example.boody_laptop.hospitalia;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class UpdateActivity extends AppCompatActivity {
    String finalResult;
    EditText Name, Email, Age, Weight, DeviceId, Gender, Password;
    Button UpdateStudent;
    String  fullname, age, email, weight, deviceid, gender, password;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
       Name = (EditText) findViewById(R.id.editName);
        Age = (EditText) findViewById(R.id.editUname);
        Email = (EditText) findViewById(R.id.editEmail);
        Weight = (EditText) findViewById(R.id.editCity);
        Gender = (EditText) findViewById(R.id.editMobile);
        DeviceId = (EditText) findViewById(R.id.editCategory);
//        Telephone = (EditText) findViewById(R.id.editTelephone);
//        Gender = (EditText) findViewById(R.id.editGender);
        Password = (EditText) findViewById(R.id.editPassword);



        UpdateStudent = (Button) findViewById(R.id.UpdateButton);
        progressDialog = new ProgressDialog(this);

        // Setting Received Student Name, Phone Number, Class into EditText.
        Name.setText(SharedPrefManager2.getInstance(this).getName());
        Age.setText(SharedPrefManager2.getInstance(this).getAge());
        Weight.setText(SharedPrefManager2.getInstance(this).getWeight());
        Gender.setText(SharedPrefManager2.getInstance(this).getGender());
        DeviceId.setText(SharedPrefManager2.getInstance(this).getDeviceid());
//        Mobile.setText(SharedPrefManager.getInstance(this).getMobile());
        Email.setText(SharedPrefManager2.getInstance(this).getEmail());
//        Gender.setText(SharedPrefManager.getInstance(this).getGender());
        Password.setText(SharedPrefManager2.getInstance(this).getPassword());


        // Adding click listener to update button .
        UpdateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Getting data from EditText after button click.
                GetDataFromEditText();

                // Sending Student Name, Phone Number, Class to method to update on server.
                StudentRecordUpdate( fullname, password, email,age,weight,gender,deviceid);

            }
        });


    }

    // Method to get existing data from EditText.
    public void GetDataFromEditText() {

        fullname = Name.getText().toString();
        age = Age.getText().toString();
        gender = Gender.getText().toString();
        weight = Weight.getText().toString();
        deviceid = DeviceId.getText().toString();
//        telephone = Telephone.getText().toString();
//        category = Category.getText().toString();
//        gender = Gender.getText().toString();
        password = Password.getText().toString();

        email = Email.getText().toString();


    }

    // Method to Update Student Record.
    public void StudentRecordUpdate( final String fullname, final String password,  final String email,  final String age,  final String weight,  final String gender,  final String deviceid) {
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_UPDATE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fullname", fullname);
                params.put("email", email);
           //    params.put("username",username);
//                params.put("city",city);
//                params.put("Mobile",mobile);
//                params.put("category",category);
//                params.put("id",ID);
//                params.put("gender",gender);
//                params.put("Telephone",telephone);
                params.put("password",password);
                params.put("gender", gender);
                params.put("age", age);
                params.put("weight", weight);
                params.put("DeviceId", deviceid);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}

