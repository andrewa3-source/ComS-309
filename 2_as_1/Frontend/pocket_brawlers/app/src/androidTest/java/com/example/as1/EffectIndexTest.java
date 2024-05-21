package com.example.as1;

import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EffectIndexTest {

    public ActivityTestRule<BrawlerActivity> activityRule = new ActivityTestRule<>(BrawlerActivity.class);;

    @Before
    public void setup(){
        Intent i = new Intent();
        i.putExtra("username",  "jack");
        i.putExtra("password", "123");
        i.putExtra("accountType", 1);
        i.putExtra("signedIn", true);
        i.putExtra("id", 18);
        i.putExtra("league_id", 10);
        i.putExtra("useWebsockets", false);
        activityRule.launchActivity(i);
        try {
            Thread.sleep(1000); //wait for server
        } catch (InterruptedException e) {
        }
    }

    @Test
    public void case0Test(){
        int result = 5;
        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ActiveLineup.removeBrawler(0);
                Brawler testBrawler = BrawlerIndex.getBrawler(1, 3, 3, 0, BrawlerEffectType.valueOf("BUY"), "https://i.imgur.com/P0WQtmC.png");
                ActiveLineup.setBrawler(0, testBrawler);
                ActiveLineup.setBrawler(1, testBrawler);
                EffectIndex.getEffect(0, 1).run();
            }
        });
        try {
            Thread.sleep(1000); //wait for server
        } catch (InterruptedException e) {
        }
        assert(ActiveLineup.getLineup()[0].getBrawler().getHealth() == result || ActiveLineup.getLineup()[1].getBrawler().getHealth() == result);
    }

    @Test
    public void case1Test(){
        int result = 5;
        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ActiveLineup.removeBrawler(0);
                ActiveLineup.removeBrawler(1);
                Brawler testBrawler = BrawlerIndex.getBrawler(2, 2, 5, 1, BrawlerEffectType.valueOf("BUY"), "https://i.imgur.com/Xhy1WqH.png");
                ActiveLineup.setBrawler(0, testBrawler);
                ActiveLineup.setBrawler(1, testBrawler);
                EffectIndex.getEffect(1, 2).run();
            }
        });
        try {
            Thread.sleep(1000); //wait for server
        } catch (InterruptedException e) {
        }
        assert(ActiveLineup.getLineup()[0].getBrawler().getHealth() == result && ActiveLineup.getLineup()[1].getBrawler().getHealth() == result);
    }

    @Test
    public void case2Test(){
        int result = 4;
        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ActiveLineup.removeBrawler(0);
                ActiveLineup.removeBrawler(1);
                Brawler testBrawler = BrawlerIndex.getBrawler(3, 2, 2, 2, BrawlerEffectType.valueOf("BUY"), "https://i.imgur.com/4gkdoAO.png");
                ActiveLineup.setBrawler(0, testBrawler);
                ActiveLineup.setBrawler(1, testBrawler);
                EffectIndex.getEffect(2, 3).run();
            }
        });
        try {
            Thread.sleep(1000); //wait for server
        } catch (InterruptedException e) {
        }
        assert(ActiveLineup.getLineup()[0].getBrawler().getAttack() == result || ActiveLineup.getLineup()[1].getBrawler().getAttack() == result);
    }

    @Test
    public void case3Test(){
        int result = 6;
        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ActiveLineup.removeBrawler(0);
                ActiveLineup.removeBrawler(1);
                Brawler testBrawler = BrawlerIndex.getBrawler(4, 2, 5, 3, BrawlerEffectType.valueOf("BUY"), "https://i.imgur.com/8JeJiLh.png");
                ActiveLineup.setBrawler(0, testBrawler);
                ActiveLineup.setBrawler(1, testBrawler);
                EffectIndex.getEffect(3, 4).run();
            }
        });
        try {
            Thread.sleep(1000); //wait for server
        } catch (InterruptedException e) {
        }
        assert(ActiveLineup.getLineup()[0].getBrawler().getAttack() == result && ActiveLineup.getLineup()[1].getBrawler().getAttack() == result);
    }

    @Test
    public void case4Test(){
        int result = 3;
        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ActiveLineup.removeBrawler(0);
                ActiveLineup.removeBrawler(1);
                Brawler testBrawler = BrawlerIndex.getBrawler(154, 5, 5, 4, BrawlerEffectType.valueOf("BUY"), "https://i.imgur.com/WCNgmz5.png");
                ActiveLineup.setBrawler(0, testBrawler);
                ActiveLineup.setBrawler(1, testBrawler);
                EffectIndex.getEffect(4, 154).run();
            }
        });
        try {
            Thread.sleep(1000); //wait for server
        } catch (InterruptedException e) {
        }
        assert(ActiveLineup.getLineup()[0].getBrawler().getAttack() == result && ActiveLineup.getLineup()[1].getBrawler().getAttack() == result);
    }

    @Test
    public void case5Test(){
        int result = 2;
        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ActiveLineup.removeBrawler(0);
                ActiveLineup.removeBrawler(1);
                Brawler testBrawler = BrawlerIndex.getBrawler(155, 2, 2, 5, BrawlerEffectType.valueOf("BATTLE"), "https://i.imgur.com/X49ue2w.png");
                ActiveLineup.setBrawler(0, testBrawler);
                ActiveLineup.setBrawler(1, testBrawler);
                EffectIndex.getEffect(5, 155).run();
            }
        });
        try {
            Thread.sleep(1000); //wait for server
        } catch (InterruptedException e) {
        }
        assert(ActiveLineup.getLineup()[0].getBrawler().getAttack() == result && ActiveLineup.getLineup()[1].getBrawler().getAttack() == result);
    }
}

