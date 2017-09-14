package com.sharon.trollmastermalayalam.helper;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.sharon.trollmastermalayalam.Constants;
import com.sharon.trollmastermalayalam.R;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class AsyncDownload extends AsyncTask<Void, Void, Void> {

    Activity appCompatActivity;
    String url, id, type;
    String videoext = Constants.video_extention;
    String picext = Constants.image_extention;
    boolean error = false;
    NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    private int NOTIFICATION_ID = 1001;

    public AsyncDownload(Activity appCompatActivity, String url, String id, String type) {
        this.appCompatActivity = appCompatActivity;
        this.url = url;
        this.id = id;
        this.type = type;
        builder = new NotificationCompat.Builder(appCompatActivity);
        mNotificationManager = (NotificationManager) appCompatActivity.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        builder.setContentTitle(appCompatActivity.getString(R.string.app_name));
        builder.setContentText(appCompatActivity.getString(R.string.helper_downloading));
        startNotification(builder);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if ((type.equals("photo") || type.equals("video")) && url != null) {
            builder.setProgress(100, 50, true)
                    .setOngoing(true);
            startNotification(builder);
            downloadFile();
        } else {
            error = true;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (!error) {
            builder.setContentTitle(appCompatActivity.getString(R.string.app_name))
                    .setContentText(appCompatActivity.getString(R.string.download_success))
                    .setOngoing(false)
                    .setProgress(0, 0, false);
            startNotification(builder);
            Toast.makeText(appCompatActivity, appCompatActivity.getString(R.string.download_success), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(appCompatActivity, appCompatActivity.getString(R.string.unsupported_error), Toast.LENGTH_SHORT).show();
        }
    }

    public void downloadFile() {
        String location = Constants.folder_main_path + Constants.folder_name;
        File folder = new File(location);
        if (!folder.exists()) {
            folder.mkdir();
        }
        File file = null;
        if (type.equals("photo")) {
            file = new File(location + "/" + id + picext);
        } else if (type.equals("video")) {
            file = new File(location + "/" + id + videoext);
        }
        try {
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            int contentLength = conn.getContentLength();

            DataInputStream stream = new DataInputStream(u.openStream());

            byte[] buffer = new byte[contentLength];
            stream.readFully(buffer);
            stream.close();

            DataOutputStream fos = new DataOutputStream(new FileOutputStream(file));
            fos.write(buffer);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(appCompatActivity, appCompatActivity.getString(R.string.error), Toast.LENGTH_SHORT).show();
            error = true;
        } catch (IOException e) {
            Toast.makeText(appCompatActivity, appCompatActivity.getString(R.string.error), Toast.LENGTH_SHORT).show();
            error = true;
        }
    }

    private void startNotification(NotificationCompat.Builder builder) {
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(appCompatActivity.getResources(), R.mipmap.ic_launcher));
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
