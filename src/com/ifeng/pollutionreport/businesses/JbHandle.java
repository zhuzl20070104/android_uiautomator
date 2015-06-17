package com.ifeng.pollutionreport.businesses;


import com.android.uiautomator.core.UiCollection;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

import android.os.RemoteException;
import com.ifeng.pollutionreport.object.JbContent;
import com.ifeng.pollutionreport.object.WuRanDetail;
import com.ifeng.pollutionreport.util.AppLaunchUtil;
import com.ifeng.pollutionreport.util.BaseActionUtil;
import com.ifeng.pollutionreport.util.Utf7ImeHelper;

/**
 * Created by Vincent on 2015/5/29.
 * 举报处理过程类
 */
public class JbHandle {
    //TAG值，用于在日志前面做标记
    private static final  String TAG="TAG:"+JbHandle.class.getName().toString()+"    ";
    public static void jubao(UiAutomatorTestCase case1,JbContent  jc)  throws RemoteException,UiObjectNotFoundException{
        //跳过广告，等待曝光台按钮出现
        UiObject uibgt=new UiObject(new UiSelector().text("曝光台"));
        //断言 主页面出现
        BaseActionUtil.takeScreenShotAndAssert(uibgt,"TestJbMainScreenFailure",TAG + "进入环保行动派主页面失败");

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


        //点击我要举报按钮
        UiObject jbBtn=new UiObject(new UiSelector().resourceId("com.ifeng.pollutionreport:id/action_report"));
        //验证举报按钮确实出现了
        BaseActionUtil.takeScreenShotAndAssert(jbBtn,"JbPageFailed",TAG+"举报按钮没有出现");
        //点击举报按钮，进入举报内容填写页面
        jbBtn.clickAndWaitForNewWindow();

        System.out.println(TAG+"进入举报内容填写页面");


        System.out.println(TAG+"准备设置举报内容");
        //获取描述内容
        UiObject  jbContent=new UiObject(new UiSelector().text("添加您的描述..."));
        //验证举报详细内容页面出现
        BaseActionUtil.takeScreenShotAndAssert(jbContent,"jbPageDescFailure",TAG+"举报详细内容页面没有出现");

        jbContent.setText(Utf7ImeHelper.e(jc.getWuranDesc()));
        case1.sleep(1000);

        System.out.println(TAG+"小米等设备可能由于定制原因缺失文件而导致异常,异常可忽略");
        System.out.println(TAG+"举报内容设置完毕");

        //  不添加照片时点击下一步按钮
        // 点击下一步按钮
        UiObject  uiNext=new UiObject(new UiSelector().text("下一步"));

        System.out.println(TAG + "还未点击下一步");
        uiNext.click();
        System.out.println(TAG + "点击下一步之后，开始断言...");
        case1.assertTrue(TAG + "不添加照片点击下一步成功了，异常", uiNext.exists());
        System.out.println(TAG + "不添加照片时点击下一步按钮无效");

        //点击添加照片按钮
        System.out.println(TAG+"点击添加照片按钮...");
        UiObject  uiAddPic=new UiObject(new UiSelector().resourceId("com.ifeng.pollutionreport:id/item_grida_image"));
        uiAddPic.clickAndWaitForNewWindow();


        //点击 “从相册中选择按钮”
        UiObject  chooseFromGallery=new UiObject(new UiSelector().resourceId("com.ifeng.pollutionreport:id/photos"));
        chooseFromGallery.clickAndWaitForNewWindow();

        System.out.println(TAG+"点击从相册选择照片 按钮");

        System.out.println(TAG+"进入相册选择照片界面");

        //点击相册中的图片并选择
        UiCollection uicPhotos=new UiCollection(new UiSelector().className("android.widget.GridView"));
        //验证相册页面打开了
        BaseActionUtil.takeScreenShotAndAssert(uicPhotos,"ChoosePhotoPageFailed",TAG + "相册选择照片页面打不开");

        System.out.println(TAG+"相册打开了");

        int PhotoCount=uicPhotos.getChildCount(new UiSelector().className("android.widget.ImageView"));

        UiObject photo=null;


        //获取需要设置的照片索引
        int [] indexes=jc.getZhaopianIndex();


        //点击索引所设置的照片
        System.out.println(TAG+"获取需要设置的相册照片索引");
        System.out.println(TAG+"获得的索引值为:"+indexes);
        for(int i=0;i<indexes.length;i++){
            if(indexes[i]<PhotoCount){
                photo = uicPhotos.getChildByInstance(new UiSelector().className("android.widget.ImageView"), indexes[i]);
                photo.click();
                System.out.println(TAG+"设置第:"+indexes[i]+"张照片");
            }
        }

        System.out.println(TAG+"根据索引值选择照片成功...");



        //照片选择完成，单击完成按钮
        UiObject uiFinish=new UiObject(new UiSelector().text("完成"));
        uiFinish.clickAndWaitForNewWindow();
        System.out.println(TAG+"点击完成按钮回到举报页面");

        //验证返回举报页面成功
        BaseActionUtil.takeScreenShotAndAssert(uiNext,"BackToJbPageFailure",TAG+"返回主页面失败");

        System.out.println(TAG+"成功返回主页面");
        uiNext.clickAndWaitForNewWindow();



        //获取污染详细内容
        WuRanDetail  wuRanDetail=jc.getWuRanDetail();

        //输入污染单位的名称
        UiObject  uidwmc=new UiObject(new UiSelector().resourceId("com.ifeng.pollutionreport:id/car_num"));

        //验证举报详细页面打开成功
        BaseActionUtil.takeScreenShotAndAssert(uidwmc,"OpenJbDescPageFailure",TAG+"打开举报详情页面失败");
        System.out.println(TAG+"打开举报详情页面成功");

        uidwmc.setText(Utf7ImeHelper.e(wuRanDetail.getWuranDanwei()));
        System.out.println(TAG + "小米等设备可能因为定制原因产生异常，可忽略");
        System.out.println(TAG+"成功设置污染单位名称");
        case1.sleep(1000);

        //输入地点信息，此处可变，可能会要求用户从地图中选择，将另写一条case
        UiObject  uidd=new UiObject(new UiSelector().resourceId("com.ifeng.pollutionreport:id/address"));
        uidd.setText(Utf7ImeHelper.e(wuRanDetail.getPaisheAddr()));
        System.out.println(TAG+"小米等设备可能因为定制原因产生异常，可忽略");
        System.out.println(TAG+"成功设置地点信息");
        case1.sleep(1000);
        //填写举报人相关信息
        UiObject  uijbr=new UiObject(new UiSelector().resourceId("com.ifeng.pollutionreport:id/username"));
        uijbr.setText(Utf7ImeHelper.e(wuRanDetail.getJbPeople()));
        System.out.println(TAG+"小米等设备可能因为定制原因产生异常，可忽略");
        System.out.println(TAG+"成功设置举报人信息");
        case1.sleep(1000);

        //填写手机号码相关的信息
        UiObject  uiPhone=new UiObject(new UiSelector().resourceId("com.ifeng.pollutionreport:id/phone"));
        uiPhone.setText(Utf7ImeHelper.e(wuRanDetail.getPhoneNumber()));
        System.out.println(TAG+"小米等设备可能因为定制原因产生异常，可忽略");
        System.out.println(TAG+"成功设置手机号信息");
        case1.sleep(1000);



        //查找到提交按钮并点击继续，提交污染内容
        UiObject  uiSubmit=new UiObject(new UiSelector().text("提交"));
        uiSubmit.click();
        long beginClick=System.currentTimeMillis();

        //等待返回首页按钮出现
        UiObject  uiBacktoHome=new UiObject(new UiSelector().text("返回首页"));
        uiBacktoHome.waitForExists(10000);
       // case1.assertTrue("返回首页不存在",uiBacktoHome.exists());
        if(!uiBacktoHome.exists()){
            //连退两次退出先前页面
            System.out.println(TAG+"您现在使用的是线下版本，不能执行提交功能");
            System.out.println(TAG+"准备退出当前程序");
            AppLaunchUtil.publicStopApp();
        }else{
            //统计评论成功耗费的时间
            long endClick=System.currentTimeMillis();
            long ecollapseTime=(endClick-beginClick)/1000;
            System.out.println(TAG+"评论成功耗费:"+ecollapseTime+"秒");

            //评论成功后，点击返回首页按钮
            uiBacktoHome.click();
            beginClick=System.currentTimeMillis();
            System.out.println(TAG+"准备返回到首页");
            UiObject uiPollutedsource=new UiObject(new UiSelector().textContains("已被举报的污染源"));
            uiPollutedsource.waitForExists(10000);

            //统计评论成功后，返回首页所需要的时间
            endClick=System.currentTimeMillis();
            long ecollapseTime1=(endClick-beginClick)/1000;
            System.out.println(TAG+"成功返回到首页");
            System.out.println(TAG+"返回首页所需要的时间:"+ecollapseTime1+"秒");
            System.out.println(TAG+"一次评论所耗费的完整时间:"+(ecollapseTime+ecollapseTime1)+"秒");
            case1.sleep(5000);
        }
    }
}
