package com.example.testsprint0projbio.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OzoneApiClient {
    private static final String BASE_URL = "http://192.168.200.90";
    private static OzoneApiClient instance;
    private final Retrofit retrofit;

    private OzoneApiClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized OzoneApiClient getInstance() {
        if (instance == null) {
            instance = new OzoneApiClient();
        }
        return instance;
    }

    // Mètode genèric per obtindre qualsevol servei
    public <T extends ApiService> T createService(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
