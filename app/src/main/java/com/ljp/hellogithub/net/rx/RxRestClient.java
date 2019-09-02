package com.ljp.hellogithub.net.rx;

import android.content.Context;

import com.ljp.hellogithub.net.HttpMethod;
import com.ljp.hellogithub.net.RestClientBuilder;
import com.ljp.hellogithub.net.RestCreate;
import com.ljp.hellogithub.net.RestService;
import com.ljp.hellogithub.net.callback.IError;
import com.ljp.hellogithub.net.callback.IFailure;
import com.ljp.hellogithub.net.callback.IProgress;
import com.ljp.hellogithub.net.callback.IRequest;
import com.ljp.hellogithub.net.callback.ISuccess;
import com.ljp.hellogithub.net.callback.RequestCallbacks;
import com.ljp.hellogithub.net.download.DownloadHandler;
import com.ljp.hellogithub.util.ProgressUtils;

import java.io.File;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/8/2
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public final class RxRestClient<T> {
    private final WeakHashMap<String, Object> params;//请求参数
    private final String url;
    private final RequestBody body; //返回信息主体
    private final boolean isLoading;//是否请求时显示加载框
    private final String dialog;//加载时显示文字
    private final File file;
    private final Context context;
    private final Class<T> mClass;//解析json时用到的泛型
    private final IProgress mProgress;

    ProgressUtils pu;

    public RxRestClient(WeakHashMap<String, Object> params,
                        String url,
                        RequestBody body,
                        Class<T> mClass,
                        boolean isLoading,
                        String dialog,
                        File file,
                        Context context,
                        IProgress mProgress) {
        this.params = params;
        this.url = url;
        this.body = body;
        this.isLoading = isLoading;
        this.file = file;
        this.dialog = dialog;
        this.context = context;
        this.mClass = mClass;
        this.mProgress = mProgress;
        pu = new ProgressUtils();//初始化加载样式
    }

    /**
     * 不带泛型参数,构建时默认返回字符串
     * @return
     */
    public static RxRestClientBuilder builder() {
        return new RxRestClientBuilder();
    }

    /**
     * 构建时传入泛型,解析时返回解析后的对象参数
     * @param t
     * @return
     */
    public static <T> RxRestClientBuilder builder(Class<T> t) {
        return new RxRestClientBuilder<>(t);
    }

    /**
     * 请求方式
     * @param httpMethod
     */
    private Observable<String> request(HttpMethod httpMethod){
        final RxRestService service = RestCreate.getRxRestService();

        Observable<String> observable = null;

        if (isLoading){
            //显示加载框
            if (dialog == null){
                pu.showDialog(context);
            }else{
                pu.showDialog(context,dialog);
            }
        }

        switch (httpMethod){
            case GET:
                observable = service.get(url,params);
                break;
            case POST:
                observable = service.post(url,params);
                break;
            case POST_RAW:
                observable = service.postRaw(url, body);
                break;
            case PUT:
                observable = service.put(url, params);
                break;
            case PUT_RAW:
                observable = service.putRaw(url, body);
                break;
            case DELETE:
                observable = service.delete(url, params);
                break;
            case UPLOAD:
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), file);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                observable = service.upload(url, body);
                break;
            default:
                break;
        }

        return observable;
    }

    public final Observable<String> get() {
        return request(HttpMethod.GET);
    }

    public final Observable<String> post() {
        if (body == null) {
            return request(HttpMethod.POST);
        } else {
            if (!params.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            return request(HttpMethod.POST_RAW);
        }
    }

    public final Observable<String> put() {
        if (body == null) {
            return request(HttpMethod.PUT);
        } else {
            if (!params.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            return request(HttpMethod.PUT_RAW);
        }
    }

    public final Observable<String> delete() {
        return request(HttpMethod.DELETE);
    }

    public final Observable<String> upload() {
        return request(HttpMethod.UPLOAD);
    }

    public final Observable<ResponseBody> download() {
//        new DownloadHandler(url, params,request, downloadDir, extension, name,
//                success, failure, error,context,mProgress)
//                .handleDownload();
        final Observable<ResponseBody> responseBodyObservable = RestCreate.getRxRestService()
                .download(url,params);
        return responseBodyObservable;
    }

}
