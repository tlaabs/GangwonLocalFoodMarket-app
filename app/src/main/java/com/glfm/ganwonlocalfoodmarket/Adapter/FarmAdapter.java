package com.glfm.ganwonlocalfoodmarket.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.glfm.ganwonlocalfoodmarket.Activity.DetailMarketActivity;
import com.glfm.ganwonlocalfoodmarket.Object.DiaryVO;
import com.glfm.ganwonlocalfoodmarket.Object.FarmItem;
import com.glfm.ganwonlocalfoodmarket.Object.ProductItem;
import com.glfm.ganwonlocalfoodmarket.R;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroCallback;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroClient;

import java.util.ArrayList;
import java.util.List;

public class FarmAdapter extends RecyclerView.Adapter<FarmAdapter.ViewHolder> {
    final static String LOG = "FarmAdapter";
    private Context parent;

    private List<FarmItem> mFarmList;
    private RetroClient retroClient;

    public FarmAdapter(Context parent, List<FarmItem> farmList) {
        this.parent = parent;
        this.mFarmList = farmList;
        retroClient = RetroClient.getInstance(parent).createBaseApi();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.farm_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;

    }

    @NonNull


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final FarmItem item = mFarmList.get(i);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(parent, DetailMarketActivity.class);
                i.putExtra("item", item);
                parent.startActivity(i);
            }
        });

        if(item.getImg() != null){
            Glide
                    .with(parent)
                    .load(item.getImg())
                    .apply(RequestOptions.centerCropTransform())
                    .into(viewHolder.farmImg);
        }else{
            Glide
                    .with(parent)
                    .load(R.drawable.farm)
                    .apply(RequestOptions.centerCropTransform())
                    .into(viewHolder.farmImg)
                    .onLoadFailed(parent.getResources().getDrawable(R.drawable.farm));
        }

        if(item.getPrice() != null) {
            viewHolder.priceView.setText("판매가격 : " + item.getPrice()+"원");
        }else{
            viewHolder.priceView.setText("판매가격 : -원");
        }

        //
        viewHolder.productView.setText(item.getProduct());
        viewHolder.addressView.setText(item.getAddress());

    }


    @Override
    public int getItemCount() {
        return mFarmList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout view;
        public ImageView farmImg;
        public TextView productView;
        public TextView priceView;
        public TextView addressView;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.view);
            farmImg = itemView.findViewById(R.id.img);
            productView = itemView.findViewById(R.id.product);
            priceView = itemView.findViewById(R.id.price);
            addressView = itemView.findViewById(R.id.address);
        }
    }
}
