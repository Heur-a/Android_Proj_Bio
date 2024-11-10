package com.example.testsprint0projbio.api;

import com.example.testsprint0projbio.pojo.UserLogin;
import com.example.testsprint0projbio.pojo.UserRegister;
import com.example.testsprint0projbio.pojo.UserResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthService extends ApiService {
    @POST("/auth/login")
    Call<ResponseBody> login(@Body UserLogin userLogin);

    @POST("/auth/logout")
    Call<Void> logout();

    @POST("/auth/register")
    Call<UserResponse> register(@Body UserRegister userRegister);

    @GET("/auth/checkAuth")
    Call<UserResponse> checkAuth();
}

