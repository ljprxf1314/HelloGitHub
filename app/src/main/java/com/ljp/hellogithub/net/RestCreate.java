package com.ljp.hellogithub.net;


import com.ljp.hellogithub.net.rx.RxRestService;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
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

//            LoggingInterceptor httpLoggingInterceptor = new LoggingInterceptor.Builder()
//                    .loggable(BuildConfig.DEBUG)
//                    .setLevel(Level.BASIC)
//                    .log(Platform.INFO)
//                    .request("请求")
//                    .response("响应")
//                    .build();
//            BUILDER.addInterceptor(httpLoggingInterceptor);
            return BUILDER;
        }

        private static final OkHttpClient OK_HTTP_CLIENT = getOkHttpBuilder().
                connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        //获得请求信息，此处如有需要可以添加headers信息
                        Request request = chain.request();
                        //添加Cookie信息
                        request.newBuilder().addHeader("Cookie","aaaa");
                        //打印请求信息
                        syso("url:" + request.url());
                        syso("method:" + request.method());
                        syso("request-body:" + request.body().toString());

                        //记录请求耗时
                        long startNs = System.nanoTime();
                        okhttp3.Response response;
                        try {
                            //发送请求，获得相应，
                            response = chain.proceed(request);
                        } catch (Exception e) {
                            throw e;
                        }
                        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
                        //打印请求耗时
                        syso("耗时:"+tookMs+"ms");
                        //使用response获得headers(),可以更新本地Cookie。
                        syso("headers==========");
                        Headers headers = response.headers();
                        syso(headers.toString());

                        //获得返回的body，注意此处不要使用responseBody.string()获取返回数据，原因在于这个方法会消耗返回结果的数据(buffer)
                        ResponseBody responseBody = response.body();

                        //为了不消耗buffer，我们这里使用source先获得buffer对象，然后clone()后使用
                        BufferedSource source = responseBody.source();
                        source.request(Long.MAX_VALUE); // Buffer the entire body.
                        //获得返回的数据
                        Buffer buffer = source.buffer();
                        //使用前clone()下，避免直接消耗
                        syso("response:" + buffer.clone().readString(Charset.forName("UTF-8")));
                        return response;
                    }
                })
                .build();
    }

    private static void syso(String msg) {
        System.out.println(msg);
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
