package com.daniel.battleforcybertron;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.daniel.battleforcybertron.Presenters.SplashActivityPresenter;

public class SplashActivity extends AppCompatActivity implements SplashActivityPresenter.View {

    private SplashActivityPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        /**

         * */
        presenter = new SplashActivityPresenter(this,this);
        presenter.startTimer();
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
