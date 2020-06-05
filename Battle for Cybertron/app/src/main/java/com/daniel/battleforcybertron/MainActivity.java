package com.daniel.battleforcybertron;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.daniel.battleforcybertron.Presenters.MainActivityPresenter;

public class MainActivity extends AppCompatActivity implements MainActivityPresenter.View {

    private MainActivityPresenter presenter;
    private Button testToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainActivityPresenter(this,this);
        setupView();
        Log.e("holo","holo");
        Toast.makeText(this, "test lel", Toast.LENGTH_SHORT).show();
    }

    private void setupView(){
        //TODO View initialization
        testToken = findViewById(R.id.token_get);
        testToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.requestAllSpark();
            }
        });

    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }
}
