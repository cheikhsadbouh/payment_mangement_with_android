package com.art.consulting;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by root on 17/04/17.
 */
public class UssdActivity extends AccessibilityService {

    public static String TAG = UssdActivity.class.getSimpleName();

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, "onAccessibilityEvent");

        AccessibilityNodeInfo source = event.getSource();
    /* if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && !event.getClassName().equals("android.app.AlertDialog")) { // android.app.AlertDialog is the standard but not for all phones  */
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && !String.valueOf(event.getClassName()).contains("AlertDialog")) {
            return;
        }
        if(event.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED && (source == null || !source.getClassName().equals("android.widget.TextView"))) {
            return;
        }
        if(event.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED && TextUtils.isEmpty(source.getText())) {
            return;
        }

        List<CharSequence> eventText;

        if(event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            eventText = event.getText();
        } else {
            eventText = Collections.singletonList(source.getText());
        }

        String text = processUSSDText(eventText);

        if( TextUtils.isEmpty(text) ) return;

        // Close dialog
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            performGlobalAction(GLOBAL_ACTION_BACK); // This works on 4.1+ only
        }

        Log.d(TAG, text);
        // Handle USSD response here

    }

    private String processUSSDText(List<CharSequence> eventText) {
        for (CharSequence s : eventText) {
            String text = String.valueOf(s);
            // Return text if text is the expected ussd response
            if( true ) {
                return text;
            }
        }
        return null;
    }

    @Override
    public void onInterrupt() {
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.d(TAG, "onServiceConnected");
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.flags = AccessibilityServiceInfo.DEFAULT;
        info.packageNames = new String[]{"com.android.phone"};
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED | AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        setServiceInfo(info);
    }
}
