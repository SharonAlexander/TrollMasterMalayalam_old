package com.sharon.trollmastermalayalam.helper;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import com.sharon.trollmastermalayalam.Constants;
import com.sharon.trollmastermalayalam.R;

public class AsyncDownloadAndShare extends AsyncTask<Void, Void, Void> {

    int i;
    Activity activity;
    String imageBasepath;
    String picurl, picid, link, type;

    public AsyncDownloadAndShare(int i, Activity activity, String picurl, String picid, String link, String type, String imageBasepath) {
        this.i = i;
        this.activity = activity;
        this.imageBasepath = imageBasepath;
        this.picid = picid;
        this.picurl = picurl;
        this.link = link;
        this.type = type;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (type.equals("photo")) {
            AsyncDownload asyncDownload = new AsyncDownload(activity, picurl, picid, type);
            asyncDownload.downloadFile();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (i == 1) {
            Toast.makeText(activity, activity.getString(R.string.download_success), Toast.LENGTH_SHORT).show();
        } else if (i == 2) {
            ShareHelper shareHelper = new ShareHelper();
            shareHelper.shareImageOnWhatsapp(activity, Constants.added_share_message, Uri.parse(imageBasepath));
        } else if (i == 3) {
            ShareHelper shareHelper = new ShareHelper();
            shareHelper.shareImage(activity, Constants.added_share_message, imageBasepath);
        } else if (i == 4) {
            ShareHelper shareHelper = new ShareHelper();
            shareHelper.shareMain(activity, Constants.added_share_message, imageBasepath, link, type);
        }
    }
}
