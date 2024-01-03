package com.example.my_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.snackbar.Snackbar;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.searchView);
        webView = findViewById(R.id.webview);

        // 获取 "query" 参数
        String query = getIntent().getStringExtra("query");
        if (query != null) {
            // 将查询字符串设置到 SearchView 中
            searchView.setQuery(query, false);
            // 执行搜索
            try {
                performSearch();
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

/*        findViewById(R.id.back).setOnClickListener(v -> {
            if (webView.canGoBack())
                webView.goBack();
            else
                webView.loadUrl("https://www.baidu.com");
        });*/

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    performSearch();
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

      /*  findViewById(R.id.forward).setOnClickListener(v -> {
            if (webView.canGoForward())
                webView.goForward();
        });
*/
        findViewById(R.id.back_button).setOnClickListener(v -> {
            finish();
        });

    }

    private void performSearch() throws UnsupportedEncodingException {
        String searchQuery = searchView.getQuery().toString();
        configureWebSettings();
        loadSearchResults(searchQuery);
    }

    private void configureWebSettings() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAllowFileAccess(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setDefaultTextEncodingName("utf-8");
    }

    private void loadSearchResults(String searchQuery) throws UnsupportedEncodingException {
        String encodedQuery = URLEncoder.encode(searchQuery, "UTF-8");
        webView.loadUrl("https://www.baidu.com/s?wd=" + encodedQuery);


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
