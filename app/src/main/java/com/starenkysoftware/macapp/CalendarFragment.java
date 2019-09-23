package com.starenkysoftware.macapp;

import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.net.ConnectivityManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class CalendarFragment extends Fragment {
    private WebView webView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        View view = inflater.inflate(R.layout.calendar_layout, container, false);
        webView = view.findViewById(R.id.calendarWebView);
        webView.getSettings().setAppCacheMaxSize(8 * 1024 * 1024);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCachePath(getContext().getCacheDir().getPath());
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.setWebViewClient(new WebViewClient());
        if (!isNetworkAvailable()) {  //offline
            webView.loadUrl("file:///android_asset/CalendarOffline.html");
            //webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ONLY );
        //webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ONLY );
        }
        else {
            webView.loadUrl("https://calendar.google.com/calendar/htmlembed?src=wlmacci%40gmail.com&ctz=America%2FToronto");
            //webView.loadUrl("https://calendar.google.com/calendar/embed?src=wlmacci%40gmail.com&ctz=America%2FToronto");
            //webView.loadUrl("https://andreystarenky.github.io/mac_calendar/index.html");
        }
        //webView.loadUrl("https://sites.google.com/view/wlmac/textfile?");
        //webView.loadUrl("https://google.ca");
        //webView.getSettings().setLoadWithOverviewMode(true);
        //webView.getSettings().setUseWideViewPort(true);
        webView.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if(event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    WebView webView = (WebView) v;

                    switch(keyCode)
                    {
                        case KeyEvent.KEYCODE_BACK:
                            if(webView.canGoBack())
                            {
                                webView.goBack();
                                return true;
                            }
                            break;
                    }
                }

                return false;
            }
        });
        return view;
    }



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService( CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
