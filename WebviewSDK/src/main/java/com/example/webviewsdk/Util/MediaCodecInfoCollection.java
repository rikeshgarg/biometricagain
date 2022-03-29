
package com.example.webviewsdk.Util;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.CAMERA_SERVICE;
import static android.content.Context.FINGERPRINT_SERVICE;
import static android.content.Context.INPUT_SERVICE;

import android.app.ActivityManager;
import android.content.Context;

import android.content.res.Resources;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraExtensionCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.input.InputManager;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.os.Build;
import android.view.InputDevice;
import android.widget.Toast;

import androidx.biometric.BiometricPrompt;
//import android.hardware.biometrics.BiometricManager;
import androidx.biometric.BiometricManager;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MediaCodecInfoCollection {

        public static List<MediaCodecInfo> extractCodecInfo() {
            List<MediaCodecInfo> mediaCodecInfoList = new ArrayList<>();
            if (Build.VERSION.SDK_INT >= 21) {
                MediaCodecList mediaCodecList = new MediaCodecList(MediaCodecList.ALL_CODECS);
                MediaCodecInfo[] mediaCodecInfos = mediaCodecList.getCodecInfos();
                mediaCodecInfoList.addAll(Arrays.asList(mediaCodecInfos));
            } else {
                int count = MediaCodecList.getCodecCount();
                for (int i = 0; i < count; i++) {
                    MediaCodecInfo mci = MediaCodecList.getCodecInfoAt(i);
                    mediaCodecInfoList.add(mci);
                }

            }
            return mediaCodecInfoList;
        }

    public static List<InputDevice> extractCodecInfo1(Context cc) {



        List<InputDevice> mediaCodecInfoList = new ArrayList<>();

             InputManager mediaCodecList = null;
        InputManager inptmgr = (InputManager)cc.getSystemService(INPUT_SERVICE);
        //int [] mediaCodecInfos = inptmgr.getInputDeviceIds();
            int[] mediaCodecInfos = InputDevice.getDeviceIds();


            for(int i=0;i<mediaCodecInfos.length;i++){
                String str1 = inptmgr.getInputDevice(mediaCodecInfos[i]).getName().toLowerCase();
                String str21= String.valueOf(inptmgr.getInputDevice(mediaCodecInfos[i]).getVendorId());
                    InputDevice dd = inptmgr.getInputDevice(mediaCodecInfos[i]);
                    String str11= dd.getName();
                    String str2 = String.valueOf(dd.getVendorId());
                mediaCodecInfoList.add(dd);


            }


        return mediaCodecInfoList;
    }

//    public static String getBioFingerprint(Context cc) {
//        FingerprintManager fingerprintManager = (FingerprintManager) cc.getSystemService(FINGERPRINT_SERVICE);
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
//            if (!fingerprintManager.isHardwareDetected()) {
//                return "not_supported";
//            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
//                return "supported";
//            } else {
//                return "enabled";
//            }
//        }
//        return "unknow";
////        } else {
////            int authCheck = BiometricManager.from(cc).canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK | BiometricManager.Authenticators.BIOMETRIC_STRONG | BiometricManager.Authenticators.DEVICE_CREDENTIAL);
////            if (authCheck == 1 || authCheck == 12) {
////                return "not_supported";
////            } else if (authCheck == 0 || authCheck == 11) {
////                return "supported";
////            } else {
////                return "enabled";
////            }
////        }
//
//    }

    public static String getBioFingerprint11(Context context) {
        String FingerPrintStatus = "Unknown";

        try {
            int authCheck = BiometricManager.from(context).canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK);
            Toast.makeText(context, "call BiometricManager", Toast.LENGTH_SHORT).show();
            switch (authCheck) {
                case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                    FingerPrintStatus = "Hardware Unavailable";
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                    FingerPrintStatus = "No Suitable Hardware";
                    break;
                case BiometricManager.BIOMETRIC_SUCCESS:
                    FingerPrintStatus = "Supported";
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                    FingerPrintStatus = "Not Enrolled";
                    break;
                case BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED:
                    FingerPrintStatus = "Not Supported";
                    break;
                case BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED:
                    FingerPrintStatus = "Security Update Required";
                    break;
                case BiometricManager.BIOMETRIC_STATUS_UNKNOWN:
                    FingerPrintStatus = "Unknown";
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown";
        }
        return FingerPrintStatus;
    }

    //public static FingerprintSensorStatus getsensor(Context cc) {
//        FingerprintManager fingerprintManager = (FingerprintManager) cc.getSystemService(FINGERPRINT_SERVICE);
//        FingerprintManagerCompat fingerprintManagerCompat = FingerprintManagerCompat.from(cc.getApplicationContext());
//        int authCheck = BiometricManager.from(cc).canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK);
//
//        //FingerprintManager.FINGERPRINT_ERROR_HW_UNAVAILABLE
//        // FingerprintManager.FINGERPRINT_ERROR_NO_FINGERPRINTS
//        int authchk1 =  BiometricManager.from(cc).canAuthenticate();
//        int jj = BiometricManager.from(cc).canAuthenticate();
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P ){
//            int authchk =  BiometricManager.from(cc).canAuthenticate();
//            if(authchk ==1 || authchk ==12){
//                return FingerprintSensorStatus.NOT_SUPPORTED;
//            }
//
//            else if(authchk ==0 || authchk ==11){
//                return FingerprintSensorStatus.SUPPORTED;
//            } else {
//                return FingerprintSensorStatus.ENABLED;
//            }
//        }
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            return FingerprintSensorStatus.NOT_SUPPORTED;
//        } else if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.P)) {
//            if (!fingerprintManagerCompat.isHardwareDetected()) {
//                return FingerprintSensorStatus.NOT_SUPPORTED;
//            }
//            else if (!fingerprintManager.hasEnrolledFingerprints()) {
//                return FingerprintSensorStatus.SUPPORTED;
//
//            }
//            else {
//                return FingerprintSensorStatus.ENABLED;
//            }
//        }
//        else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P ){
//            int authchk =  BiometricManager.from(cc).canAuthenticate();
//            if(authchk ==1 || authchk ==12){
//                return FingerprintSensorStatus.NOT_SUPPORTED;
//            }
//
//            else if(authchk ==0 || authchk ==11){
//                return FingerprintSensorStatus.SUPPORTED;
//            } else {
//                return FingerprintSensorStatus.ENABLED;
//            }
//        }
//
//
////        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
////            return FingerprintSensorStatus.NOT_SUPPORTED;
////        } else if (!fingerprintManager.isHardwareDetected()) {
////            return FingerprintSensorStatus.NOT_SUPPORTED;
////        } else if (!fingerprintManager.hasEnrolledFingerprints()) {
////            return FingerprintSensorStatus.SUPPORTED;
////        } else {
////            return FingerprintSensorStatus.ENABLED;
////        }
//        return FingerprintSensorStatus.UNKNOWN;
    //}

    public static List<Camera.CameraInfo> extractcamerainfo(Context cc) throws CameraAccessException {
        String getLanguage = cc.getResources().getSystem().getConfiguration().locale.getLanguage();
        String locale = cc.getResources().getConfiguration().locale.getDisplayName();
        String locale1 = java.util.Locale.getDefault().getDisplayName();
        Locale locale11;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Locale locale112 = Resources.getSystem().getConfiguration().getLocales().get(0);
        } else {
            //noinspection deprecation
            Locale locale111 = Resources.getSystem().getConfiguration().locale;
        }

        String Country = cc.getResources().getConfiguration().locale.getCountry();

        String locale111;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale111 = cc.getResources().getConfiguration().getLocales().get(0).getCountry();
        } else {
            locale111 = cc.getResources().getConfiguration().locale.getCountry();
        }

        String str = TimeZone.getDefault().getDisplayName();
        ActivityManager actManager = (ActivityManager) cc.getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo(memInfo);
        //long totalMemory = memInfo.totalMem / (1024 * 1024);
        long totalMemory = memInfo.totalMem;
        String str1 = bytesToHuman(totalMemory);

//        double mb = totalMemory / 1024.0;
//        double gb = totalMemory / 1048576.0;
//        double tb = totalMemory / 1073741824.0;
//
//        if (tb > 1) {
//            lastValue = twoDecimalForm.format(tb).concat(" TB");
//        } else if (gb > 1) {
//            lastValue = twoDecimalForm.format(gb).concat(" GB");
//        } else if (mb > 1) {
//            lastValue = twoDecimalForm.format(mb).concat(" MB");
//        } else {
//            lastValue = twoDecimalForm.format(totRam).concat(" KB");
//        }


        List<Camera.CameraInfo> mediaCodecInfoList = new ArrayList<>();
        CameraManager inptmgr = (CameraManager)cc.getSystemService(CAMERA_SERVICE);
        String[] mediaCodecInfos = inptmgr.getCameraIdList();
        final int numCameras1 = Camera.getNumberOfCameras();
        final int numCameras = Camera.getNumberOfCameras();
        CameraManager myCamera = (CameraManager) cc.getSystemService(Context.CAMERA_SERVICE);
        String ids [] = myCamera.getCameraIdList();
        int no_of_camera = ids.length;

        for(String cameraId: myCamera.getCameraIdList()){
            CameraCharacteristics cameraCharacteristics = myCamera.getCameraCharacteristics(cameraId);
            String strCameraName = "";
            if(cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT){
                 strCameraName = "front";
            } else  if(cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_BACK) {
                 strCameraName = "back";
            }
            String strCameraid = cameraId;
            continue;
        }
        LinkedList result = new LinkedList();
        List<String> lst = new ArrayList<>();
        for(int i = 0; i < numCameras; i++){
            Log.d("cameraNum", "Camera "+i);
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i, cameraInfo);
            String type,orientation ;
            String type1 = String.valueOf(cameraInfo.facing);
            String orientation1 = String.valueOf(cameraInfo.orientation);
            lst.add(i,type1 + "," + orientation1);
            if(Camera.CameraInfo.CAMERA_FACING_FRONT == 1){
                type="front";

                orientation = String.valueOf(cameraInfo.orientation);
            } else if(Camera.CameraInfo.CAMERA_FACING_BACK ==0){
                type="back";
                orientation = String.valueOf(cameraInfo.orientation);
            }

        }

        return mediaCodecInfoList;
    }

    private String cameraType(){
          String cameraType="";

          return cameraType;
    }

     private enum  FingerprintSensorStatus {
        NOT_SUPPORTED("not_supported"),
        SUPPORTED("supported"),
        ENABLED("enabled"),
         NOT_ENABLED("not_enabled"),
        UNKNOWN("unknown");

         private final String stringDescription;
         FingerprintSensorStatus(String stringDescription) {
             this.stringDescription = stringDescription;
         }
         public final String getStringDescription() {
             return this.stringDescription;
         }
     }

//    private fun stringDescriptionForType(type: Int) = when (type) {
//        Camera.CameraInfo.CAMERA_FACING_FRONT -> "front"
//        Camera.CameraInfo.CAMERA_FACING_BACK -> "back"
//        else -> ""
//    }

    private static String bytesToHuman(long size) {
        long Kb = 1024;
        long Mb = Kb * 1024;
        long Gb = Mb * 1024;
        long Tb = Gb * 1024;
        long Pb = Tb * 1024;
        long Eb = Pb * 1024;

        if (size < Kb) return floatForm(size) + " byte";
        if (size >= Kb && size < Mb) return floatForm((double) size / Kb) + " KB";
        if (size >= Mb && size < Gb) return floatForm((double) size / Mb) + " MB";
        if (size >= Gb && size < Tb) return floatForm((double) size / Gb) + " GB";
        if (size >= Tb && size < Pb) return floatForm((double) size / Tb) + " TB";
        if (size >= Pb && size < Eb) return floatForm((double) size / Pb) + " Pb";
        if (size >= Eb) return floatForm((double) size / Eb) + " Eb";

        return "0";
    }

    private static String floatForm(double d) {
        return String.format(java.util.Locale.US, "%.2f", d);
    }

}