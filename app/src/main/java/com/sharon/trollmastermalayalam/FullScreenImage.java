package com.sharon.trollmastermalayalam;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.joaquimley.faboptions.FabOptions;
import com.sharon.trollmastermalayalam.helper.AsyncDownloadAndShare;
import com.sharon.trollmastermalayalam.methods.TouchImageView;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class FullScreenImage extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    FabOptions fabOptions;
    String picurl, picid, picmessage;
    String imageBasepath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreenimage);

        picurl = getIntent().getStringExtra("picurl");
        picid = getIntent().getStringExtra("picid");
        picmessage = getIntent().getStringExtra("picmessage");
        imageBasepath = Constants.folder_main_path + Constants.folder_name + picid + Constants.image_extention;
        final TouchImageView imageView = findViewById(R.id.img);

        Glide.with(this).asBitmap().load(picurl).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                imageView.setImageBitmap(resource);
            }
        });

        fabOptions = findViewById(R.id.fab_options);
        fabOptions.setButtonsMenu(R.menu.fab_buttons);
        fabOptions.setOnClickListener(this);
        imageView.setOnTouchListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.faboptions_favorite:
                Toast.makeText(this, R.string.coming_soon, Toast.LENGTH_SHORT).show();
                break;

            case R.id.faboptions_whatsapp:
                imageDownloadMethod(2);
                break;

            case R.id.faboptions_download:
                imageDownloadMethod(1);
                break;

            case R.id.faboptions_share:
                imageDownloadMethod(3);
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            fabOptions.setVisibility(View.GONE);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            fabOptions.setVisibility(View.VISIBLE);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(001)
    private void imageDownloadMethod(int i) {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            new AsyncDownloadAndShare(i, this, picurl, picmessage, picid, null, "photo", imageBasepath).execute();
        } else {
            EasyPermissions.requestPermissions(this, this.getString(R.string.storage_permission_prompt_message),
                    001, perms);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(this, R.string.permission_prompt_settings_return, Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
