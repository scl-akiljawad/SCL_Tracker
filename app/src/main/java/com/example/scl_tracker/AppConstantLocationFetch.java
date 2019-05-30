package com.example.scl_tracker;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class AppConstantLocationFetch extends AsyncTask<GetUserInfo,Void,GetUserLocation> {
    String result = "";
    Context context;
    private TaskCompleted mCallBack;

    AppConstantLocationFetch(Context context) {
        this.context = context;
        this.mCallBack=(TaskCompleted)context;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(GetUserLocation getUserLocation) {
        Log.i("GetUserLocation :",getUserLocation.getPhone());
        mCallBack.onTaskComplete(getUserLocation);
    }

    @Override
    protected GetUserLocation doInBackground(GetUserInfo... params) {
        try {
            URL url = new URL(params[0].url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            JSONObject json = new JSONObject();
            json.put("phone", params[0].phone);
            Log.i("JSON", json.toString());
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            os.writeBytes(json.toString());
            os.flush();
            os.close();
            InputStream inputStream = new BufferedInputStream(conn.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append((line + "\n"));
            }

            inputStream.close();
            result = sb.toString();


            JSONArray jsonArray = new JSONArray(result);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String phone = jsonObject.getString("phone");
                double lat = jsonObject.getDouble("lat");
                double lon = jsonObject.getDouble("lon");
                String geolocation = jsonObject.getString("geolocation");
                String created_at = jsonObject.getString("created_at");
                String status = jsonObject.getString("status");
                GetUserLocation getUserLocation=new GetUserLocation(id,phone,lat,lon,geolocation,created_at,status);
                return getUserLocation;
            }

            //Log.i("JSON", String.valueOf(getUserLocation.getLat()));
            //Log.i("LINE", result);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
