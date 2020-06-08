package com.daniel.battleforcybertron.Presenters;

import android.content.Context;
import android.os.CountDownTimer;

import com.daniel.battleforcybertron.Model.Transformer;

import java.util.ArrayList;

public class SplashActivityPresenter {

    private SplashActivityPresenter.View view;
    private Context context;
    private CountDownTimer splashTimer = null;

    public SplashActivityPresenter(SplashActivityPresenter.View view, Context context) {
        this.view = view;
        this.context = context;

    }

    public void startTimer() {
        splashTimer = new CountDownTimer(2000, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                view.goToMain();
                stopTimer();
            }
        };
        splashTimer.start();
    }

    public void stopTimer(){
        if(splashTimer!=null)
            splashTimer.cancel();
    }

    public interface View{
        void showProgressDialog();
        void hideProgressDialog();
        void goToMain();
    }
}
