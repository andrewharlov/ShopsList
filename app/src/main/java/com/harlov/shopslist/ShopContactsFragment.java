package com.harlov.shopslist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ShopContactsFragment extends Fragment {

    public ShopContactsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop_contacts, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button websiteButton = (Button) view.findViewById(R.id.website_button);

        if (ShopDetails.selectedShop.getWebsite() == null){
            websiteButton.setVisibility(View.GONE);
        }
        TextView shopDetails = (TextView) view.findViewById(R.id.shop_details_text_view);
        shopDetails.setText("Address : " + ShopDetails.selectedShop.getAddress());
        super.onViewCreated(view, savedInstanceState);
    }
}
