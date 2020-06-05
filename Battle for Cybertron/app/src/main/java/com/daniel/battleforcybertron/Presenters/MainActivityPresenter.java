package com.daniel.battleforcybertron.Presenters;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.daniel.battleforcybertron.Remote.TransformersService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityPresenter {

    private View view;
    private Context context;
    private Gson gson;
    Retrofit retrofit;

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
    }

    public void requestAllSpark(){
        //TODO hacer mas cheverloasbolas
        TransformersService service = retrofit.create(TransformersService.class);

        Call<String> allSpark = service.getAllSpark();

        allSpark.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String token = response.body();
                Log.e("token:",token);
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
