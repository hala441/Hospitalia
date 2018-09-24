package com.example.boody_laptop.hospitalia;

import android.os.AsyncTask;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * Created by j on 29/04/2018.
 */

public class PostRequestHandler extends AsyncTask<Void, Void, String> {
    // Request URL
    String url;
    // Key, Value pair
    HashMap<String, String> requestedParams;

    PostRequestHandler(String url, HashMap<String, String> params){
        this.url = url;
        this.requestedParams = params;
        // Log.d("Input Box", designation);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(Void... voids) {

        // Now Send a post request
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try {
            String s = backgroundWorker.postRequestHandler(url, requestedParams);
//                    Log.d("HashMap--------", requestedParams.get("salary"));
//                    Log.d("Results------", s.toString());
//                    Toast.makeText(getApplicationContext(), s.toString(), Toast.LENGTH_LONG).show();
            return s.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //progressBar.setVisibility(GONE);
        //Toast.makeText(getApplicationContext, "Result : " + s, Toast.LENGTH_LONG).show();
        //Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();
    }
}