package com.msproject.myhome.boostcamp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;

public class WebViewActiviy extends AppCompatActivity {
    WebView webView;
    Intent intent;
    EditText editText;
    ImageView searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_activiy);
        webView = findViewById(R.id.web_view);
        editText = findViewById(R.id.web_url);
        searchButton = findViewById(R.id.search_bt);

        intent = getIntent();
        String link = intent.getStringExtra("Movie");
        editText.setText(link);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClientClass());



        loadURL(link);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadURL(editText.getText().toString());
            }
        });
    }

    private void loadURL(String link){
        webView.loadUrl(link);
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("check URL",url);
            webView.loadUrl(url);
            return true;
        }
    }

}
