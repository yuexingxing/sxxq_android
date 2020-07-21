package com.exam.commonbiz.net.interceptor;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * @Author yuexingxing
 * @time 2020/6/10
 */
public class CookiesInterceptor implements Interceptor {
    private Context context;
    private static final String key = "Cookie";

    //重写拦截方法，处理自定义的Cookies信息
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        if (CookieManager.cookies.containsKey(key)) {
            List<String> cookies = CookieManager.getCookie(key);
            for (String cookie : cookies) {
                builder.addHeader(key, cookie);
            }
        }
        Request compressedRequest = builder
                .build();
        Response response = chain.proceed(compressedRequest);
        List<String> cookies = response.headers("Set-Cookie");
        if (cookies != null && cookies.size() > 0) {
            CookieManager.cookies.clear();
            CookieManager.setCookie(key, cookies);
        }
        return response;
    }


    static final class CookieManager {
        private static final Map<String, List<String>> cookies = new HashMap<>();

        public static void setCookie(String key, List<String> value) {
            List<String> values = null;
            if (cookies.containsKey(key)) {
                values = cookies.get(key);
            } else
                values = new ArrayList<>();
            values.addAll(value);
            cookies.put(key, values);
        }

        public static List<String> getCookie(String key) {
            return cookies.containsKey(key) ? cookies.get(key) : null;
        }

    }

}