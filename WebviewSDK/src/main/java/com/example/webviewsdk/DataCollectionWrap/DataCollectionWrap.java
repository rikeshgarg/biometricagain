package com.example.webviewsdk.DataCollectionWrap;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.BATTERY_SERVICE;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Build;
import android.os.CpuUsageInfo;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

//http://www.devexchanges.info/2015/06/get-some-basic-android-device.html

public class DataCollectionWrap {
    private static final int BUFFER_SIZE = 1;
    private static DataCollectionWrap _datacollection = null;
    private static String _devicename="";
    private static String _UDID="";
    private static int _batteryinfo=0;
    private static String _cpuinfo="";
    private static String _screenorientation="";
    private static String _mobilsOS="";
    private static boolean _isEmulator;
    private static String _langiage;
    private static String _timezone;
    private static float _brightness;
    private static String _screensize;
    private static String _macAddress;
    private static String _ADID;
    private static String _KERNELVERSION;
    private static String _CARRIERINFO;
    private static String _BUILDINFO;

    private static String sdfc="";

    private void DataCollectionWrap()
    {
        sdfc = Build.MANUFACTURER;
    }
    public static DataCollectionWrap getInstance()
    {
        if (_datacollection == null)
            _datacollection = new DataCollectionWrap();
        return _datacollection;
    }

    public void setDeviceName(String deviceName){
            _devicename=deviceName;
    }
    public String getDeviceName(){
        return _devicename;
    }

    public void setUDID(String UDID){
        _UDID=UDID;
    }
    public String getUDID(){
        return _UDID;
    }

    public void setBatteryInfo(int batteryinfo){
        _batteryinfo=batteryinfo;
    }
    public int getBatteryInfo(){
        return _batteryinfo;
    }

    public void setCPUInfo(String CPUinfo){
        _cpuinfo=CPUinfo;
    }
    public String getCPUInfo(){
        return _cpuinfo;
    }

    public void setOrientation(String orientation){
        _screenorientation=orientation;
    }
    public String getOrientation(){
        return  _screenorientation;
    }

    public void setmobileOS(String mobileOS){
        _mobilsOS=mobileOS;
    }
    public String getmobileOS(){
        return  _mobilsOS;
    }

    public void setisEmulator(boolean isEmulator){
        _isEmulator=isEmulator;
    }
    public boolean getisEmulator(){
        return  _isEmulator;
    }

    public void setLanguage(String language){
        _langiage=language;
    }
    public String getLanguage(){
        return  _langiage;
    }

    public void setTimeZone(String timezone){
        _timezone=timezone;
    }
    public String getTimeZone(){
        return  _timezone;
    }

    public void setBrightness(float brightness){
        _brightness=brightness;
    }
    public float getBrightness(){
        return  _brightness;
    }

    public void setScreenSize(String screensize){
        _screensize=screensize;
    }
    public String getScreenSize(){
        return _screensize;
    }

    public void setMacAddress1(String macAddress){
        _macAddress=macAddress;
    }
    public String getMacAddress1(){
        return _macAddress;
    }

    public void setADID(String ADID){
        _ADID=ADID;
    }
    public String getADID(){
        return _ADID;
    }

    public void setKernelversion(String Kernelversion){
        _KERNELVERSION=Kernelversion;
    }
    public String getKernelversion(){
        return _KERNELVERSION;
    }

    public void setCarrierinfo(String Carrierinfo){
        _CARRIERINFO=Carrierinfo;
    }
    public String getCarrierinfo(){
        return _CARRIERINFO;
    }

    public void setBuildinfo(String Buildinfo){
        _BUILDINFO=Buildinfo;
    }
    public String getBuildinfo(){
        return _BUILDINFO;
    }

    public void generateDeviceInfo() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            setDeviceName(model);
        } else {
            setDeviceName(manufacturer + " " + model);
        }
    }

    public void generateDeviceUDID(Context context) {
        String m_androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        setUDID(m_androidId);
    }

    public void getBatteryPercentage(Context context)  {

        if (Build.VERSION.SDK_INT >= 21) {

            BatteryManager bm = (BatteryManager) context.getSystemService(BATTERY_SERVICE);
            setBatteryInfo(bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY));

        } else {

            IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = context.registerReceiver(null, iFilter);

            int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
            int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;

            double batteryPct = level / (double) scale;
            setBatteryInfo((int) (batteryPct * 100));
            //return (int) (batteryPct * 100);
        }

    }
    public void  generateCPUInfo() throws IOException {
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
        setCPUInfo(output.toString());
        Log.d("CPU_INFO", output.toString());
    }
    public void generateScreenOrientation(Context context){


        try
        {
            // get list of senser
            SensorManager smm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            List<Sensor> sensor = smm.getSensorList(Sensor.TYPE_ALL);
            JSONArray jsonArray = new JSONArray();
            if(sensor.size()>0){
                for(int i=0;i<sensor.size();i++){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("sensorname",sensor.get(i).getName());
                    jsonObject.put("vendorname",sensor.get(i).getVendor());
                    jsonArray.put(jsonObject);
                }
            }
        }
        catch (JSONException ex){
            ex.printStackTrace();;
        }

        int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setOrientation("Portrait");
        } else {
            setOrientation("Landscape");
        }
    }
    public void getAndroidOS() {
        String release = Build.VERSION.RELEASE;
        int sdkVersion = Build.VERSION.SDK_INT;
        setmobileOS(sdkVersion + " (" + release +")");
        //return "Android SDK: " + sdkVersion + " (" + release +")";
    }



    public void generateCPUInfo1(){
        try {

            String[] DATA = {"/system/bin/cat", "/proc/cpuinfo"};
            ProcessBuilder processBuilder = new ProcessBuilder(DATA);
            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            byte[] byteArry = new byte[1024];
            String output = "";
            while (inputStream.read(byteArry) != -1) {
                output = output + new String(byteArry);
            }
            inputStream.close();

            Log.d("CPU_INFO", output);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void isEmulator() {
        boolean isphone;
        if ((Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.PRODUCT.contains("sdk_google")
                || Build.PRODUCT.contains("google_sdk")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("sdk_x86")
                || Build.PRODUCT.contains("sdk_gphone64_arm64")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator")) {
            setisEmulator(true);
        } else {
            setisEmulator(false);
        }
    }

    public void getDeviceLanguage(Context context) {
        String getLanguage = context.getResources().getSystem().getConfiguration().locale.getLanguage();
        setLanguage(getLanguage);
    }

    public void getDeviceTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        //System.out.println("TimeZone   "+tz.getDisplayName(false, TimeZone.LONG)+" Timezone id :: " +tz.getID());
        setTimeZone(tz.getID());
    }

    public void getDeviceBrightness(Context context) {
        float brightness = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, -1);
        setBrightness(brightness);
    }

    public void getDeviceSize(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        setScreenSize(String.valueOf(height) + ',' + String.valueOf(width));
    }

    public String getMacAddress123(){
        try{
            List<NetworkInterface> networkInterfaceList = Collections.list(NetworkInterface.getNetworkInterfaces());
            String stringMac = "";
            for(NetworkInterface networkInterface : networkInterfaceList)
            {
                if(networkInterface.getName().equalsIgnoreCase("wlon0"));
                {
                    for(int i = 0 ;i <networkInterface.getHardwareAddress().length; i++){
                        String stringMacByte = Integer.toHexString(networkInterface.getHardwareAddress()[i]& 0xFF);
                        if(stringMacByte.length() == 1)
                        {
                            stringMacByte = "0" +stringMacByte;
                        }
                        stringMac = stringMac + stringMacByte.toUpperCase() + ":";
                    }
                    break;
                }
            }
            return stringMac;
        }catch (SocketException e)
        {
            e.printStackTrace();
        }
        return  "0";
    }

    public void getMacAddr(Context context){
//        try {
//            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//            WifiInfo wInfo = wifiManager.getConnectionInfo();
//            String mac = wInfo.getMacAddress();
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    AdvertisingIdClient.Info adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
                    String myId = adInfo != null ? adInfo.getId() : null;

                    Log.i("UIDMY",myId);
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
            }
        });
    }

    public void getADID(Context context){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    AdvertisingIdClient.Info adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
                    String myId = adInfo != null ? adInfo.getId() : null;
                    setADID(myId);
                    Log.i("UIDMY",myId);
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
            }
        });
    }
    public void getMacAddr1(Context context) {
        try {
//            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
//            for (NetworkInterface nif : all) {
//                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;
//
//                byte[] macBytes = nif.getHardwareAddress();
//                if (macBytes == null) {
//                    setMacAddress("");
//                }
//
//                StringBuilder res1 = new StringBuilder();
//                for (byte b : macBytes) {
//                    res1.append(Integer.toHexString(b & 0xFF) + ":");
//                }
//
//                if (res1.length() > 0) {
//                    res1.deleteCharAt(res1.length() - 1);
//                }
//                setMacAddress(res1.toString());
//                //return res1.toString();
//            }

            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wInfo = wifiManager.getConnectionInfo();
            List<ScanResult> networkInterfaceList=wifiManager.getScanResults();
            //String mac = wInfo.getMacAddress();
        } catch (Exception ex) {
            //handle exception
        }

    }

    public void  generateKernelVersion() {
        try {
            Process p = Runtime.getRuntime().exec("uname -a");
            InputStream is = null;
            if (p.waitFor() == 0) {
                is = p.getInputStream();
            } else {
                is = p.getErrorStream();
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = br.readLine();
            Log.i("Kernel Version", line);
            String str=System.getProperty("os.version");
            String str1 = Build.FINGERPRINT;


            setKernelversion(str);
            br.close();
            //return line;
        } catch (Exception ex) {
            //return "ERROR: " + ex.getMessage();
            ex.getMessage();
        }
    }

    public void generateCarrierInfo(Context context){
        TelephonyManager manager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String carrierName = manager.getNetworkOperatorName();
        setCarrierinfo(carrierName);
    }

    public void generateBuildInfo(Context context) {
        try {
            StatFs hh;

            // get list of senser
            SensorManager smm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            List<Sensor> sensor = smm.getSensorList(Sensor.TYPE_ALL);

            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String versionName = pInfo.versionName; //information build
            int versioncode = pInfo.versionCode;
            setBuildinfo("VersionName " + versionName + "," + "VersionCode " + versioncode);

            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
            activityManager.getMemoryInfo(mi);
            double availableMegs1 = mi.totalMem;
            double availableMegs = mi.availMem / 0x100000L; // memory ifo


            long uptimr = SystemClock.uptimeMillis();// up times
        }
        catch(PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
    }


    public void generateBuildInfo1(Context context){
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String versionName = pInfo.versionName; //information build
            int versioncode = pInfo.versionCode;
            setBuildinfo("VersionName " + versionName + "," + "VersionCode " + versioncode);
//            TelephonyManager manager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
//            String carrierName = manager.getNetworkOperatorName(); //Carrier information
            //boolean dd = RootTools.isRootAvailable();
            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
            activityManager.getMemoryInfo(mi);
            double availableMegs = mi.availMem / 0x100000L; // memory ifo

//Percentage can be calculated for API 16+
            long uptimr = SystemClock.uptimeMillis();


            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo;

            wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo.getSupplicantState() == SupplicantState.COMPLETED) {
                String ssid = wifiInfo.getSSID();
                String Ipadd = String.valueOf(wifiInfo.getIpAddress());
                String networkID = String.valueOf(wifiInfo.getNetworkId());
            }

            List<ScanResult> networkInterfaceList = wifiManager.getScanResults();

            //String ssidq = wifiInfo.getMacAddress();
            double percentAvail = mi.availMem / (double) mi.totalMem * 100.0;


            // get list of senser
            SensorManager smm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            List<Sensor> sensor = smm.getSensorList(Sensor.TYPE_ALL);

            // get usb accessory
//            UsbManager m = (UsbManager)context.getApplicationContext().getSystemService(context.USB_SERVICE);
//            HashMap<String, UsbDevice> usbDevices = m.getDeviceList();
//            Collection<UsbDevice> ite = usbDevices.es();
//            UsbDevice[] usbs = ite.toArray(new UsbDevice[]{});
//            for (UsbDevice usb : usbs) {
//                Log.d("Connected usb devices","Connected usb devices are "+ usb.getDeviceName());
//            }
            UsbManager manager = (UsbManager) context.getSystemService(context.USB_SERVICE);

            HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
            Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
            String actionString = context.getPackageName() + ".action.USB_PERMISSION";

            PendingIntent mPermissionIntent = PendingIntent.getBroadcast(context, 0, new
                    Intent(actionString), 0);
            while (deviceIterator.hasNext()) {
                UsbDevice device = deviceIterator.next();

                manager.requestPermission(device, mPermissionIntent);
                String Model = device.getDeviceName();

                int DeviceID = device.getDeviceId();
                int Vendor = device.getVendorId();
                int Product = device.getProductId();
                int Class = device.getDeviceClass();
                int Subclass = device.getDeviceSubclass();

                Log.d("Version Info ", versionName + "  " + versioncode);
            }

            } catch(PackageManager.NameNotFoundException e){
                e.printStackTrace();
            }

    }

    public void getrootstatus(Context context){
        Process process = null;
        boolean rootstatus= false;
        try {
            process = Runtime.getRuntime().exec("su");
            rootstatus=true;
            Toast.makeText(context, "It is rooted device", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            rootstatus=false;
            Toast.makeText(context, "It is not rooted device", Toast.LENGTH_LONG).show();
        } finally {
            if (process != null) {
                try {
                    process.destroy();
                } catch (Exception e) { }
            }
        }
    }

    public static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    // get storage info
    public static String getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        return formatSize(availableBlocks * blockSize);
    }

    public static String getTotalInternalMemorySize(Context context)  {

        double totalSize = new File(context.getApplicationContext().getFilesDir().getAbsoluteFile().toString()).getTotalSpace();
        double totMb = totalSize / (1024 * 1024);
        long freeBytesInternal = new File(context.getFilesDir().getAbsoluteFile().toString()).getFreeSpace();
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        return formatSize(totalBlocks * blockSize);
    }

    public static String getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long availableBlocks = stat.getAvailableBlocksLong();
            return formatSize(availableBlocks * blockSize);
        } else {
            return "ERROR";
        }
    }

    public static String getTotalExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long totalBlocks = stat.getBlockCountLong();
            return formatSize(totalBlocks * blockSize);
        } else {
            return "ERROR";
        }
    }

    public static String formatSize(long size) {
        String suffix = null;

        if (size >= 1024) {
            suffix = "KB";
            size /= 1024;
            if (size >= 1024) {
                suffix = "MB";
                size /= 1024;
            }
        }

        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }

        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }
}


