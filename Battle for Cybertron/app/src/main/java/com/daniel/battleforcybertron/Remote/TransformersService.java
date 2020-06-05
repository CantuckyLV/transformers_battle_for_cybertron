package com.daniel.battleforcybertron.Remote;

import com.daniel.battleforcybertron.Model.TransformersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface TransformersService {
    @GET("allspark")
    Call<String> getAllSpark();

    @GET("transformers")
    Call<TransformersResponse> getTransformers(@Header("Authorization") String authorization);
}
