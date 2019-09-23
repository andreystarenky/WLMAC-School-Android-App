package com.starenkysoftware.macapp;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class LateStartsFragment extends Fragment {
    private WebView webView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.late_starts_layout, container, false);
        webView = (WebView) view.findViewById(R.id.LateStartsWebView);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setAppCachePath(getContext().getCacheDir().getPath());
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setWebViewClient(new WebViewClient());
        if ( !isNetworkAvailable() )  //offline
            webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ONLY );
        webView.loadUrl("https://andreystarenky.github.io/latestarts/index.html");
        //webView.loadUrl("https://google.ca");

        TextView lateStartTextView = view.findViewById(R.id.notificationDescription);
        int width = +getResources().getDisplayMetrics().widthPixels;
        int textWidth = (int)(width*0.7);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)lateStartTextView.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.width = textWidth;
        lateStartTextView.setLayoutParams(params);
        if(width<=720){

        }

        return view;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService( CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void viewPolicy(View view){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://andreystarenky.github.io/starenkysoftware/privacy_policy.html")));
    }
}
