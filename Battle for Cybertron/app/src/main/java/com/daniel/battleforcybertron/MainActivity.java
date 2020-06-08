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
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
    private Button testAdd;
    private ImageButton btnAutoDelete,btnAutoEdit, btnAutoAdd, btnDecepDelete, btnDecepEdit, btnDecepAdd, btnWar;
    private LinearLayout btnBattle;
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
        btnBattle = findViewById(R.id.btn_battle);
        btnAutoDelete = findViewById(R.id.btn_auto_delete);
        btnAutoEdit = findViewById(R.id.btn_auto_edit);
        btnAutoAdd = findViewById(R.id.btn_auto_add);
        btnDecepDelete = findViewById(R.id.btn_decep_delete);
        btnDecepEdit = findViewById(R.id.btn_decep_edit);
        btnDecepAdd = findViewById(R.id.btn_decep_add);
        testAdd.setOnClickListener(this);
        btnBattle.setOnClickListener(this);
        btnAutoDelete.setOnClickListener(this);
        btnAutoEdit.setOnClickListener(this);
        btnAutoAdd.setOnClickListener(this);
        btnDecepDelete.setOnClickListener(this);
        btnDecepEdit.setOnClickListener(this);
        btnDecepAdd.setOnClickListener(this);
        presenter.initialSetup();

    }

    /**
     * Displays a Dialog for the user to add new transfomer
     *
     * @param  team  the team in which the new transformer will be created
     */
    public void showAddTransformer(int team) {
        final TransformerRequest newTransformer = new TransformerRequest();
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.add_transformer_dialog);
        dialog.setTitle("Add Transformer");
        Button btnAddTransformer = dialog.findViewById(R.id.btn_add_transformer);
        final EditText etName = dialog.findViewById(R.id.et_name), etStrength = dialog.findViewById(R.id.et_strength), etIntelligence = dialog.findViewById(R.id.et_intelligence), etSpeed = dialog.findViewById(R.id.et_speed), etEndurance = dialog.findViewById(R.id.et_endurance), etRank = dialog.findViewById(R.id.et_rank), etCourage = dialog.findViewById(R.id.et_courage), etFirePower = dialog.findViewById(R.id.et_fire_power), etSkill = dialog.findViewById(R.id.et_skill);
        switch(team){
            case 0:
                newTransformer.setTeam("A");
                break;
            case 1:
                newTransformer.setTeam("D");
                break;

        }
        btnAddTransformer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    newTransformer.setName(etName.getText().toString());
                    newTransformer.setStrength(Integer.parseInt(etStrength.getText().toString()));
                    newTransformer.setIntelligence(Integer.parseInt(etIntelligence.getText().toString()));
                    newTransformer.setSpeed(Integer.parseInt(etSpeed.getText().toString()));
                    newTransformer.setEndurance(Integer.parseInt(etEndurance.getText().toString()));
                    newTransformer.setRank(Integer.parseInt(etRank.getText().toString()));
                    newTransformer.setCourage(Integer.parseInt(etCourage.getText().toString()));
                    newTransformer.setFirepower(Integer.parseInt(etFirePower.getText().toString()));
                    newTransformer.setSkill(Integer.parseInt(etSkill.getText().toString()));
                    presenter.addTransformer(newTransformer);
                    dialog.dismiss();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Please verify fields",Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    /**
     * Displays a Dialog for the user to modify new transfomer
     *
     * @param  transformer  a Transformer object which will be modified
     */
    public void showEditTransformer(Transformer transformer) {
        final TransformerRequest newTransformer = new TransformerRequest();
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.add_transformer_dialog);
        dialog.setTitle("Edit Transformer");
        Button btnAddTransformer = dialog.findViewById(R.id.btn_add_transformer);
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
        btnAddTransformer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    newTransformer.setName(etName.getText().toString());
                    newTransformer.setStrength(Integer.parseInt(etStrength.getText().toString()));
                    newTransformer.setIntelligence(Integer.parseInt(etIntelligence.getText().toString()));
                    newTransformer.setSpeed(Integer.parseInt(etSpeed.getText().toString()));
                    newTransformer.setEndurance(Integer.parseInt(etEndurance.getText().toString()));
                    newTransformer.setRank(Integer.parseInt(etRank.getText().toString()));
                    newTransformer.setCourage(Integer.parseInt(etCourage.getText().toString()));
                    newTransformer.setFirepower(Integer.parseInt(etFirePower.getText().toString()));
                    newTransformer.setSkill(Integer.parseInt(etSkill.getText().toString()));
                    presenter.updateTransformer(newTransformer);
                    dialog.dismiss();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Please verify fields",Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    /**
     * Resets the stats view of each team and resets the adapters for the team members lists
     *
     * @param  autobots  the list of autobots to be displayed
     * @param  decepticons  the list of decepticons to be displayed
     */

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
        currentAutobot = new Transformer();
        currentDecepticon = new Transformer();

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

    /**
     * Starts a new activity for the match to happen
     */
    @Override
    public void goToWar(ArrayList<Transformer> autobots, ArrayList<Transformer> decepticons) {
        Intent intent = new Intent(this, WarActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("autobots", autobots);
        bundle.putSerializable("decepticons",decepticons);
        intent.putExtras(bundle);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_battle:
                presenter.getTeams();
                break;
            case R.id.btn_auto_delete:
                if(currentAutobot!=null){
                    presenter.deleteTransformer(currentAutobot.getId());
                }else{
                    Toast.makeText(getApplicationContext(),"Please select Autobot first",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_auto_edit:
                if(currentAutobot!=null){
                    showEditTransformer(currentAutobot);
                }else{
                    Toast.makeText(getApplicationContext(),"Please select Autobot first",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_decep_delete:
                if(currentDecepticon!=null){
                    presenter.deleteTransformer(currentDecepticon.getId());
                }else{
                    Toast.makeText(getApplicationContext(),"Please select Decepticon first",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_decep_edit:
                if(currentDecepticon!=null){
                    showEditTransformer(currentDecepticon);
                }else{
                    Toast.makeText(getApplicationContext(),"Please select Decepticon first",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_auto_add:
                showAddTransformer(0);
                break;
            case R.id.btn_decep_add:
                showAddTransformer(1);
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
