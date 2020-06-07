package com.daniel.battleforcybertron.Presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.daniel.battleforcybertron.Model.Transformer;
import com.daniel.battleforcybertron.Model.TransformerRequest;
import com.daniel.battleforcybertron.Model.TransformersResponse;
import com.daniel.battleforcybertron.Remote.TransformersService;
import com.daniel.battleforcybertron.Util.TransformerRankComparator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
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
    private ArrayList<Transformer> allTransformers= new ArrayList<>();
    private ArrayList<Transformer> autobots;
    private ArrayList<Transformer> decepticons;
    private SharedPreferences prefs;
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

    public void initialSetup(){
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        //String persistedTransformers = "";
        allSparkToken = prefs.getString("ALLSPARK", "");
        //persistedTransformers = prefs.getString("TRANSFORMERS","");
        if(allSparkToken.equals("")){
            requestAllSpark();
        }else{
            getTransformers();
        }
    }
    //TODO handle error and response messages
    public void requestAllSpark(){
        Call<String> allSpark = service.getAllSpark();
        allSpark.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code() == 200){
                    allSparkToken = response.body();
                    prefs.edit().putString("ALLSPARK", allSparkToken).apply();
                    Log.e("token:",allSparkToken);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("fallo:",t.toString());
            }

        });
    }
    public void addTransformer(TransformerRequest newTransformer){
        Call<Transformer> call = service.addTransformer("Bearer "+allSparkToken/*tstToken*/,newTransformer);

        call.enqueue(new Callback<Transformer>() {
            @Override
            public void onResponse(Call<Transformer> call, Response<Transformer> response) {
                Transformer newT = response.body();
                Log.e("transformer:",newT.toString());
                Log.e("response",response.toString());
                if(response.code()==201){
                    getTransformers();
                }
            }

            @Override
            public void onFailure(Call<Transformer> call, Throwable t) {
                Log.e("fallo",t.toString());
            }

        });
    }
    public void getTransformers(){
        Call<TransformersResponse> call = service.getTransformers("Bearer "+allSparkToken);

        call.enqueue(new Callback<TransformersResponse>() {
            @Override
            public void onResponse(Call<TransformersResponse> call, Response<TransformersResponse> response) {
                if(response.code() == 200){
                    allTransformers = response.body().getTransformers();
                    refreshTeams();
                    Log.e("transformers:",allTransformers.toString());
                }
            }

            @Override
            public void onFailure(Call<TransformersResponse> call, Throwable t) {
                Log.e("fallo",t.toString());
            }

        });
    }

    public void updateTransformer(TransformerRequest transformer){

        Call<Transformer> call = service.updateTransformer("Bearer "+allSparkToken/*tstToken*/,transformer);

        call.enqueue(new Callback<Transformer>() {
            @Override
            public void onResponse(Call<Transformer> call, Response<Transformer> response) {
                Transformer newT = response.body();
                //if 200: actualizar lista de transformers
                Log.e("transformer:",newT.toString());
                Log.e("response",response.toString());
                if(response.code()==200){
                    getTransformers();
                }
            }

            @Override
            public void onFailure(Call<Transformer> call, Throwable t) {
                Log.e("fallo",t.toString());
            }

        });
    }

    public void deleteTransformer(String id){
        Call<String> call = service.deleteTransformer("Bearer "+allSparkToken/*tstToken*/,id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("deleted:",response.toString());
                if(response.code()==204){
                    getTransformers();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("fallo:",t.toString());
            }

        });
    }

    public void refreshTeams(){
        autobots = new ArrayList<>();
        decepticons = new ArrayList<>();
        Collections.sort(allTransformers, new TransformerRankComparator());
        Collections.reverse(allTransformers);
        for (Transformer transformer: allTransformers){
            if(transformer.getTeam().equals("A")){
                autobots.add(transformer);
            }else{
                decepticons.add(transformer);
            }
        }
        view.refreshTeams(autobots,decepticons);
    }

    public void getTeams(){
        view.goToWar(autobots,decepticons);
    }

    public interface View{
        void showProgressDialog();
        void hideProgressDialog();
        void refreshTeams(ArrayList<Transformer> autobots,ArrayList<Transformer> decepticons);
        void goToWar(ArrayList<Transformer> autobots,ArrayList<Transformer> decepticons);
    }
}
