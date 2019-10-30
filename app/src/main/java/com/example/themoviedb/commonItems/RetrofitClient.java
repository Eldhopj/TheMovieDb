package com.example.themoviedb.commonItems;

import android.content.Context;

import com.example.themoviedb.BuildConfig;
import com.example.themoviedb.interfaces.Movies;

import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {
    private static final String BASE_URL = "https://api.themoviedb.org/";
    private static final String HEADER_CACHE_CONTROL = "Cache-Control"; // removes Cache-Control header from the server, and apply our own cache control
    private static final String HEADER_PRAGMA = "Pragma"; // overrides "Not to use caching scenario"
    private static final long cacheSize = 5 * 1024 * 1024; // 5 MB
    private static RetrofitClient mInstance;
    private final Retrofit retrofit;

    private RetrofitClient(Context context) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        httpClient.cache(cache(context));
//        httpClient.addInterceptor(offlineInterceptor(context));
        httpClient.addNetworkInterceptor(networkInterceptor()); // only used when network is on

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.level(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

    public static synchronized RetrofitClient getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RetrofitClient(context);
        }
        return mInstance;
    }

    /**
     * This interceptor will be called ONLY if the network is available
     */
    private static Interceptor networkInterceptor() {
        return chain -> {
            Response response = chain.proceed(chain.request());
            CacheControl cacheControl = new CacheControl.Builder()
                    .maxAge(5, TimeUnit.SECONDS) // Cache the response only for 30 sec, so if a request comes within 30sec it will show from cache
                    .build();
            return response.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL) // removes cache control from the server
                    .header(HEADER_CACHE_CONTROL, cacheControl.toString()) // applying our cache control
                    .build();
        };
    }

//    private Cache cache(Context context) {
//        return new Cache(new File(context.getCacheDir(), "MovieDbResponseCache"), cacheSize);
//    }
//
//    /**
//     * This interceptor will be called both if the network is available and the network is not available
//     */
//    private Interceptor offlineInterceptor(Context context) {
//        return chain -> {
//            Request request = chain.request();
//            if (!Utility.hasNetwork(context)) { // makes the network is not available only
//                CacheControl cacheControl = new CacheControl.Builder()
//                        .maxStale(5, TimeUnit.DAYS)
//                        .build();
//
//                request = request.newBuilder()
//                        .removeHeader(HEADER_PRAGMA)
//                        .removeHeader(HEADER_CACHE_CONTROL)
//                        .cacheControl(cacheControl)
//                        .build();
//            }
//            return chain.proceed(request);
//        };
//    }

    public Movies getNowPlayingMovies() {
        return retrofit.create(Movies.class);
    }
}

