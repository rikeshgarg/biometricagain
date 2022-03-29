package com.example.webviewsdk;
//import android.os.Bundle;
//import android.webkit.WebView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.MediumTest;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;


import static com.example.webviewsdk.AssertUtil.waitToBeDisplayed;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;

import android.os.Bundle;
import android.webkit.WebView;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class CaptchDialogFrafment {

    public FragmentScenario<WebViewDialogFragment> getTestScenario() {

        final Bundle args = new Bundle();
        args.putInt("hh",1000);
        args.putInt("ww",1000);
        args.putString("api_key","api_key");
        return FragmentScenario.launch(WebViewDialogFragment.class, args);
    }
//    @Test
//    public void loader_is_visible_while_webview_is_loading() {
//        getTestScenario();
//        onView(withId(R.id.loadingContainer)).check(matches(isDisplayed()));
//        onView(withId(R.id.webview)).check(matches(isDisplayed()));
//    }
    @Test
    @MediumTest
    public void webview_is_visible_after_loading() {
        final FragmentScenario<WebViewDialogFragment> scenario = getTestScenario();
        scenario.onFragment(new FragmentScenario.FragmentAction<WebViewDialogFragment>() {
            @Override
            public void perform(@NotNull WebViewDialogFragment fragment) {
                assertNotNull(fragment.getDialog());
                WebView.setWebContentsDebuggingEnabled(true);
                fragment.onStart();
            }

        });

        onView(withId(R.id.webview)).check(matches(isDisplayed()));
        onView(isRoot()).perform(waitToBeDisplayed(R.id.webview, 1000));

    }

}
