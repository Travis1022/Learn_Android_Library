package com.sunnybear.library.network.request;

import com.sunnybear.library.network.progress.ProgressRequestBody;
import com.sunnybear.library.network.progress.UIProgressRequestListener;
import com.sunnybear.library.util.FileUtils;
import com.sunnybear.library.util.Logger;

import java.io.File;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 表单请求构建类
 * Created by chenkai.gu on 2016/10/9.
 */
public class MultiRequestBuilder {
    private static final String TAG = FormRequestBuilder.class.getSimpleName();
    /* form的分割线,自己定义 */
    String boundary = "xx--------------------------------------------------------------xx";
    private Map<String, Serializable> params;//请求参数

    private Request.Builder builder;
    private String url;

    public MultiRequestBuilder() {
        params = new ConcurrentHashMap<>();
        builder = new Request.Builder();
    }

    /**
     * 创建MultiRequestBuilder实例
     */
    public static MultiRequestBuilder newInstance() {
        return new MultiRequestBuilder();
    }

    /**
     * 设置请求url
     *
     * @param url url
     */
    public MultiRequestBuilder url(String url) {
        this.url = url;
        return this;
    }

    /**
     * 添加请求参数
     *
     * @param name  name
     * @param value value
     */
    public MultiRequestBuilder param(String name, Serializable value) {
        try {
            params.put(name, value);
        } catch (NullPointerException e) {
            Logger.e(TAG, "设置参数为空,参数名:" + name + ",参数值:" + value);
        }
        return this;
    }

    /**
     * 添加请求参数
     *
     * @param params 请求参数
     */
    public MultiRequestBuilder params(Map<String, Serializable> params) {
        this.params = params;
        return this;
    }

    public Request build(UIProgressRequestListener listener) {
        MultipartBody.Builder builder = new MultipartBody.Builder(boundary).setType(MultipartBody.FORM);
        for (Map.Entry<String, Serializable> param : params.entrySet()) {
            String name = param.getKey();
            Serializable value = param.getValue();
            if (value instanceof String)
                builder.addFormDataPart(name, (String) value);
            else if (value instanceof File)
                builder.addFormDataPart(name, FileUtils.getFileName(((File) value).getAbsolutePath())
                        , RequestBody.create(MediaType.parse("application/octet-stream"), (File) value));
        }
        return new Request.Builder().url(url).post(new ProgressRequestBody(builder.build(), listener)).build();
    }
}
