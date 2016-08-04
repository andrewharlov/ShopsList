package com.harlov.shopslist;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapterShops extends RecyclerView.Adapter <RecyclerAdapterShops.RecyclerViewHolder>{
    ArrayList<Shop> arrayList = new ArrayList<>();
    Context context;

    public RecyclerAdapterShops(ArrayList<Shop> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_layout, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view, context, arrayList);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Shop shop = arrayList.get(position);
        holder.shopName.setText(shop.getName());
    }

    @Override
    public int getItemCount() { return arrayList.size(); }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView shopName;
        ArrayList<Shop> shops = new ArrayList<Shop>();
        Context context;

        public RecyclerViewHolder(View view, Context context, ArrayList<Shop> shops){
            super(view);
            this.shops = shops;
            this.context = context;
            view.setOnClickListener(this);
            shopName = (TextView) view.findViewById(R.id.shop_name);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Shop shop = this.shops.get(position);
            Intent intent = new Intent(context, ShopDetails.class);
            intent.putExtra("id", shop.getId());
            intent.putExtra("name", shop.getName());
            intent.putExtra("address", shop.getAddress());
            intent.putExtra("phone", shop.getPhone());
            intent.putExtra("website", shop.getWebsite());
            intent.putExtra("total_items", shop.getTotalItems());
            intent.putExtra("latitude", shop.getLatitude());
            intent.putExtra("longitude", shop.getLongitude());
            this.context.startActivity(intent);
        }
    }
}
