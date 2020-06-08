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
/**
 * Presenter class for the War Activity.
 */

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
    private boolean isMatchFinished = false;

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
        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            allSparkToken = prefs.getString("ALLSPARK", "");
        } catch (Exception e) {
            allSparkToken = "";
        }
    }

    /**
     * Recieves the Autobots and decepticons list and iterates through each of them matching each other with every cycle.
     * Takes Each transformer's stats to match up against it's opponent and decides based on criteria which transformer wins each battle.
     * sets the value of result which is a comparison of each teams score
     * Iterates through the remaining transformers of the loosing teams (both teams if a tie takes place) and adds them to a new list
     * calls the View's show Winner method.
     * @param  autobots a list of battling Autobots.
     * @param decepticons a list of battling Decepticons.
     */
    public void wageWar(ArrayList<Transformer> autobots, ArrayList<Transformer> decepticons){
        this.autobots = autobots;
        this.decepticons = decepticons;
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
        isMatchFinished = true;
        view.showWinner(result,rounds,survivors);
    }
    /**
     * Initializes the retrofit interface for the destroy transformer API call.
     * @param  id The id of the transformer that lost the match and must be deleted.
     */

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

    /**
     * Destroys all transformers in the game
     */
    public void destroyAll(){
        for(Transformer t : autobots){
            destroyTransformer(t.getId());
        }
        for(Transformer t : decepticons){
            destroyTransformer(t.getId());
        }
        isMatchFinished = true;
        view.showWinner(0,rounds,new ArrayList<Transformer>());
    }
    /**
     * Starts a timer for the duration of the "battling" animation.
     */
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
    /**
     * Stops the timer if there is one.
     */
    public void stopTimer(){
        if(animationTimer!=null)
            animationTimer.cancel();
    }

    public int getAutobotsScore() {
        return autobotsScore;
    }

    public void setAutobotsScore(int autobotsScore) {
        this.autobotsScore = autobotsScore;
    }

    public int getDecepticonsScore() {
        return decepticonsScore;
    }

    public void setDecepticonsScore(int decepticonsScore) {
        this.decepticonsScore = decepticonsScore;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public boolean isMatchFinished() {
        return isMatchFinished;
    }

    public void setMatchFinished(boolean matchFinished) {
        isMatchFinished = matchFinished;
    }

    public String getAllSparkToken() {
        return allSparkToken;
    }

    public void setAllSparkToken(String allSparkToken) {
        this.allSparkToken = allSparkToken;
    }

    public interface View{
        void showProgressDialog();
        void hideProgressDialog();
        void showWinner(int result,int rounds,ArrayList<Transformer> survivors);
    }
}
