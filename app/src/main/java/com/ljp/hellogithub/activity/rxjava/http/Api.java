package com.ljp.hellogithub.activity.rxjava.http;

import java.util.WeakHashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface Api {

    @FormUrlEncoded
    @POST
    Observable<String> login(@Url String url,  @FieldMap WeakHashMap<String, Object> params);

//    @FormUrlEncoded
//    @POST
//    Observable<RegisterResponse> register(@Body RegisterRequest request);
}