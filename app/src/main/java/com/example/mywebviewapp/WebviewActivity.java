package com.example.mywebviewapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.webviewsdk.MyWebview;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class WebviewActivity extends AppCompatActivity {
    public static final String TAG = WebviewActivity.class.getSimpleName();
    private static final String DIRECTORY_DOCUMENTS = "Documents";
    public static final String KEY_URL_TO_LOAD = "KEY_URL_TO_LOAD";

    @VisibleForTesting
    protected static final String WEB_FORM_URL = "https://www.google.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_web);
        WebView mywebview = (WebView) findViewById(R.id.webview1);
        // mywebview.loadUrl("https://www.javatpoint.com/");
        /*String data = "<html><body><h1>Hello, Javatpoint!</h1></body></html>";
        mywebview.loadData(data, "text/html", "UTF-8"); */
        //mywebview.getSettings().setJavaScriptEnabled(true);
        mywebview.loadUrl(urlFromIntent(getIntent()));
        try{
            mywebview.setWebChromeClient(new WebChromeClient());
            mywebview.getSettings().setJavaScriptEnabled(true);
            //mywebview.loadUrl("https://www.javatpoint.com/");
            mywebview.loadUrl(urlFromIntent(getIntent()));
        } catch (Exception ex){
            Log.e("error",ex.getMessage());
        }

    }
    private static String urlFromIntent(@NonNull Intent intent) {

        String url = intent.getStringExtra(KEY_URL_TO_LOAD);
        return !TextUtils.isEmpty(url) ? url : WEB_FORM_URL;
    }

}