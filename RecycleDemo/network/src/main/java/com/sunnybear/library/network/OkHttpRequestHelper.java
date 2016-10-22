package com.sunnybear.library.network;

import android.content.Context;

import com.sunnybear.library.network.callback.DownloadCallback;
import com.sunnybear.library.network.callback.RequestCallback;
import com.sunnybear.library.network.interceptor.NetworkInterceptor;
import com.sunnybear.library.network.interceptor.ProgressResponseInterceptor;
import com.sunnybear.library.network.progress.UIProgressRequestListener;
import com.sunnybear.library.network.progress.UIProgressResponseListener;
import com.sunnybear.library.network.request.FormRequestBuilder;
import com.sunnybear.library.network.request.MultiRequestBuilder;
import com.sunnybear.library.network.request.RequestMethod;
import com.sunnybear.library.util.FileUtils;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ResourcesUtils;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 普通网络请求助手
 * Created by guchenkai on 2016/1/22.
 */
public class OkHttpRequestHelper {
    private static final String TAG = OkHttpRequestHelper.class.getSimpleName();

    private Context mContext;
    private int mCacheType;
    //    private OkHttpClient mOkHttpClient;
    private OkHttpManager mOkHttpManager;
    private Map<String, Call> requestQueue;//请求队列

    private Map<String, Serializable> uploadParams;//上传请求参数

    public OkHttpRequestHelper(Context context) {
        mContext = context;
        mOkHttpManager = OkHttpManager.getInstance()
                .addNetworkInterceptor(new NetworkInterceptor())
                /*.addInterceptor(new ResponseInfoInterceptor())*/;
        mCacheType = CacheType.NETWORK_ELSE_CACHE;//默认是先请求网络,再请求缓存

        requestQueue = new ConcurrentHashMap();
        uploadParams = new ConcurrentHashMap<>();
    }

    public static OkHttpRequestHelper newInstance() {
        return new OkHttpRequestHelper(NetworkConfiguration.getContext());
    }

    /**
     * 设置缓存策略
     *
     * @param cacheType 缓存策略
     * @return OkHttpFormEncodingHelper实例
     */
    public OkHttpRequestHelper cacheType(int cacheType) {
        mCacheType = cacheType;
        return this;
    }

    /**
     * 添加应用拦截器
     *
     * @param interceptor 拦截器
     */
    public OkHttpRequestHelper addInterceptor(Interceptor interceptor) {
        mOkHttpManager.addInterceptor(interceptor);
        return this;
    }

    /**
     * 添加应用拦截器
     *
     * @param interceptors 拦截器组
     */
    public OkHttpRequestHelper addInterceptors(List<Interceptor> interceptors) {
        mOkHttpManager.addInterceptors(interceptors);
        return this;
    }

    /**
     * 添加网络拦截器
     *
     * @param interceptor 拦截器
     */
    public OkHttpRequestHelper addNetworkInterceptor(Interceptor interceptor) {
        mOkHttpManager.addNetworkInterceptor(interceptor);
        return this;
    }

    /**
     * 添加网络拦截器
     *
     * @param interceptors 拦截器组
     */
    public OkHttpRequestHelper addNetworkInterceptor(List<Interceptor> interceptors) {
        mOkHttpManager.addNetworkInterceptor(interceptors);
        return this;
    }

    /**
     * 添加上传文件参数
     *
     * @param name  name
     * @param value value
     */
    public OkHttpRequestHelper addUploadParam(String name, Serializable value) {
        uploadParams.put(name, value);
        return this;
    }

    /**
     * 网络请求
     *
     * @param request  请求实例
     * @param callback 请求回调
     */
    private void requestFromNetwork(Request request, Callback callback) {
        String url = getUrl(request);
        Logger.d(TAG, "读取网络信息,Url=" + url);
        Call call = mOkHttpManager.build().newCall(request);
        requestQueue.put(url, call);
        call.enqueue(callback);
    }

    /**
     * 缓存请求
     *
     * @param request  请求实例
     * @param callback 请求回调
     */
    private void requestFromCache(Request request, RequestCallback callback) {
        Response response = getResponse(mOkHttpManager.build().cache(), request);
        if (response != null)
            try {
                Logger.d(TAG, "读取缓存信息,Url=" + getUrl(request));
                callback.onCacheResponse(null, response);
            } catch (IOException e) {
                callback.onFailure(null, e);
                Logger.e(e);
            }
        else
            callback.onFailure(null, new IOException(ResourcesUtils.getString(NetworkConfiguration.getContext(), R.string.not_cache)));
    }

    /**
     * 反射方法取得响应体
     *
     * @param cache   缓存
     * @param request 请求体
     * @return 响应体
     */
    private Response getResponse(Cache cache, Request request) {
        Class clz = cache.getClass();
        try {
            Method get = clz.getDeclaredMethod("get", Request.class);
            get.setAccessible(true);
            return (Response) get.invoke(cache, request);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e(e);
        }
        return null;
    }

    /**
     * 请求
     *
     * @param request  请求实例
     * @param callback 请求回调
     */
    public void request(final Request request, final RequestCallback callback) {
        if (callback == null)
            throw new NullPointerException("请设置请求回调");
        //如果不是缓存请求,执行OnStart方法
        if (mCacheType == CacheType.NETWORK || mCacheType == CacheType.NETWORK_ELSE_CACHE)
            callback.onStart();
        switch (mCacheType) {
            case CacheType.NETWORK:
                requestFromNetwork(request, callback);
                break;
            case CacheType.CACHE:
                requestFromCache(request, callback);
                break;
            case CacheType.CACHE_ELSE_NETWORK:
                requestFromCache(request, new RequestCallback(mContext) {
                    @Override
                    public void onCacheResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful())
                            callback.onCacheResponse(call, response);
                        else
                            requestFromNetwork(request, callback);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        requestFromNetwork(request, callback);
                    }
                });
                break;
            case CacheType.NETWORK_ELSE_CACHE:
                requestFromNetwork(request, new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful())
                            callback.onResponse(call, response);
                        else
                            requestFromCache(request, callback);
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        requestFromCache(request, callback);
                    }
                });
                break;
        }
    }

    /**
     * 取消请求
     *
     * @param url url
     */
    public void cancelRequest(String url) {
        Call currentCall = requestQueue.remove(url);
        currentCall.cancel();
    }

    /**
     * 获取Url
     *
     * @param request 请求体
     * @return url
     */
    private String getUrl(Request request) {
        return request.url().url().toString();
    }

    /**
     * 下载文件
     *
     * @param url      下载地址
     * @param filePath 保存文件的路径
     * @param callback 下载文件回调
     */
    /*public void download(String url, String filePath, DownloadCallback callback) {
        callback.onStart();
        callback.setFilePath(filePath);
        if (FileUtils.isExists(filePath)) {
            callback.onFinish(url, true, "现在文件已存在,请不要重复下载");
            return;
        }
        mOkHttpClient.newCall(FormRequestBuilder.newInstance()
                .url(url).method(RequestMethod.GET)
                .build()).enqueue(callback);
    }*/

    /**
     * 下载文件,带有进度处理
     *
     * @param url      下载地址
     * @param filePath 保存文件的路径
     * @param listener 下载进度回调
     * @param callback 下载文件回调
     * @param isCover  是否覆盖
     */
    public void download(String url, String filePath, UIProgressResponseListener listener, DownloadCallback callback, boolean isCover) {
        callback.onStart();
        callback.setFilePath(filePath);
        if (FileUtils.isExists(filePath) && !isCover) {
            callback.onFinish(url, true, "不覆盖下载过的文件");
            return;
        }
        if (listener != null)
            addInterceptor(new ProgressResponseInterceptor(listener));
        mOkHttpManager.build().newCall(FormRequestBuilder.newInstance()
                .url(url).method(RequestMethod.GET)
                .build()).enqueue(callback);
    }

    /**
     * 上传文件,带有进度处理
     *
     * @param url      上传地址
     * @param listener 上传进度回调
     * @param callback 上传文件回调
     */
    public void upload(String url, UIProgressRequestListener listener, RequestCallback callback) {
        callback.onStart();
        mOkHttpManager.build()
                .newCall(MultiRequestBuilder.newInstance().url(url).params(uploadParams).build(listener))
                .enqueue(callback);
    }
}
