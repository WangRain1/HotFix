/*
 * Copyright (c) 2019. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.example.ts.smoothlineview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {


    /**
     *
     * skinView : view + skinAttr （当前的view和view的attr数据）
     * skinAttr : skinType + resName
     * skinType : 处理 view 的属性改变的 枚举
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
