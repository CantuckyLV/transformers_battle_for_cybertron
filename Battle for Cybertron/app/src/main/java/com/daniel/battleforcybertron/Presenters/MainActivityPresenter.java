package com.daniel.battleforcybertron.Presenters;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.daniel.battleforcybertron.Model.Transformer;
import com.daniel.battleforcybertron.Model.TransformerRequest;
import com.daniel.battleforcybertron.Model.TransformersResponse;
import com.daniel.battleforcybertron.Remote.TransformersService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityPresenter {

    private View view;
    private Context context;
    private Gson gson;
    private Retrofit retrofit;
    private TransformersService service;
    private String allSparkToken = "";
    //TODO QUITAR TEST OBJECTS
    String tstToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0cmFuc2Zvcm1lcnNJZCI6Ii1NOTYzU2t0SGthZE81YlZnYXE2IiwiaWF0IjoxNTkxNDAzOTk0fQ.9bdqIZhmpJ-zGgsO3M4ZuGnG7okEoezulGU7nWOXuWE";
    Transformer testTransformer;

    public MainActivityPresenter(View view, Context context) {
        this.view = view;
        this.context = context;
        gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://transformers-api.firebaseapp.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        service = retrofit.create(TransformersService.class);
        //Cambiar este ID cuando borres
        testTransformer = new Transformer("-M96Z4ggG9KjR364Rf_A","testotron","A",10,10,10,10,10,10,10,10,"fdhged");
    }

    //TODO handle error and response messages
    public void requestAllSpark(){
        Call<String> allSpark = service.getAllSpark();
        allSpark.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                allSparkToken = response.body();
                Log.e("token:",allSparkToken);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("fallo:",t.toString());
            }

        });
    }
    public void addTransformer(TransformerRequest newTransformer){
        Call<Transformer> call = service.addTransformer("Bearer "+/*allSparkToken*/tstToken,newTransformer);

        call.enqueue(new Callback<Transformer>() {
            @Override
            public void onResponse(Call<Transformer> call, Response<Transformer> response) {
                Transformer newT = response.body();
                Log.e("transformer:",newT.toString());
            }

            @Override
            public void onFailure(Call<Transformer> call, Throwable t) {
                Log.e("fallo",t.toString());
            }

        });
    }
    public void getTransformers(){
        Call<TransformersResponse> call = service.getTransformers("Bearer "+/*allSparkToken*/tstToken);

        call.enqueue(new Callback<TransformersResponse>() {
            @Override
            public void onResponse(Call<TransformersResponse> call, Response<TransformersResponse> response) {
                TransformersResponse newT = response.body();
                Log.e("transformer:",newT.toString());
            }

            @Override
            public void onFailure(Call<TransformersResponse> call, Throwable t) {
                Log.e("fallo",t.toString());
            }

        });
    }

    public void updateTransformer(/*TransformerRequest transformer*/){
        //TODO cambiar test objects por reales cuando ya tenga eltransformer que va a modificar
        TransformerRequest testT = new TransformerRequest(testTransformer);
        Random rand = new Random();
        testT.setStrength(rand.nextInt((10 - 0) + 1) + 0);

        Call<Transformer> call = service.updateTransformer("Bearer "+/*allSparkToken*/tstToken,testT);

        call.enqueue(new Callback<Transformer>() {
            @Override
            public void onResponse(Call<Transformer> call, Response<Transformer> response) {
                Transformer newT = response.body();
                Log.e("transformer:",newT.toString());
            }

            @Override
            public void onFailure(Call<Transformer> call, Throwable t) {
                Log.e("fallo",t.toString());
            }

        });
    }

    public void deleteTransformer(/*String id*/){
        Call<String> call = service.deleteTransformer("Bearer "+/*allSparkToken*/tstToken,testTransformer.getId());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("deleted:","ya");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("fallo:",t.toString());
            }

        });
    }

    public interface View{
        void showProgressDialog();
        void hideProgressDialog();
        /*void setupSportsList(ArrayList<Sport> sports);
        void goToSport(Sport sport);*/
    }
}
