package com.example.mywebviewapp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.test.platform.app.InstrumentationRegistry;

public  class WebviewTest {
    private static final String VALID_URL = "http://www.google.com";
    private static final String INVALID_URL = "http://invalid.url.doesnotexist987.com";
    private static final long TIMEOUT = 5000;
    private WebviewActivity mActivity;
    private WebView mWeb;
    private MockWebViewClient mMockWebViewClient;

    public final void testLoadValidUrl() {
        assertLoadUrl(VALID_URL);
        assertFalse(mMockWebViewClient.mError);
    }

    public final void testLoadInvalidUrl() {
        assertLoadUrl(INVALID_URL);
        assertTrue(mMockWebViewClient.mError);
    }

    private void assertLoadUrl(String url) {
        mWeb.loadUrl(url);
        sleep();

        //Added to hopefully let webview load all the way
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        assertTrue(!(mWeb.getProgress() < 100));
    }

    private void sleep() {
        try {
            Thread.sleep(TIMEOUT);
        } catch (InterruptedException e) {
            fail("Unexpected timeout");
        }
    }
    public static class MockWebViewClient extends WebViewClient {
        static boolean mError;

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            mError = true;
        }
    }
}
