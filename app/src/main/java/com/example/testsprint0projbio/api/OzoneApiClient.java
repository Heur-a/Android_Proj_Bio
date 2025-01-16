package com.example.testsprint0projbio.api;

import android.content.Context;

import androidx.annotation.NonNull;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class OzoneApiClient {
    private static final String BASE_URL = "http://192.168.18.134";

    private static OzoneApiClient instance;
    private final Retrofit retrofit;

    private OzoneApiClient(Context context) {
        // Interceptor per afegir i guardar cookies
        Interceptor cookieInterceptor = new Interceptor() {
            @NonNull
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder();

                // Obté la cookie guardada mitjançant CookieManager
                String sessionCookie = CookieManager.getSessionCookie(context);

                // Si hi ha una cookie, afegeix-la a les capçaleres
                if (sessionCookie != null) {
                    requestBuilder.addHeader("Cookie", sessionCookie);
                }

                Response response = chain.proceed(requestBuilder.build());

                // Guarda la cookie retornada al dispositiu
                String setCookieHeader = response.header("Set-Cookie");
                if (setCookieHeader != null && setCookieHeader.contains("connect.sid")) {
                    CookieManager.saveSessionCookie(setCookieHeader, context);
                }

                return response;
            }
        };

        // Configura OkHttpClient amb l'interceptor
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(cookieInterceptor)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        // Crea Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized OzoneApiClient getInstance(Context context) {
        if (instance == null) {
            instance = new OzoneApiClient(context);
        }
        return instance;
    }

    // Mètode genèric per obtindre qualsevol servei
    public <T extends ApiService> T createService(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
