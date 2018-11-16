package com.glfm.ganwonlocalfoodmarket.Service;

import com.glfm.ganwonlocalfoodmarket.Object.BuyVO;
import com.glfm.ganwonlocalfoodmarket.Object.Login;
import com.glfm.ganwonlocalfoodmarket.Object.ProductItem;
import com.glfm.ganwonlocalfoodmarket.Object.SignUp;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RetroBaseApiService {

//    final String Base_URL = "http://172.30.1.52:8080/";
//    final String Base_URL = "http://192.168.43.97:8080/";
    final String Base_URL = "http://172.16.22.9:8080/";

    //회원가입
    @POST("user/signup")
    Call<ResponseBody> signup(@Body SignUp dto);

    //로그인
    @POST("user/login")
    Call<ResponseBody> login(@Body Login dto);

    @POST("order/buy")
    Call<BuyVO> order(@Body BuyVO order);
//
    @GET("product/get")
    Call<ProductItem> readProduct(@Query("id") String id);

    @GET("order/state2")
    Call<BuyVO> getState2(@Query("order_id") String order_id);

    @GET("order/state")
    Call<ResponseBody> updateState(@Query("order_id") String id,
                                   @Query("state") String state);

    @Multipart
    @POST("product/add")
    Call<ResponseBody> uploadForm(@Part MultipartBody.Part file,
                                  @Query("id") String id,
                                  @Query("name") String name,
                                  @Query("unit") String unit,
                                  @Query("price") String price);

    @GET("order/gets")
    Call<List<BuyVO>> getOrders(@Query("product_id") String product_id);
//
//
}
