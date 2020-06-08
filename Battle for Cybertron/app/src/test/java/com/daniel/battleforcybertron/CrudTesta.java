package com.daniel.battleforcybertron;

import android.content.Context;

import com.daniel.battleforcybertron.Model.Transformer;
import com.daniel.battleforcybertron.Model.TransformerRequest;
import com.daniel.battleforcybertron.Presenters.MainActivityPresenter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import static org.awaitility.Awaitility.await;
/**
 * Class that tests CRUD operations for the mainActivityPresenter.
 * set ids of Transformers manually id evrey time you add new transdormer (its in the log).
 */

@RunWith(MockitoJUnitRunner.class)
public class CrudTesta {
    private Transformer testTransformer = new Transformer("-M9HhjSG2MCbT5zRx7_d","testotron","A",10,10,10,10,10,10,10,10,"fdhged");
    private String tstToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0cmFuc2Zvcm1lcnNJZCI6Ii1NOTYzU2t0SGthZE81YlZnYXE2IiwiaWF0IjoxNTkxNDAzOTk0fQ.9bdqIZhmpJ-zGgsO3M4ZuGnG7okEoezulGU7nWOXuWE";
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Mock
    Context context;
    @Before
    public void beforeTest() {

    }
    @Test
    public void testGetAllspark() {
        final MainActivityPresenter p = new MainActivityPresenter(new MainActivityPresenter.View() {

            @Override
            public void refreshTeams(ArrayList<Transformer> autobots, ArrayList<Transformer> decepticons) {

            }

            @Override
            public void goToWar(ArrayList<Transformer> autobots, ArrayList<Transformer> decepticons) {

            }
        },context);
        p.requestAllSpark();
        await().until(new Callable<Boolean>() {
            @Override
            public Boolean call(){
                return p.getAllSparkToken().length() > 0;
            }
        });
        String token = p.getAllSparkToken();
        Assert.assertFalse(token.isEmpty());
    }


    @Test
    public void testGetTransformers() {
        final MainActivityPresenter p = new MainActivityPresenter(new MainActivityPresenter.View() {

            @Override
            public void refreshTeams(ArrayList<Transformer> autobots, ArrayList<Transformer> decepticons) {

            }

            @Override
            public void goToWar(ArrayList<Transformer> autobots, ArrayList<Transformer> decepticons) {

            }
        },context);
        p.setAllSparkToken(tstToken);
        p.getTransformers();
        await().until(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return p.getAllTransformers().size() > 0;
            }
        });
        Assert.assertTrue(p.getAllTransformers().size() > 0);
    }
    @Test
    public void testAddTransformers() {
        final MainActivityPresenter p = new MainActivityPresenter(new MainActivityPresenter.View() {
            @Override
            public void refreshTeams(ArrayList<Transformer> autobots, ArrayList<Transformer> decepticons) {

            }

            @Override
            public void goToWar(ArrayList<Transformer> autobots, ArrayList<Transformer> decepticons) {

            }
        },context);
        p.setAllSparkToken(tstToken);
        p.getTransformers();
        await().until(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return p.getAllTransformers().size() > 0;
            }
        });
        final int sizeBefore = p.getAllTransformers().size();
        p.addTransformer(new TransformerRequest(testTransformer));
        await().until(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return p.getAllTransformers().size() > sizeBefore;
            }
        });
        Assert.assertTrue(p.getAllTransformers().size() > sizeBefore);
    }
    @Test
    public void testEditTransformers() {
        final MainActivityPresenter p = new MainActivityPresenter(new MainActivityPresenter.View() {
            @Override
            public void refreshTeams(ArrayList<Transformer> autobots, ArrayList<Transformer> decepticons) {

            }

            @Override
            public void goToWar(ArrayList<Transformer> autobots, ArrayList<Transformer> decepticons) {

            }
        },context);
        p.setAllSparkToken(tstToken);
        final String nameBefore = testTransformer.getName();
        final Transformer temp = testTransformer;
        temp.setName("holotron");
        await().until(new Callable<Boolean>() {
            @Override
            public Boolean call(){
                return !nameBefore.equals(p.updateTransformer(new TransformerRequest(temp)));
            }
        });
        Assert.assertFalse(nameBefore.equals(p.updateTransformer(new TransformerRequest(temp))));
    }

    @Test
    public void testDeleteTransformer() {
        final MainActivityPresenter p = new MainActivityPresenter(new MainActivityPresenter.View() {
            @Override
            public void refreshTeams(ArrayList<Transformer> autobots, ArrayList<Transformer> decepticons) {

            }

            @Override
            public void goToWar(ArrayList<Transformer> autobots, ArrayList<Transformer> decepticons) {

            }
        },context);
        p.setAllSparkToken(tstToken);
        p.getTransformers();
        await().until(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return p.getAllTransformers().size() > 0;
            }
        });
        final int sizeBefore = p.getAllTransformers().size();
        p.deleteTransformer(testTransformer.getId());
        await().until(new Callable<Boolean>() {
            @Override
            public Boolean call(){
                return p.getAllTransformers().size() < sizeBefore;
            }
        });
        Assert.assertTrue(p.getAllTransformers().size() < sizeBefore);
    }
}
