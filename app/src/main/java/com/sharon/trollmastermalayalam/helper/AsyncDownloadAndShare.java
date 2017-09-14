package com.sharon.trollmastermalayalam.helper;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import com.sharon.trollmastermalayalam.Constants;
import com.sharon.trollmastermalayalam.Preferences;
import com.sharon.trollmastermalayalam.R;

public class AsyncDownloadAndShare extends AsyncTask<Void, Void, Void> {

    int i;
    Activity activity;
    String imageBasepath;
    String picurl, picid, link, type, captionmessage;

    public AsyncDownloadAndShare(int i, Activity activity, String picurl, String captionmessage, String picid, String link, String type, String imageBasepath) {
        this.i = i;
        this.activity = activity;
        this.imageBasepath = imageBasepath;
        this.picid = picid;
        this.picurl = picurl;
        this.link = link;
        this.type = type;
        this.captionmessage = captionmessage;
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
        String sharemessage = Constants.added_share_message;
        Preferences prefs = new Preferences(activity);
        if (prefs.getCheckPref("captioninclude")) {
            sharemessage = captionmessage + "\n" + Constants.added_share_message;
        }
        if (i == 1) {
            Toast.makeText(activity, activity.getString(R.string.download_success), Toast.LENGTH_SHORT).show();
        } else if (i == 2) {
            ShareHelper shareHelper = new ShareHelper();
            shareHelper.shareImageOnWhatsapp(activity, sharemessage, Uri.parse(imageBasepath));
        } else if (i == 3) {
            ShareHelper shareHelper = new ShareHelper();
            shareHelper.shareImage(activity, sharemessage, imageBasepath);
        } else if (i == 4) {
            ShareHelper shareHelper = new ShareHelper();
            shareHelper.shareMain(activity, sharemessage, imageBasepath, link, type);
        }
    }
}
