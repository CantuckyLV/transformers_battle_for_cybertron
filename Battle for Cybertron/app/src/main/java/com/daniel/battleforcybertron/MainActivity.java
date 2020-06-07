package com.daniel.battleforcybertron;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.daniel.battleforcybertron.Adapters.TransformerAdapter;
import com.daniel.battleforcybertron.Model.Transformer;
import com.daniel.battleforcybertron.Model.TransformerRequest;
import com.daniel.battleforcybertron.Presenters.MainActivityPresenter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener,MainActivityPresenter.View {

    private MainActivityPresenter presenter;
    private Button testAdd,btnWar,btnAutoDelete,btnAutoEdit,btnDecepDelete, btnDecepEdit;
    private RecyclerView rvAutobots,rvDecepticons;
    private TransformerAdapter autobotsAdapter,decepticonsAdapter;
    private TextView cardAutoName,cardAutoStrength,cardAutoIntelligence,cardAutoSpeed,cardAutoEndurance,cardAutoRank,cardAutoCourage,cardAutoFirepower,cardAutoSkill;
    private TextView cardDecepName,cardDecepStrength,cardDecepIntelligence,cardDecepSpeed,cardDecepEndurance,cardDecepRank,cardDecepCourage,cardDecepFirepower,cardDecepSkill;
    private Transformer currentAutobot, currentDecepticon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainActivityPresenter(this,this);
        setupView();
    }
    private void setupView(){
        //TODO View initialization
        testAdd = findViewById(R.id.btn_tst_add);
        rvAutobots = findViewById(R.id.rv_autobots);
        rvDecepticons = findViewById(R.id.rv_decepticons);
        cardAutoName = findViewById(R.id.card_auto_name);
        cardAutoStrength = findViewById(R.id.card_auto_strength);
        cardAutoIntelligence = findViewById(R.id.card_auto_intelligence);
        cardAutoSpeed = findViewById(R.id.card_auto_speed);
        cardAutoEndurance = findViewById(R.id.card_auto_endurance);
        cardAutoRank = findViewById(R.id.card_auto_rank);
        cardAutoCourage = findViewById(R.id.card_auto_courage);
        cardAutoFirepower  = findViewById(R.id.card_auto_firepower);
        cardAutoSkill = findViewById(R.id.card_auto_skill);
        cardDecepName = findViewById(R.id.card_decep_name);
        cardDecepStrength = findViewById(R.id.card_decep_strength);
        cardDecepIntelligence = findViewById(R.id.card_decep_intelligence);
        cardDecepSpeed = findViewById(R.id.card_decep_speed);
        cardDecepEndurance = findViewById(R.id.card_decep_endurance);
        cardDecepRank = findViewById(R.id.card_decep_rank);
        cardDecepCourage  = findViewById(R.id.card_decep_courage);
        cardDecepFirepower = findViewById(R.id.card_decep_firepower);
        cardDecepSkill = findViewById(R.id.card_decep_skill);
        btnWar = findViewById(R.id.btn_war);
        btnAutoDelete = findViewById(R.id.btn_auto_delete);
        btnAutoEdit = findViewById(R.id.btn_auto_edit);
        btnDecepDelete = findViewById(R.id.btn_decep_delete);
        btnDecepEdit = findViewById(R.id.btn_decep_edit);
        testAdd.setOnClickListener(this);
        btnWar.setOnClickListener(this);
        btnAutoDelete.setOnClickListener(this);
        btnAutoEdit.setOnClickListener(this);
        btnDecepDelete.setOnClickListener(this);
        btnDecepEdit.setOnClickListener(this);
        presenter.initialSetup();

    }

    public void showAddTransformer(View v) {
        final TransformerRequest newTransformer = new TransformerRequest();
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.add_transformer_dialog);
        dialog.setTitle("Add Transformer");
        Button btnAddTransformer = dialog.findViewById(R.id.btn_add_transformer);
        final RadioGroup team = dialog.findViewById(R.id.radioGroup);
        final int childCount = team.getChildCount();
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

    public void showEditTransformer(View v, Transformer transformer) {
        final TransformerRequest newTransformer = new TransformerRequest();
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.add_transformer_dialog);
        dialog.setTitle("Edit Transformer");
        Button btnAddTransformer = dialog.findViewById(R.id.btn_add_transformer);
        final RadioGroup team = dialog.findViewById(R.id.radioGroup);
        RadioButton current;
        final int childCount = team.getChildCount();
        if(transformer.getTeam().equals("A")){
           current = (RadioButton)team.getChildAt(0);
        }else{
            current = (RadioButton)team.getChildAt(1);
        }
        current.setChecked(true);
        final EditText etName = dialog.findViewById(R.id.et_name), etStrength = dialog.findViewById(R.id.et_strength), etIntelligence = dialog.findViewById(R.id.et_intelligence), etSpeed = dialog.findViewById(R.id.et_speed), etEndurance = dialog.findViewById(R.id.et_endurance), etRank = dialog.findViewById(R.id.et_rank), etCourage = dialog.findViewById(R.id.et_courage), etFirePower = dialog.findViewById(R.id.et_fire_power), etSkill = dialog.findViewById(R.id.et_skill);
        etName.setText(transformer.getName());
        etStrength.setText(String.valueOf(transformer.getStrength()));
        etIntelligence.setText(String.valueOf(transformer.getIntelligence()));
        etSpeed.setText(String.valueOf(transformer.getSpeed()));
        etEndurance.setText(String.valueOf(transformer.getEndurance()));
        etRank.setText(String.valueOf(transformer.getRank()));
        etCourage.setText(String.valueOf(transformer.getCourage()));
        etFirePower.setText(String.valueOf(transformer.getFirepower()));
        etSkill.setText(String.valueOf(transformer.getSkill()));
        newTransformer.setId(transformer.getId());
        newTransformer.setTeam(transformer.getTeam());
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
                Log.e("Transformer a mod ",newTransformer.toString());
                presenter.updateTransformer(newTransformer);
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
    public void refreshTeams(final ArrayList<Transformer> autobots, final ArrayList<Transformer> decepticons) {
        cardAutoName.setText("");
        cardAutoStrength.setText("");
        cardAutoIntelligence.setText("");
        cardAutoSpeed.setText("");
        cardAutoEndurance.setText("");
        cardAutoRank.setText("");
        cardAutoCourage.setText("");
        cardAutoFirepower.setText("");
        cardAutoSkill.setText("");

        cardDecepName.setText("");
        cardDecepStrength.setText("");
        cardDecepIntelligence.setText("");
        cardDecepSpeed.setText("");
        cardDecepEndurance.setText("");
        cardDecepRank.setText("");
        cardDecepCourage.setText("");
        cardDecepFirepower.setText("");
        cardDecepSkill.setText("");

        autobotsAdapter = new TransformerAdapter(autobots, new TransformerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                currentAutobot = autobots.get(position);
                cardAutoName.setText(currentAutobot.getName());
                cardAutoStrength.setText(String.valueOf(currentAutobot.getStrength()));
                cardAutoIntelligence.setText(String.valueOf(currentAutobot.getIntelligence()));
                cardAutoSpeed.setText(String.valueOf(currentAutobot.getSpeed()));
                cardAutoEndurance.setText(String.valueOf(currentAutobot.getEndurance()));
                cardAutoRank.setText(String.valueOf(currentAutobot.getRank()));
                cardAutoCourage.setText(String.valueOf(currentAutobot.getCourage()));
                cardAutoFirepower.setText(String.valueOf(currentAutobot.getFirepower()));
                cardAutoSkill.setText(String.valueOf(currentAutobot.getSkill()));
                Log.e("transformerCLicked",""+position);
            }
        });
        decepticonsAdapter = new TransformerAdapter(decepticons, new TransformerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                currentDecepticon = decepticons.get(position);
                cardDecepName.setText(currentDecepticon.getName());
                cardDecepStrength.setText(String.valueOf(currentDecepticon.getStrength()));
                cardDecepIntelligence.setText(String.valueOf(currentDecepticon.getIntelligence()));
                cardDecepSpeed.setText(String.valueOf(currentDecepticon.getSpeed()));
                cardDecepEndurance.setText(String.valueOf(currentDecepticon.getEndurance()));
                cardDecepRank.setText(String.valueOf(currentDecepticon.getRank()));
                cardDecepCourage.setText(String.valueOf(currentDecepticon.getCourage()));
                cardDecepFirepower.setText(String.valueOf(currentDecepticon.getFirepower()));
                cardDecepSkill.setText(String.valueOf(currentDecepticon.getSkill()));
                Log.e("transformerCLicked",""+position);
            }
        });
        rvAutobots.setAdapter(autobotsAdapter);
        rvDecepticons.setAdapter(decepticonsAdapter);
        rvAutobots.setLayoutManager(new LinearLayoutManager(this));
        rvDecepticons.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void goToWar(ArrayList<Transformer> autobots, ArrayList<Transformer> decepticons) {
        Intent intent = new Intent(this, WarActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("autobots", autobots);
        bundle.putSerializable("decepticons",decepticons);
        intent.putExtras(bundle);
        //startActivity(intent);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tst_add:
                showAddTransformer(view);
                break;
            case R.id.btn_war:
                presenter.getTeams();
                break;
            case R.id.btn_auto_delete:
                presenter.deleteTransformer(currentAutobot.getId());
                break;
            case R.id.btn_auto_edit:
                showEditTransformer(view,currentAutobot);
                break;
            case R.id.btn_decep_delete:
                presenter.deleteTransformer(currentDecepticon.getId());
                break;
            case R.id.btn_decep_edit:
                showEditTransformer(view,currentDecepticon);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                presenter.initialSetup();
            }
        }
    }
}
