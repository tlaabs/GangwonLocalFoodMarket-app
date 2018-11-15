package com.glfm.ganwonlocalfoodmarket.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.glfm.ganwonlocalfoodmarket.Adapter.FarmAdapter;
import com.glfm.ganwonlocalfoodmarket.Object.FarmItem;
import com.glfm.ganwonlocalfoodmarket.R;
import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{

    private SQLiteDatabase db;

    private EditText searchInput;
    private Button searchBtn;
    private RecyclerView list;

    private ArrayList<FarmItem> farmList = new ArrayList<FarmItem>();
    private ArrayList<FarmItem> searchFarmList = new ArrayList<FarmItem>();

    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        iniNavItem();
        initView();



        initDatabase();
        insertDataFromFile();
        farmList = getFarmListFromDB();
        initList(farmList);

    }
    private void iniNavItem(){
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.activity_main_drawer);
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //first layout 메뉴 버튼이 눌리면 실행됨
        if (id == R.id.nav_first_layout) {
            Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_second_layout) {
            Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);//Drawer를 닫음
        return true;
    }
    private void initView(){
        searchInput = findViewById(R.id.search_input);
        searchBtn = findViewById(R.id.search_btn);
        list = findViewById(R.id.list);
    }

    private void initDatabase() {
        db = openOrCreateDatabase(getString(R.string.db_name), MODE_PRIVATE, null);

        db.execSQL("CREATE TABLE IF NOT EXISTS " + getString(R.string.table_name) + "("
                + "ID TEXT PRIMARY KEY, "
                + "NAME TEXT, "
                + "PRODUCT TEXT, "
                + "ADDRESS TEXT)");

        db.execSQL("DELETE FROM " + getString(R.string.table_name) + ";");

        //order history
        db.execSQL("CREATE TABLE IF NOT EXISTS " + "tbl_order" + "("
                + "ID TEXT PRIMARY KEY, "
                + "PRODUCT TEXT, "
                + "QUANTITY TEXT, "
                + "PRICE TEXT)");

    }

    private void insertDataFromFile() {
        ContentValues recordValues;
        try {
            InputStream is = getResources().openRawResource(R.raw.data);
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            CSVReader reader = new CSVReader(isr);
            String[] nextLine = null;
            reader.readNext(); // skip first line
            db.beginTransaction();
            while ((nextLine = reader.readNext()) != null) {
                recordValues = new ContentValues();

                recordValues.put("ID", nextLine[0]);
                recordValues.put("NAME", nextLine[15]);
                recordValues.put("PRODUCT", nextLine[11]);
                recordValues.put("ADDRESS", nextLine[9]);

                Log.d("sss",nextLine[0] + "|" + nextLine[15] + "|" + nextLine[9] + "|" + nextLine[11]);

                db.insert(getString(R.string.table_name), null, recordValues);
            }
            db.setTransactionSuccessful();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    private ArrayList<FarmItem> getFarmListFromDB() {
        ArrayList<FarmItem> arr = new ArrayList<FarmItem>();

        SQLiteDatabase db = openOrCreateDatabase(getString(R.string.db_name), MODE_PRIVATE, null);
        String sql = "SELECT * FROM " + getString(R.string.table_name);
        Cursor cursor = db.rawQuery(sql, null);
        int count = cursor.getCount();
        if (cursor != null && count != 0) {
            cursor.moveToFirst();
            for (int i = 0; i < count; i++) {
                FarmItem item = new FarmItem();
                item.setId(cursor.getString(0));
                item.setName(cursor.getString(1));
                item.setProduct(cursor.getString(2));
                item.setAddress(cursor.getString(3));
//                Log.d("lolz",item.getId() + "|" + item.getName() + "|" + item.getProduct() + "|" + item.getAddress());
                arr.add(item);
                cursor.moveToNext();
            }
        }

        return arr;
    }

    private void initList(ArrayList<FarmItem> farmList){
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new FarmAdapter(this,farmList));
        list.invalidate();
    }

    @OnClick(R.id.search_btn)
    public void searchBtnClicked(){
        String keyword = searchInput.getText().toString();
        if(keyword.equals("")){
            initList(farmList);
        }else{
            initList(filterSearch(keyword));
        }
    }

    private ArrayList<FarmItem> filterSearch(String keyword){
        ArrayList<FarmItem> arr = new ArrayList<FarmItem>();

        for(int i = 0 ; i < farmList.size(); i++){
            FarmItem item = farmList.get(i);
            if(item.getProduct().equals(keyword)){
                arr.add(item);
            }
        }
        searchFarmList = arr;
        return arr;

    }
}
