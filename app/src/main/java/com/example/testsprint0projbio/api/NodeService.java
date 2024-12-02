package com.example.testsprint0projbio.api;

import com.example.testsprint0projbio.pojo.Node;
import com.example.testsprint0projbio.pojo.NodeResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NodeService extends ApiService {
    @POST("/node")
    Call<ResponseBody> createNode(@Body Node node);

    @GET("/node/")
    Call<NodeResponse> getNodeById();
}
