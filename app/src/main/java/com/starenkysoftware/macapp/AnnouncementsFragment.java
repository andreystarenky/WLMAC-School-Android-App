package com.starenkysoftware.macapp;

import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class AnnouncementsFragment extends Fragment {
    private WebView webView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.announcements_layout, container, false);
        TextView coming_soon_text = view.findViewById(R.id.ComingSoon);
        //coming_soon_text.setGravity(Gravity.CENTER);
        coming_soon_text.setTextColor(Color.WHITE);
        coming_soon_text.setTextSize(25);
        webView = (WebView) view.findViewById(R.id.announcementsWebView);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setAppCachePath(getContext().getCacheDir().getPath());
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setWebViewClient(new WebViewClient());
        if ( !isNetworkAvailable() )  //offline
            webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ONLY );
        webView.loadUrl("https://andreystarenky.github.io/");
        //webView.loadUrl("https://google.ca");
        return view;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService( CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
