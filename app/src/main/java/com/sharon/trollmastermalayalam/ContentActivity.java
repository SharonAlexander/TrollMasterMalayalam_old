package com.sharon.trollmastermalayalam;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.GsonBuilder;
import com.sharon.trollmastermalayalam.helper.AsyncDownload;
import com.sharon.trollmastermalayalam.helper.AsyncDownloadAndShare;
import com.sharon.trollmastermalayalam.methods.EndlessRecyclerViewScrollListener;
import com.sharon.trollmastermalayalam.model.Datum;
import com.sharon.trollmastermalayalam.model.FirstResponse;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class ContentActivity extends Fragment implements ListAdapter.ListAdapterListener {

    AccessToken accessToken;
    RecyclerView recyclerView;
    Button retry;
    LinearLayout noInternetLayout;
    LinearLayoutManager linearLayoutManager;
    ListAdapter listAdapter;
    List<Datum> d;
    FirstResponse fr, f;
    String after;
    String id;
    int page_pic;
    InternetConnectionChecker internetConnectionChecker;
    Preferences preferences;
    private EndlessRecyclerViewScrollListener scrollListener;
    private AdView mAdViewBannerMain;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.troll_layout, container, false);

        preferences = new Preferences(getActivity());
        mAdViewBannerMain = (AdView) rootView.findViewById(R.id.adViewBannerMain);
        if (!preferences.getPremiumInfo()) {
            AdRequest adRequest = new AdRequest.Builder().addTestDevice("EFE01EDD6C65F47A8B03AFD4526C76C9").build();
            mAdViewBannerMain.loadAd(adRequest);
        } else {
            mAdViewBannerMain.setVisibility(View.GONE);
        }

        retry = rootView.findViewById(R.id.retry_button);
        noInternetLayout = rootView.findViewById(R.id.no_internet_layout);
        d = new ArrayList<>();
        internetConnectionChecker = new InternetConnectionChecker(getActivity());
        id = getArguments().getString("id");
        page_pic = getArguments().getInt("pic");

        accessToken = new AccessToken(Constants.facebook_app_token, Constants.facebook_app_id, Constants.facebook_app_id, null, null, null, null, null);

        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        recyclerView = rootView.findViewById(R.id.troll_view);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.removeAllViews();

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (internetConnectionChecker.isOnline()) {
                    noInternetLayout.setVisibility(View.GONE);
                    makeTheCall();
                } else {
                    internetConnectionChecker.showNotConnectedDialog();
                    noInternetLayout.setVisibility(View.VISIBLE);
                }
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (internetConnectionChecker.isOnline()) {
                    noInternetLayout.setVisibility(View.GONE);
                    makeFirstCall();
                } else {
                    internetConnectionChecker.showNotConnectedDialog();
                    noInternetLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        if (internetConnectionChecker.isOnline()) {
            noInternetLayout.setVisibility(View.GONE);
            makeFirstCall();
        } else {
            internetConnectionChecker.showNotConnectedDialog();
            noInternetLayout.setVisibility(View.VISIBLE);
        }
        return rootView;
    }

    private void makeFirstCall() {
        GraphRequest request = GraphRequest.newGraphPathRequest(
                accessToken,
                id,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        fr = new GsonBuilder().create().fromJson(response.getRawResponse(), FirstResponse.class);
                        //d.addAll(fr.getData());
                        listAdapter = new ListAdapter(d, page_pic, getActivity(), ContentActivity.this);
                        listAdapter.addAll(fr.getData());
                        recyclerView.setAdapter(listAdapter);
                        after = fr.getPaging().getCursors().getAfter();
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", Constants.feed_fields);
        parameters.putString("limit", Constants.feed_limit);
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void makeTheCall() {
        GraphRequest request = GraphRequest.newGraphPathRequest(
                accessToken,
                id,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        f = new GsonBuilder().create().fromJson(response.getRawResponse(), FirstResponse.class);
                        //d.addAll(f.getData());
                        listAdapter.addAll(f.getData());
                        after = f.getPaging().getCursors().getAfter();
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("pretty", "0");
        parameters.putString("fields", Constants.feed_fields);
        parameters.putString("limit", Constants.feed_limit);
        parameters.putString("after", after);
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onLinkClicked(int position) {
        String url = d.get(position).getLink();
        openLinkInChromeCustom(url);
    }

    private void openLinkInChromeCustom(String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(getActivity(), Uri.parse(url));
    }

    @Override
    public void onVideoPlayRequest(int position) {
        Intent intent = new Intent(getActivity(), PlayVideo.class);
        String videourl = d.get(position).getSource();
        intent.putExtra("videourl", videourl);
        startActivity(intent);
    }

    @Override
    public void onPhotoFullScreenRequest(int position) {
        String picurl = d.get(position).getFullPicture();
        String picid = d.get(position).getId();
        String picmessage = d.get(position).getMessage();
        Intent intent = new Intent(getActivity(), FullScreenImage.class);
        intent.putExtra("picurl", picurl);
        intent.putExtra("picid", picid);
        intent.putExtra("picmessage", picmessage);
        startActivity(intent);
    }

    @Override
    public void onMainDownloadClicked(int position) {
        String url = null;
        if ("photo".equals(d.get(position).getType())) {
            url = d.get(position).getFullPicture();
        } else if ("video".equals(d.get(position).getType())) {
            url = d.get(position).getSource();
        }
        String id = d.get(position).getId();
        String type = d.get(position).getType();
        downloadMethod(url, id, type);
    }

    @Override
    public void onMainShareClicked(int position) {
        String url = null;
        if ("photo".equals(d.get(position).getType())) {
            url = d.get(position).getFullPicture();
        } else if ("video".equals(d.get(position).getType())) {
            url = d.get(position).getSource();
        }
        String captionmessage = d.get(position).getMessage();
        String id = d.get(position).getId();
        String type = d.get(position).getType();
        String link = d.get(position).getType().equals("link") ? d.get(position).getLink() : d.get(position).getPermalinkUrl();
        String imageBasepath = Constants.folder_main_path + Constants.folder_name + id + Constants.image_extention;
        new AsyncDownloadAndShare(4, getActivity(), url, captionmessage, id, link, type, type.equals("photo") ? imageBasepath : null).execute();
    }

    @Override
    public void onMainVisitFB(int position) {
        String url = d.get(position).getPermalinkUrl();
        openLinkInChromeCustom(url);
    }

    @AfterPermissionGranted(002)
    private void downloadMethod(String url, String id, String type) {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            new AsyncDownload(getActivity(), url, id, type).execute();
        } else {
            EasyPermissions.requestPermissions(this, getActivity().getString(R.string.storage_permission_prompt_message),
                    002, perms);
        }
    }
}
