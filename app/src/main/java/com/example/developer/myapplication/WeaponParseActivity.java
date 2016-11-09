package com.example.developer.myapplication;

import android.os.AsyncTask;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WeaponParseActivity extends AsyncTask<Void,Void,List<BELWeaponItem>> {

    private final String URL_TO_HIT = "https://redarmyserver.appspot.com/_ah/api/myApi/v1/torretinfocollection";
    private List<BELWeaponItem> listWeapon = new ArrayList<>();

    public List<BELWeaponItem> getListWeapon() {
        return listWeapon;
    }
    public void setListWeapon(List<BELWeaponItem> listWeapon) {
        this.listWeapon = listWeapon;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<BELWeaponItem> doInBackground(Void... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(URL_TO_HIT);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line ="";
            while ((line = reader.readLine()) != null){
                buffer.append(line);
            }

            String finalJson = buffer.toString();

            JSONObject parentObject = new JSONObject(finalJson);
            JSONArray parentArray = parentObject.getJSONArray("items");

            List<BELWeaponItem> weaponItemList = new ArrayList<>();

            Gson gson = new Gson();
            for(int i=1; i<=parentArray.length(); i++) {
                JSONObject finalObject = parentArray.getJSONObject(i-1);
                BELWeaponItem weaponItem = gson.fromJson(finalObject.toString(), BELWeaponItem.class);
                weaponItemList.add(weaponItem);
            }
            listWeapon = weaponItemList;
            return weaponItemList;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
            try {
                if(reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  null;
    }
    @Override
    protected void onPostExecute(final List<BELWeaponItem> result) {
        super.onPostExecute(result);
        if(result != null) {
            listWeapon = result;
        }
    }
}
