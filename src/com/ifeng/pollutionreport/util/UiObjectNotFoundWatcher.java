package com.ifeng.pollutionreport.util;

import com.android.uiautomator.core.UiWatcher;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Fredric,Zhu on 2015/6/4.
 * UI Watcher对象，用于处理OK Cancel对话框
 */
public class UiObjectNotFoundWatcher implements UiWatcher{
    //当前注册Watcher时使用的Watcher名
    public static final String UI_NOT_FOUND_WATCHER_NAME="OBJECT_NOT_FOUND_WATCHER";
    @Override
    public boolean checkForCondition(){
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String sName=sdf.format(date);
        BaseActionUtil.screenShot(sName,1.0f,80);
        return false;
    }
}
