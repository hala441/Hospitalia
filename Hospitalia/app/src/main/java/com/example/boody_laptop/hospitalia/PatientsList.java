package com.example.boody_laptop.hospitalia;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.RequiresApi;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.boody_laptop.hospitalia.ViewItem.SubNameTextView;

public class PatientsList extends AppCompatActivity {

    TextView username;
    ListView SubjectFullFormListView;
    ProgressBar progressBar;
    String uname;

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_list);

        username = (TextView) findViewById(R.id.userName);

        SubjectFullFormListView = (ListView) findViewById(R.id.SubjectFullFormListView);

        progressBar = (ProgressBar) findViewById(R.id.ProgressBar1);
        username.setText(SharedPrefManager.getInstance(this).getUsername().toString());
        uname = username.getText().toString();

        new ParseJSonDataClass(this).execute();
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private class ParseJSonDataClass extends AsyncTask<Void, Void, Void> {
        String HttpURL = "http://wwwhealthcaresystemcom.000webhostapp.com/Android_folder/adddoctor.php?username="+uname;

        public Context context;
        String FinalJSonResult;
        List<Doctor> SubjectFullFormList;

        public ParseJSonDataClass(Context context) {

            this.context = context;
        }


        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpServicesClass httpServiceClass = new HttpServicesClass(HttpURL);

            try {
                httpServiceClass.ExecutePostRequest();

                if (httpServiceClass.getResponseCode() == 200) {

                    FinalJSonResult = httpServiceClass.getResponse();

                    if (FinalJSonResult != null) {

                        JSONArray jsonArray = null;
                        try {

                            jsonArray = new JSONArray(FinalJSonResult);
                            JSONObject jsonObject;
                            Doctor doctor;

                            SubjectFullFormList = new ArrayList<Doctor>();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                doctor = new Doctor();

                                jsonObject = jsonArray.getJSONObject(i);

                                doctor.Doctor_Name = jsonObject.getString("username");
                                doctor.Doctor_Category = jsonObject.getString("medicalHistory");

                                SubjectFullFormList.add(doctor);
                                final Doctor finalDoctor = doctor;
                                SubjectFullFormListView.setOnItemClickListener(new OnItemClickListener()
                                {

                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view,
                                                            int position, long id) {
                                        // TODO Auto-generated method stub


                                        String TempListViewClickedValue = finalDoctor.Doctor_Name;
                                        String TempListViewClickedValue1 = finalDoctor.Doctor_Category;

                                        Intent intent = new Intent(PatientsList.this, MeasurementActivity.class);
                                        intent.putExtra("ListViewClickedValue", TempListViewClickedValue);
                                        intent.putExtra("ListViewClickedValue1", TempListViewClickedValue1);

                                        // Sending value to another activity using intent.

                                        startActivity(intent);

                                    }
                                });


                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } else {

                    Toast.makeText((Context) context, httpServiceClass.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)

        {
            progressBar.setVisibility(View.GONE);

            SubjectFullFormListView.setVisibility(View.VISIBLE);

            if (SubjectFullFormList != null) {

                ListAdapter adapter = new ListAdapter(SubjectFullFormList, (Context) context);

                SubjectFullFormListView.setAdapter(adapter);
            }
        }
    }


    public void myClickHandler(View view){
        Button bt=(Button)view;
        final String name = SubNameTextView.getText().toString().trim();
        Toast.makeText(this, "Button "+bt.getText().toString(), Toast.LENGTH_LONG).show();
    }

}