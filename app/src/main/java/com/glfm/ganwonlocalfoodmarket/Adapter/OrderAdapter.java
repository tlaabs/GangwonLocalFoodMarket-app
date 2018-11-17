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

import com.glfm.ganwonlocalfoodmarket.Activity.OrderManageForSeller;
import com.glfm.ganwonlocalfoodmarket.Object.OrderVO;
import com.glfm.ganwonlocalfoodmarket.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{

    private Context parent;

    private List<OrderVO> mOrdertList;

    public OrderAdapter(Context parent, List<OrderVO> list){
        this.parent = parent;
        this.mOrdertList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_order_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;

    }

    @NonNull


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final OrderVO item = mOrdertList.get(i);
        viewHolder.itemBoxView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(parent, OrderManageForSeller.class);
                i.putExtra("item",item);
                parent.startActivity(i);
            }
        });
        viewHolder.orderIdView.setText(item.getAddress());
        viewHolder.whoView.setText(item.getCard());
        viewHolder.productNameView.setText(item.getProduct_name());
        viewHolder.quantityView.setText(item.getQuantity());
        viewHolder.totalView.setText(item.getTotal());
        viewHolder.stateView.setText(item.getState());
    }

    @Override
    public int getItemCount() {
        return mOrdertList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout itemBoxView;
        public TextView orderIdView;
        public TextView whoView;
        public TextView productNameView;
        public TextView quantityView;
        public TextView totalView;
        public TextView stateView;


        public ViewHolder(View itemView){
            super(itemView);
            itemBoxView = itemView.findViewById(R.id.item_box);
            orderIdView = itemView.findViewById(R.id.order_id);
            whoView= itemView.findViewById(R.id.who);
            productNameView = itemView.findViewById(R.id.product_name);
            quantityView= itemView.findViewById(R.id.quantity);
            totalView = itemView.findViewById(R.id.total);
            stateView= itemView.findViewById(R.id.state);

        }
    }
}
