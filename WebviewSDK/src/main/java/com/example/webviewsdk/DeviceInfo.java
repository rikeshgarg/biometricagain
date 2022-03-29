package com.example.webviewsdk;

import android.os.Build;

public class DeviceInfo {
    private static DeviceInfo device_info = null;
    private DeviceInfo()
    {

    }
    public static DeviceInfo getInstance()
    {
        if (device_info == null)
            device_info = new DeviceInfo();
        return device_info;
    }

    public static String getDevicename() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model;
        } else {
            return manufacturer + " " + model;
        }
    }


}
