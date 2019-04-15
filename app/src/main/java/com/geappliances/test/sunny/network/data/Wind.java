package com.geappliances.test.sunny.network.data;

import com.google.gson.annotations.SerializedName;

/**
 * @file Wind.java
 * @date 20/02/2019
 * @brief
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */
public class Wind {
    @SerializedName("speed")
    public float speed;
    @SerializedName("deg")
    public float deg;
}