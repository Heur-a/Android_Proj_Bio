package com.example.testsprint0projbio.api;

import com.example.testsprint0projbio.pojo.Node;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NodeService extends ApiService {
    @POST("/nodes")
    Call<Node> createNode(@Body Node node);
}
