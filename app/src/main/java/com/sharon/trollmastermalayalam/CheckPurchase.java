package com.sharon.trollmastermalayalam;

import android.content.Context;
import android.util.Log;

import com.sharon.trollmastermalayalam.util.IabHelper;
import com.sharon.trollmastermalayalam.util.IabResult;
import com.sharon.trollmastermalayalam.util.Inventory;
import com.sharon.trollmastermalayalam.util.Purchase;


public class CheckPurchase {

    static final String ITEM_SKU_SMALL = "com.sharon.trollmaster_small";
    public static boolean isPremium = false;
    static IabHelper mHelper;
    //    static final String ITEM_SKU_SMALL = "com.test.purchased";
    static Preferences prefManager;
    static IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d("onQueryInventory: ", result.isSuccess() + "");
            Log.d("onQueryInventory: ", inventory + "");
            if (mHelper == null) return;
            if (result.isFailure()) {
            } else {
                Purchase premiumPurchase = inventory.getPurchase(ITEM_SKU_SMALL);
                if (inventory.hasPurchase(ITEM_SKU_SMALL)) {
                    boolean pre = true;
                }
                boolean premium = (premiumPurchase != null && verifyDeveloperPayload(premiumPurchase));
                prefManager.putPremiumInfo(premium);
            }
        }
    };

    public static void checkpurchases(Context context) {
        prefManager = new Preferences(context);
        String base64EncodedPublicKey = licensekey();
        mHelper = new IabHelper(context, base64EncodedPublicKey);
        mHelper.enableDebugLogging(true);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    return;
                }
                if (mHelper == null) return;
                try {
                    mHelper.queryInventoryAsync(mGotInventoryListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    static boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();
        return true;
    }

    private static String licensekey() {
        return Constants.apilicence;
    }

    public static void dispose() {
        if (mHelper != null) {
            mHelper.disposeWhenFinished();
            mHelper = null;
        }
    }
}