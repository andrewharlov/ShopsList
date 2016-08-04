package com.harlov.shopslist;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

public class BackgroundTaskShops extends AsyncTask<Void,Shop,Void>{
    Context context;
    Activity activity;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Shop> arrayList = new ArrayList<>();
    String json_url_shops = "http://aschoolapi.appspot.com/stores";

    public BackgroundTaskShops(Context context){
        this.context = context;
        this.activity = (Activity) context;
    }

    @Override
    protected void onPreExecute() {
        recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerViewShops);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerAdapterShops(arrayList, context);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected Void doInBackground(Void... params) {

        try {
            URL url_stores = new URL(json_url_shops);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url_stores.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }

            httpURLConnection.disconnect();
            String json_string = stringBuilder.toString().trim();
            JSONArray jsonArray = new JSONArray(json_string);

            int count = 0;
            while (count < jsonArray.length()){
                JSONObject jsonObject = jsonArray.getJSONObject(count);
                count++;

                Shop shop = new Shop(jsonObject.getInt("id"), jsonObject.getString("name"),
                        jsonObject.getString("address"), jsonObject.getString("phone"),
                        (jsonObject.has("website") ? jsonObject.getString("website") : null),
                        jsonObject.getJSONObject("location").getInt("latitude"),
                        jsonObject.getJSONObject("location").getInt("longitude"));

                //-----------------------------------------------------------------------
                String json_url_instruments = "http://aschoolapi.appspot.com/stores/"
                        + shop.getId() + "/instruments";
                URL url_instruments = new URL(json_url_instruments);
                httpURLConnection = (HttpURLConnection) url_instruments.openConnection();
                inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line + "\n");
                }
                httpURLConnection.disconnect();
                String json_string_instruments = stringBuilder.toString().trim();
                JSONArray jsonArrayInstruments = new JSONArray(json_string_instruments);
                shop.setTotalItems(jsonArrayInstruments.length());
                //----------------------------------------------------------------------

                publishProgress(shop);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Shop... values) {
        arrayList.add(values[0]);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        ShopList.shopsArrayList = arrayList;
        super.onPostExecute(aVoid);
    }
}
