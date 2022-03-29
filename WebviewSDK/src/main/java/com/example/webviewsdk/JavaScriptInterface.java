package com.example.webviewsdk;

import android.content.Context;
import android.os.Build;
import android.webkit.JavascriptInterface;

import com.example.webviewsdk.DataCollectionWrap.DataCollectionWrap;

public class JavaScriptInterface {
    Context mContext;
    JavaScriptInterface(Context c) {
        mContext = c;
    }
    @JavascriptInterface
    public String getFromAndroid() {
        String str;
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            str = model;
        } else {
            str= manufacturer + " " + model;

        }
        return str;
    }
}
