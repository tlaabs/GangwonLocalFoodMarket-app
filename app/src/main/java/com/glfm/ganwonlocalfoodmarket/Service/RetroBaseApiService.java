package com.glfm.ganwonlocalfoodmarket.Service;

import com.glfm.ganwonlocalfoodmarket.Object.DiaryVO;
import com.glfm.ganwonlocalfoodmarket.Object.OrderVO;
import com.glfm.ganwonlocalfoodmarket.Object.Login;
import com.glfm.ganwonlocalfoodmarket.Object.OnlyIdDTO;
import com.glfm.ganwonlocalfoodmarket.Object.ProductItem;
import com.glfm.ganwonlocalfoodmarket.Object.SignUp;
import com.glfm.ganwonlocalfoodmarket.Object.UserVO;

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

    final String Base_URL = "http://172.30.1.52:8080/";
//    final String Base_URL = "http://192.168.43.97:8080/";
//    final String Base_URL = "http://172.16.22.9:8080/";

    //회원가입
    @POST("user/signup")
    Call<ResponseBody> signup(@Body SignUp dto);

    //로그인
    @POST("user/login")
    Call<ResponseBody> login(@Body Login dto);

    //유저 프로필 조회
    @POST("user/get/profile")
    Call<UserVO> getProfile(@Body OnlyIdDTO dto);

    //유저 프로필 업데이트
    @POST("user/update/profile")
    Call<ResponseBody> updateProfile(@Body UserVO vo);

    //상품 관리 - 업데이트
    @Multipart
    @POST("product/edit")
    Call<ResponseBody> uploadProduct(@Part MultipartBody.Part file,
                                     @Query("id") String id,
                                     @Query("name") String name,
                                     @Query("detail") String detail,
                                     @Query("unit") String unit,
                                     @Query("price") String price
    );

    //상품 가져오기 - seller
    @GET("product/get")
    Call<ProductItem> readProduct(@Query("id") String id);

    //상품 가져오기 - customer
    @GET("product/getBySellerId")
    Call<ProductItem> readProductBySellerId(@Query("id") String id);

    //상품 구매
    @POST("order/buy")
    Call<ResponseBody> order(@Body OrderVO order);

    //주문 조회 - 고객
    @GET("order/getsByCustomer")
    Call<List<OrderVO>> getOrdersByCustomer(@Query("user_id") String user_id);

    //주문 조회 - Seller
    @GET("order/gets")
    Call<List<OrderVO>> getOrders(@Query("seller_id") String seller_id);

    //주문 상태 변경
    @GET("order/state")
    Call<ResponseBody> updateState(@Query("order_id") String order_id,
                                   @Query("state") String state);

    //농장 일기 쓰기 - Seller
    @Multipart
    @POST("diary/add")
    Call<ResponseBody> uploadDiary(@Part MultipartBody.Part file,
                                     @Query("seller_id") String seller_id,
                                     @Query("title") String title,
                                     @Query("content") String content
    );

    //다이어리 조회 - Seller
    @GET("diary/getsForSeller")
    Call<List<DiaryVO>> getDiarysForSeller(@Query("seller_id") String seller_id);

    //다이어리 삭제 - Seller
    @GET("diary/delete")
    Call<ResponseBody> deleteDiary(@Query("diary_id") String diary_id);

    //다이어리 조회 - Customer
    @GET("diary/getsForCustomer")
    Call<List<DiaryVO>> getDiarysForCustomer(@Query("farm_id") String farm_id);

    @GET("order/state2")
    Call<OrderVO> getState2(@Query("order_id") String order_id);
//
//
}
