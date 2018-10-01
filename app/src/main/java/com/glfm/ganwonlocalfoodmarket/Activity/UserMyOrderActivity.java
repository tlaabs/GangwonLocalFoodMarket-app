package com.glfm.ganwonlocalfoodmarket.Activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.glfm.ganwonlocalfoodmarket.Adapter.FarmAdapter;
import com.glfm.ganwonlocalfoodmarket.Adapter.MyOrderAdapter;
import com.glfm.ganwonlocalfoodmarket.Object.BuyVO;
import com.glfm.ganwonlocalfoodmarket.Object.FarmItem;
import com.glfm.ganwonlocalfoodmarket.R;
import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class UserMyOrderActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    ArrayList<BuyVO> orderList;

    private RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_my_order);
        init();
        initView();
        initDatabase();
        orderList = getOrderListFromDB();

        initList(orderList);
    }

    public void init(){

    }

    public void initView(){
        list = findViewById(R.id.list);
    }

    private void initDatabase() {
        db = openOrCreateDatabase(getString(R.string.db_name), MODE_PRIVATE, null);
    }

    private ArrayList<BuyVO> getOrderListFromDB() {
        ArrayList<BuyVO> arr = new ArrayList<BuyVO>();

        SQLiteDatabase db = openOrCreateDatabase(getString(R.string.db_name), MODE_PRIVATE, null);
        String sql = "SELECT * FROM " + "tbl_order";
        Cursor cursor = db.rawQuery(sql, null);
        int count = cursor.getCount();
        if (cursor != null && count != 0) {
            cursor.moveToFirst();
            for (int i = 0; i < count; i++) {
                BuyVO item = new BuyVO();
                item.setId(cursor.getString(0));
                item.setProduct_name(cursor.getString(1));
                item.setQuantity(cursor.getString(2));
                item.setTotal(cursor.getString(3));
                Log.d("lolz", item.getId() + "|" + item.getProduct_name() + "|" + item.getQuantity() + "|" + item.getTotal());
                arr.add(item);
                cursor.moveToNext();
            }
        }
        return arr;
    }

    private void initList(ArrayList<BuyVO> arr){
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new MyOrderAdapter(this,arr));
        list.invalidate();
    }

}
