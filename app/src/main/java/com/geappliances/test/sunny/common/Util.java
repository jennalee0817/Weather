package com.geappliances.test.sunny.common;

import android.app.Activity;
import android.view.View;
import android.view.ViewTreeObserver;

import com.geappliances.test.sunny.search.listener.KeyboardVisibilityListener;

/**
 * @file Util.java
 * @date 20/02/2019
 * @brief Utility
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */
public class Util {

    static int APP_HEIGHT;
    static int CURRENT_ORIENTATION = -1;

    public static final int SIZE_LIMIT_CITYLIST = 20;

    public static void setKeyboardVisibilityListener(final Activity activity, final KeyboardVisibilityListener keyboardVisibilityListener) {

        final View contentView = activity.findViewById(android.R.id.content);
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            private int mPreviousHeight;

            @Override

            public void onGlobalLayout() {

                int newHeight = contentView.getHeight();
                if (newHeight == mPreviousHeight) {
                    return;
                }
                mPreviousHeight = newHeight;

                if (activity.getResources().getConfiguration().orientation != CURRENT_ORIENTATION) {
                    CURRENT_ORIENTATION = activity.getResources().getConfiguration().orientation;
                    APP_HEIGHT = 0;
                }

                if (newHeight >= APP_HEIGHT) {
                    APP_HEIGHT = newHeight;
                }
                if (newHeight != 0) {
                    if (APP_HEIGHT > newHeight) {
                        keyboardVisibilityListener.onKeyboardVisibilityChanged(true);
                    } else {
                        keyboardVisibilityListener.onKeyboardVisibilityChanged(false);
                    }
                }
            }
        });
    }


}
