package com.glfm.ganwonlocalfoodmarket.Retrofit;

public interface RetroCallback<T> {
    void onError(Throwable t);

    void onSuccess(int code, T receivedData);

    void onFailure(int code);
}
