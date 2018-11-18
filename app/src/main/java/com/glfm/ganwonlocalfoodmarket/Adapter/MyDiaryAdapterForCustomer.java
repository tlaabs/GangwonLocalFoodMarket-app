package com.glfm.ganwonlocalfoodmarket.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.glfm.ganwonlocalfoodmarket.Activity.DiaryActivityForSeller;
import com.glfm.ganwonlocalfoodmarket.Object.DiaryVO;
import com.glfm.ganwonlocalfoodmarket.R;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroCallback;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroClient;

import java.util.List;

public class MyDiaryAdapterForCustomer extends RecyclerView.Adapter<MyDiaryAdapterForCustomer.ViewHolder>{

    final static String LOG = "MyDiaryAdapter";

    private Context parent;

    private List<DiaryVO> mDiaryList;

    private RetroClient retroClient;

    public MyDiaryAdapterForCustomer(Context parent, List<DiaryVO> list){
        this.parent = parent;
        this.mDiaryList = list;
        retroClient = RetroClient.getInstance(this.parent).createBaseApi();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_diary_list_for_customer,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;

    }

    @NonNull


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final DiaryVO item = mDiaryList.get(i);
        Glide
                .with(parent)
                .load(item.getImg())
                .into(viewHolder.imgView);

        viewHolder.titleView.setText("제목 : " + item.getTitle());
        viewHolder.regDateView.setText("작성일 : " + item.getRegDate());
        viewHolder.contentView.setText(item.getContent());
    }

    @Override
    public int getItemCount() {
        return mDiaryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
//        public LinearLayout itemBoxView;
        public TextView titleView;
        public TextView regDateView;
        public ImageView imgView;
        public TextView contentView;

        public ViewHolder(View itemView){
            super(itemView);
//            itemBoxView = itemView.findViewById(R.id.item_box);
            titleView = itemView.findViewById(R.id.title);
            regDateView = itemView.findViewById(R.id.reg_date);
            imgView= itemView.findViewById(R.id.img);
            contentView= itemView.findViewById(R.id.content);

        }
    }
}
