package com.sharon.trollmastermalayalam;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static Drawer result;
    static PrimaryDrawerItem item_icu, item_trollmalayalam, item_trollrepublic, item_mnt, item_dank, item_malayalampling, item_kidilantrolls,
            item_sct, item_trollcricket, item_trollfootball, item_trollmalayalamcinema, item_malayalamtrollmasters, item_sheruenthub,
            item_cinemamixer, item_cybertrollers, item_thengakola, item_trollmollywood, item_trollclasherskerala, item_outspoken,
            item_btechtrolls, item_psctrolls, item_trollkerala, item_trollreligion, item_trollktu, item_pravasitrolls, item_onlinetrollmedia;
    Toolbar toolbar;
    AccountHeader headerResult;
    DividerDrawerItem item_divider;
    SecondaryDrawerItem item_settings, item_about, item_shareTheApp, item_addRemove;
    Preferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        preferences = new Preferences(this);

        if (preferences.isFirstTimeLaunch()) {
            setDefaultDrawerItemChecks();
        }

        completeNavigationDrawer();
    }

    private void setDefaultDrawerItemChecks() {
        preferences.putCheckPref("icu", true);
        preferences.putCheckPref("trollm", true);
        preferences.putCheckPref("trollr", true);
        preferences.putCheckPref("mnt", true);
        preferences.putCheckPref("dank", true);
        preferences.putCheckPref("mpling", true);
        preferences.putCheckPref("kidilan", true);
        preferences.putCheckPref("sct", true);
        preferences.putCheckPref("trollcricket", true);
        preferences.putCheckPref("trollfootball", true);
        preferences.putCheckPref("trollmcinema", true);
        preferences.putCheckPref("mtm", true);
        preferences.putCheckPref("sheru", true);
        preferences.putCheckPref("cinemamixer", true);
        preferences.putCheckPref("cybertrollers", true);
        preferences.putCheckPref("thengakola", true);
        preferences.putCheckPref("trollmollywood", true);
        preferences.putCheckPref("trollclashers", true);
        preferences.putCheckPref("outspoken", true);
        preferences.putCheckPref("btechtrolls", true);
        preferences.putCheckPref("psctrolls", true);
        preferences.putCheckPref("trollkerala", true);
        preferences.putCheckPref("trollreligion", true);
        preferences.putCheckPref("trollktu", true);
        preferences.putCheckPref("pravasitrolls", true);
        preferences.putCheckPref("onlinetm", true);

        preferences.setFirstTimeLaunch(false);
    }

    private void completeNavigationDrawer() {
        createNavigationHeader();
        createDrawerLayout();
        createDrawerItems();
        addDrawerItems();
        createDrawerClicks();

        //The First Page is ICU. And cannot be changed from addremove
        result.setSelection(1);
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if (result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            alertExitPic();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createNavigationHeader() {
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.nav_header)
                .addProfiles(
                        new ProfileDrawerItem().withName(getString(R.string.app_name)).withIcon(getResources().getDrawable(R.mipmap.ic_launcher))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
    }

    private void createDrawerLayout() {
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .withTranslucentStatusBar(true)
                .build();
    }

    private void createDrawerItems() {
        item_icu = new PrimaryDrawerItem().withIdentifier(1).withName(getString(R.string.pagename_icu)).withIcon(R.drawable.icon_icu);
        item_trollmalayalam = new PrimaryDrawerItem().withIdentifier(2).withName(getString(R.string.pagename_troll_malayalam)).withIcon(R.drawable.icon_trollm);
        item_trollrepublic = new PrimaryDrawerItem().withIdentifier(3).withName(getString(R.string.pagename_troll_republic)).withIcon(R.drawable.icon_trollrepublic);
        item_mnt = new PrimaryDrawerItem().withIdentifier(4).withName(getString(R.string.pagename_malayalam_naughty_trolls)).withIcon(R.drawable.icon_mnt);
        item_dank = new PrimaryDrawerItem().withIdentifier(5).withName(getString(R.string.pagename_dank_memes_malayalam)).withIcon(R.drawable.icon_dank);
        item_psctrolls = new PrimaryDrawerItem().withIdentifier(6).withName(getString(R.string.pagename_psc_trolls)).withIcon(R.drawable.icon_psc);
        item_kidilantrolls = new PrimaryDrawerItem().withIdentifier(7).withName(getString(R.string.pagename_kidilan_trolls)).withIcon(R.drawable.icon_kidilantrolls);
        item_sct = new PrimaryDrawerItem().withIdentifier(8).withName(getString(R.string.pagename_school_college_trolls)).withIcon(R.drawable.icon_sct);
        item_trollcricket = new PrimaryDrawerItem().withIdentifier(9).withName(getString(R.string.pagename_troll_cricket_malayalam)).withIcon(R.drawable.icon_trollcricket);
        item_trollfootball = new PrimaryDrawerItem().withIdentifier(10).withName(getString(R.string.pagename_troll_football_malayalam)).withIcon(R.drawable.icon_trollfootballmalayalam);
        item_trollmalayalamcinema = new PrimaryDrawerItem().withIdentifier(11).withName(getString(R.string.pagename_troll_malayalam_cinema)).withIcon(R.drawable.icon_trollmalayalamcinema);
        item_malayalamtrollmasters = new PrimaryDrawerItem().withIdentifier(12).withName(getString(R.string.pagename_malayalam_troll_masters_mtm)).withIcon(R.drawable.icon_malayalamtrollmasters);
        item_sheruenthub = new PrimaryDrawerItem().withIdentifier(13).withName(getString(R.string.pagename_sheru_entertainment_hub)).withIcon(R.drawable.icon_sheruenthub);
        item_cinemamixer = new PrimaryDrawerItem().withIdentifier(14).withName(getString(R.string.pagename_cinemamixer)).withIcon(R.drawable.icon_cinemamixer);
        item_cybertrollers = new PrimaryDrawerItem().withIdentifier(15).withName(getString(R.string.pagename_cyber_trollers)).withIcon(R.drawable.icon_cybertroller);
        item_thengakola = new PrimaryDrawerItem().withIdentifier(16).withName(getString(R.string.pagename_thengakola)).withIcon(R.drawable.icon_thengakola);
        item_trollmollywood = new PrimaryDrawerItem().withIdentifier(17).withName(getString(R.string.pagename_troll_mollywood)).withIcon(R.drawable.icon_trollmollywood);
        item_trollclasherskerala = new PrimaryDrawerItem().withIdentifier(18).withName(getString(R.string.pagename_troll_clashers_kerala)).withIcon(R.drawable.icon_trollclasherskerala);
        item_outspoken = new PrimaryDrawerItem().withIdentifier(19).withName(getString(R.string.pagename_outspoken)).withIcon(R.drawable.icon_outspoken);
        item_btechtrolls = new PrimaryDrawerItem().withIdentifier(20).withName(getString(R.string.pagename_b_tech_trolls)).withIcon(R.drawable.icon_btechtrolls);
        item_onlinetrollmedia = new PrimaryDrawerItem().withIdentifier(21).withName(getString(R.string.pagename_online_troll_media_otm)).withIcon(R.drawable.icon_onlinetrollmedia);
        item_trollkerala = new PrimaryDrawerItem().withIdentifier(22).withName(getString(R.string.pagename_troll_kerala)).withIcon(R.drawable.icon_trollkerala);
        item_trollreligion = new PrimaryDrawerItem().withIdentifier(23).withName(getString(R.string.pagename_troll_religion)).withIcon(R.drawable.icon_trollreligion);
        item_trollktu = new PrimaryDrawerItem().withIdentifier(24).withName(getString(R.string.pagename_troll_ktu)).withIcon(R.drawable.icon_trollktu);
        item_pravasitrolls = new PrimaryDrawerItem().withIdentifier(25).withName(getString(R.string.pagename_pravasi_trolls)).withIcon(R.drawable.icon_pravasitrolls);
        item_malayalampling = new PrimaryDrawerItem().withIdentifier(26).withName(getString(R.string.pagename_malayalam_pling)).withIcon(R.drawable.icon_malayalampling);

        item_divider = new DividerDrawerItem();

        item_settings = new SecondaryDrawerItem().withIdentifier(100).withName(getString(R.string.action_settings)).withIcon(R.drawable.settings).withSelectable(false);
        item_about = new SecondaryDrawerItem().withIdentifier(101).withName(getString(R.string.about)).withIcon(R.drawable.about).withSelectable(false);
        item_shareTheApp = new SecondaryDrawerItem().withIdentifier(102).withName(R.string.share).withIcon(R.drawable.share).withSelectable(false);
        item_addRemove = new SecondaryDrawerItem().withIdentifier(103).withName(R.string.add_remove).withIcon(R.drawable.add_remove).withSelectable(false);
    }

    private void addDrawerItems() {
        Preferences preferences = new Preferences(this);
        if (preferences.getCheckPref("icu")) {
            result.addItem(item_icu);
        }
        if (preferences.getCheckPref("trollm")) {
            result.addItem(item_trollmalayalam);
        }
        if (preferences.getCheckPref("trollr")) {
            result.addItem(item_trollrepublic);
        }
        if (preferences.getCheckPref("mnt")) {
            result.addItem(item_mnt);
        }
        if (preferences.getCheckPref("dank")) {
            result.addItem(item_dank);
        }
        if (preferences.getCheckPref("psctrolls")) {
            result.addItem(item_psctrolls);
        }
        if (preferences.getCheckPref("kidilan")) {
            result.addItem(item_kidilantrolls);
        }
        if (preferences.getCheckPref("sct")) {
            result.addItem(item_sct);
        }
        if (preferences.getCheckPref("trollcricket")) {
            result.addItem(item_trollcricket);
        }
        if (preferences.getCheckPref("trollfootball")) {
            result.addItem(item_trollfootball);
        }
        if (preferences.getCheckPref("trollmcinema")) {
            result.addItem(item_trollmalayalamcinema);
        }
        if (preferences.getCheckPref("mtm")) {
            result.addItem(item_malayalamtrollmasters);
        }
        if (preferences.getCheckPref("sheru")) {
            result.addItem(item_sheruenthub);
        }
        if (preferences.getCheckPref("cinemamixer")) {
            result.addItem(item_cinemamixer);
        }
        if (preferences.getCheckPref("cybertrollers")) {
            result.addItem(item_cybertrollers);
        }
        if (preferences.getCheckPref("thengakola")) {
            result.addItem(item_thengakola);
        }
        if (preferences.getCheckPref("trollmollywood")) {
            result.addItem(item_trollmollywood);
        }
        if (preferences.getCheckPref("trollclashers")) {
            result.addItem(item_trollclasherskerala);
        }
        if (preferences.getCheckPref("outspoken")) {
            result.addItem(item_outspoken);
        }
        if (preferences.getCheckPref("btechtrolls")) {
            result.addItem(item_btechtrolls);
        }
        if (preferences.getCheckPref("onlinetm")) {
            result.addItem(item_onlinetrollmedia);
        }
        if (preferences.getCheckPref("trollkerala")) {
            result.addItem(item_trollkerala);
        }
        if (preferences.getCheckPref("trollreligion")) {
            result.addItem(item_trollreligion);
        }
        if (preferences.getCheckPref("trollktu")) {
            result.addItem(item_trollktu);
        }
        if (preferences.getCheckPref("pravasitrolls")) {
            result.addItem(item_pravasitrolls);
        }
        if (preferences.getCheckPref("mpling")) {
            result.addItem(item_malayalampling);
        }
        result.addItems(item_divider, item_addRemove, item_settings, item_about, item_shareTheApp);
    }

    private void createDrawerClicks() {
        result.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Fragment fragment = new ContentActivity();
                Bundle bundle = new Bundle();
                switch ((int) drawerItem.getIdentifier()) {
                    case 1:
                        bundle.putString("id", Constants.id_icu);
                        bundle.putInt("pic", R.drawable.icon_icu);
                        break;
                    case 2:
                        bundle.putString("id", Constants.id_trollmalayalam);
                        bundle.putInt("pic", R.drawable.icon_trollm);
                        break;
                    case 3:
                        bundle.putString("id", Constants.id_trollrepublic);
                        bundle.putInt("pic", R.drawable.icon_trollrepublic);
                        break;
                    case 4:
                        alertMntAccess();
                        bundle.putString("id", Constants.id_mnt);
                        bundle.putInt("pic", R.drawable.icon_mnt);
                        break;
                    case 5:
                        bundle.putString("id", Constants.id_dank);
                        bundle.putInt("pic", R.drawable.icon_dank);
                        break;
                    case 6:
                        bundle.putString("id", Constants.id_psctrolls);
                        bundle.putInt("pic", R.drawable.icon_psc);
                        break;
                    case 7:
                        bundle.putString("id", Constants.id_kidilantrolls);
                        bundle.putInt("pic", R.drawable.icon_kidilantrolls);
                        break;
                    case 8:
                        bundle.putString("id", Constants.id_sctschoolcollege);
                        bundle.putInt("pic", R.drawable.icon_sct);
                        break;
                    case 9:
                        bundle.putString("id", Constants.id_trollcricketmalayalam);
                        bundle.putInt("pic", R.drawable.icon_trollcricket);
                        break;
                    case 10:
                        bundle.putString("id", Constants.id_trollfootballmalayalam);
                        bundle.putInt("pic", R.drawable.icon_trollfootballmalayalam);
                        break;
                    case 11:
                        bundle.putString("id", Constants.id_trollmalayalamcinema);
                        bundle.putInt("pic", R.drawable.icon_trollmalayalamcinema);
                        break;
                    case 12:
                        bundle.putString("id", Constants.id_malayalamtrollmasters);
                        bundle.putInt("pic", R.drawable.icon_malayalamtrollmasters);
                        break;
                    case 13:
                        bundle.putString("id", Constants.id_sheruentertainmenthub);
                        bundle.putInt("pic", R.drawable.icon_sheruenthub);
                        break;
                    case 14:
                        bundle.putString("id", Constants.id_cinemamixer);
                        bundle.putInt("pic", R.drawable.icon_cinemamixer);
                        break;
                    case 15:
                        bundle.putString("id", Constants.id_cybertrollers);
                        bundle.putInt("pic", R.drawable.icon_cybertroller);
                        break;
                    case 16:
                        bundle.putString("id", Constants.id_thengakola);
                        bundle.putInt("pic", R.drawable.icon_thengakola);
                        break;
                    case 17:
                        bundle.putString("id", Constants.id_trollmollywood);
                        bundle.putInt("pic", R.drawable.icon_trollmollywood);
                        break;
                    case 18:
                        bundle.putString("id", Constants.id_trollclasherskerala);
                        bundle.putInt("pic", R.drawable.icon_trollclasherskerala);
                        break;
                    case 19:
                        bundle.putString("id", Constants.id_outspoken);
                        bundle.putInt("pic", R.drawable.icon_outspoken);
                        break;
                    case 20:
                        bundle.putString("id", Constants.id_btechtrolls);
                        bundle.putInt("pic", R.drawable.icon_btechtrolls);
                        break;
                    case 21:
                        bundle.putString("id", Constants.id_onlinetrollmedia);
                        bundle.putInt("pic", R.drawable.icon_onlinetrollmedia);
                        break;
                    case 22:
                        bundle.putString("id", Constants.id_trollkerala);
                        bundle.putInt("pic", R.drawable.icon_trollkerala);
                        break;
                    case 23:
                        bundle.putString("id", Constants.id_trollreligion);
                        bundle.putInt("pic", R.drawable.icon_trollreligion);
                        break;
                    case 24:
                        bundle.putString("id", Constants.id_trollktu);
                        bundle.putInt("pic", R.drawable.icon_trollktu);
                        break;
                    case 25:
                        bundle.putString("id", Constants.id_pravasitrolls);
                        bundle.putInt("pic", R.drawable.icon_pravasitrolls);
                        break;
                    case 26:
                        bundle.putString("id", Constants.id_malayalampling);
                        bundle.putInt("pic", R.drawable.icon_malayalampling);
                        break;

                    case 100://settings
                        return true;
                    case 101://about
                        showAlertAboutUs();
                        return true;
                    case 102://share the app
                        return true;
                    case 103: //add remove pages
                        startActivity(new Intent(MainActivity.this, AddRemovePagesActivity.class));
                        return true;


                    default:
                        showPageError();
                        return true;
                }
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, fragment).commit();
                result.closeDrawer();
                return true;
            }
        });
    }

    private void alertMntAccess() {
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(
                new ContextThemeWrapper(MainActivity.this, R.style.AlertDialogTheme));
        ImageView imageView = new ImageView(MainActivity.this);
        int x = (int) (Math.random() * 3);
        if (x == 0) {
            imageView.setImageResource(R.drawable.mnt_access_pic);
        } else if (x == 1) {
            imageView.setImageResource(R.drawable.mnt_access_pic2);
        } else {
            imageView.setImageResource(R.drawable.mnt_access_pic3);
        }
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        alertdialog.setView(imageView)
                .setTitle("കൊച്ചു കള്ളൻ..!!")
                .setPositiveButton("ഇവിടെ മാത്രം ഞെക്കുക ..", null)
                .show();
    }

    private void alertExitPic() {
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(
                new ContextThemeWrapper(MainActivity.this, R.style.AlertDialogTheme));
        ImageView imageView = new ImageView(MainActivity.this);
        int x = (int) (Math.random() * 2);
        if (x == 0) {
            //EDIT THIS IMAGE
            imageView.setImageResource(R.drawable.exit_troll_pic);
        } else if (x == 1) {
            imageView.setImageResource(R.drawable.exit_troll_pic2);
        }
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        alertdialog.setView(imageView)
                .setPositiveButton("ആർക്കും എന്നെ തടുക്കാനാവില്ല.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .show();
    }

    private void showPageError() {
        new AlertDialog.Builder(this)
                .setTitle("Page Error")
                .setMessage("The page cannot be displayed. \nTry another page instead")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setIcon(R.mipmap.ic_launcher)
                .show();
    }

    private void showAlertAboutUs() {
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = pInfo.versionName;
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage("Version:" + version + "\n" + Constants.alert_developer_info)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(R.mipmap.ic_launcher)
                .show();
    }
}
