package com.geappliances.test.sunny.search;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.Log;

import com.geappliances.test.sunny.common.Dlog;
import com.geappliances.test.sunny.internal.ComponentHolder;
import com.geappliances.test.sunny.search.listener.EditTextFocusListener;

/**
 * @file SearchEditText.java
 * @date 20/02/2019
 * @brief
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */
public class SearchEditText extends AppCompatEditText {
    private EditTextFocusListener editTextFocusListener;

    public SearchEditText(Context context) {
        super(context);

    }

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public SearchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) {
            editTextFocusListener.focusOn();
        } else {
            editTextFocusListener.focusOut();
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        String str = String.valueOf(getText());
        Dlog.d("weather", str);
        if (!str.isEmpty()) {
            editTextFocusListener.searchCity(ComponentHolder.getInstance().getCoppiedDbHelper().findCityList(str,20,0));
        }
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }

    public void setListener(EditTextFocusListener listener) {
        this.editTextFocusListener = listener;
    }


}
