package com.ifeng.pollutionreport.util;

/**
 * Created by Vincent on 2015/5/29.
 * 基础行为处理类
 * 大多数的测试都要用到的方法，都要处理的步骤
 */

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import junit.framework.Assert;

import java.io.File;

/**
 * Created by Fredric,Zhu on 2015/3/18.
 */
public class BaseActionUtil{
    private static final long WAIT_TIME_OUT=8000;
    private static final String SCREEN_SHOT_PATH = "/data/local/tmp/";
    private BaseActionUtil(){
    }
    /**
     * 截图接口，图片存在手机/data/local/tmp文件夹中
     * @param pictureName
     * @param scal
     * @param quality
     */
    public static void screenShot (String pictureName, float scal,int quality){

        if(UiDevice.getInstance().takeScreenshot(new File(SCREEN_SHOT_PATH + pictureName + ".png"), scal, quality)) {
            return;
        }else {
            System.out.println(" 截图失败！！");
        }
    }

    /**
     * 截图并Assert的接口，图片存在手机/data/local/tmp文件夹中
     * @param objName  需要assert的对象名
     * @param pngFileName  截图生成的 PNG文件名
     * @param assertMsg   断言失败后的消息
     */
    public static  void  takeScreenShotAndAssert(UiObject objName,String pngFileName,String assertMsg){
        //验证返回举报页面成功
        if(!objName.waitForExists(WAIT_TIME_OUT)){
            BaseActionUtil.screenShot(pngFileName,1.0f,80);
        }
        Assert.assertTrue(assertMsg, objName.exists());
    }

    /**
     * 当出现Assert条件验证的时候，默认截图
     * @param  assertCondition  需要用来Assert的条件
     * @param  pngFileName 截图生成的图片文件
     * @param  assertMsg   assert失败后的错误消息
     */
    public  static void  assertThenTakeScreenshot(boolean assertCondition,String pngFileName,String assertMsg){
        try{
            //断言条件
            Assert.assertTrue(assertMsg,assertCondition);
        }catch(AssertionError e){
            //打印异常
            System.out.println("TAG:Exception:"+e.getMessage());
            //截取屏幕图像
            BaseActionUtil.screenShot(pngFileName,1.0f,80);
            //停止程序
            AppLaunchUtil.publicStopApp();
        }
    }
}

