package com.daniel.battleforcybertron.Remote;

import com.daniel.battleforcybertron.Model.Transformer;
import com.daniel.battleforcybertron.Model.TransformerRequest;
import com.daniel.battleforcybertron.Model.TransformersResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TransformersService {
    @GET("allspark")
    Call<String> getAllSpark();

    @POST("transformers")
    Call<Transformer> addTransformer(@Header("Authorization") String authorization,@Body TransformerRequest transformerRequest);

    @GET("transformers")
    Call<TransformersResponse> getTransformers(@Header("Authorization") String authorization);

    @PUT("transformers")
    Call<Transformer> updateTransformer(@Header("Authorization") String authorization,@Body TransformerRequest transformerRequest);

    @DELETE("transformers/{transformerId}")
    Call<String> deleteTransformer(@Header("Authorization") String authorization,@Path("transformerId") String id);
}
