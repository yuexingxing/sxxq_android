package com.exam.commonbiz.net;

import androidx.annotation.NonNull;

import com.exam.commonbiz.kits.Kits;
import com.exam.commonbiz.log.LogInterceptor;
import com.exam.commonbiz.net.interceptor.HeadInterceptor;
import com.exam.commonbiz.progress.ProgressHelper;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Author yuexingxing
 * @time 2020/6/10
 */
public class XApi {

    public interface HOST_TYPE {
        String JAVA = "java";
        String NODE = "node";
    }

    public interface HOST_URL {
        String JAVA = "http://t2javaapi.sancell.top/";
//        String JAVA = "http://192.168.200.225:9080/";
        String NODE = "http://dev.kmlab.com/ssxq/";
    }

    private static String defaultBaseUrl;

    private static NetProvider sProvider = null;

    private Map<String, NetProvider> providerMap = new HashMap<>();
    private Map<String, Retrofit> retrofitMap = new HashMap<>();
    private Map<String, OkHttpClient> clientMap = new HashMap<>();
    private static Map<String, String> mHostMap = new HashMap<>();//多服务器

    public static final long connectTimeoutMills = 10 * 1000l;
    public static final long readTimeoutMills = 10 * 1000l;

    private static XApi instance;

    private XApi() {

    }

    public static XApi getInstance() {
        if (instance == null) {
            synchronized (XApi.class) {
                if (instance == null) {
                    instance = new XApi();
                }
            }
        }
        return instance;
    }

    public static <S> S get(Class<S> service) {
        return getInstance().getRetrofit(defaultBaseUrl).create(service);
    }

    public static <S> S get(Class<S> service, String hostType) {
        if (mHostMap.containsKey(hostType)) {
            String hostUrl = mHostMap.get(hostType);
            return getInstance().getRetrofit(hostUrl).create(service);
        } else {
            return getInstance().getRetrofit(defaultBaseUrl).create(service);
        }
    }

    public static <S> S get(String baseUrl, Class<S> service) {
        return getInstance().getRetrofit(baseUrl).create(service);
    }

    public static void registerDefaultProvider(String baseUrl, NetProvider provider) {
        defaultBaseUrl = baseUrl;
        XApi.sProvider = provider;
    }

    public static void registerProvider(String baseUrl, NetProvider provider) {
        getInstance().providerMap.put(baseUrl, provider);
    }


    public Retrofit getRetrofit(String baseUrl) {
        return getRetrofit(baseUrl, null);
    }


    public Retrofit getRetrofit(@NonNull String baseUrl, NetProvider provider) {
        if (Kits.Empty.check(baseUrl)) {
            throw new IllegalStateException("baseUrl can not be null");
        }
        if (retrofitMap.get(baseUrl) != null) return retrofitMap.get(baseUrl);

        if (provider == null) {
            provider = providerMap.get(baseUrl);
            if (provider == null) {
                provider = sProvider;
            }
        }
        checkProvider(provider);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getClient(baseUrl, provider))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        Retrofit retrofit = builder.build();
        retrofitMap.put(baseUrl, retrofit);
        providerMap.put(baseUrl, provider);

        return retrofit;
    }

    public OkHttpClient getClient(@NonNull String baseUrl, NetProvider provider) {
        if (Kits.Empty.check(baseUrl)) {
            throw new IllegalStateException("baseUrl can not be null");
        }
        if (clientMap.get(baseUrl) != null) return clientMap.get(baseUrl);

        checkProvider(provider);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.connectTimeout(provider.configConnectTimeoutMills() != 0
                ? provider.configConnectTimeoutMills()
                : connectTimeoutMills, TimeUnit.MILLISECONDS);
        builder.readTimeout(provider.configReadTimeoutMills() != 0
                ? provider.configReadTimeoutMills() : readTimeoutMills, TimeUnit.MILLISECONDS);

        CookieJar cookieJar = provider.configCookie();
        if (cookieJar != null) {
            builder.cookieJar(cookieJar);
        }
        provider.configHttps(builder);

        RequestHandler handler = provider.configHandler();
        if (handler != null) {
            builder.addInterceptor(new XInterceptor(handler));
        }

        if (provider.dispatchProgressEnable()) {
            builder.addInterceptor(ProgressHelper.get().getInterceptor());
        }

        Interceptor[] interceptors = provider.configInterceptors();
        if (!Kits.Empty.check(interceptors)) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        Interceptor[] netInterceptors = provider.configNetInterceptors();
        if (!Kits.Empty.check(netInterceptors)) {
            for (Interceptor interceptor : netInterceptors) {
                builder.addNetworkInterceptor(interceptor);
            }
        }

        if (provider.configLogEnable()) {
            LogInterceptor logInterceptor = new LogInterceptor();
            builder.addInterceptor(logInterceptor);
        }

        builder.addInterceptor(new HeadInterceptor());

        OkHttpClient client = builder.build();
        clientMap.put(baseUrl, client);
        providerMap.put(baseUrl, provider);

        return client;
    }


    private void checkProvider(NetProvider provider) {
        if (provider == null) {
            throw new IllegalStateException("must register provider first");
        }
    }

    public static String getCommonHost() {
        return defaultBaseUrl;
    }

    public static NetProvider getCommonProvider() {
        return sProvider;
    }

    public OkHttpClient getCommonClient() {
        return getClient(defaultBaseUrl, null);
    }

    public Map<String, Retrofit> getRetrofitMap() {
        return retrofitMap;
    }

    public Map<String, OkHttpClient> getClientMap() {
        return clientMap;
    }

    protected void destoryClient(OkHttpClient okHttpClient) {
        try {
            okHttpClient.dispatcher().executorService().shutdown(); //清除并关闭线程池
            okHttpClient.connectionPool().evictAll();                 //清除并关闭连接池
            okHttpClient.cache().close();                             //清除cache
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearCache() {
        getInstance().retrofitMap.clear();
        getInstance().clientMap.clear();
    }

    public static void setHostMap(Map<String, String> hostMap) {
        if (hostMap == null) {
            return;
        }
        mHostMap.clear();
        for (Map.Entry<String, String> entry : hostMap.entrySet()) {
            String mapKey = entry.getKey();
            String mapValue = entry.getValue();
            mHostMap.put(mapKey, mapValue);
        }
    }
}
