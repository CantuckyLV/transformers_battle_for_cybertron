package com.daniel.battleforcybertron;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daniel.battleforcybertron.Presenters.MainActivityPresenter;
import com.daniel.battleforcybertron.Presenters.WarActivityPresenter;

public class WarActivity extends AppCompatActivity implements WarActivityPresenter.View, View.OnClickListener {

    private WarActivityPresenter presenter;
    private TextView warNumberBattles,warWinningTeam,warLoosers;
    private Button buttonBack;

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
        buttonBack.setOnClickListener(this);
        presenter.wageWar(extras);
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void showWinner(int result,int rounds) {
        warWinningTeam.setText("winner: "+result);
        warNumberBattles.setText("Rounds: "+rounds);
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
