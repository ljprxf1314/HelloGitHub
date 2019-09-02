package com.ljp.hellogithub.net;


import com.ljp.hellogithub.net.rx.RxRestService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/8/3
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class RestCreate {

    /**
     * 构建okhttp
     */
    private static final class OKHttpHolder {
        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        //拦截器
        //        private static final ArrayList<Interceptor> INTERCEPTORS =

        private static final OkHttpClient.Builder getOkHttpBuilder() {
            return BUILDER;
        }

        private static final OkHttpClient OK_HTTP_CLIENT = getOkHttpBuilder().
                connectTimeout(TIME_OUT, TimeUnit.SECONDS).
                build();
    }

    /**
     * 构建全局retrofit客户端
     */
    private static final class RetrofitHolder {

        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder().
                baseUrl("https://yxtest.sqkx.net")
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * Service接口
     */
    private static final class RestServiceHolder {

        private static final RestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

    public static final RestService getRestService() {
        return RestServiceHolder.REST_SERVICE;
    }

    /**
     * RxJava的Service接口
     */
    private static final class RxRestServiceHolder {

        private static final RxRestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RxRestService.class);
    }

    public static final RxRestService getRxRestService() {
        return RxRestServiceHolder.REST_SERVICE;
    }

}
