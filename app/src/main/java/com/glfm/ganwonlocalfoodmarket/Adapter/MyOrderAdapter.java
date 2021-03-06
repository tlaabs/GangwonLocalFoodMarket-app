package com.glfm.ganwonlocalfoodmarket.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glfm.ganwonlocalfoodmarket.Object.OrderVO;
import com.glfm.ganwonlocalfoodmarket.R;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroCallback;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroClient;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder>{

    final static String LOG = "MyOrderAdapter";

    private Context parent;

    private List<OrderVO> mOrdertList;

    private RetroClient retroClient;

    public MyOrderAdapter(Context parent, List<OrderVO> list){
        this.parent = parent;
        this.mOrdertList = list;
        retroClient = RetroClient.getInstance(this.parent).createBaseApi();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;

    }

    @NonNull


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final OrderVO item = mOrdertList.get(i);
//        viewHolder.itemBoxView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(parent, UpdateStateActivity.class);
//                i.putExtra("item",item);
//                parent.startActivity(i);
//            }
//        });
        viewHolder.orderIdView.setText("주문 번호 : " + item.getOrder_id());
        viewHolder.orderDateView.setText("주문접수일 : " + item.getRegDate());
        viewHolder.productNameView.setText("품명 : " + item.getProduct_name());
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
        public TextView productNameView;
        public TextView stateView;

        public ViewHolder(View itemView){
            super(itemView);
            itemBoxView = itemView.findViewById(R.id.item_box);
            orderIdView = itemView.findViewById(R.id.order_id);
            productNameView = itemView.findViewById(R.id.product_name);
            orderDateView= itemView.findViewById(R.id.reg_date);
            stateView= itemView.findViewById(R.id.state);

        }
    }
}
