package com.example.webviewsdk.Util;

import java.util.function.Function;

public class executesafe {

    public static final Object executeSafe(Function code, Object defaultValue){
        Object var2;
        try {
            var2 = code;
        } catch (Exception var4) {
            var2 = defaultValue;
        }

        return var2;
    }

}
