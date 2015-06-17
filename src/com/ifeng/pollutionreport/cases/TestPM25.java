package com.ifeng.pollutionreport.cases;

import android.os.RemoteException;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.ifeng.pollutionreport.businesses.PM25Handle;
import com.ifeng.pollutionreport.launcher.UiAutomatorHelper;
import com.ifeng.pollutionreport.util.AppLaunchUtil;

/**
 * Created by Vincent on 2015/6/1.
 * 测试PM 2.5的case
 */
public class TestPM25 extends UiAutomatorTestCase{
    private static UiDevice device;


    public static void main(String[] args) {
        UiAutomatorHelper helper=new UiAutomatorHelper("Runner1","TestPM25","","1");
    }



    public void setUp() throws Exception {
        //启动App
        AppLaunchUtil.startPhoenixApp();
        //获取UI设备类
        device=getUiDevice();
        //唤醒设备，避免手动唤醒
        device.wakeUp();
    }



    public void  testPM25() throws RemoteException,UiObjectNotFoundException{
        //测试 PM2.5值页面的基本功能
        //1. 当日的 PM2.5值 是否与 描述 匹配
        //2. 滚动页面是否能滚动，滚动后日期值是否变化
        PM25Handle.testPM25(this, device);

    }

    public void tearDown() throws Exception {
        //停止App
        AppLaunchUtil.publicStopApp();
        //按Home键回到主屏幕
        device.pressHome();

    }
}
