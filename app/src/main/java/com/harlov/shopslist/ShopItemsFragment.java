package com.harlov.shopslist;

import android.os.Bundle;
import android.support.v4.app.Fragment;;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ShopItemsFragment extends Fragment {

    public ShopItemsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop_items, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewShopItems);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        BackgroundTaskShopItems.adapter = new RecyclerAdapterShopItems(BackgroundTaskShopItems.arrayList, getContext());
        RecyclerView.Adapter adapter = BackgroundTaskShopItems.adapter;
        recyclerView.setAdapter(adapter);
        super.onViewCreated(view, savedInstanceState);
    }
}
