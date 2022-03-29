package com.example.webviewsdk;

import static android.app.PendingIntent.getActivity;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowWebView;

import java.io.InputStream;

//import org.robolectric.TestRunners;
//import static org.assertj.core.api.Assertions.assertThat;
//import static com.google.common.truth.Truth.assertThat;
import static org.robolectric.shadows.ShadowInstrumentation.getInstrumentation;
import static org.robolectric.util.FragmentTestUtil.startFragment;
@RunWith(RobolectricTestRunner.class)

public class WebViewTest {
    private WebView webView;
    //private ShadowWebView shadowWebView;
    private  FragmentManager fragmentManager;
    private WebViewDialogFragment activity1;
    private WebView wv;
    FragmentActivity activity;
    LayoutInflater inflater;
    @Mock
    private WebView mwebview;
    private WebView webView1;
    private ShadowWebView shadowWebView;
    @Mock
    FragmentActivity fragmentActivity;
//    @Before
//    public void setUp() throws Exception {
//        activity = Robolectric.buildActivity(FragmentActivity.class).create().start().resume().get();
//        fragmentManager = activity.getSupportFragmentManager();
//        activity1 = new WebViewDialogFragment();
//    }



//    @Before
//    public void setUp() throws Exception {
//
//         activity1 = WebViewDialogFragment.newInstance(0,0,"dfgdfgf");
//        activity1.show(new FragmentManager() {
//        }, WebViewDialogFragment.TAG);
//
//        //activity1.show(fragmentManager, WebViewDialogFragment.TAG);
//        //activity.onStart();
//        //activity.onStart();
//        //webView1 = new WebView(RuntimeEnvironment.application);
//        activity = Robolectric.buildActivity( FragmentActivity.class)
//                .create()
//                .start()
//                .resume()
//                .get();
//
//        FragmentManager fragmentManager = activity.getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add( activity1, WebViewDialogFragment.TAG );
//        fragmentTransaction.commit();
//        //activity1.show(fragmentManager,WebViewDialogFragment.TAG);
//        //wv=new WebView(activity1.getActivity().getApplicationContext());
//    }

//    @Test
//    public void shouldRecordLastLoadedUrl() {
//        webView1.loadUrl("http://example.com");
//
//    }


    @Test
    public void checkActivityNotNull() throws Exception {
        assertNotNull(activity);

    }

    @Test
    public void checkWebviewNotNull() throws Exception {
        //assertNotNull(wv);
        final MyWebview hCaptcha = spy(MyWebview.getContext(fragmentActivity));
        doReturn(hCaptcha).when(hCaptcha).ShowWebview(500,500);
        //hCaptcha.ShowWebview(500,500);

        //View ww = activity.findViewById(R.layout.activity_webview);
        //WebView vv = ww.findViewById(R.id.webview);
        //View dd = inflater.inflate(R.layout.activity_webview,null);
        //wv = activity1.getActivity().findViewById(R.id.webview);

        //WebView dd = activity1.getActivity().
        //mwebview.loadUrl();
        //activity1.onCreateView();
        //activity1.setupWebView(activity1.webView);
        //Mockito.verify(mwebview).loadDataWithBaseURL(eq(""),anyString(),anyString(),anyString(),anyString());
        //Mockito.verify(mwebview).loadDataWithBaseURL(anyString(),anyString(),anyString(),anyString(),anyString());
        //mwebview.loadUrl("https://developer.chrome.com/docs/android/trusted-web-activity/integration-guide/");
        //assertNotNull(mwebview);
    }

//    @Before
//    public void setUp() throws Exception {
//        webView = new WebView(RuntimeEnvironment.application);
//        shadowWebView = shadowOf(webView);
//    }

//    @Test
//    public void testBuilder_DialogFragment()
//    {
//        final android.app.DialogFragment dialogFragment = spy(Robolectric.buildFragment(android.app.DialogFragment.class).create().get());
//        final Activity activity = Robolectric.buildActivity(Activity.class).create().get();
//        final Dialog dialog = mock(Dialog.class);
//        when(dialogFragment.getDialog()).thenReturn(dialog);
//        when(dialog.getOwnerActivity()).thenReturn(activity);
//        when(dialog.findViewById(android.R.id.content)).thenReturn(activity.findViewById(android.R.id.content));
//        final MaterialTapTargetPrompt.Builder builder = new MaterialTapTargetPrompt.Builder(dialogFragment);
//        assertTrue(builder.getResourceFinder() instanceof DialogResourceFinder);
//    }
//
//    @Test
//    public void shouldRecordLastLoadedUrl() {
//        webView.loadUrl("http://example.com");
//        assertThat(shadowOf(webView).getLastLoadedUrl()).isEqualTo("http://example.com");
//    }
}
