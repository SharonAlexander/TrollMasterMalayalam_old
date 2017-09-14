package com.sharon.trollmastermalayalam;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;


public class AddRemovePagesActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    CheckBox icuCheck, trollMCheck, trollRCheck, mntCheck, dankCheck, psctrollsCheck, kidilanCheck, sctCheck,
            trollcricketCheck, trollfootballCheck, trollmcinemaCheck, mtmCheck, sheruCheck, cinemamixerCheck,
            cybertrollersCheck, thengakolaCheck, trollmollywoodCheck, trollclashersCheck, outspokenCheck,
            btechtrollsCheck, mplingCheck, trollkeralaCheck, trollreligionCheck, trollktuCheck, pravasitrollsCheck, onlinetmCheck;
    Preferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_remove_layout);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_to_add);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            linearLayout.setLayoutDirection(LinearLayout.LAYOUT_DIRECTION_RTL);
        }

        preferences = new Preferences(this);

        initialiseCheckBoxes();
        setChecks();
        setCheckListeners();
    }


    private void initialiseCheckBoxes() {
        icuCheck = (CheckBox) findViewById(R.id.icuCheck);
        trollMCheck = (CheckBox) findViewById(R.id.trollMCheck);
        trollRCheck = (CheckBox) findViewById(R.id.trollRCheck);
        mntCheck = (CheckBox) findViewById(R.id.mntCheck);
        dankCheck = (CheckBox) findViewById(R.id.dankCheck);
        psctrollsCheck = (CheckBox) findViewById(R.id.psctrollsCheck);
        kidilanCheck = (CheckBox) findViewById(R.id.kidilanCheck);
        sctCheck = (CheckBox) findViewById(R.id.sctCheck);
        trollcricketCheck = (CheckBox) findViewById(R.id.trollcricketCheck);
        trollfootballCheck = (CheckBox) findViewById(R.id.trollfootballCheck);
        trollmcinemaCheck = (CheckBox) findViewById(R.id.trollmcinemaCheck);
        mtmCheck = (CheckBox) findViewById(R.id.mtmCheck);
        sheruCheck = (CheckBox) findViewById(R.id.sheruCheck);
        cinemamixerCheck = (CheckBox) findViewById(R.id.cinemamixerCheck);
        cybertrollersCheck = (CheckBox) findViewById(R.id.cybertrollersCheck);
        thengakolaCheck = (CheckBox) findViewById(R.id.thengakolaCheck);
        trollmollywoodCheck = (CheckBox) findViewById(R.id.trollmollywoodCheck);
        trollclashersCheck = (CheckBox) findViewById(R.id.trollclashersCheck);
        outspokenCheck = (CheckBox) findViewById(R.id.outspokenCheck);
        btechtrollsCheck = (CheckBox) findViewById(R.id.btechtrollsCheck);
        mplingCheck = (CheckBox) findViewById(R.id.mplingCheck);
        trollkeralaCheck = (CheckBox) findViewById(R.id.trollkeralaCheck);
        trollreligionCheck = (CheckBox) findViewById(R.id.trollreligionCheck);
        trollktuCheck = (CheckBox) findViewById(R.id.trollktuCheck);
        pravasitrollsCheck = (CheckBox) findViewById(R.id.pravasitrollsCheck);
        onlinetmCheck = (CheckBox) findViewById(R.id.onlinetmCheck);
    }

    private void setChecks() {
        icuCheck.setChecked(preferences.getCheckPref("icu"));
        trollMCheck.setChecked(preferences.getCheckPref("trollm"));
        trollRCheck.setChecked(preferences.getCheckPref("trollr"));
        mntCheck.setChecked(preferences.getCheckPref("mnt"));
        dankCheck.setChecked(preferences.getCheckPref("dank"));
        psctrollsCheck.setChecked(preferences.getCheckPref("psctrolls"));
        kidilanCheck.setChecked(preferences.getCheckPref("kidilan"));
        sctCheck.setChecked(preferences.getCheckPref("sct"));
        trollcricketCheck.setChecked(preferences.getCheckPref("trollcricket"));
        trollfootballCheck.setChecked(preferences.getCheckPref("trollfootball"));
        trollmcinemaCheck.setChecked(preferences.getCheckPref("trollmcinema"));
        mtmCheck.setChecked(preferences.getCheckPref("mtm"));
        sheruCheck.setChecked(preferences.getCheckPref("sheru"));
        cinemamixerCheck.setChecked(preferences.getCheckPref("cinemamixer"));
        cybertrollersCheck.setChecked(preferences.getCheckPref("cybertrollers"));
        thengakolaCheck.setChecked(preferences.getCheckPref("thengakola"));
        trollmollywoodCheck.setChecked(preferences.getCheckPref("trollmollywood"));
        trollclashersCheck.setChecked(preferences.getCheckPref("trollclashers"));
        outspokenCheck.setChecked(preferences.getCheckPref("outspoken"));
        btechtrollsCheck.setChecked(preferences.getCheckPref("btechtrolls"));
        mplingCheck.setChecked(preferences.getCheckPref("mpling"));
        trollkeralaCheck.setChecked(preferences.getCheckPref("trollkerala"));
        trollreligionCheck.setChecked(preferences.getCheckPref("trollreligion"));
        trollktuCheck.setChecked(preferences.getCheckPref("trollktu"));
        pravasitrollsCheck.setChecked(preferences.getCheckPref("pravasitrolls"));
        onlinetmCheck.setChecked(preferences.getCheckPref("onlinetm"));
    }

    private void setCheckListeners() {
        icuCheck.setOnCheckedChangeListener(this);
        trollMCheck.setOnCheckedChangeListener(this);
        trollRCheck.setOnCheckedChangeListener(this);
        mntCheck.setOnCheckedChangeListener(this);
        dankCheck.setOnCheckedChangeListener(this);
        psctrollsCheck.setOnCheckedChangeListener(this);
        kidilanCheck.setOnCheckedChangeListener(this);
        sctCheck.setOnCheckedChangeListener(this);
        trollcricketCheck.setOnCheckedChangeListener(this);
        trollfootballCheck.setOnCheckedChangeListener(this);
        trollmcinemaCheck.setOnCheckedChangeListener(this);
        mtmCheck.setOnCheckedChangeListener(this);
        sheruCheck.setOnCheckedChangeListener(this);
        cinemamixerCheck.setOnCheckedChangeListener(this);
        cybertrollersCheck.setOnCheckedChangeListener(this);
        thengakolaCheck.setOnCheckedChangeListener(this);
        trollmollywoodCheck.setOnCheckedChangeListener(this);
        trollclashersCheck.setOnCheckedChangeListener(this);
        outspokenCheck.setOnCheckedChangeListener(this);
        btechtrollsCheck.setOnCheckedChangeListener(this);
        mplingCheck.setOnCheckedChangeListener(this);
        trollkeralaCheck.setOnCheckedChangeListener(this);
        trollreligionCheck.setOnCheckedChangeListener(this);
        trollktuCheck.setOnCheckedChangeListener(this);
        pravasitrollsCheck.setOnCheckedChangeListener(this);
        onlinetmCheck.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.icuCheck:
                if (compoundButton.isChecked()) {
//                    MainActivity.result.addItemAtPosition(MainActivity.item_icu, 1);
                    icuCheck.setChecked(true);
//                    preferences.putCheckPref("icu", true);
                } else {
//                    MainActivity.result.removeItem(1);
                    icuCheck.setChecked(true);
//                    preferences.putCheckPref("icu", false);
                }
                break;
            case R.id.trollMCheck:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_trollmalayalam, 2);
                    trollMCheck.setChecked(true);
                    preferences.putCheckPref("trollm", true);
                } else {
                    MainActivity.result.removeItem(2);
                    trollMCheck.setChecked(false);
                    preferences.putCheckPref("trollm", false);
                }
                break;
            case R.id.trollRCheck:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_trollrepublic, 3);
                    trollRCheck.setChecked(true);
                    preferences.putCheckPref("trollr", true);
                } else {
                    MainActivity.result.removeItem(3);
                    trollRCheck.setChecked(false);
                    preferences.putCheckPref("trollr", false);
                }
                break;
            case R.id.mntCheck:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_mnt, 4);
                    mntCheck.setChecked(true);
                    preferences.putCheckPref("mnt", true);
                } else {
                    MainActivity.result.removeItem(4);
                    mntCheck.setChecked(false);
                    preferences.putCheckPref("mnt", false);
                }
                break;
            case R.id.dankCheck:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_dank, 5);
                    dankCheck.setChecked(true);
                    preferences.putCheckPref("dank", true);
                } else {
                    MainActivity.result.removeItem(5);
                    dankCheck.setChecked(false);
                    preferences.putCheckPref("dank", false);
                }
                break;
            case R.id.psctrollsCheck:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_psctrolls, 5);
                    psctrollsCheck.setChecked(true);
                    preferences.putCheckPref("psctrolls", true);
                } else {
                    MainActivity.result.removeItem(6);
                    psctrollsCheck.setChecked(false);
                    preferences.putCheckPref("psctrolls", false);
                }
                break;
            case R.id.kidilanCheck:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_kidilantrolls, 5);
                    kidilanCheck.setChecked(true);
                    preferences.putCheckPref("kidilan", true);
                } else {
                    MainActivity.result.removeItem(7);
                    kidilanCheck.setChecked(false);
                    preferences.putCheckPref("kidilan", false);
                }
                break;
            case R.id.sctCheck:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_sct, 5);
                    sctCheck.setChecked(true);
                    preferences.putCheckPref("sct", true);
                } else {
                    MainActivity.result.removeItem(8);
                    sctCheck.setChecked(false);
                    preferences.putCheckPref("sct", false);
                }
                break;
            case R.id.trollcricketCheck:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_trollcricket, 5);
                    trollcricketCheck.setChecked(true);
                    preferences.putCheckPref("trollcricket", true);
                } else {
                    MainActivity.result.removeItem(9);
                    trollcricketCheck.setChecked(false);
                    preferences.putCheckPref("trollcricket", false);
                }
                break;
            case R.id.trollfootballCheck:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_trollfootball, 5);
                    trollfootballCheck.setChecked(true);
                    preferences.putCheckPref("trollfootball", true);
                } else {
                    MainActivity.result.removeItem(10);
                    trollfootballCheck.setChecked(false);
                    preferences.putCheckPref("trollfootball", false);
                }
                break;
            case R.id.trollmcinemaCheck:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_trollmalayalamcinema, 5);
                    trollmcinemaCheck.setChecked(true);
                    preferences.putCheckPref("trollmcinema", true);
                } else {
                    MainActivity.result.removeItem(11);
                    trollmcinemaCheck.setChecked(false);
                    preferences.putCheckPref("trollmcinema", false);
                }
                break;
            case R.id.mtmCheck:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_malayalamtrollmasters, 5);
                    mtmCheck.setChecked(true);
                    preferences.putCheckPref("mtm", true);
                } else {
                    MainActivity.result.removeItem(12);
                    mtmCheck.setChecked(false);
                    preferences.putCheckPref("mtm", false);
                }
                break;
            case R.id.sheruCheck:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_sheruenthub, 5);
                    sheruCheck.setChecked(true);
                    preferences.putCheckPref("sheru", true);
                } else {
                    MainActivity.result.removeItem(13);
                    sheruCheck.setChecked(false);
                    preferences.putCheckPref("sheru", false);
                }
                break;
            case R.id.cinemamixerCheck:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_cinemamixer, 5);
                    cinemamixerCheck.setChecked(true);
                    preferences.putCheckPref("cinemamixer", true);
                } else {
                    MainActivity.result.removeItem(14);
                    cinemamixerCheck.setChecked(false);
                    preferences.putCheckPref("cinemamixer", false);
                }
                break;
            case R.id.cybertrollersCheck:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_cybertrollers, 5);
                    cybertrollersCheck.setChecked(true);
                    preferences.putCheckPref("cybertrollers", true);
                } else {
                    MainActivity.result.removeItem(15);
                    cybertrollersCheck.setChecked(false);
                    preferences.putCheckPref("cybertrollers", false);
                }
                break;
            case R.id.thengakolaCheck:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_thengakola, 5);
                    thengakolaCheck.setChecked(true);
                    preferences.putCheckPref("thengakola", true);
                } else {
                    MainActivity.result.removeItem(16);
                    thengakolaCheck.setChecked(false);
                    preferences.putCheckPref("thengakola", false);
                }
                break;
            case R.id.trollmollywoodCheck:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_trollmollywood, 5);
                    trollmollywoodCheck.setChecked(true);
                    preferences.putCheckPref("trollmollywood", true);
                } else {
                    MainActivity.result.removeItem(17);
                    trollmollywoodCheck.setChecked(false);
                    preferences.putCheckPref("trollmollywood", false);
                }
                break;
            case R.id.trollclashersCheck:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_trollclasherskerala, 5);
                    trollclashersCheck.setChecked(true);
                    preferences.putCheckPref("trollclashers", true);
                } else {
                    MainActivity.result.removeItem(18);
                    trollclashersCheck.setChecked(false);
                    preferences.putCheckPref("trollclashers", false);
                }
                break;
            case R.id.outspokenCheck:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_outspoken, 5);
                    outspokenCheck.setChecked(true);
                    preferences.putCheckPref("outspoken", true);
                } else {
                    MainActivity.result.removeItem(19);
                    outspokenCheck.setChecked(false);
                    preferences.putCheckPref("outspoken", false);
                }
                break;
            case R.id.btechtrollsCheck:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_btechtrolls, 5);
                    btechtrollsCheck.setChecked(true);
                    preferences.putCheckPref("btechtrolls", true);
                } else {
                    MainActivity.result.removeItem(20);
                    btechtrollsCheck.setChecked(false);
                    preferences.putCheckPref("btechtrolls", false);
                }
                break;
            case R.id.onlinetmCheck:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_onlinetrollmedia, 5);
                    onlinetmCheck.setChecked(true);
                    preferences.putCheckPref("onlinetm", true);
                } else {
                    MainActivity.result.removeItem(21);
                    onlinetmCheck.setChecked(false);
                    preferences.putCheckPref("onlinetm", false);
                }
                break;
            case R.id.trollkeralaCheck:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_trollkerala, 5);
                    trollkeralaCheck.setChecked(true);
                    preferences.putCheckPref("trollkerala", true);
                } else {
                    MainActivity.result.removeItem(22);
                    trollkeralaCheck.setChecked(false);
                    preferences.putCheckPref("trollkerala", false);
                }
                break;
            case R.id.trollreligionCheck:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_trollreligion, 5);
                    trollreligionCheck.setChecked(true);
                    preferences.putCheckPref("trollreligion", true);
                } else {
                    MainActivity.result.removeItem(23);
                    trollreligionCheck.setChecked(false);
                    preferences.putCheckPref("trollreligion", false);
                }
                break;
            case R.id.trollktuCheck:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_trollktu, 5);
                    trollktuCheck.setChecked(true);
                    preferences.putCheckPref("trollktu", true);
                } else {
                    MainActivity.result.removeItem(24);
                    trollktuCheck.setChecked(false);
                    preferences.putCheckPref("trollktu", false);
                }
                break;
            case R.id.pravasitrollsCheck:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_pravasitrolls, 5);
                    pravasitrollsCheck.setChecked(true);
                    preferences.putCheckPref("pravasitrolls", true);
                } else {
                    MainActivity.result.removeItem(25);
                    pravasitrollsCheck.setChecked(false);
                    preferences.putCheckPref("pravasitrolls", false);
                }
                break;
            case R.id.mplingCheck:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_malayalampling, 5);
                    mplingCheck.setChecked(true);
                    preferences.putCheckPref("mpling", true);
                } else {
                    MainActivity.result.removeItem(26);
                    mplingCheck.setChecked(false);
                    preferences.putCheckPref("mpling", false);
                }
                break;
        }
    }
}
