package com.geappliances.test.sunny.search.listener;

import com.geappliances.test.sunny.db.common.CityItem;

import java.util.ArrayList;

/**
 * @file EditTextFocusListener.java
 * @date 20/02/2019
 * @brief
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */
public interface EditTextFocusListener {
    void focusOn();
    void focusOut();
    void searchCity(ArrayList<CityItem> items);
}
