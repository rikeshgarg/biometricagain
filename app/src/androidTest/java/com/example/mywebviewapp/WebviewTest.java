package com.example.mywebviewapp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.web.webdriver.DriverAtoms;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xml.sax.Locator;

import static androidx.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static androidx.test.espresso.web.sugar.Web.onWebView;
import static androidx.test.espresso.web.webdriver.DriverAtoms.findElement;
import static androidx.test.espresso.web.webdriver.DriverAtoms.getText;
import static androidx.test.espresso.web.webdriver.DriverAtoms.webClick;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public  class WebviewTest {
    private static final String VALID_URL = "http://www.google.com";
    private static final String INVALID_URL = "http://invalid.url.doesnotexist987.com";
    private static final long TIMEOUT = 5000;
    private WebviewActivity mActivity;
    private WebView mWeb;

    private static final String MACCHIATO = "Macchiato";
    private static final String DOPPIO = "Doppio";

    @Rule
    public ActivityTestRule<WebviewActivity> mActivityRule = new ActivityTestRule<WebviewActivity>(
            WebviewActivity.class, false, false) {
        @Override
        protected void afterActivityLaunched() {

            // Technically we do not need to do this - WebViewActivity has javascript turned on.
            // Other WebViews in your app may have javascript turned off, however since the only way
            // to automate WebViews is through javascript, it must be enabled.
            onWebView().forceJavascriptEnabled();

        }


    };

    @Test
    public void loadsample(){
        mActivityRule.launchActivity(withWebFormIntent());
    }

   /* @Test
    public void typeTextInInput_clickButton_SubmitsForm() {
        // Lazily launch the Activity with a custom start Intent per test
        mActivityRule.launchActivity(withWebFormIntent());

        // Selects the WebView in your layout. If you have multiple WebViews you can also use a
        // matcher to select a given WebView, onWebView(withId(R.id.web_view)).
        onWebView()
                // Find the input element by ID
                .withElement(findElement(Locator.ID, "text_input"))
                // Clear previous input
                .perform(clearElement())
                // Enter text into the input element
                .perform(DriverAtoms.webKeys(MACCHIATO))
                // Find the submit button
                .withElement(findElement(Locator.ID, "submitBtn"))
                // Simulate a click via javascript
                .perform(webClick())
                // Find the response element by ID
                .withElement(findElement(Locator.ID, "response"))
                // Verify that the response page contains the entered text
                .check(webMatches(getText(), containsString(MACCHIATO)));
    }

    private Object clearElement() {
    }

    @Test
    public void typeTextInInput_clickButton_ChangesText() {
        // Lazily launch the Activity with a custom start Intent per test
        mActivityRule.launchActivity(withWebFormIntent());

        // Selects the WebView in your layout. If you have multiple WebViews you can also use a
        // matcher to select a given WebView, onWebView(withId(R.id.web_view)).
        onWebView()
                // Find the input element by ID
                .withElement(findElement(Locator.ID, "text_input"))
                // Clear previous input
                .perform(clearElement())
                // Enter text into the input element
                .perform(DriverAtoms.webKeys(DOPPIO))
                // Find the change text button.
                .withElement(findElement(Locator.ID, "changeTextBtn"))
                // Click on it.
                .perform(webClick())
                // Find the message element by ID
                .withElement(findElement(Locator.ID, "message"))
                // Verify that the text is displayed
                .check(webMatches(getText(), containsString(DOPPIO)));
    }*/

    /**
     * @return start {@link Intent} for the simple web form URL.
     */
    private static Intent withWebFormIntent() {
        Intent basicFormIntent = new Intent();
        basicFormIntent.putExtra(WebviewActivity.KEY_URL_TO_LOAD, WebviewActivity.WEB_FORM_URL);
        return basicFormIntent;
    }
}
