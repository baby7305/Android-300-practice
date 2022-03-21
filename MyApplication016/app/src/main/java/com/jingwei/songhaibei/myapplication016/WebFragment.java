package com.jingwei.songhaibei.myapplication016;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

public class WebFragment extends Fragment {
    String myUrl = "http://www.baidu.com";
    WebView myWebView;

    @Override
    public void setArguments(Bundle args) {
        myUrl = args.getString("myUrl");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myWebView = new WebView(inflater.getContext());
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.loadUrl(myUrl);
        myWebView.setWebViewClient(new WebViewClient());
        return myWebView;
    }

    public void reloadView() {
        myWebView.loadUrl(myUrl);
    }
}
