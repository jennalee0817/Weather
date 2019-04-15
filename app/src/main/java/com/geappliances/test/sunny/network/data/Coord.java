package com.geappliances.test.sunny.network.data;

import com.google.gson.annotations.SerializedName;

/**
 * @file Coord.java
 * @date 20/02/2019
 * @brief
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */
public class Coord {
    @SerializedName("lon")
    public float lon;
    @SerializedName("lat")
    public float lat;
}