package com.harlov.shopslist;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
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

public class BackgroundTaskShopItems extends AsyncTask<Void, ShopItem, Void>{
    Context context;
    Activity activity;
    int shopId;
    ShopItemsFragment shopItemsFragment;
    static RecyclerView.Adapter adapter;
    static ArrayList<ShopItem> arrayList = new ArrayList<>();

    public BackgroundTaskShopItems(Context context, ShopItemsFragment shopItemsFragment, int shopId){
        this.context = context;
        this.activity = (Activity) context;
        this.shopItemsFragment = shopItemsFragment;
        this.shopId = shopId;
    }

    @Override
    protected void onPreExecute() { arrayList.clear(); }

    @Override
    protected Void doInBackground(Void... params) {
        String json_url = "http://aschoolapi.appspot.com/stores/" + this.shopId + "/instruments";
        try {
            URL url_instruments = new URL(json_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url_instruments.openConnection();
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

                ShopItem shopItem = new ShopItem(jsonObject.getJSONObject("instrument").getInt("id"),
                        jsonObject.getJSONObject("instrument").getString("brand"),
                        jsonObject.getJSONObject("instrument").getString("model"),
                        jsonObject.getJSONObject("instrument").getString("type"),
                        jsonObject.getJSONObject("instrument").getDouble("price"),
                        jsonObject.getInt("quantity"));

                publishProgress(shopItem);
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
    protected void onProgressUpdate(ShopItem... values) {
        arrayList.add(values[0]);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        ShopDetails.shopItemsArrayList = arrayList;
        super.onPostExecute(aVoid);
    }
}
