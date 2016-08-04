package com.harlov.shopslist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class ShopList extends AppCompatActivity {

    public static ArrayList<Shop> shopsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_shop_list);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.shop_list_activity_title);

        BackgroundTaskShops backgroundTask = new BackgroundTaskShops(ShopList.this);
        backgroundTask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shoplist_map_menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.navigate_to_map){
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
