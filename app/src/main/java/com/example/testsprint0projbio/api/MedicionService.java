package com.example.testsprint0projbio.api;



import com.example.testsprint0projbio.pojo.Medicion;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MedicionService extends ApiService {
    @POST("/mediciones")
    Call<ResponseBody> createMeasurement(@Body Medicion measurement);

    @GET("/mediciones/diaria")
    Call<ResponseBody> getDailyMeasurements();
}
