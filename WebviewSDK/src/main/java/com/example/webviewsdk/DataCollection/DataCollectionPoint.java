package com.example.webviewsdk.DataCollection;

import static android.content.Context.BATTERY_SERVICE;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.BatteryManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import com.example.webviewsdk.DataCollectionWrap.DataCollectionWrap;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DataCollectionPoint {
    //private DataCollectionWrap _datacollectionwrap;
    private static DataCollectionPoint _datacollectionpoint = null;
    private void DataCollectionPoint()
    {

    }
    public static DataCollectionPoint getInstance()
    {
        if (_datacollectionpoint == null)
            _datacollectionpoint = new DataCollectionPoint();
        return _datacollectionpoint;
    }
    public void deviceInfo() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            DataCollectionWrap.getInstance().setDeviceName(model);
        } else {
            DataCollectionWrap.getInstance().setDeviceName(manufacturer + " " + model);
        }
    }
    public void generateDeviceInfo() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            DataCollectionWrap.getInstance().setDeviceName(model);
        } else {
            DataCollectionWrap.getInstance().setDeviceName(manufacturer + " " + model);
        }
    }

    public void generateDeviceUDID(Context context) {
        String m_androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        DataCollectionWrap.getInstance().setUDID(m_androidId);
    }

    public void getBatteryPercentage(Context context)  {
        if (Build.VERSION.SDK_INT >= 21) {
            BatteryManager bm = (BatteryManager) context.getSystemService(BATTERY_SERVICE);
            DataCollectionWrap.getInstance().setBatteryInfo(bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY));
        } else {
            IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = context.registerReceiver(null, iFilter);

            int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
            int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;

            double batteryPct = level / (double) scale;
            DataCollectionWrap.getInstance().setBatteryInfo((int) (batteryPct * 100));
            //return (int) (batteryPct * 100);
        }
    }
    public Map<String, String> generateCPUInfo() throws IOException {
        BufferedReader br = new BufferedReader (new FileReader("/proc/cpuinfo"));
        String str;
        Map<String, String> output = new HashMap<>();
        while ((str = br.readLine ()) != null) {
            String[] data = str.split (":");
            if (data.length > 1) {
                String key = data[0].trim ().replace (" ", "_");
                if (key.equals ("model_name")) key = "cpu_model";
                output.put (key, data[1].trim ());
            }
        }
        br.close ();
        //String str1=output.toString();
        DataCollectionWrap.getInstance().setCPUInfo(output.toString());
        Log.d("CPU_INFO", output.toString());
        return output;
    }
    public void generateScreenOrientation(Context context){
        int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            DataCollectionWrap.getInstance().setOrientation("Portrait");
        } else {
            DataCollectionWrap.getInstance().setOrientation("Landscape");
        }
    }
}
