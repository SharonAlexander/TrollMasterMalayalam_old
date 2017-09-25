package com.sharon.trollmastermalayalam.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.sharon.trollmastermalayalam.Constants;
import com.sharon.trollmastermalayalam.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import pub.devrel.easypermissions.AfterPermissionGranted;

public class ShareHelper {


    public void shareImageOnWhatsapp(Activity activity, String textBody, Uri fileUri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.setPackage(Constants.whatsapp_package);
        intent.putExtra(Intent.EXTRA_TEXT, !TextUtils.isEmpty(textBody) ? textBody : "");

        if (fileUri != null) {
            intent.putExtra(Intent.EXTRA_STREAM, fileUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/*");
        }

        try {
            activity.startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
            Toast.makeText(activity, activity.getString(R.string.toast_whatsapp_not_found), Toast.LENGTH_SHORT).show();
        }
    }

    public void shareImage(Activity activity, String textBody, String filePath) {
        File file = new File(filePath);
        Uri fileUri = Uri.fromFile(file);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, textBody);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        activity.startActivity(Intent.createChooser(shareIntent, activity.getString(R.string.intent_title_share_images)));
    }

    public void shareMain(Activity activity, String textBody, String filePath, String link, String type) {
        Uri fileUri = null;
        if (filePath != null) {
            File file = new File(filePath);
            fileUri = Uri.fromFile(file);
        }
        Intent shareIntent = new Intent();
        if (type.equals("link") || type.equals("video")) {
            textBody = link + "\n\n" + textBody;
        }
        shareIntent.putExtra(Intent.EXTRA_TEXT, textBody);
        shareIntent.setType("text/plain");
        shareIntent.setAction(Intent.ACTION_SEND);
        if (type.equals("photo")) {
            shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
            shareIntent.setType("image/*");
        }
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        activity.startActivity(Intent.createChooser(shareIntent, activity.getString(R.string.intent_title_share)));
    }

    public void shareAppDetails(Activity activity) {
        saveBitmapToFile(activity);
        Uri uri = Uri.fromFile(new File(Constants.folder_main_path + Constants.folder_name + "app_share_image.jpeg"));
        Intent intent = new Intent();
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, activity.getString(R.string.app_share_message));
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/jpeg");
        intent.setAction(Intent.ACTION_SEND);

        try {
            activity.startActivity(Intent.createChooser(intent, activity.getString(R.string.intent_title_share)));
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
            Toast.makeText(activity, activity.getString(R.string.error), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean saveBitmapToFile(Context context) {

        File folder = new File(Constants.folder_main_path + Constants.folder_name);
        if (!folder.exists()) {
            folder.mkdir();
        }
        File imageFile = new File(folder, "app_share_image.jpeg");
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.app_share_image);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imageFile);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            return true;
        } catch (IOException e) {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return false;
    }
}
