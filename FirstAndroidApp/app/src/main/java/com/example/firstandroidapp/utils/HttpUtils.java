package com.example.SimpleSender.utils;

import android.os.NetworkOnMainThreadException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.SimpleSender.config.ApplicationConfig;
import com.example.SimpleSender.enums.ServiceEnum;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author william
 * @description
 * @Date: 2021-10-21 15:35
 */
public class HttpUtils {
    private static final MediaType JSON
        = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = "HttpUtilsLog";

    /**
     * 使用默认的contenttype进行post请求的发送
     *
     * @param url       请求url地址,务必携带http等前缀
     * @param paramPair 路径参数键值对
     * @param json      请求的消息体json对象
     * @param callback  异步请求时传入callback对象
     */
    public static void asyncPost(@NotNull String url,
                                 @Nullable Map<String, String> paramPair,
                                 @Nullable JSONObject json,
                                 @NotNull Callback callback) {
        ThreadPoolUtil.submit(() -> {
            Log.i(TAG, "提交按钮被按下,异步提交启动");
            doAsyncPost(url, paramPair, json, callback);
        });
    }

    public static void asyncPost(@NotNull ServiceEnum serviceEnum,
                                 @Nullable Map<String, String> paramPair,
                                 @Nullable JSONObject json,
                                 @NotNull Callback callback) {
        ThreadPoolUtil.submit(() -> {
            Log.i(TAG, "提交按钮被按下,异步提交启动");
            doAsyncPost(serviceEnum.getUrl(), paramPair, json, callback);
        });
    }


    /**
     * 同步post请求并响应结果，不要在主线程调用
     *
     * @param url       请求url地址,务必携带http等前缀
     * @param paramPair param对
     * @param json      请求body json对象
     * @return
     */
    public static JSONObject post(String url, Map<String, String> paramPair, JSONObject json) {
        if ("main".equals(Thread.currentThread().getName())) {
            throw new NetworkOnMainThreadException();
        }
        Log.i(TAG, "提交按钮被按下,异步提交启动");
        return doSyncPost(url, paramPair, json);
    }

    public static JSONObject post(ServiceEnum serviceEnum, Map<String, String> paramPair, JSONObject json) {
        if ("main".equals(Thread.currentThread().getName())) {
            throw new NetworkOnMainThreadException();
        }
        Log.i(TAG, "提交按钮被按下,异步提交启动");
        return doSyncPost(serviceEnum.getUrl(), paramPair, json);
    }

    private static void doAsyncPost(@NonNull String url, @Nullable Map<String, String> paramPair, @Nullable JSONObject json, @NonNull Callback callback) {
        String token = ApplicationConfig.getToken();
        Request.Builder builder = new Request.Builder();
        String realUrl = url;
        Map<String, String> realParamPair = new HashMap<>(8);

        //携带token
        if (token != null && !token.isEmpty()) {
            builder.addHeader("token", token);
            realParamPair.put("token", token);
        }

        //装载param
        if (paramPair != null && !paramPair.isEmpty()) {
            realParamPair.putAll(paramPair);
        }

        //拼装url后缀
        if (realParamPair != null && realParamPair.size() > 0) {
            StringJoiner sj = new StringJoiner("&");
            realParamPair.forEach((k, v) -> {
                sj.add(String.format("%s=%s", k, v));
            });
            realUrl = realUrl + "?" + sj.toString();
        }

        //封装请求
        RequestBody body = RequestBody.create(JSON, json == null ? "{}" : json.toString());
        builder
            .url(realUrl)
            .post(body);
        Request request = builder.build();

        //开始发送数据
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    @Nullable
    private static JSONObject doSyncPost(String url, Map<String, String> paramPair, JSONObject json) {
        String token = ApplicationConfig.getToken();
        Request.Builder builder = new Request.Builder();
        String realUrl = url;
        Map<String, String> realParamPair = new HashMap<>(8);

        //携带token
        if (token != null && !token.isEmpty()) {
            builder.addHeader("token", token);
            if (paramPair != null && !paramPair.isEmpty()) {
                realParamPair.putAll(paramPair);
            }
            realParamPair.put("token", token);
        }

        //拼装url后缀
        if (realParamPair != null && realParamPair.size() > 0) {
            StringJoiner sj = new StringJoiner("&");
            realParamPair.forEach((k, v) -> {
                sj.add(String.format("%s=%s", k, v));
            });
            realUrl = url + "?" + sj.toString();
        }

        //封装请求
        RequestBody body = RequestBody.create(JSON, json == null ? "{}" : json.toString());
        builder
            .url(realUrl)
            .post(body);
        Request request = builder.build();

        //开始发送数据
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        try {
            Response execute = call.execute();
            String resultString = execute.body().string();
            return new JSONObject(resultString);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
