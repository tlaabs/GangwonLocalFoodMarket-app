package com.glfm.ganwonlocalfoodmarket.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.glfm.ganwonlocalfoodmarket.Activity.DetailMarketActivity;
import com.glfm.ganwonlocalfoodmarket.Object.FarmItem;
import com.glfm.ganwonlocalfoodmarket.R;

import java.util.List;

public class FarmAdapter extends RecyclerView.Adapter<FarmAdapter.ViewHolder>{

    private Context parent;

    private List<FarmItem> mFarmList;

    public FarmAdapter(Context parent, List<FarmItem> farmList){
        this.parent = parent;
        this.mFarmList = farmList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.farm_list,parent,false);
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
                i.putExtra("item",item);
                parent.startActivity(i);
            }
        });
        viewHolder.productView.setText("품목 : " + item.getProduct());
        viewHolder.nameView.setText("사업자 명 : " + item.getName());
        viewHolder.addressView.setText(item.getAddress());

    }

    @Override
    public int getItemCount() {
        return mFarmList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout view;
        public TextView productView;
        public TextView nameView;
        public TextView addressView;

        public ViewHolder(View itemView){
            super(itemView);
            view = itemView.findViewById(R.id.view);
            productView = itemView.findViewById(R.id.product);
            nameView = itemView.findViewById(R.id.name);
            addressView = itemView.findViewById(R.id.address);
        }
    }
}
