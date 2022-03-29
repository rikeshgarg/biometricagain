package com.example.mywebviewapp;

import java.util.List;

public class PermissionData {
    private String permissionName;
    private boolean isGranted;


    public PermissionData(String permissionName, boolean isGranted) {
        this.permissionName = permissionName;
        this.isGranted = isGranted;
    }
    public PermissionData() {

    }
    public String getPermission() {
        return permissionName;
    }
    public void setPermission(String permissionName) {
        this.permissionName = permissionName;
    }
    public boolean getisGranted() {
        return isGranted;
    }
    public void setisGranted(boolean isGranted) {
        this.isGranted = isGranted;
    }


}
