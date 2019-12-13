/*
 * Copyright (c) 2019. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.example.ts.smoothlineview;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.example.ts.smoothlineview.fix.FixClass;

public class APP extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        FixClass fixClass = new FixClass();
        fixClass.fixHot(this,"");
    }
}
