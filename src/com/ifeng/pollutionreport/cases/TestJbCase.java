package com.ifeng.pollutionreport.cases;

import com.android.uiautomator.core.*;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

import android.os.RemoteException;
import com.ifeng.pollutionreport.businesses.JbHandle;
import com.ifeng.pollutionreport.businesses.TcHandle;
import com.ifeng.pollutionreport.launcher.UiAutomatorHelper;
import com.ifeng.pollutionreport.object.JbContent;
import com.ifeng.pollutionreport.object.TcContent;
import com.ifeng.pollutionreport.object.WuRanDetail;
import com.ifeng.pollutionreport.util.AppLaunchUtil;

/**
 * Created by Vincent on 2015/5/28.
 */
public class TestJbCase extends UiAutomatorTestCase {

    private static UiDevice device;


    public static void main(String[] args) {
        UiAutomatorHelper helper=new UiAutomatorHelper("Runner1","TestJbCase","","1");
    }


    public void setUp() throws Exception {
        //启动App
        AppLaunchUtil.startPhoenixApp();
        //获取UI设备类
        device=getUiDevice();
        //唤醒设备，避免手动唤醒
        device.wakeUp();
    }

    //测试举报
    public void  testJb()  throws UiObjectNotFoundException,RemoteException{


        //构造一个举报细节类
        WuRanDetail  wuRanDetail=new WuRanDetail("北京市某污染公司","北京","张三","18513691808");
        //构造需要选择的照片索引数组
        int [] photoIndexs={0,4,5,7};
        //构造举报内容类
        JbContent  jc=new JbContent("工业烟囱污染","该公司乱倒污染物，到处制造白色垃圾，且没有安装污染处理装置",
                photoIndexs ,wuRanDetail);
        //处理当前举报
        JbHandle.jubao(this, jc);


    }


    //测试吐槽
    public  void  testTuCao() throws UiObjectNotFoundException,RemoteException{
        //设置需要选择的照片索引数组
        int [] photoIndexes={0,2,6,9};
        TcContent  tc=new TcContent("脏，好脏，真的有好多垃圾啊，天啦，到处都是白色垃圾",photoIndexes);

        TcHandle.tucao(this,tc);
    }

    public void tearDown() throws Exception {
        //停止App
        AppLaunchUtil.publicStopApp();
        //按Home键回到主屏幕
        device.pressHome();

    }
}
