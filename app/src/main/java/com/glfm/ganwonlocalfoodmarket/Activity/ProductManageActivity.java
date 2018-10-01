package com.glfm.ganwonlocalfoodmarket.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.glfm.ganwonlocalfoodmarket.R;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroCallback;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroClient;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProductManageActivity extends AppCompatActivity {

    private String id;

    final static String LOG = "WriteReportActivity";
    final static int PICK_FROM_ALBUM = 0;
    final static int MY_PERMISSIONS_REQUEST_READ_EXT_STORAGE = 1;

    @BindView(R.id.name)
    EditText nameView;
    @BindView(R.id.unit)
    EditText unitView;
    @BindView(R.id.price)
    EditText priceView;
    @BindView(R.id.img)
    ImageView imgView;
    @BindView(R.id.submit)
    Button submitView;

    private Context mContext;

    private Uri mImageCaptureUri;
    private RetroClient retroClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_manage);
        mContext = this;
        ButterKnife.bind(this);
        init();
        initView();

        requestReadExternalStoragePermission();

        retroClient = RetroClient.getInstance(this).createBaseApi();
    }

    public void init(){
        Intent i = getIntent();
        id = i.getStringExtra("id");
    }

    public void initView(){

    }

    @OnClick(R.id.submit)
    void submit() {
        String id = this.id;
        String name = nameView.getText().toString();
        String unit = unitView.getText().toString();
        String price = priceView.getText().toString();

        if(!checkValidation()){
            Toast.makeText(getApplicationContext(),"빠진 내용이 있어요!",Toast.LENGTH_SHORT).show();
            return;
        }

        //IMAGE
        String realPath = getRealPathFromURI(mImageCaptureUri);
        Log.d(LOG,realPath);
        File file = new File(realPath);

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        retroClient.uploadForm(body,id,name,unit,price, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(LOG, t.toString());
            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Log.d(LOG, "성공");
                Toast.makeText(mContext, "등록 완료", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFailure(int code) {
                Log.d(LOG, "실패");
            }
        });
    }

    private boolean checkValidation(){
        if(nameView.getText().toString().equals("")) return false;
        if(unitView.getText().toString().equals("")) return false;
        if(priceView.getText().toString().equals("")) return false;
        if(mImageCaptureUri == null) return false;
        return true;
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(mContext, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }


    @OnClick(R.id.img)
    void selectImg() {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(i, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_FROM_ALBUM) {
            if (resultCode == RESULT_OK) {
                mImageCaptureUri = data.getData();
                Glide.with(this)
                        .load(mImageCaptureUri)
                        .into(imgView);
            }
        }
    }

    private void requestReadExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXT_STORAGE);
                // MY_PERMISSIONS_REQUEST_READ_EXT_STORAGE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXT_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


}
