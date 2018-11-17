package com.glfm.ganwonlocalfoodmarket.Retrofit;

import android.content.Context;

import com.glfm.ganwonlocalfoodmarket.Object.OrderVO;
import com.glfm.ganwonlocalfoodmarket.Object.Login;
import com.glfm.ganwonlocalfoodmarket.Object.OnlyIdDTO;
import com.glfm.ganwonlocalfoodmarket.Object.ProductItem;
import com.glfm.ganwonlocalfoodmarket.Object.SignUp;
import com.glfm.ganwonlocalfoodmarket.Object.UserVO;
import com.glfm.ganwonlocalfoodmarket.Service.RetroBaseApiService;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {
    private RetroBaseApiService apiService;
    public static String baseUrl = RetroBaseApiService.Base_URL;
    private static Context mContext;
    private static Retrofit retrofit;


    private static class SingletonHolder {
        private static RetroClient INSTANCE = new RetroClient(mContext);
    }

    public static RetroClient getInstance(Context context) {
        if (context != null) {
            mContext = context;
        }
        return SingletonHolder.INSTANCE;
    }

    private RetroClient(Context context) {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    public RetroClient createBaseApi() {
        apiService = create(RetroBaseApiService.class);
        return this;
    }

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     */
    public  <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }

    public void uploadProduct(MultipartBody.Part file,
                           String id,
                           String name,
                           String detail,
                           String unit,
                           String price,
                           final RetroCallback callback) {
        apiService.uploadProduct(file,id,name,detail,unit,price).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void readProduct(String id, final RetroCallback callback) {
        apiService.readProduct(id).enqueue(new Callback<ProductItem>() {

            @Override
            public void onResponse(Call<ProductItem> call, Response<ProductItem> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<ProductItem> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void readProductBySellerId(String id, final RetroCallback callback) {
        apiService.readProductBySellerId(id).enqueue(new Callback<ProductItem>() {

            @Override
            public void onResponse(Call<ProductItem> call, Response<ProductItem> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<ProductItem> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void order(OrderVO order, final RetroCallback callback) {
        apiService.order(order).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void getOrders(String product_id, final RetroCallback callback) {
        apiService.getOrders(product_id).enqueue(new Callback<List<OrderVO>>() {

            @Override
            public void onResponse(Call<List<OrderVO>> call, Response<List<OrderVO>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<OrderVO>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void getOrdersByCustomer(String user_id, final RetroCallback callback) {
        apiService.getOrdersByCustomer(user_id).enqueue(new Callback<List<OrderVO>>() {

            @Override
            public void onResponse(Call<List<OrderVO>> call, Response<List<OrderVO>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<OrderVO>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void updateState(String order_id, String state, final RetroCallback callback) {
        apiService.updateState(order_id,state).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onError(t);
            }
        });
    }


    public void getState2(String order_id, final RetroCallback callback) {
        apiService.getState2(order_id).enqueue(new Callback<OrderVO>() {

            @Override
            public void onResponse(Call<OrderVO> call, Response<OrderVO> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<OrderVO> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    //회원가입

    public void signup(SignUp dto, final RetroCallback callback) {
        apiService.signup(dto).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    //로그인
    public void login(Login dto, final RetroCallback callback) {
        apiService.login(dto).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onError(t);
            }
        });
    }


    //유저 프로필 가져오기
    public void getProfile(OnlyIdDTO dto, final RetroCallback callback) {
        apiService.getProfile(dto).enqueue(new Callback<UserVO>() {

            @Override
            public void onResponse(Call<UserVO> call, Response<UserVO> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<UserVO> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    //유저 프로필 업데이트
    public void updateProfile(UserVO dto, final RetroCallback callback) {
        apiService.updateProfile(dto).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

}
