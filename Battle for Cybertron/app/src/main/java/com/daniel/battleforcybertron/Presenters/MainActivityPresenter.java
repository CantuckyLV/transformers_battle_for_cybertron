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

/**
 * Presenter class for the main Activity.
 */
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
    }

    /**
     * Checks if there is a token persisted in the shared preferences. if there is a token persisted, calls the getTransformers() method,
     * If ther is not, it calls the requestAllspark() method.
     */
    public void initialSetup(){
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        allSparkToken = prefs.getString("ALLSPARK", "");
        if(allSparkToken.equals("")){
            requestAllSpark();
        }else{
            getTransformers();
        }
    }

    public boolean requestAllSpark(){
        Call<String> allSpark = service.getAllSpark();
        final boolean fetchedAllSpark=false;
        allSpark.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code() == 200){
                    allSparkToken = response.body();
                    try{
                        prefs.edit().putString("ALLSPARK", allSparkToken).apply();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    Log.e("token:",allSparkToken);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("fallo:",t.toString());
            }

        });
        return (allSparkToken.length()>0);
    }
    /**
     * Initializes retrofit interface to make the Add transformer API call. if the response is 201: the getTransfomrers() method is called.
     *  @param newTransformer the Transformer object which will be the body of the API call
     */
    public void addTransformer(TransformerRequest newTransformer){
        Call<Transformer> call = service.addTransformer("Bearer "+allSparkToken/*tstToken*/,newTransformer);

        call.enqueue(new Callback<Transformer>() {
            @Override
            public void onResponse(Call<Transformer> call, Response<Transformer> response) {
                Transformer newT = response.body();
                if(response.code()==201){
                    getTransformers();
                }
                System.out.println("transformer:"+newT.toString());
                System.out.println("response:"+response.toString());
            }

            @Override
            public void onFailure(Call<Transformer> call, Throwable t) {
                Log.e("fallo",t.toString());
            }

        });
    }
    /**
     * Initializes retrofit interface to make the get all transformers API call. if the response is 200: the refresTeams() method is called.
     * the response is assigned to the allTransformersVariable which is the list of all transformers the user has created.
     */
    public void getTransformers(){
        Call<TransformersResponse> call = service.getTransformers("Bearer "+allSparkToken);

        call.enqueue(new Callback<TransformersResponse>() {
            @Override
            public void onResponse(Call<TransformersResponse> call, Response<TransformersResponse> response) {
                if(response.code() == 200){
                    allTransformers = response.body().getTransformers();
                    refreshTeams();
                    System.out.println("transformers:"+allTransformers.toString());
                }
            }

            @Override
            public void onFailure(Call<TransformersResponse> call, Throwable t) {
                Log.e("fallo",t.toString());
            }

        });
    }

    /**
     * Initializes retrofit interface to make the editTransformer API call. if the response is 200: the getTransfomrers() method is called.
     *  @param transformer the TransformerRequest object which will be the body of the API call
     */
    public Transformer updateTransformer(TransformerRequest transformer){
        final Transformer[] newT = {new Transformer()};
        Call<Transformer> call = service.updateTransformer("Bearer "+allSparkToken/*tstToken*/,transformer);

        call.enqueue(new Callback<Transformer>() {
            @Override
            public void onResponse(Call<Transformer> call, Response<Transformer> response) {
                newT[0] = response.body();
                //if 200: actualizar lista de transformers
                if(response.code()==200){
                    getTransformers();
                }
                System.out.println("transformers:"+allTransformers.toString());
                System.out.println("response"+response.body().toString());
            }

            @Override
            public void onFailure(Call<Transformer> call, Throwable t) {
                Log.e("fallo",t.toString());
            }

        });
        return newT[0];
    }

    /**
     * Initializes retrofit interface to make the delete Transformer API call. if the response is 200: the getTransfomrers() method is called.
     *  @param id the id of the transformer that will be deleted
     */
    public void deleteTransformer(String id){
        Call<String> call = service.deleteTransformer("Bearer "+allSparkToken/*tstToken*/,id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code()==204){
                    getTransformers();
                }
                System.out.println("deleted:"+response.toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("fallo:",t.toString());
            }

        });
    }

    /**
     * Sorts the transformers in allTransformers, uses TransformersRankCOmparator and then reverses the list to be un descendant order.
     * Separates the list into autobots list and decepticons list.
     * cals the view method "refreshTeams"
     */
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

    public String getAllSparkToken(){
        return allSparkToken;
    }
    public void setAllSparkToken(String token){
        this.allSparkToken = token;
    }

    public ArrayList<Transformer> getAllTransformers() {
        return allTransformers;
    }

    public void setAllTransformers(ArrayList<Transformer> allTransformers) {
        this.allTransformers = allTransformers;
    }

    public interface View{
        void refreshTeams(ArrayList<Transformer> autobots,ArrayList<Transformer> decepticons);
        void goToWar(ArrayList<Transformer> autobots,ArrayList<Transformer> decepticons);
    }
}
