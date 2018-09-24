package com.example.boody_laptop.hospitalia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hsalf.smilerating.SmileRating;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Home2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ProgressDialog pd;
    TextView username,email;
    String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Hospitalia");

        GPStracker gpsTracker = new GPStracker(this);

        CardView readings = (CardView) findViewById(R.id.readingscardId);
        CardView chat = (CardView) findViewById(R.id.ChatcardId);
        CardView fitness = (CardView) findViewById(R.id.FittnesscardId);
        CardView map = (CardView) findViewById(R.id.mapscardId);
        CardView alarm = (CardView) findViewById(R.id.AlarmcardId);

        FloatingActionButton sms =(FloatingActionButton) findViewById(R.id.emergency);

        final Intent intent = new Intent(this, FittnessTracker.class);
        final Intent intent3 = new Intent(this, ScanningActivity.class);
        final Intent intent2 = new Intent(this, mapsActivity.class);
        final Intent intent4 = new Intent(this, BasicChat.class);
        final Intent intent5 = new Intent(this, AlarmActivity.class);

        readings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent3);
            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent2);
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent4);
            }
        });
        fitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent5);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (gpsTracker.canGetLocation())
        {
            String stringLatitude = String.valueOf(gpsTracker.latitude);

            String stringLongitude = String.valueOf(gpsTracker.longitude);

            String country = gpsTracker.getCountryName(this);

            String city = gpsTracker.getLocality(this);

            String postalCode = gpsTracker.getPostalCode(this);

            String addressLine = gpsTracker.getAddressLine(this);

            temp = stringLatitude + "," + stringLongitude ;
        }
        else
        {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings

        }

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String number = SharedPrefManager2.getInstance(Home2Activity.this).gethelpnumber();
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number, null, "I need help "+"http://maps.google.com?q="+temp, null, null);
                    Toast.makeText(getApplicationContext(), "Message Sent",
                            Toast.LENGTH_LONG).show();
                } catch (Exception ex) {

                    ex.printStackTrace();
                }
            }

        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        } else if (id == R.id.Log_out) {
            SharedPrefManager2.getInstance(this).logout();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else if (id == R.id.help) {
            Intent myintent = new Intent(this, HelpActivity.class);
            startActivity(myintent);

        } else if (id == R.id.exit) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        username = (TextView) findViewById(R.id.username);
        email = (TextView) findViewById(R.id.mail);
        username.setText(SharedPrefManager.getInstance(this).getUsername());
        email.setText(SharedPrefManager.getInstance(this).getEmail());

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_account) {
            Intent myintent = new Intent(this, AccountActivity.class);
            startActivity(myintent);
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        } else if (id == R.id.nav_measurement) {
            Intent intent = new Intent(this, Measurement2Activity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            Intent i = new Intent(
                    Intent.ACTION_SEND);

            i.setType("text/plain");

            i.putExtra(
                    Intent.EXTRA_TEXT, "My new app https://play.google.com/store/search?q=TECHHUBINDIAN");

            startActivity(Intent.createChooser(i, "Share Via"));
            //  Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_rate) {

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(Home2Activity.this);
            View mView = getLayoutInflater().inflate(R.layout.popup_rating, null);
            final SmileRating newRate = (SmileRating) mView.findViewById(R.id.smile_rating);
            Button rate = (Button) mView.findViewById(R.id.button);

            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            rate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(Home2Activity.this,
                            "Thank you.",
                            Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                   /* if(newRate.isSelected()){
                        Toast.makeText(Home2Activity.this,
                                "Thank you.",
                                Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }else{
                        Toast.makeText(Home2Activity.this,
                                "Please rate our application.",
                                Toast.LENGTH_SHORT).show();
                    }*/
                }
            });

        } else if (id == R.id.nav_feedback) {
            Intent myintent = new Intent(this, FeedBackActivity.class);
            startActivity(myintent);

        } else if (id == R.id.nav_log_out) {
            SharedPrefManager.getInstance(this).logout();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else if(id== R.id.nav_emergency) {
            String dooctorname = SharedPrefManager2.getInstance(this).getDoctor();

            pd = new ProgressDialog(Home2Activity.this);
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

                                    String Mobile = jsonobject.getString("Mobile");

                                    Intent callintent= new Intent (Intent.ACTION_CALL);
                                    callintent.setData (Uri.parse ("tel:"+Mobile));
                                    if (ActivityCompat.checkSelfPermission(Home2Activity.this, android.Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){}
                                    startActivity (callintent);
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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
