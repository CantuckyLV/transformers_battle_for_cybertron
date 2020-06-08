package com.daniel.battleforcybertron;

import android.content.Context;
import android.os.Bundle;

import com.daniel.battleforcybertron.Model.Transformer;
import com.daniel.battleforcybertron.Presenters.MainActivityPresenter;
import com.daniel.battleforcybertron.Presenters.WarActivityPresenter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import static org.awaitility.Awaitility.await;

/**
 * Class that tests a single battle.
 * set ids of Transformers manually id evrey time you add new transdormer (its in the log).
 * move parameters to test different scenarios.
 */
public class BattleTest {
    private Transformer testTransformer = new Transformer("-M9HhjSG2MCbT5zRx7_d","testotron","A",10,10,10,10,10,10,10,10,"fdhged");
    private Transformer testTransformer2 = new Transformer("-M96Z4ggG9KjR364Rf_A","testotron2","D",10,10,10,10,10,10,10,10,"fdhged");
    private Transformer testTransformer3 = new Transformer("-M9HhjSG2MCbT5zRx7_d","testotron3","A",10,10,10,10,10,10,10,10,"fdhged");
    private Transformer testTransformer4 = new Transformer("-M96Z4ggG9KjR364Rf_A","testotron4","D",10,10,10,10,10,10,10,10,"fdhged");
    private String tstToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0cmFuc2Zvcm1lcnNJZCI6Ii1NOTYzU2t0SGthZE81YlZnYXE2IiwiaWF0IjoxNTkxNDAzOTk0fQ.9bdqIZhmpJ-zGgsO3M4ZuGnG7okEoezulGU7nWOXuWE";
    private ArrayList<Transformer> autobots = new ArrayList<>();
    private ArrayList<Transformer> decepticons = new ArrayList<>();


    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Mock
    Context context;
    @Before
    public void beforeTest() {
        autobots.add(testTransformer);
        autobots.add(testTransformer2);
        decepticons.add(testTransformer3);
        decepticons.add(testTransformer4);
    }
    @Test
    public void testBattle() {
        final WarActivityPresenter p = new WarActivityPresenter(new WarActivityPresenter.View() {

            @Override
            public void showProgressDialog() {

            }

            @Override
            public void hideProgressDialog() {

            }

            @Override
            public void showWinner(int result, int rounds, ArrayList<Transformer> survivors) {

            }
        },context);
        p.setAllSparkToken(tstToken);
        p.wageWar(autobots,decepticons);
        await().until(new Callable<Boolean>() {
            @Override
            public Boolean call(){
                return p.isMatchFinished();
            }
        });
        Assert.assertTrue(p.isMatchFinished()&&p.getRounds()>0);
    }
}
