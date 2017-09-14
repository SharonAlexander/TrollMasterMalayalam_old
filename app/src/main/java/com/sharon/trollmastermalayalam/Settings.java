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
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sharon.trollmastermalayalam.util.IabHelper;
import com.sharon.trollmastermalayalam.util.IabResult;
import com.sharon.trollmastermalayalam.util.Purchase;

public class Settings extends PreferenceFragment {

    static final String ITEM_SKU_SMALL = "com.sharon.trollmaster_small";
    static final String DONATE_SMALL_THANKS = "1";
    IabHelper mHelper;
    int measureWidth, measureHeight;
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result,
                                          Purchase purchase) {
            if (result.isFailure()) {
                // Handle error
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.purchase_error)
                        .setMessage(R.string.purchase_already_owned)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(R.mipmap.ic_launcher)
                        .show();
            } else if (purchase.getSku().equals(ITEM_SKU_SMALL)) {
                Message(DONATE_SMALL_THANKS);

            }

        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_preferences);

        measureWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        measureHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        Preference preferences = findPreference("rateus");
        preferences.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.sharon.trollmastermalayalam"));
                startActivity(intent);
                return false;
            }
        });
        Preference donate = findPreference("donate");
        donate.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
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
            imageView.setImageResource(R.drawable.exit_troll_pic2);
        } else {
            imageView.setImageResource(R.drawable.exit_troll_pic);
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
        return "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArie0pEfCFNWlqZ5x0vLLD2W0rQ/nFg3AGlAKh0U4U9GNwirUyxYluqcn1131aLUd2ff1IQ+eILzm/TKvX2KDsXUSgU0Jcl+ar/oSLlXy8RvevK1JmN2CPgotGCoLiTiRSw2SbprvQ+sUt6Pk0DJy4YWrm1SFAqYJuGeCIkRlaihZlN6nIgtdEcmw4XnRGuGtqkZm9CV6iRrzaeh1qk4kEhPbFlldpk+jBZJ3C/vlWvtfEqf+Fijb1Z5v+IoYNBxykzkpbMkBkuWWDw+hdZyp/cRyPhtjIL/g5zNTw1fgtPYR6n5iNmUpJFRREUSvXXQf+7i0EX2tz6IwNk9EX/xqdwIDAQAB";
    }

    private void showAlertAboutUs() {
        PackageInfo pInfo = null;
        try {
            pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = pInfo.versionName;
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.app_name)
                .setMessage("Version:" + version + "\n" + Constants.alert_developer_info)
                .setPositiveButton(android.R.string.yes, null)
                .setIcon(R.mipmap.ic_launcher)
                .show();
    }
}
