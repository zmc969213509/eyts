package com.guojianyiliao.eryitianshi.View.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.Utils.MyBaseActivity1;

public class WebActivity extends MyBaseActivity1 {
    private WebView webView;
    String url;
    LinearLayout ll_close;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        findView();

        init();
    }

    private void findView() {
        ll_close = (LinearLayout) findViewById(R.id.ll_close);

        webView = (WebView) findViewById(R.id.webView);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        webView.setVerticalScrollBarEnabled(false);

        ll_close.setOnClickListener(listener);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    if (View.INVISIBLE == progressBar.getVisibility()) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    progressBar.setProgress(newProgress);
                }

                super.onProgressChanged(view, newProgress);

            }
        });
    }

    private void init() {
        url = getIntent().getStringExtra("url");

        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl(url);

        webView.setWebViewClient(new HelloWebViewClient());

    }


    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

}
