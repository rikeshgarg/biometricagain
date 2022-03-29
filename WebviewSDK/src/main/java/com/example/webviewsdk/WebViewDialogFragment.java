package com.example.webviewsdk;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.HttpAuthHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.webkit.WebViewFeature;

import com.example.webviewsdk.Util.testclass;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class WebViewDialogFragment extends DialogFragment {
    public static final String TAG = WebViewDialogFragment.class.getSimpleName();
    public static final String JS_INTERFACE_TAG = "JSInterface";
    private View rootView;
    public WebView webView;
    private int ViewHeight;
    private int ViewWidth;
    private String api_key;
    private final Handler handler = new Handler(Looper.getMainLooper());
    public static WebViewDialogFragment newInstance(

            final int webViewHeight,
            final int webViewWidth,
            final String api_key
    ) {
        final Bundle args=new Bundle();
        args.putInt("hh",webViewHeight);
        args.putInt("ww",webViewWidth);
        args.putString("api_key",api_key);
        final WebViewDialogFragment webViewDialogFragment = new WebViewDialogFragment();
        webViewDialogFragment.setArguments(args);
        return webViewDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toast.makeText(getContext(), "hi " + TAG, Toast.LENGTH_SHORT).show();
        assert getArguments() != null;

        api_key=getArguments().getString("api_key");
        ViewHeight= getArguments().getInt("hh");
        ViewWidth= getArguments().getInt("ww");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            rootView = inflater.inflate(R.layout.activity_webview, container);
        } catch (Exception ex){
            Log.d(TAG,ex.getMessage());
        }
        return rootView;
    }
    @Nullable
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            webView = view.findViewById(R.id.webview);


           //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            DisplayMetrics displayMetrics = new DisplayMetrics();
//            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//            int height = displayMetrics.heightPixels*70/100;
//            int width = displayMetrics.widthPixels*90/100;
//            ViewGroup.LayoutParams params = webView.getLayoutParams();
//            params.height = height;
//            params.width = width;
            //webView.setLayoutParams(params);
            setupWebView(webView);
        } catch (Exception ex){
            Log.d(TAG,ex.getMessage());
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
        if (webView != null) {
            webView.removeJavascriptInterface(JS_INTERFACE_TAG);
            ((ViewGroup) rootView).removeAllViews();
            webView.destroy();
            webView = null;
        }
        } catch (Exception ex){
            Log.d(TAG,ex.getMessage());
        }
    }


    public void onLoaded() {
//        new Thread(new Runnable(){
//            @Override
//            public void run () {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run () {
//                        final Dialog dialog = getDialog();
//                        if (dialog != null) {
//                            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//                            dialog.getWindow().setDimAmount(0.5f);
//                            dialog.show();
//                        }
//                    }
//                });
//            }
//        }).start();
        handler.post(new Runnable() {
            @Override
            public void run() {
                final Dialog dialog = getDialog();
                if (dialog != null) {
                    dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                    dialog.getWindow().setDimAmount(0.5f);
                    dialog.show();

                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        //testclass tt = new testclass();
        try{
            final Dialog dialog = getDialog();
            if (dialog != null) {
                final Window window = dialog.getWindow();
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                // Remove dialog shadow to appear completely invisible
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        } catch (Exception ex){
            Log.d(TAG,ex.getMessage());
        }

    }

    //@SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    //https://stackoverflow.com/questions/11157876/read-html-file-from-assets
    private void setupWebView(final WebView webView) {
        try
        {
            String sEncoded = URLEncoder.encode("mystring1", "UTF-8");
            String sDecoded = URLDecoder.decode(sEncoded, "UTF-8");
            final WebSettings settings = webView.getSettings();

            settings.setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient() {

//                @Override
//                public void onPageFinished(WebView view, String url) {
//                    if (!WebViewFeature.isFeatureSupported(WebViewFeature.RECEIVE_HTTP_ERROR)) {
//                        if (view.getTitle().equals("404 Not Found")) {
//                            // Handle HTTP 404 error here.
//                        }
//                        // TODO: Handle more errors, not just 404.
//                    }
//                }


                //@TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                    try {
                        webView.stopLoading();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (webView.canGoBack()) {
                        webView.goBack();
                    }

                    Toast.makeText(getActivity(),"method1",Toast.LENGTH_LONG).show();
                }

                //@TargetApi(android.os.Build.VERSION_CODES.M)
                @Override
                public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                    // Redirect to deprecated method, so you can use it in all SDK versions
                    try {
                        webView.stopLoading();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (webView.canGoBack()) {
                        webView.goBack();
                    }
                    Toast.makeText(getActivity(),"method2",Toast.LENGTH_LONG).show();

                }

                @Override
                public void onLoadResource(WebView view, String url) {
                    super.onLoadResource(view, url);
                }

                @Nullable
                @Override
                public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                    return super.shouldInterceptRequest(view, request);
                }

                @Override
                public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
                    super.onReceivedHttpAuthRequest(view, handler, host, realm);
                }

                @Override
                public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                    super.onReceivedHttpError(view, request, errorResponse);

                    try {
                        webView.stopLoading();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (webView.canGoBack()) {
                        webView.goBack();
                    }
                    Toast.makeText(getActivity(),"metho3",Toast.LENGTH_LONG).show();

                }
            });
            WebView.setWebContentsDebuggingEnabled(true);
            /*5 March 2021*/
            webView.clearCache(true);
            settings.setLoadWithOverviewMode(true);
            settings.setAllowFileAccess(true);
            settings.setDomStorageEnabled(true);
            settings.setLoadsImagesAutomatically(true);

            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            webView.setWebChromeClient(new WebChromeClient());
            webView.setBackgroundColor(Color.TRANSPARENT);
            webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);

            //webView.addJavascriptInterface(hCaptchaJsInterface, JS_INTERFACE_TAG);
            //webView.loadDataWithBaseURL("https://trendoceans.com/how-to-fix-exception-open-failed-eacces-permission-denied-on-android/","","","","");
            //webView.loadUrl("https://trendoceans.com/how-to-fix-exception-open-failed-eacces-permission-denied-on-android/");


            webView.addJavascriptInterface(new JavaScriptInterface(getActivity()), "Android");
            webView.loadUrl("file:///android_asset/a1.html");
            //webView.loadUrl("file:///android_asset/hcaptcha-form.html");
//            webView.setWebViewClient(new WebViewClient() {
//                @Override
//                public void onPageFinished(WebView view, String url) {
//                    if (!WebViewFeature.isFeatureSupported(WebViewFeature.RECEIVE_HTTP_ERROR)) {
//                        if (view.getTitle().equals("404 Not Found")) {
//                            // Handle HTTP 404 error here.
//                        }
//                        // TODO: Handle more errors, not just 404.
//                    }
//                }
//                @Override
//                public void onReceivedHttpError(final WebView view, final WebResourceRequest request, WebResourceResponse errorResponse) {
//                    final int statusCode;
//                    // SDK < 21 does not provide statusCode
//                    if (Build.VERSION.SDK_INT < 21) {
//                        statusCode = 100;
//                    } else {
//                        statusCode = errorResponse.getStatusCode();
//                    }
//
//                    Toast.makeText(getActivity(),"metho3",Toast.LENGTH_LONG).show();
//                }
//                @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
//                @Override
//                public void onReceivedError(final WebView view, int errorCode, String description,
//                                            final String failingUrl) {
//                    //control you layout, show something like a retry button, and
//                    //call view.loadUrl(failingUrl) to reload.
//
//                    super.onReceivedError(view, errorCode, description, failingUrl);
//                    Toast.makeText(getActivity(),"metho3",Toast.LENGTH_LONG).show();
//                }
//            });
            //webView.loadUrl("https://trendoceans.com/how-to-fix-exception-open-failed-eacces-permission-denied-on-android/");
//            DisplayMetrics displayMetrics = new DisplayMetrics();
//            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//            int height = displayMetrics.heightPixels*50/100;
//            int width = displayMetrics.widthPixels*90/100;


//            ViewTreeObserver viewTreeObserver  = webView.getViewTreeObserver();
//            viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//
//                @Override
//                public boolean onPreDraw() {
//                    int height = webView.getMeasuredHeight();
//                    int width = webView.getMeasuredWidth();
//
//                    int height1 = webView.getContentHeight();
//                    //int width1 = webView.();
//                    if( height != 0 ){
//                        ViewGroup.LayoutParams params = webView.getLayoutParams();
//                        params.height = height;
//                        params.width = width;
//                        webView.setLayoutParams(params);
//                        Toast.makeText(getActivity(),"height:"+height,Toast.LENGTH_SHORT).show();
//                        webView.getViewTreeObserver().removeOnPreDrawListener(this);
//
//                    }
//                    return false;
//                }
//            });


            //webView.loadUrl("file:///android_asset/hcaptcha-form.html");
            //webView.loadUrl("file:///android_asset/a.html");
            //getContext()
//            InputStream is = getActivity().getAssets().open("a.html");
//            int size = is.available();
//
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//
//            String str = new String(buffer);
//            str = str.replace("https://media.geeksforgeeks.org/wp-content/cdn-uploads/20190710102234/download3.png", "https://image.shutterstock.com/image-vector/link-icon-hyperlink-chain-symbol-600w-1186749931.jpg");
//            webView.loadDataWithBaseURL("file:///android_asset/", str, "text/html", "UTF-8", null);

        } catch(Exception ex){
            Log.d(TAG,ex.getMessage());
        }

    }



    @Override
    public void onCancel(@NonNull DialogInterface dialogInterface) {
        // User canceled the dialog through either `back` button or an outside touch
        super.onCancel(dialogInterface);

    }


}
