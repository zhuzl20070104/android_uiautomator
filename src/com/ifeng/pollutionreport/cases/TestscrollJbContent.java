package com.ifeng.pollutionreport.cases;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.ifeng.pollutionreport.businesses.ScrollHandle;
import com.ifeng.pollutionreport.launcher.UiAutomatorHelper;
import com.ifeng.pollutionreport.util.AppLaunchUtil;

import android.os.RemoteException;

/**
 * Created by Vincent on 2015/5/29.
 * 测试滑动举报内容的类
 */
public class TestscrollJbContent extends UiAutomatorTestCase{
    private static UiDevice device;


    public static void main(String[] args) {
        UiAutomatorHelper helper=new UiAutomatorHelper("Runner1","TestscrollJbContent","","1");
    }



    public void setUp() throws Exception {
        //启动App
        AppLaunchUtil.startPhoenixApp();
        //获取UI设备类
        device=getUiDevice();
        //唤醒设备，避免手动唤醒
        device.wakeUp();
    }

    //测试页面能继续往下 滑动 内容的方法
    public void testScroll() throws RemoteException,UiObjectNotFoundException{
        //测试下滑
        //第二个参数为滚动次数，若滚动次数为-1，则滚动到最底部，否则滚动指定的步数
        //第三个参数 为滚动后等待的时间
        ScrollHandle.scroll(this,-1,25000);
//	    ScrollHandle.scroll(this,2,3000);

    }


    //测试滚动以后点赞
    public void  testScrollDianZan() throws RemoteException,UiObjectNotFoundException{
        //测试下滑
        //第二个参数为滚动次数，若滚动次数为-1，则滚动到最底部，否则滚动指定的步数
        //第三个参数 为滚动后等待的时间
        ScrollHandle.scroll(this,20,2000);
        //测试点赞
        ScrollHandle.testDianzan(this);
        //测试评论 是否成功
        ScrollHandle.scrollDianzanDetail(this,"说得太对了，现在人素质真差，到处乱扔垃圾!!!");
        //测试在 详情 页面点赞后 主页和详情页面 是否显示点赞信息
        ScrollHandle.testDianzanInDetail(this);
    }




    public void tearDown() throws Exception {
        //停止App
         AppLaunchUtil.publicStopApp();
        //按Home键回到主屏幕
        device.pressHome();

    }
}
