package com.example.webviewsdk;
import android.content.Context;
import android.content.res.Resources;

import androidx.fragment.app.FragmentActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.Robolectric;

import java.util.Locale;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

@RunWith(MockitoJUnitRunner.class)
public class MyWebViewTest {

    Context cc;
    @Mock
    FragmentActivity fragmentActivity;



    @Test
    public void test_client_creation_via_context_and_activity() {
        assertNotNull(MyWebview.getContext((Context) fragmentActivity));
        assertNotNull(MyWebview.getContext(fragmentActivity));
    }

    @Test
    public void test_verify_with_mywebview_passes_height_width() {

        //Context cc = MyWebview.getContext((Context) fragmentActivity);
//        final String siteKey = "mock-site-key";
//        String str = ((Context) fragmentActivity).getApplicationInfo().packageName;
//        Resources res = fragmentActivity.getResources();
//        String api_key = res.getString(res.getIdentifier("twitter_key", "string", str));
        final MyWebview mywebview = spy(MyWebview.getContext(fragmentActivity));
        doReturn(mywebview).when(mywebview).ShowWebview(100,100);

//        assertEquals(siteKey, config.getSiteKey());
//        hCaptcha.verifyWithHCaptcha(siteKey);
//        final HCaptchaConfig config = hCaptchaConfigCaptor.getValue();
//        assertNotNull(config);
//        assertEquals(siteKey, config.getSiteKey());
//        // Rest of params must be the defaults
//        final String locale = Locale.getDefault().getLanguage();
//        assertEquals(HCaptchaSize.INVISIBLE, config.getSize());
//        assertEquals(HCaptchaTheme.LIGHT, config.getTheme());
//        assertNull(config.getRqdata());
//        assertEquals(locale, config.getLocale());
//        assertEquals("https://hcaptcha.com/1/api.js", config.getApiEndpoint());
    }
}
