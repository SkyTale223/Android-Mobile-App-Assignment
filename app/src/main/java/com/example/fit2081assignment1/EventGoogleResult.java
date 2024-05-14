package com.example.fit2081assignment1;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EventGoogleResult extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_google_result);

        // Retrieve event name from Intent
        String eventName = getIntent().getStringExtra("SelectedEvent");

        // Construct Google search URL
        String googleSearchUrl = "https://www.google.com/search?q=" + eventName;

        // Initialize WebView
        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();

        // Load URL in WebView
        webView.loadUrl(googleSearchUrl);
    }
}
