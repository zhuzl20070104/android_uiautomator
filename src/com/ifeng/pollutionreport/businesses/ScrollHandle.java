package com.ifeng.pollutionreport.businesses;


import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;

import com.android.uiautomator.testrunner.UiAutomatorTestCase;

import android.os.RemoteException;
import android.graphics.Rect;
import com.ifeng.pollutionreport.util.BaseActionUtil;
import com.ifeng.pollutionreport.util.UiObjectNotFoundWatcher;
import com.ifeng.pollutionreport.util.Utf7ImeHelper;

/**
 * Created by Vincent on 2015/5/29.
 * 测试滚动过程的处理类
 */
public class ScrollHandle {
    //TAG值，用于在日志前面做标记
    private static final  String TAG="TAG:"+ScrollHandle.class.getName().toString()+"    ";

    public static void  scroll(UiAutomatorTestCase case1,int scrollNumber,int sleepTime)  throws RemoteException,UiObjectNotFoundException{
        case1.getUiDevice().registerWatcher(UiObjectNotFoundWatcher.UI_NOT_FOUND_WATCHER_NAME,new UiObjectNotFoundWatcher());
        //跳过广告，等待曝光台按钮出现
        UiObject uibgt=new UiObject(new UiSelector().text("曝光台"));
        //验证曝光台按钮确实出现了，否则进入主页就失败了
        BaseActionUtil.takeScreenShotAndAssert(uibgt, "scrollModuleGotoScreenFailure", TAG + "滚动时进入主界面失败");

        System.out.println(TAG+"开始进入曝光台页面");
        uibgt.clickAndWaitForNewWindow();

        //获取右上角的数字看一共能滚多少步
        //获取曝光台 数字
        UiObject uibgNumber=new UiObject(new UiSelector().resourceId("com.ifeng.pollutionreport:id/main_layout_exposure_text"));

        BaseActionUtil.takeScreenShotAndAssert(uibgNumber,"BgNumberGetFailure",TAG+"曝光台数字获取失败");
        String  bgText=uibgNumber.getText().trim();
        System.out.println(TAG+"已经成功获取曝光数字");
        System.out.println(TAG+"曝光数字为:"+bgText);
        int bgNumber=Integer.parseInt(bgText);



        //因为ListView控件被 行动 按钮挡住了，所以不能直接使用ListView控件的ScrollForward方法
        //可以使用UiDevice类来直接进行滚动
        //获取ListView控件的Bounds

        //如果滚动次数为-1，则滚动到底部
        if(scrollNumber==-1){
            System.out.println(TAG+"您传递的滚动数字是:"+scrollNumber);
            System.out.println(TAG+"您想要滚动到最底部");
            System.out.println(TAG+"现在开始滚动");
            scrollSeveralTimes(case1, bgNumber);
        }else{
            //否则滚动到指定的滚动次数
            scrollSeveralTimes(case1,scrollNumber);
        }


        System.out.println(TAG+"滚动功能验证成功，下面将暂停"+(sleepTime/1000)+"秒");
        System.out.println(TAG+"您可以在余下来的时间内检查界面上的设置和图片是否正确");

        case1.sleep(sleepTime);

    }

    //测试点赞功能
    public static void testDianzan(UiAutomatorTestCase case1) throws RemoteException,UiObjectNotFoundException{

        //获取界面上的点赞按钮
        UiObject  uiDianzan=new UiObject(new UiSelector().resourceId("com.ifeng.pollutionreport:id/exposure_item_support_count"));
        //验证点赞按钮存在
        BaseActionUtil.takeScreenShotAndAssert(uiDianzan,"ShowDianzanBtnFailure",TAG+"点赞按钮显示失败");
        //获取点赞之前的文本
        String dianZanStr=uiDianzan.getText();
        //打印点赞之前的文本
        System.out.println(TAG+"点赞之前:"+dianZanStr);

        System.out.println(TAG+"现在开始准备点赞");
        //点击 “点赞”按钮
        uiDianzan.click();

        System.out.println(TAG+"点击了点赞按钮");
        //获取点赞之后的 字符串
        String dianZanStr1=uiDianzan.getText();

        //打印点赞之后的文本
        System.out.println(TAG+"点赞之后:"+dianZanStr1);

        //确定点赞之后比 点赞之前多了一个赞
        //case1.assertTrue(Integer.parseInt(dianZanStr1) - Integer.parseInt(dianZanStr) == 1);
        BaseActionUtil.assertThenTakeScreenshot(Integer.parseInt(dianZanStr1) - Integer.parseInt(dianZanStr) == 1,"DianZanFailureInMainScreen",
                TAG+"主界面点赞失败");

        System.out.println(TAG+"确认点赞之后点赞次数加1成功");
        System.out.println(TAG+"等待3秒，确认您能看清点赞过程");
        //等待3秒，让测试人员能够看清点赞过程
        case1.sleep(3000);

        System.out.println(TAG+"再次点击点赞按钮，取消上一次的点赞");
        //再次点击“点赞” 按钮，取消点赞过程
        uiDianzan.click();

        System.out.println(TAG+"点赞按钮点击成功");
        //获取取消点赞之后的文本
        dianZanStr1=uiDianzan.getText();

        System.out.println(TAG+"再次点击点赞按钮后的点赞次数为："+dianZanStr1);
        //确认取消点赞之后的 文本和未点赞之前的文本相同
       // case1.assertTrue(dianZanStr1.equals(dianZanStr));
        BaseActionUtil.assertThenTakeScreenshot(dianZanStr1.equals(dianZanStr),"AgainDianzanFailure",
                TAG+"再次点赞后没有取消点赞");

        System.out.println(TAG+"验证再次点击会取消点赞成功");

        System.out.println(TAG+"点赞 和 取消点赞 功能正常...");
    }

    //测试 “点赞” 详情页面
    public  static  void scrollDianzanDetail(UiAutomatorTestCase  case1,String pinglunText) throws RemoteException,UiObjectNotFoundException{
        //点击 “点赞” 详情汉堡按钮
        UiObject uiDetail=new UiObject(new UiSelector().resourceId("com.ifeng.pollutionreport:id/btn_detail_menu"));
        //确认点赞详情 汉堡按钮存在
        BaseActionUtil.takeScreenShotAndAssert(uiDetail,"DianzanDetailBtnFailure",TAG+"点赞详情按钮不存在");
        System.out.println(TAG+"准备点击点赞详情按钮");
        //等待打开新窗口
        uiDetail.clickAndWaitForNewWindow(3000);
        System.out.println(TAG+"点赞详情按钮点击完毕，进入新页面");


        //点击评论按钮
        UiObject  uiPinglun=new UiObject(new UiSelector().resourceId("com.ifeng.pollutionreport:id/txt_detail_comment"));
        uiPinglun.waitForExists(5000);
        //避免未加载时的零值出现
        case1.sleep(3000);

        String pinglunStatus=uiPinglun.getText();
        System.out.println(TAG+"获取评论前的评论次数");

        String beforePinglun=pinglunStatus.substring(pinglunStatus.indexOf("(")+1,pinglunStatus.indexOf(")"));
        System.out.println(TAG+"评论之前的评论次数为："+beforePinglun);



        System.out.println(TAG+"准备点击评论按钮");
        //等待评论页面弹出
        uiPinglun.clickAndWaitForNewWindow(3000);


        //获取到评论 编辑框  并准备发表评论
        UiObject  pinglunEdit=new UiObject(new UiSelector().text("请发表评论信息"));
        //确认评论编辑 存在
        BaseActionUtil.takeScreenShotAndAssert(pinglunEdit,"PingLunEditNotExist",TAG+"评论编辑文本框不存在");

        System.out.println(TAG+"成功进入评论编辑页面");

        System.out.println(TAG+"开始设置评论信息");
        //设置要发表的评论信息
        pinglunEdit.setText(Utf7ImeHelper.e(pinglunText));

        System.out.println(TAG+"小米等设备可能因为定制而缺少系统文件导致异常，可忽略");
        System.out.println(TAG+"评论信息设置成功");

        System.out.println(TAG+"准备发送评论信息");
        //获取到 “发送”  按钮并发送评论
        UiObject  sendBtn=new UiObject(new UiSelector().text("发送"));
        //发送 评论后页面重新被加载
        System.out.println(TAG+"还未点击发送按钮");
        sendBtn.clickAndWaitForNewWindow();

        //等待3s以等待评论加载，避免评论太快界面没有刷新的现象发生
        case1.sleep(5000);

        System.out.println(TAG + "已点击发送按钮");
        System.out.println(TAG + "获取评论之后的评论信息条数");

        uiPinglun.waitForExists(5000);
        //避免未加载时的零值出现
        case1.sleep(3000);

        //获取评论过后的评论状态栏
        pinglunStatus=uiPinglun.getText();
        String afterPinglun=pinglunStatus.substring(pinglunStatus.indexOf("(")+1,pinglunStatus.indexOf(")"));
        System.out.println(TAG+"评论之后的评论信息条数为:"+afterPinglun);

        //确认评论过后多了一条 评论
        BaseActionUtil.assertThenTakeScreenshot(Integer.parseInt(afterPinglun)-Integer.parseInt(beforePinglun)==1,"PinglunFailure",
                TAG+"评论失败，评论后评论数没有增加");
        System.out.println(TAG+"确认评论后评论多了一条成功");
        //打印评论 成功状态
        System.out.println(TAG+"评论功能验证成功...");
    }


    //测试在点赞详情页面点支持
    public  static void  testDianzanInDetail(UiAutomatorTestCase  case1) throws RemoteException,UiObjectNotFoundException{
        //获取点赞详情页面的 支持按钮
        UiObject  uiZhichi=new UiObject(new UiSelector().resourceId("com.ifeng.pollutionreport:id/txt_detail_support"));
        //验证支持按钮存在
        BaseActionUtil.takeScreenShotAndAssert(uiZhichi,"ZhiChiBtnNotExist",TAG+"支持按钮不存在，case失败");
        //支持之前的字符串
        String  beforeZhichiStr=uiZhichi.getText();
        System.out.println(TAG+"准备获取支持之前的支持数");
        //获取支持之前的  支持数
        beforeZhichiStr=beforeZhichiStr.substring(beforeZhichiStr.indexOf("(")+1,beforeZhichiStr.indexOf(")"));
        System.out.println(TAG+"支持之前的支持数为:"+beforeZhichiStr);
        //点击支持
        uiZhichi.click();

        System.out.println(TAG+"按下了支持按钮");
        //获取支持之后支持按钮 上的字符串
        String afterZhichiStr=uiZhichi.getText();
        System.out.println(TAG+"准备获取点击支持后的支持字符串");
        //获取支持之后的 支持数
        afterZhichiStr=afterZhichiStr.substring(afterZhichiStr.indexOf("(")+1,afterZhichiStr.indexOf(")"));
        System.out.println(TAG+"点击支持之后的支持数为:"+afterZhichiStr);
        //确认支持后比支持前的支持数多1
//        case1.assertTrue(Integer.parseInt(afterZhichiStr)-Integer.parseInt(beforeZhichiStr)==1);
        BaseActionUtil.assertThenTakeScreenshot(Integer.parseInt(afterZhichiStr)-Integer.parseInt(beforeZhichiStr)==1,
                "ZhichiClickFailure",TAG+"点击支持后支持失败，支持按钮上的支持数未增加");
        System.out.println(TAG+"验证点击支持之后支持数增加1成功");
        //打印点赞后的具体信息
        System.out.println(TAG+"点赞详情页面点赞正确");


        //返回到环保行动派 主页
        UiObject  uiCancel=new UiObject(new UiSelector().text("环保行动派"));
        System.out.println(TAG+"准备点击取消按钮回到主页");
        //等待主页面出现
        uiCancel.clickAndWaitForNewWindow();

        System.out.println(TAG+"点击了取消按钮，准备回到主页");
        //获取 曝光台页面的点赞 文本视图
        UiObject  uiBgtDianzan=new UiObject(new UiSelector().resourceId("com.ifeng.pollutionreport:id/exposure_item_support_count"));
        //确认曝光台页面的 点赞文本视图存在
        BaseActionUtil.takeScreenShotAndAssert(uiBgtDianzan,"BackToBgtDianzanBtnFailure",TAG+"曝光台点赞按钮不存在，返回主页失败");
        System.out.println(TAG+"返回主页成功");
        //获取曝光台页面的 点赞数
        String afterZhichiStr1=uiBgtDianzan.getText();
        System.out.println(TAG+"曝光台页面的支持数为："+afterZhichiStr1);

        //确认详情页面和曝光台页面的 支持数相同
//        case1.assertTrue(afterZhichiStr1.equals(afterZhichiStr));
        BaseActionUtil.assertThenTakeScreenshot(afterZhichiStr1.equals(afterZhichiStr),
                "MainScreenAndZhichiPageNotConsistentFailure",TAG+"主界面和点赞详情页面的支持数不同");
        System.out.println(TAG+"确认点赞详情页面 和 曝光台页面的支持数相同 成功");

        //打印点赞正确信息
        System.out.println(TAG+"支持之后主页面的点赞信息正确");


        System.out.println(TAG+"准备点击曝光台页面的 取消点赞按钮");
       //取消上一次的支持，还原现场
        uiBgtDianzan.click();

        System.out.println(TAG+"点击取消点赞按钮成功");

        System.out.println(TAG+"获取取消点赞后的点赞数");
        //取消点赞后 ，验证 曝光台页面的 点赞信息  正确
        String afterZhichiStr2=uiBgtDianzan.getText();
        System.out.println(TAG+"取消点赞后的点赞数为:"+afterZhichiStr2);
        //确认 取消点赞后 点赞次数少1
//        case1.assertTrue(Integer.parseInt(afterZhichiStr1)-Integer.parseInt(afterZhichiStr2)==1);
        BaseActionUtil.assertThenTakeScreenshot(Integer.parseInt(afterZhichiStr1)-Integer.parseInt(afterZhichiStr2)==1,
                "MainScreenCancelDianzanFailure",TAG+"主页面取消点赞失败");
        System.out.println(TAG+"确认取消点赞后主页面点赞数减少成功");
        //打印 取消点赞成功信息
        System.out.println(TAG+"在主页面取消点赞成功");

        //进入 点赞详情页面，进一步验证点赞信息 是否正确

        System.out.println(TAG+"准备进入点赞详情页面进行检查");
        //点击 “点赞” 详情汉堡按钮
        UiObject uiDetail=new UiObject(new UiSelector().resourceId("com.ifeng.pollutionreport:id/btn_detail_menu"));
        //等待打开新窗口
        System.out.println(TAG+"准备点击点赞详情按钮");
        uiDetail.clickAndWaitForNewWindow();
        //获取 点赞详情页面 中支持按钮上的文本
        BaseActionUtil.takeScreenShotAndAssert(uiZhichi,"GoToZhiChiBtnFailure",TAG+"返回点赞详情页面失败");
        afterZhichiStr=uiZhichi.getText();
        System.out.println(TAG+"准备获取支持按钮上的点赞数");
        //获取支持按钮上的点赞次数
        afterZhichiStr=afterZhichiStr.substring(afterZhichiStr.indexOf("(")+1,afterZhichiStr.indexOf(")"));
        System.out.println(TAG+"支持按钮上的点赞数为："+afterZhichiStr);
        //确认主页的 支持 按钮和  详情页面的支持按钮  拥有相同的点赞次数
//        case1.assertTrue(afterZhichiStr.equals(afterZhichiStr2));
        BaseActionUtil.assertThenTakeScreenshot(afterZhichiStr.equals(afterZhichiStr2),"HomePageAndDetailPageNotConsistentAfterCancelDianzanFailure",
                TAG+"主页面取消点赞后，点赞详情页面的点赞数和 主页面的点赞数不相同");
        System.out.println(TAG+"点赞详情页面的点赞数和 主页面的点赞数相同 验证成功");
        //打印点赞信息正确信息
        System.out.println(TAG+"点赞详情页面点赞信息正确");

    }

    //使 曝光台 页面向下滚动指定的次数
    private static  void scrollSeveralTimes(UiAutomatorTestCase case1,int times) throws RemoteException,UiObjectNotFoundException{
        UiScrollable  jbContentList=new UiScrollable(new UiSelector().resourceId("android:id/list"));
        //验证滚动列表确实存在
        BaseActionUtil.takeScreenShotAndAssert(jbContentList,"ScrollListDoesNotExist",TAG+"滚动列表不存在");
        //获取滚动列表的范围
        Rect  rectLst =  jbContentList.getBounds();

        //获取  行动 按钮的Bounds
        UiObject  uiActionButton=new UiObject(new UiSelector().resourceId("com.ifeng.pollutionreport:id/main_layout_report_b"));
        Rect  rectBtn=uiActionButton.getBounds();


        //设置滑动的起点和终点
        int startX=rectBtn.centerX();
        int startY=rectLst.bottom-rectBtn.height()-50;
        int endX=startX;
        int endY=startY-800;
        System.out.println(TAG+"开始滚动过程");
        for(int i=0;i<times;i++){
            System.out.println(TAG+"开始第"+(i+1)+"次滚动");
            //滑动设备
            case1.getUiDevice().swipe(startX, startY, endX, endY, 5);
            System.out.println(TAG+"结束第"+(i+1)+"次滚动");
        }
        System.out.println(TAG+"结束滚动过程");
    }
}
