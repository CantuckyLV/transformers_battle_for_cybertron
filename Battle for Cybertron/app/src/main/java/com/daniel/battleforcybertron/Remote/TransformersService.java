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
/**
 * API calls interface for use with Retrofit
 */

public interface TransformersService {
    /**
     * get Token API call
     */
    @GET("allspark")
    Call<String> getAllSpark();

    /**
     * Add transformer API call.
     * @param authorization the String value for the headers
     * @param transformerRequest the request body for the API call
     */
    @POST("transformers")
    Call<Transformer> addTransformer(@Header("Authorization") String authorization,@Body TransformerRequest transformerRequest);

    /**
     * get all transformers API call.
     * @param authorization the String value for the headers
     */
    @GET("transformers")
    Call<TransformersResponse> getTransformers(@Header("Authorization") String authorization);

    /**
     * edit transformer API call.
     * @param authorization the String value for the headers
     * @param transformerRequest the request body for the API call
     */
    @PUT("transformers")
    Call<Transformer> updateTransformer(@Header("Authorization") String authorization,@Body TransformerRequest transformerRequest);

    /**
     * delete transformer API call.
     * @param authorization the String value for the headers
     * @param id the id for the URL path
     */
    @DELETE("transformers/{transformerId}")
    Call<String> deleteTransformer(@Header("Authorization") String authorization,@Path("transformerId") String id);
}
