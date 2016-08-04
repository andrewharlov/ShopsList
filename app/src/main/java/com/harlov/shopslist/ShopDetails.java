package com.harlov.shopslist;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class ShopDetails extends AppCompatActivity {

    private static final int REQUEST_PHONE = 155;
    static Shop selectedShop;
    public static ArrayList<ShopItem> shopItemsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_shop_details);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        ShopItemsFragment shopItemsFragment = new ShopItemsFragment();
        ShopContactsFragment shopContactsFragment = new ShopContactsFragment();

        viewPagerAdapter.addFragments(shopItemsFragment,
                getIntent().getIntExtra("total_items", -1) + " Items");
        viewPagerAdapter.addFragments(shopContactsFragment, "Contacts");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        selectedShop = new Shop(getIntent().getIntExtra("id", -1),
                getIntent().getStringExtra("name"),
                getIntent().getStringExtra("address"),
                getIntent().getStringExtra("phone"),
                getIntent().getStringExtra("website"),
                getIntent().getIntExtra("latitude", 0),
                getIntent().getIntExtra("longitude", 0));

        getSupportActionBar().setTitle(selectedShop.getName());
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BackgroundTaskShopItems backgroundTask = new BackgroundTaskShopItems(
                ShopDetails.this, shopItemsFragment, selectedShop.getId());
        backgroundTask.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void phoneCall(View view) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + selectedShop.getPhone()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(intent);
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                    Toast.makeText(this, R.string.need_phone_permission_toast, Toast.LENGTH_SHORT).show();
                }
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PHONE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            } else {
                Toast.makeText(this, R.string.permission_not_granted_toast, Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void makePhoneCall(){
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + selectedShop.getPhone()));
        try{
            startActivity(intent);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

    public void sendEmail(View view) {
        String emailAddress = "dronindigo@gmail.com";
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + emailAddress));
        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    public void openWebsite(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(selectedShop.getWebsite()));
        startActivity(intent);
    }
}
