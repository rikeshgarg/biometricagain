package com.example.mywebviewapp;

import static android.content.ContentValues.TAG;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class PermissionList extends AppCompatActivity {

    List<PermissionData> permissionData= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_permission_list);


        getAllPermission();
//        PermissionData[] myListData = new PermissionData[] {
//                new PermissionData("Email", true),
//                new PermissionData("Info", false)
//
//        };


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
                        permissionData.add(new PermissionData(requestedPermissions[i], true) );

                        //permissions.append(requestedPermissions[i]+ "," + "Granted" + "\n");
                        }

                    else {
                        permissionData.add(new PermissionData(requestedPermissions[i], false) );
                        //permissions.append(requestedPermissions[i] + "," + "Not Granted" + "\n");

                    }

                }

                Log.d(TAG, "Permissions: " + permissions);
            }
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            PermissionListAdapter adapter = new PermissionListAdapter(permissionData);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
        catch ( PackageManager.NameNotFoundException e ) {
            e.printStackTrace();
        }
    }
}
