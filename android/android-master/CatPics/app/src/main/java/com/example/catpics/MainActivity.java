package com.example.catpics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private WebView mTheCatWebView;
    private static final String TAG = "MainActivity";
    private TextView mOriginalURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOriginalURL = findViewById(R.id.actualURLTetView);
        mTheCatWebView = findViewById(R.id.theWebView);

        // settings
        mTheCatWebView.getSettings().setJavaScriptEnabled(true);
        mTheCatWebView.getSettings().setLoadWithOverviewMode(true);
        mTheCatWebView.getSettings().setUseWideViewPort(true);

        mTheCatWebView.zoomOut();

        mTheCatWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return (true);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                mTheCatWebView.setEnabled(false);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mTheCatWebView.setEnabled(true);
                String actualURL = getString(R.string.sourceURL) + mTheCatWebView.getUrl();
                Log.d(TAG, "URL string" + mOriginalURL);
                mOriginalURL.setText(actualURL);
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Log.d(TAG, "--- Receive Error ---");
            }
        });

        Button theButton = findViewById(R.id.newPicButton);
        theButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNewPic();
            }
        });

        loadNewPic();
    }

    void loadNewPic() {
        // note app/manifests/AndroidManifest.xml must contain:
        //  <uses-permission android:name="android.permission.INTERNET" />
        mTheCatWebView.loadUrl("https://api.thecatapi.com/v1/images/search?format=src&mime_types=image/gif");
    //    mTheCatWebView.loadUrl("https://www.thecatapi.com/api/images/get?type=jpg&size=small");
    }
}

