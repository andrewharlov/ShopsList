package com.harlov.shopslist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapterShopItems extends RecyclerView.Adapter <RecyclerAdapterShopItems.RecyclerViewHolder>{
    ArrayList<ShopItem> arrayList = new ArrayList<>();
    Context context;

    public RecyclerAdapterShopItems(ArrayList<ShopItem> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_item_layout, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view, context, arrayList);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        ShopItem shopItem = arrayList.get(position);
        String price = Double.toString(shopItem.getPrice()) + " $";

        holder.itemModel.setText(shopItem.getModel());
        holder.itemBrand.setText(shopItem.getBrand());
        holder.itemPrice.setText(price);
    }

    @Override
    public int getItemCount() { return arrayList.size(); }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView itemBrand;
        TextView itemModel;
        TextView itemPrice;
        ArrayList<ShopItem> shopItems = new ArrayList<>();
        Context context;

        public RecyclerViewHolder(View itemView, Context context, ArrayList<ShopItem> shopItems) {
            super(itemView);
            this.context = context;
            this.shopItems = shopItems;

            itemBrand = (TextView) itemView.findViewById(R.id.shop_item_brand);
            itemModel = (TextView) itemView.findViewById(R.id.shop_item_model);
            itemPrice = (TextView) itemView.findViewById(R.id.shop_item_price);
        }
    }
}
