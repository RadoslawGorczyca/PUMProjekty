package org.gorczyca.pum.project4;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.gorczyca.pum.R;

@SuppressLint("SetJavaScriptEnabled")
public class FindPairsActivity extends AppCompatActivity {

    private WebView webFindPair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_4_find_pairs);
        setTitle(R.string.find_pairs);
        bindIds();
        setUpWebView();
    }

    private void bindIds() {
        webFindPair = findViewById(R.id.web_find_pair);
    }

    private void setUpWebView() {
        webFindPair.getSettings().setJavaScriptEnabled(true);
        webFindPair.setWebViewClient(new WebViewClient());
        webFindPair.loadUrl("file:///android_asset/memo.html");
    }
}
