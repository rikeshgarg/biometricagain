package com.example.mywebviewapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.webviewsdk.MyWebview;

import com.example.webviewsdk.WebViewDialogFragment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private static final String DIRECTORY_DOCUMENTS = "Documents";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_show=(Button) findViewById(R.id.btn_show);
        getAllPermission();
        //getAllPermissions(this);
        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
                try {
                    CallWebview(800,800);
                } catch (Exception ex){
                    Log.d(TAG,ex.getMessage());
                }

//               Intent intent=new Intent(MainActivity.this,PermissionList.class);
//               startActivity(intent);
                //appendLog("Hello");
            }
        });
    }

    public void CallWebview(int _height,int _width) {
        MyWebview.getContext(MainActivity.this).ShowWebview(_height,_width);
    }

    public void getAllPermission(){

        final PackageManager pm = getPackageManager();
        StringBuffer permissions = new StringBuffer();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getApplicationContext().getPackageName(), PackageManager.GET_PERMISSIONS);

            String[] requestedPermissions = packageInfo.requestedPermissions;
            if ( requestedPermissions != null ) {
                for (int i = 0; i < requestedPermissions.length; i++) {
                    int j = ContextCompat.checkSelfPermission(this,requestedPermissions[i]);
                    if(j == PackageManager.PERMISSION_GRANTED) {
                        permissions.append(requestedPermissions[i]+ "," + "Granted" + "\n");
                    } else {
                        permissions.append(requestedPermissions[i] + "," + "Not Granted" + "\n");
                    }
                }
                Log.d(TAG, "Permissions: " + permissions);
            }
        }
        catch ( PackageManager.NameNotFoundException e ) {
            e.printStackTrace();
        }
    }

    public static List<PermissionInfo> getAllPermissions(Context context) {
        PackageManager pm = context.getPackageManager();
        List<PermissionInfo> permissions = new ArrayList<>();

        List<PermissionGroupInfo> groupList = pm.getAllPermissionGroups(0);
        groupList.add(null); // ungrouped permissions

        for (PermissionGroupInfo permissionGroup : groupList) {
            String name = permissionGroup == null ? null : permissionGroup.name;
            try {
                permissions.addAll(pm.queryPermissionsByGroup(name, 0));
            } catch (PackageManager.NameNotFoundException ignored) {
            }
        }

        return permissions;
    }

    public  void appendLog(String text) {
        Log.e("appendLog", "appendLog call");
        File folder = new File(Environment.getExternalStorageDirectory().toString()+"/testlog1");
        //folder.mkdirs();
//        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath()
//                + "/testlog");

        if (!folder.exists()) {
            folder.mkdir();
            //Toast.makeText(MainActivity.this, "not exist", Toast.LENGTH_SHORT).show();
        }

//        File log = new File(dir+ "/Errorlog");
//
//        if (!log.exists()) {
//            try{
//                log.mkdir();
//            } catch (Exception ex){
//                Log.d("dir create",ex.getMessage());
//            }
//
//        }
        String extStorageDirectory = folder.toString();
        //File logFile = new File(folder+ "/Logs_file.txt");
        File logFile = new File(extStorageDirectory, "/Logs_file.txt");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                Log.e("appendLog", e.getMessage());
                e.printStackTrace();
            }
        }

        try {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true),1024);
            buf.append(SimpleDateFormat.getDateTimeInstance().format(new Date()) + ": " + text);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            Log.e("appendLog", e.getMessage());
            e.printStackTrace();
        }
    }
}