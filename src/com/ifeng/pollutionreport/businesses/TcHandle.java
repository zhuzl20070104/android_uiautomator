package com.ifeng.pollutionreport.businesses;

import com.android.uiautomator.core.UiCollection;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.ifeng.pollutionreport.object.TcContent;

import android.os.RemoteException;
import com.ifeng.pollutionreport.util.AppLaunchUtil;
import com.ifeng.pollutionreport.util.BaseActionUtil;
import com.ifeng.pollutionreport.util.Utf7ImeHelper;

/**
 * Created by Vincent on 2015/5/29.
 * 吐槽过程处理类
 */
public class TcHandle {
    //TAG值，用于在日志前面做标记
    private static final  String TAG="TAG:"+TcHandle.class.getName().toString()+"    ";

    public static void tucao(UiAutomatorTestCase case1,TcContent tc) throws RemoteException,UiObjectNotFoundException{
        //跳过广告，等待曝光台按钮出现
        UiObject uibgt=new UiObject(new UiSelector().text("曝光台"));
        //断言 主页面出现
        BaseActionUtil.takeScreenShotAndAssert(uibgt, "TestJbMainScreenFailure", TAG + "进入环保行动派主页面失败");

        System.out.println(TAG+"进入环保行动派主页面");

        //点击曝光台，等待新窗口出现
        uibgt.clickAndWaitForNewWindow();

        //等待5s，让新页面加载完毕
        case1.sleep(5000);

        System.out.println(TAG+"进入曝光台页面");
        //使用UiAutomator点击NAF对象，可以使用resource-id,点击行动按钮
        //点击行动按钮
        UiObject action=new UiObject(new UiSelector().resourceId("com.ifeng.pollutionreport:id/main_layout_report_b"));
        //验证行动按钮是否出现
        BaseActionUtil.takeScreenShotAndAssert(action,"ActionButtonShowFailure",TAG+"行动按钮没有出现");

        //点击行动按钮，跳转到  举报和吐槽窗口
        action.clickAndWaitForNewWindow();

        System.out.println(TAG+"点击行动按钮，跳转到举报和吐槽窗口");

        //获取到界面上的 “我要吐槽”
        UiObject  uiTucao=new UiObject(new UiSelector().text("我要吐槽"));
        //点击以准备开始吐槽
        //验证 举报和吐槽页面成功打开
        BaseActionUtil.takeScreenShotAndAssert(uiTucao,"GotoToCaoPageFailure",TAG+"进入举报和吐槽页面失败");
        //点击 吐槽按钮
        System.out.println(TAG+"已经进入 我要吐槽 页面");
        uiTucao.clickAndWaitForNewWindow();

        //获取描述内容
        UiObject  jbContent=new UiObject(new UiSelector().text("添加您的描述..."));
        BaseActionUtil.takeScreenShotAndAssert(jbContent,"GotoToCaoDescPageFailure",TAG+"进入吐槽描述页面失败");
        System.out.println(TAG + "成功进入吐槽描述页面");
        System.out.println(TAG+"开始设置吐槽描述页面的内容");
        jbContent.setText(Utf7ImeHelper.e(tc.getTucaoDesc()));
        System.out.println(TAG+"小米等设备可能因为定制而缺少系统文件导致异常，可忽略");
        System.out.println(TAG+"设置吐槽描述成功");

        //  不添加照片时点击 提交按钮
        // 点击下一步按钮
        UiObject  uiSubmit=new UiObject(new UiSelector().text("提交"));
        System.out.println(TAG+"还未点击提交");
        uiSubmit.click();
        System.out.println(TAG+"点击提交之后，开始断言..");
        case1.assertTrue(TAG+"没有选择图片点击提交成功了，Case失败",uiSubmit.exists());
        System.out.println(TAG + "不添加照片时点击提交按钮无效");

        //点击添加照片按钮
        UiObject  uiAddPic=new UiObject(new UiSelector().resourceId("com.ifeng.pollutionreport:id/item_grida_image"));
        System.out.println(TAG+"点击添加图片按钮");
        uiAddPic.clickAndWaitForNewWindow();


        //点击 “从相册中选择按钮”
        UiObject  chooseFromGallery=new UiObject(new UiSelector().resourceId("com.ifeng.pollutionreport:id/photos"));

        System.out.println(TAG+"点击从相册选择图片按钮");
        chooseFromGallery.clickAndWaitForNewWindow();

        System.out.println(TAG+"开始进入相册页面");
        //点击相册中的图片并选择
        UiCollection uicPhotos=new UiCollection(new UiSelector().className("android.widget.GridView"));
        BaseActionUtil.takeScreenShotAndAssert(uicPhotos,"GotoGalleryFailure",TAG+"进入相册失败");
        System.out.println(TAG+"成功进入相册");

        int PhotoCount=uicPhotos.getChildCount(new UiSelector().className("android.widget.ImageView"));
        UiObject photo=null;



        //获取需要设置的照片索引
        int [] indexes=tc.getPhotoIndexes();
        System.out.println(TAG+"获取的相册中的图片索引为:"+indexes);
        System.out.println(TAG+"开始选择相册中的图片");

        //点击索引所设置的照片
        for(int i=0;i<indexes.length;i++){
            if(indexes[i]<PhotoCount){
                photo = uicPhotos.getChildByInstance(new UiSelector().className("android.widget.ImageView"), indexes[i]);
                photo.click();
                System.out.println(TAG+"选择第:"+indexes[i]+"张图片");
            }
        }

        System.out.println(TAG+"图片选择完毕");

        //照片选择完成，单击完成按钮
        UiObject uiFinish=new UiObject(new UiSelector().text("完成"));
        System.out.println(TAG+"点击选择完成按钮");
        uiFinish.clickAndWaitForNewWindow();


        //点击提交按钮并计算提交所需时间
        //确认返回 吐槽详细 页面
        BaseActionUtil.takeScreenShotAndAssert(uiSubmit,"BacktoTucaoFailure",TAG+"返回吐槽详细页面失败");

        long beginClick=System.currentTimeMillis();
        System.out.println(TAG+"准备提交您的吐槽");
        uiSubmit.clickAndWaitForNewWindow();

        //获取被举报的污染源视图
        UiObject  uiWuRanyuan=new UiObject(new UiSelector().textContains("已被举报的污染源"));
        uiWuRanyuan.waitForExists(4000);
        System.out.println(TAG+"准备验证是否返回主页");
        if(!uiWuRanyuan.exists()){
            System.out.println(TAG+"对不起，您使用的是线下版本，无法使用吐槽功能");
            System.out.println(TAG+"准备退出应用程序");
            AppLaunchUtil.publicStopApp();
        }else {
            long endClick = System.currentTimeMillis();

            //获取吐槽一次所需要的时间
            long ecollapsetime = (endClick - beginClick) / 1000;
            System.out.println(TAG + "吐槽一次所需要的时间为:" + ecollapsetime + "秒");

            //等待一下以观看退出过程
            case1.sleep(3000);
        }
    }
}
