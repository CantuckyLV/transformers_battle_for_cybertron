package com.daniel.battleforcybertron;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.daniel.battleforcybertron.Model.Transformer;
import com.daniel.battleforcybertron.Model.TransformerRequest;
import com.daniel.battleforcybertron.Presenters.MainActivityPresenter;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener,MainActivityPresenter.View {

    private MainActivityPresenter presenter;
    private Button testToken, testAdd, testCheck, testEdit, testDelete;

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
        testAdd = findViewById(R.id.btn_tst_add);
        testCheck = findViewById(R.id.btn_tst_check);
        testEdit = findViewById(R.id.btn_tst_edit);
        testDelete = findViewById(R.id.btn_tst_delete);
        testToken.setOnClickListener(this);
        testAdd.setOnClickListener(this);
        testCheck.setOnClickListener(this);
        testEdit.setOnClickListener(this);
        testDelete.setOnClickListener(this);

    }

    public void showAddTransformer(View v) {
        final TransformerRequest newTransformer = new TransformerRequest();
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.add_transformer_dialog);
        dialog.setTitle("Add Transformer");
        Button btnAddTransformer = dialog.findViewById(R.id.btn_add_transformer);
        final RadioGroup team = dialog.findViewById(R.id.radioGroup);
        final int childCount = team.getChildCount();
        //RadioButton current = (RadioButton)rg.getChildAt(sortingOption);
        //current.setChecked(true);
        final EditText etName = dialog.findViewById(R.id.et_name), etStrength = dialog.findViewById(R.id.et_strength), etIntelligence = dialog.findViewById(R.id.et_intelligence), etSpeed = dialog.findViewById(R.id.et_speed), etEndurance = dialog.findViewById(R.id.et_endurance), etRank = dialog.findViewById(R.id.et_rank), etCourage = dialog.findViewById(R.id.et_courage), etFirePower = dialog.findViewById(R.id.et_fire_power), etSkill = dialog.findViewById(R.id.et_skill);
        team.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                for (int i = 0; i < childCount; i++) {
                    RadioButton btn = (RadioButton) team.getChildAt(i);
                    if (btn.getId() == id) {
                        switch (i){
                            case 0:
                                newTransformer.setTeam("A");
                                break;
                            case 1:
                                newTransformer.setTeam("D");
                                break;
                        }
                    }
                }
            }
        });
        btnAddTransformer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTransformer.setName(etName.getText().toString());
                newTransformer.setStrength(Integer.parseInt(etStrength.getText().toString()));
                newTransformer.setIntelligence(Integer.parseInt(etIntelligence.getText().toString()));
                newTransformer.setSpeed(Integer.parseInt(etSpeed.getText().toString()));
                newTransformer.setEndurance(Integer.parseInt(etEndurance.getText().toString()));
                newTransformer.setRank(Integer.parseInt(etRank.getText().toString()));
                newTransformer.setCourage(Integer.parseInt(etCourage.getText().toString()));
                newTransformer.setFirepower(Integer.parseInt(etFirePower.getText().toString()));
                newTransformer.setSkill(Integer.parseInt(etSkill.getText().toString()));
                Log.e("Transformer a agregar ",newTransformer.toString());
                presenter.addTransformer(newTransformer);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.token_get:
                presenter.requestAllSpark();
                break;
            case R.id.btn_tst_add:
                showAddTransformer(view);
                break;
            case R.id.btn_tst_check:
                presenter.getTransformers();
                break;
            case R.id.btn_tst_edit:
                presenter.updateTransformer(/*Transformer to update*/);
                break;
            case R.id.btn_tst_delete:
                presenter.deleteTransformer(/*id of Transformer to delete*/);
                break;
        }
    }
}
