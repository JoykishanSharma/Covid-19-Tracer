package com.joyappsdevteam.covid_19tracer.maps_module;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.joyappsdevteam.covid_19tracer.info_module.InfoActivity;
import com.joyappsdevteam.covid_19tracer.news_module.NewsActivity;
import com.joyappsdevteam.covid_19tracer.R;
import com.joyappsdevteam.covid_19tracer.home_module.HomeActivity;

public class MapsActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private WebView webView;
    private ProgressDialog progressBar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.map);
        webView = findViewById(R.id.india_webView);

        progressBar = ProgressDialog.show(MapsActivity.this, "Showing Covid-19 India Report", "Loading...");

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        return true;

                    case R.id.map:
                        return true;

                    case R.id.info:
                        startActivity(new Intent(getApplicationContext(), InfoActivity.class));
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        return true;

                    case R.id.news:
                        startActivity(new Intent(getApplicationContext(), NewsActivity.class));
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        return true;
                }
                return false;
            }
        });

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);// enable javascript
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setGeolocationDatabasePath( getApplicationContext().getFilesDir().getPath() );

        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Open Chrome")
                    .setMessage("Press OK to open in Chrome Browser.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String url = "https://www.covid19india.org/";
                            try {
                                Intent i = new Intent("android.intent.action.MAIN");
                                i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                                i.addCategory("android.intent.category.LAUNCHER");
                                i.setData(Uri.parse(url));
                                startActivity(i);
                            }
                            catch(ActivityNotFoundException e) {
                                // Chrome is not installed
                                e.printStackTrace();
                                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                startActivity(i);
                            }
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // When user clicks a hyperlink, load in the existing WebView
                view.loadUrl(url);
                return true;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (progressBar.isShowing()){
                    progressBar.dismiss();
                }
            }
        });

        webView.loadUrl("https://www.covid19india.org/");

    }

    @Override
    public void onBackPressed() {
        if (webView.isFocused() && webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
            startActivity(new Intent(MapsActivity.this, HomeActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

    }

}
