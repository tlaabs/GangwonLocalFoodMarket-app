package com.glfm.ganwonlocalfoodmarket.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glfm.ganwonlocalfoodmarket.Activity.DetailOrderManageForSeller;
import com.glfm.ganwonlocalfoodmarket.Object.OrderVO;
import com.glfm.ganwonlocalfoodmarket.R;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroClient;

import java.util.List;

public class MyOrderForSellerAdapter extends RecyclerView.Adapter<MyOrderForSellerAdapter.ViewHolder>{

    final static String LOG = "MyOrderForSellerAdapter";

    private Context parent;

    private List<OrderVO> mOrdertList;

    private RetroClient retroClient;

    public MyOrderForSellerAdapter(Context parent, List<OrderVO> list){
        this.parent = parent;
        this.mOrdertList = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_for_seller_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;

    }

    @NonNull


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final OrderVO item = mOrdertList.get(i);
        viewHolder.itemBoxView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(parent, DetailOrderManageForSeller.class);
                i.putExtra("item",item);
                parent.startActivity(i);
            }
        });
        viewHolder.orderIdView.setText("주문 번호 : " + item.getOrder_id());
        viewHolder.orderDateView.setText("주문접수일 : " + item.getRegDate());
        viewHolder.stateView.setText("상태 : " + item.getState());
    }

    @Override
    public int getItemCount() {
        return mOrdertList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout itemBoxView;
        public TextView orderIdView;
        public TextView orderDateView;
        public TextView stateView;

        public ViewHolder(View itemView){
            super(itemView);
            itemBoxView = itemView.findViewById(R.id.item_box);
            orderIdView = itemView.findViewById(R.id.order_id);
            orderDateView= itemView.findViewById(R.id.reg_date);
            stateView= itemView.findViewById(R.id.state);

        }
    }
}
