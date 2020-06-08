package com.daniel.battleforcybertron.Presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;

import com.daniel.battleforcybertron.Model.Transformer;
import com.daniel.battleforcybertron.Remote.TransformersService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WarActivityPresenter {

    private WarActivityPresenter.View view;
    private Context context;
    private Gson gson;
    private Retrofit retrofit;
    private TransformersService service;
    private String allSparkToken = "";

    private ArrayList<Transformer> autobots;
    private ArrayList<Transformer> decepticons;
    private int autobotsScore=0,decepticonsScore=0, rounds=0;
    private CountDownTimer animationTimer = null;

    public WarActivityPresenter(WarActivityPresenter.View view, Context context) {
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
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        allSparkToken = prefs.getString("ALLSPARK", "");
    }

    public void wageWar(Bundle extras){
        autobots = (ArrayList<Transformer>)extras.getSerializable("autobots");
        decepticons = (ArrayList<Transformer>)extras.getSerializable("decepticons");
        Iterator<Transformer> autoIt = autobots.iterator();
        Iterator<Transformer> decepIt = decepticons.iterator();
        while (autoIt.hasNext() && decepIt.hasNext()) {
            Transformer autobot = autoIt.next();
            Transformer decepticon = decepIt.next();
            if(autobot.getName().equals("Optimus Prime")||autobot.getName().equals("Predaking")){
                if(decepticon.getName().equals("Optimus Prime")||decepticon.getName().equals("Predaking")){
                    destroyAll();
                    rounds++;
                    break;
                }else{
                    autobotsScore++;
                    destroyTransformer(decepticon.getId());
                    rounds++;
                    continue;
                }
            }
            if(decepticon.getName().equals("Optimus Prime")||decepticon.getName().equals("Predaking")){
                if(autobot.getName().equals("Optimus Prime")||autobot.getName().equals("Predaking")){
                    destroyAll();
                    rounds++;
                    break;
                }else{
                    decepticonsScore++;
                    destroyTransformer(autobot.getId());
                    rounds++;
                    continue;
                }
            }
            if((autobot.getCourage()-decepticon.getCourage())>=4&&(autobot.getStrength()-decepticon.getStrength())>=3){
                autobotsScore++;
                destroyTransformer(decepticon.getId());
                rounds++;
                continue;
            }
            if((decepticon.getCourage()-autobot.getCourage())>=4&&(decepticon.getStrength()-autobot.getStrength())>=3){
                decepticonsScore++;
                destroyTransformer(autobot.getId());
                rounds++;
                continue;
            }
            if((autobot.getSkill()-decepticon.getSkill())>=3){
                autobotsScore++;
                destroyTransformer(decepticon.getId());
                rounds++;
                continue;
            }
            if((decepticon.getSkill()-autobot.getSkill())>=3){
                decepticonsScore++;
                destroyTransformer(autobot.getId());
                rounds++;
                continue;
            }
            int autobotOverall = autobot.getStrength()+autobot.getIntelligence()+autobot.getSpeed()+autobot.getEndurance()+autobot.getRank()+autobot.getCourage()+autobot.getFirepower()+autobot.getSkill();
            int decepticonOverall = decepticon.getStrength()+decepticon.getIntelligence()+decepticon.getSpeed()+decepticon.getEndurance()+decepticon.getRank()+decepticon.getCourage()+decepticon.getFirepower()+decepticon.getSkill();
            if(autobotOverall>decepticonOverall){
                autobotsScore++;
                destroyTransformer(decepticon.getId());
                rounds++;
                continue;
            }else if(autobotOverall<decepticonOverall){
                decepticonsScore++;
                destroyTransformer(autobot.getId());
                rounds++;
                continue;
            }else{
                autobotsScore++;
                destroyTransformer(decepticon.getId());
                decepticonsScore++;
                destroyTransformer(autobot.getId());
                rounds++;
                continue;
            }

        }
        int result = Integer.compare(autobotsScore,decepticonsScore);
        ArrayList<Transformer> survivors = new ArrayList<>();
        switch (result){
            case -1:
                while (autoIt.hasNext()) {
                    survivors.add(autoIt.next());
                }
                break;
            case 0:
                while (autoIt.hasNext()||decepIt.hasNext()) {
                    if(autoIt.hasNext()){
                        survivors.add(autoIt.next());
                    }
                    if(decepIt.hasNext()){
                        survivors.add(decepIt.next());
                    }

                }
                break;
            case 1:
                while (decepIt.hasNext()) {
                    survivors.add(decepIt.next());
                }
                break;
        }
        view.showWinner(result,rounds,survivors);
    }

    public void destroyTransformer(String id){
        Call<String> call = service.deleteTransformer("Bearer "+allSparkToken,id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("deleted:",response.toString());
                if(response.code()==204){
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("fallo:",t.toString());
            }

        });
    }

    public void destroyAll(){
        for(Transformer t : autobots){
            destroyTransformer(t.getId());
        }
        for(Transformer t : decepticons){
            destroyTransformer(t.getId());
        }
        view.showWinner(0,rounds,new ArrayList<Transformer>());
    }

    public void startTimer() {
        animationTimer = new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                view.hideProgressDialog();
            }
        };
        animationTimer.start();
    }
    public void stopTimer(){
        if(animationTimer!=null)
            animationTimer.cancel();
    }

    public interface View{
        void showProgressDialog();
        void hideProgressDialog();
        void showWinner(int result,int rounds,ArrayList<Transformer> survivors);
    }
}
