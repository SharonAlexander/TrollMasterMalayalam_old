package com.sharon.trollmastermalayalam;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sharon.trollmastermalayalam.util.IabHelper;
import com.sharon.trollmastermalayalam.util.IabResult;
import com.sharon.trollmastermalayalam.util.Purchase;

public class Settings extends PreferenceFragment {

    static final String ITEM_SKU_SMALL = "com.sharon.trollmaster_small";
    //    static final String ITEM_SKU_SMALL = "android.test.purchased";
    static final String DONATE_SMALL_THANKS = "1";
    IabHelper mHelper;
    int measureWidth, measureHeight;
    Preferences settingspreferences;
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result,
                                          Purchase purchase) {
            if (purchase != null) {
                if (purchase.getSku().contentEquals(ITEM_SKU_SMALL)) {
                    Message(DONATE_SMALL_THANKS);
                }
            } else if (result.getResponse() == 7) {
                Toast.makeText(getActivity(), R.string.settings_purchased_info, Toast.LENGTH_SHORT).show();
            } else if (result.getResponse() == 6) {
                Toast.makeText(getActivity(), R.string.settings_purchase_cancel, Toast.LENGTH_SHORT).show();
            } else if (result.getResponse() == 0) {
                Toast.makeText(getActivity(), R.string.settings_purchase_success, Toast.LENGTH_SHORT).show();
                Message(DONATE_SMALL_THANKS);
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_preferences);

        settingspreferences = new Preferences(getActivity());

        getActivity().setTitle("Settings");

        measureWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        measureHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        final CheckBoxPreference captioninclude = (CheckBoxPreference) getPreferenceManager().findPreference("captioninclude");
        captioninclude.setSummary(settingspreferences.getCheckPref("captioninclude") ? getActivity().getString(R.string.captionsummarychecked)
                : getActivity().getString(R.string.captionsummaryunchecked));
        captioninclude.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                if (captioninclude.isChecked()) {
                    settingspreferences.putCheckPref("captioninclude", true);
                    captioninclude.setSummary(getActivity().getString(R.string.captionsummarychecked));
                } else {
                    settingspreferences.putCheckPref("captioninclude", false);
                    captioninclude.setSummary(getActivity().getString(R.string.captionsummaryunchecked));
                }
                return false;
            }
        });
        Preference addremove = findPreference("addremove");
        addremove.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), AddRemovePagesActivity.class);
                startActivity(intent);
                return false;
            }
        });
        Preference rateus = findPreference("rateus");
        rateus.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.sharon.trollmastermalayalam"));
                startActivity(intent);
                return false;
            }
        });
        Preference donate = findPreference("donate");
        donate.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                if (mHelper != null) mHelper.flagEndAsync();
                try {
                    mHelper.launchPurchaseFlow(getActivity(), ITEM_SKU_SMALL, 10001,
                            mPurchaseFinishedListener, "donateSmallPurchase");
                } catch (IabHelper.IabAsyncInProgressException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        Preference about = findPreference("about");
        about.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                showAlertAboutUs();
                return false;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        String base64EncodedPublicKey = apilicense();


        mHelper = new IabHelper(getActivity(), base64EncodedPublicKey);

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                } else {
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        if (!mHelper.handleActivityResult(requestCode,
                resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        } else {
        }
    }

    private void Message(String message) {

        final Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                builder.dismiss();
            }
        });

        ImageView imageView = new ImageView(getActivity());
        if (message.contentEquals(DONATE_SMALL_THANKS)) {
            imageView.setImageResource(R.drawable.purchase_success);
        } else {
            imageView.setImageResource(R.drawable.exit_troll_pic3);
        }
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                measureWidth,
                measureHeight));
        builder.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) try {
            mHelper.dispose();
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
        mHelper = null;
    }

    private String apilicense() {
        return Constants.apilicence;
    }

    private void showAlertAboutUs() {
        PackageInfo pInfo = null;
        try {
            pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = pInfo != null ? pInfo.versionName : "";
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.app_name)
                .setMessage("Version:" + version + "\n" + Constants.alert_developer_info)
                .setPositiveButton(android.R.string.yes, null)
                .setIcon(R.mipmap.ic_launcher)
                .show();
    }
}
