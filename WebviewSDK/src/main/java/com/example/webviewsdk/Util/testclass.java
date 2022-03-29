package com.example.webviewsdk.Util;

import android.os.Build;

import com.example.webviewsdk.DataCollectionWrap.DataCollectionWrap;

import org.json.JSONException;
import org.json.JSONObject;

public class testclass {
    private static  testclass _testclass = null;

    private static String sdfc="";

    private static void testclass()
    {
        sdfc = Build.MANUFACTURER;
    }
    public static testclass getInstance()
    {
        if (_testclass == null)
            _testclass = new testclass();
        sdfc = Build.MANUFACTURER;
        return _testclass;
    }

    private void  getdata() throws JSONException {
        JSONObject hh = new JSONObject();
        hh.put("dd",sdfc);

    }
}
