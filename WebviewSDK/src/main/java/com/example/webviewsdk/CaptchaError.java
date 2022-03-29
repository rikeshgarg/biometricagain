package com.example.webviewsdk;

import java.io.Serializable;
import androidx.annotation.NonNull;
public enum CaptchaError implements Serializable {

    /**
     * Internet connection is missing.
     *
     * Make sure AndroidManifest requires internet permission: {@code {@literal <}uses-permission android:name="android.permission.INTERNET" /{@literal >}}
     */

    NETWORK_ERROR(7, "No internet connection"),

    /**
     * hCaptcha session timed out either the token or challenge expired
     */
    SESSION_TIMEOUT(15, "Session Timeout"),

    /**
     * User closed the challenge by pressing `back` button or touching the outside of the dialog.
     */
    CHALLENGE_CLOSED(30, "Challenge Closed"),

    /**
     * Rate limited due to too many tries.
     */
    RATE_LIMITED(31, "Rate Limited"),

    /**
     * Generic error for unknown situations - should never happen.
     */
    ERROR(29, "Unknown error");

    private final int errorId;

    private final String message;

    CaptchaError(final int errorId, final String message) {
        this.errorId = errorId;
        this.message = message;
    }

    /**
     * @return the integer encoding of the enum
     */
    public int getErrorId() {
        return this.errorId;
    }

    /**
     * @return the error message
     */
    public String getMessage() {
        return this.message;
    }

    @NonNull
    @Override
    public String toString() {
        return message;
    }

    /**
     * Finds the enum based on the integer encoding
     *
     * @param errorId the integer encoding
     * @return the {@link CaptchaError} object
     * @throws RuntimeException when no match
     */
    @NonNull
    public static CaptchaError fromId(final int errorId) {
        final CaptchaError[] errors = CaptchaError.values();
        for (final CaptchaError error : errors) {
            if (error.errorId == errorId) {
                return error;
            }
        }
        throw new RuntimeException("Unsupported error id: " + errorId);
    }
}
