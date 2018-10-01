package com.glfm.ganwonlocalfoodmarket.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glfm.ganwonlocalfoodmarket.Activity.UpdateStateActivity;
import com.glfm.ganwonlocalfoodmarket.Object.BuyVO;
import com.glfm.ganwonlocalfoodmarket.R;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroCallback;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroClient;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder>{

    final static String LOG = "MyOrderAdapter";

    private Context parent;

    private List<BuyVO> mOrdertList;

    private RetroClient retroClient;

    public MyOrderAdapter(Context parent, List<BuyVO> list){
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
        final BuyVO item = mOrdertList.get(i);
//        viewHolder.itemBoxView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(parent, UpdateStateActivity.class);
//                i.putExtra("item",item);
//                parent.startActivity(i);
//            }
//        });
        viewHolder.orderIdView.setText("주문 번호 : " + item.getId());
        viewHolder.productNameView.setText("품명 : " + item.getProduct_name());
        viewHolder.quantityView.setText("수량 : " + item.getQuantity());
        viewHolder.totalView.setText("총 금액 : " + item.getTotal());

        retroClient.getState2(item.getId(), new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(LOG, t.toString());
            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                BuyVO res = (BuyVO)receivedData;
                Log.d(LOG, "성공");
                viewHolder.stateView.setText("상 태 : " + res.getState());
            }

            @Override
            public void onFailure(int code) {
                Log.d(LOG, "실패");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mOrdertList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout itemBoxView;
        public TextView orderIdView;
        public TextView productNameView;
        public TextView quantityView;
        public TextView totalView;
        public TextView stateView;

        public ViewHolder(View itemView){
            super(itemView);
            itemBoxView = itemView.findViewById(R.id.item_box);
            orderIdView = itemView.findViewById(R.id.order_id);
            productNameView = itemView.findViewById(R.id.product_name);
            quantityView= itemView.findViewById(R.id.quantity);
            totalView = itemView.findViewById(R.id.total);
            stateView= itemView.findViewById(R.id.state);

        }
    }
}
