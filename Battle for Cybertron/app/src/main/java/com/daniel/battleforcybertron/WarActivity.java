package com.daniel.battleforcybertron;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daniel.battleforcybertron.Model.Transformer;
import com.daniel.battleforcybertron.Presenters.MainActivityPresenter;
import com.daniel.battleforcybertron.Presenters.WarActivityPresenter;

import java.util.ArrayList;

public class WarActivity extends AppCompatActivity implements WarActivityPresenter.View, View.OnClickListener {

    private WarActivityPresenter presenter;
    private TextView warNumberBattles,warWinningTeam,warLoosers;
    private Button buttonBack;
    private ImageView ivLoading, teamIcon;
    private ConstraintLayout clResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_war);
        presenter = new WarActivityPresenter(this,this);
        setupView();
    }

    protected void setupView(){
        Bundle extras = getIntent().getExtras();
        warNumberBattles = findViewById(R.id.war_number_battles);
        warWinningTeam = findViewById(R.id.war_winning_team);
        warLoosers = findViewById(R.id.war_loosers);
        buttonBack = findViewById(R.id.button_back);
        ivLoading = findViewById(R.id.iv_loading);
        clResult = findViewById(R.id.cl_war_results);
        teamIcon = findViewById(R.id.team_icon);
        buttonBack.setOnClickListener(this);
        showProgressDialog();
        presenter.wageWar(extras);
    }

    @Override
    public void showProgressDialog() {
        presenter.startTimer();
        ivLoading.setVisibility(View.VISIBLE);
        clResult.setVisibility(View.GONE);
        Animation fade_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        Animation fade_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        final AnimationSet s = new AnimationSet(false);
        s.addAnimation(fade_in);
        fade_out.setStartOffset(fade_in.getDuration());
        s.addAnimation(fade_out);

        s.setFillAfter(true);

        s.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                s.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ivLoading.startAnimation(s);

    }

    @Override
    public void hideProgressDialog() {
        presenter.stopTimer();
        ivLoading.setVisibility(View.GONE);
        clResult.setVisibility(View.VISIBLE);
    }

    @Override
    public void showWinner(int result, int rounds,ArrayList<Transformer> survivors) {
        switch (result){
            case -1:
                warWinningTeam.setTextColor(getResources().getColor(R.color.colorDecepticons));
                teamIcon.setImageDrawable(getResources().getDrawable(R.drawable.demo));
                break;
            case 0:
                warWinningTeam.setTextColor(getResources().getColor(R.color.colorAccent));
                teamIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_battle));
                warWinningTeam.setText("Tie");
                break;
            case 1:
                warWinningTeam.setTextColor(getResources().getColor(R.color.colorAutobots));
                teamIcon.setImageDrawable(getResources().getDrawable(R.drawable.auto));
                break;
        }
        warNumberBattles.setText(String.valueOf(rounds));
        StringBuilder surv = new StringBuilder();
        if(survivors.size()>0){
            for(Transformer t:survivors){
                surv.append(t.getName()+", ");
            }
        }
        warLoosers.setText(surv.toString());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_back:
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                break;
        }
    }
}
