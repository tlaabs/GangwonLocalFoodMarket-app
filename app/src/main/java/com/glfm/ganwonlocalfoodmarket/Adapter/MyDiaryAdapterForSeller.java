package com.glfm.ganwonlocalfoodmarket.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.glfm.ganwonlocalfoodmarket.Activity.DiaryActivityForSeller;
import com.glfm.ganwonlocalfoodmarket.Object.DiaryVO;
import com.glfm.ganwonlocalfoodmarket.R;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroCallback;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroClient;

import java.util.List;

public class MyDiaryAdapterForSeller extends RecyclerView.Adapter<MyDiaryAdapterForSeller.ViewHolder>{

    final static String LOG = "MyDiaryAdapter";

    private Context parent;

    private List<DiaryVO> mDiaryList;

    private RetroClient retroClient;

    public MyDiaryAdapterForSeller(Context parent, List<DiaryVO> list){
        this.parent = parent;
        this.mDiaryList = list;
        retroClient = RetroClient.getInstance(this.parent).createBaseApi();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_diary_list_for_seller,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;

    }

    @NonNull


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final DiaryVO item = mDiaryList.get(i);
        viewHolder.deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retroClient.deleteDiary(item.getDiary_id(), new RetroCallback() {
                    @Override
                    public void onError(Throwable t) {
                        Log.d("epzo", t.toString());
                    }

                    @Override
                    public void onSuccess(int code, Object receivedData) {
                        Log.d(LOG, "성공");
                        Toast.makeText(parent, "삭제 완료.", Toast.LENGTH_SHORT).show();
                        ((DiaryActivityForSeller)parent).invalidateResume();
                    }

                    @Override
                    public void onFailure(int code) {
                        Log.d(LOG, "실패");
                    }
                });
            }
        });

        Glide
                .with(parent)
                .load(item.getImg())
                .into(viewHolder.imgView);

        viewHolder.titleView.setText(item.getTitle());
        viewHolder.regDateView.setText("작성일자 : " + item.getRegDate());
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

        public ImageView deleteView;

        public ViewHolder(View itemView){
            super(itemView);
//            itemBoxView = itemView.findViewById(R.id.item_box);
            titleView = itemView.findViewById(R.id.title);
            regDateView = itemView.findViewById(R.id.reg_date);
            imgView= itemView.findViewById(R.id.img);
            contentView= itemView.findViewById(R.id.content);

            deleteView = itemView.findViewById(R.id.delete);

        }
    }
}
