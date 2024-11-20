package com.example.testsprint0projbio.api;

import com.example.testsprint0projbio.pojo.Node;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NodeService extends ApiService {
    @POST("/node")
    Call<ResponseBody> createNode(@Body Node node);
}
