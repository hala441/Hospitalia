package com.example.boody_laptop.hospitalia;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hsalf.smilerating.SmileRating;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView username, email;
    List<PatientProfileActivity> List=new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter2 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);

        CardView readings = (CardView) findViewById(R.id.readingscardId);
        CardView chat = (CardView) findViewById(R.id.ChatcardId);
        CardView map = (CardView) findViewById(R.id.mapscardId);

        final Intent intent = new Intent(this, PatientsList.class);
        final Intent intent3 = new Intent(this, BasicChat.class);
        final Intent intent2 = new Intent(this, mapsActivity.class);

        readings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
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
                startActivity(intent3);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        } else if (id == R.id.Log_out){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("finish", true);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
            startActivity(intent);
            finish();
        } else if (id == R.id.help){
            Intent myintent = new Intent(this,HelpActivity.class);
            startActivity(myintent);

        } else if (id == R.id.exit){
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
            Intent myintent = new Intent(this,Account2Activity.class);
            startActivity(myintent);
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, Settings.class);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(HomeActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.popup_rating, null);
            final SmileRating newRate = (SmileRating) mView.findViewById(R.id.smile_rating);
            Button rate = (Button) mView.findViewById(R.id.button);

            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            rate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(HomeActivity.this,
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
            Intent myintent = new Intent(this,FeedBackActivity.class);
            startActivity(myintent);

        } else if (id == R.id.nav_log_out) {
            SharedPrefManager2.getInstance(this).logout();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
