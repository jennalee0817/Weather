package com.geappliances.test.sunny;

import android.location.Location;

/**
 * @file LocationTracker.java
 * @date 2019. 2. 21. FW8
 * @brief
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */
public interface LocationTracker {
    public interface LocationUpdateListener{
        public void onUpdate(Location oldLoc, long oldTime, Location newLoc, long newTime);
    }

    public void start();
    public void start(LocationUpdateListener update);

    public void stop();

    public boolean hasLocation();

    public boolean hasPossiblyStaleLocation();

    public Location getLocation();

    public Location getPossiblyStaleLocation();
}
