package com.glfm.ganwonlocalfoodmarket.Service;

import com.glfm.ganwonlocalfoodmarket.Object.BuyVO;
import com.glfm.ganwonlocalfoodmarket.Object.ProductItem;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RetroBaseApiService {

    final String Base_URL = "http://devsim.cafe24.com/GLFM/";

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
//    @POST("report/delete")
//    Call<ResponseBody> deleteReport(@Body DeleteReportDTO report);
}
