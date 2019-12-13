/*
 * Copyright (c) 2019. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.example.ts.smoothlineview.fix;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class FixClass {


    private static final String TAG = "FixClass";

    public void fixHot(Context context,String path){
        // 修改了bug的dex 差分包的path
        File file = new File(path);
        if (!file.exists()){
            Log.e(TAG,"----fixHot---exists");
            return;
        }
        ClassLoader loader = context.getClassLoader();
        List<File> files = new ArrayList<>();
        files.add(file);
        File optimized = context.getCacheDir();
        Log.e(TAG,"----fixHot---" + optimized);
        /**
         * Field
         * Method
         * Class
         */
        try {
            // 反射拿到pathList field，由于这个属性是在父类中的，所以要通过 getSuper 拿到父类
            Field pathlist = loader.getClass().getSuperclass().getDeclaredField("pathList");
            // 由于这个属性是私有的，改成可访问权限
            pathlist.setAccessible(true);
            // 拿到需要反射的方法，注意传参数类型，防止方法重载
            Method method = pathlist.getClass().getDeclaredMethod("makeDexElements", List.class,File.class,List.class,ClassLoader.class);
            // 修改私有权限
            method.setAccessible(true);
            ArrayList<IOException> suppressedExceptions = new ArrayList<IOException>();
            // 获取一个新的数组， 第一个参数为null：因为方法是static的类直接访问，
            Object[] newElements = (Object[]) method.invoke(null,files,optimized,suppressedExceptions,loader);
            // 获取系统的属性
            Object[] systemElements = (Object[]) pathlist.get("dexElements");
            // 通过反射拿到一个新的数组
            Object[] curElements = (Object[]) Array.newInstance(systemElements.getClass().getComponentType(),newElements.length + systemElements.length);
            // 复制数组
            System.arraycopy(newElements,0,curElements,0,newElements.length);
            System.arraycopy(systemElements,0,curElements,newElements.length,systemElements.length);
            // 用新的属性 替换掉 系统的属性
            pathlist.set(systemElements,curElements);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
