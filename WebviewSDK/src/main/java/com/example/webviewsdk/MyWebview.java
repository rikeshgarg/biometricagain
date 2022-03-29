package com.example.webviewsdk;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.media.MediaCodecInfo;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.InputDevice;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.webviewsdk.DataCollection.DataCollectionPoint;
import com.example.webviewsdk.DataCollectionWrap.DataCollectionWrap;

import com.example.webviewsdk.Util.MediaCodecInfoCollection;
import com.example.webviewsdk.Util.testclass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.TimeZone;

public class MyWebview {
    private static  Context _context;
    private final FragmentManager fragmentManager;
    private DataCollectionPoint dataCollectionPoint;
    private MyWebview( final Activity activity) {
        this((Context) activity);
    }

    private MyWebview( final Context context) {
        this(((FragmentActivity) context).getSupportFragmentManager());
    }

    private MyWebview( final FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }
    public static MyWebview getContext( final Context context) {

        _context=context;
        //_context.getString(R.string.YOURSTRING));
        //Resources.getSystem().getString(android.R.string.app_name)

        return new MyWebview(context);
    }
//    public static MyWebview getContext(@NonNull final Activity activity) {
//
//        return new MyWebview(activity);
//    }

    public MyWebview ShowWebview(final int hh,final int ww) {

        //testGetMetrics();
        ///dataCollectionPoint=new DataCollectionPoint();
        //DataCollectionPoint.deviceInfo();
       //DataCollectionWrap.getInstance().generateDeviceInfo();
//        String str = _context.getApplicationInfo().packageName;
//        Resources res = _context.getResources();
//        String api_key = res.getString(res.getIdentifier("twitter_key", "string", str));
        CollectDataPoint();
        final WebViewDialogFragment webViewDialogFragment = WebViewDialogFragment.newInstance(800,800,"api_key");

        webViewDialogFragment.show(fragmentManager, WebViewDialogFragment.TAG);
        return this;
    }
//    private void testGetMetrics()  {
//        final JSONObject json = new JSONObject();
//        DeviceInfo deviceInfo=DeviceInfo.getInstance();
//
//        //String str = DeviceInfo.getDevicename();
//        try {
//            json.put("devicename",deviceInfo.getDevicename());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    public void CollectDataPoint()  {
        try {

           // String ddddd = MediaCodecInfoCollection.getBioFingerprint(_context);
            List<Camera.CameraInfo> extractCodecInfo1 = MediaCodecInfoCollection.extractcamerainfo(_context);
            List<InputDevice> extractCodecInfo = MediaCodecInfoCollection.extractCodecInfo1(_context);
            List<MediaCodecInfo> cc = MediaCodecInfoCollection.extractCodecInfo();
            //MediaCodecInfoCollection.getsensor(_context);
            String str = MediaCodecInfoCollection.getBioFingerprint11(_context);
            JSONArray ar = new JSONArray();
            for (int i = 0; i < cc.size(); i++) {
                final MediaCodecInfo codecInfo = cc.get(i);
                JSONObject codec = new JSONObject();
                final String[] supportedTypes = codecInfo.getSupportedTypes();
                codec.put("name", codecInfo.getName());
                JSONObject supportedTypesJson = new JSONObject();
                for (String type : supportedTypes) {
                    supportedTypesJson.put("supportedTypes", type);
                }
                codec.put("supportedTypes", supportedTypesJson);
                ar.put(codec);
            }
        } catch (JSONException | CameraAccessException ex){

        }
        testclass tt = testclass.getInstance();

        DataCollectionWrap dataCollectionWrap = DataCollectionWrap.getInstance();
        dataCollectionWrap.generateDeviceInfo();
        dataCollectionWrap.generateDeviceUDID(_context);
        dataCollectionWrap.getBatteryPercentage(_context);
        try {
            dataCollectionWrap.generateCPUInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dataCollectionWrap.generateScreenOrientation(_context);
        dataCollectionWrap.getAndroidOS();
        dataCollectionWrap.isEmulator();
        dataCollectionWrap.getDeviceLanguage(_context);
        dataCollectionWrap.getDeviceTimeZone();
        dataCollectionWrap.getDeviceBrightness(_context);
        dataCollectionWrap.getDeviceSize(_context);
        dataCollectionWrap.getMacAddr(_context);
        dataCollectionWrap.getADID(_context);
        dataCollectionWrap.generateKernelVersion();
        dataCollectionWrap.generateBuildInfo(_context);
        dataCollectionWrap.generateCarrierInfo(_context);
        //dataCollectionWrap.getMacAddress123();
        dataCollectionWrap.getrootstatus(_context);

//        String ll = _context.getResources().getSystem().getConfiguration().locale.getLanguage();
//        TimeZone tz = TimeZone.getDefault();
//        System.out.println("TimeZone   "+tz.getDisplayName(false, TimeZone.LONG)+" Timezone id :: " +tz.getID());
//        float brightness = Settings.System.getInt(_context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, -1);  // in the range [0, 255]
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        WindowManager wm = (WindowManager) _context.getSystemService(Context.WINDOW_SERVICE);
//        wm.getDefaultDisplay().getMetrics(displayMetrics);
//        int height = displayMetrics.heightPixels;
//        int width = displayMetrics.widthPixels;
        String str = dataCollectionWrap.getAvailableInternalMemorySize();
        String str1=  dataCollectionWrap.getTotalInternalMemorySize(_context);
        String str1111=  dataCollectionWrap.getAvailableExternalMemorySize();
        String str1123=  dataCollectionWrap.getTotalExternalMemorySize();
        JSONObject datapointObj = new JSONObject();
        datapointObj= BindJSON(dataCollectionWrap);
       //String ll = _context.getResources().getSystem().getConfiguration().locale.getLanguage();
    }

    private JSONObject BindJSON(DataCollectionWrap dataCollectionWrap){
        final JSONObject json = new JSONObject();
        try {
            json.put("Device Name:",dataCollectionWrap.getDeviceName());
            json.put("UDID:",dataCollectionWrap.getUDID());
            json.put("Battery Information:",dataCollectionWrap.getBatteryInfo()+"%");
            json.put("CPU Information:",dataCollectionWrap.getCPUInfo());
            json.put("Device Orientation:",dataCollectionWrap.getOrientation());
            json.put("Mobile OS:",dataCollectionWrap.getmobileOS());
            json.put("Is Emulator:",dataCollectionWrap.getisEmulator());
            json.put("Language:",dataCollectionWrap.getLanguage());
            json.put("TimeZone:",dataCollectionWrap.getTimeZone());
            json.put("Brightness:",dataCollectionWrap.getBrightness());
            json.put("ScreeSize:",dataCollectionWrap.getScreenSize());
            json.put("MAcAddress :",dataCollectionWrap.getMacAddress1());
            json.put("ADID :",dataCollectionWrap.getADID());
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(json);
            JSONObject datapointObj = new JSONObject();
            datapointObj.put("Data Collection", jsonArray);
            String jsonStr = datapointObj.toString();
            System.out.println("jsonString: "+jsonStr);
            Log.d("Data point ",jsonStr.toString());
            //getAllPermission();
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void getAllPermission(){
        final PackageManager pm = _context.getPackageManager();
        StringBuffer permissions = new StringBuffer();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(_context.getPackageName(), PackageManager.GET_PERMISSIONS);

            String[] requestedPermissions = packageInfo.requestedPermissions;
            if ( requestedPermissions != null ) {
                for (int i = 0; i < requestedPermissions.length; i++) {
                    int j = ContextCompat.checkSelfPermission(_context,requestedPermissions[i]);
                    if(j == PackageManager.PERMISSION_GRANTED) {
                        //permissionData.add(new PermissionData(requestedPermissions[i], true) );

                        permissions.append(requestedPermissions[i]+ "," + "true" + "\n");
                    }

                    else {
                        //permissionData.add(new PermissionData(requestedPermissions[i], false) );
                        permissions.append(requestedPermissions[i] + "," + "false" + "\n");

                    }

                }

                Log.d(TAG, "Permissions: " + permissions);
            }

        }
        catch ( PackageManager.NameNotFoundException e ) {
            e.printStackTrace();
        }
    }
}
