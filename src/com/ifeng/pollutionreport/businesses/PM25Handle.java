package com.ifeng.pollutionreport.businesses;


import android.graphics.Rect;
import android.os.RemoteException;
import com.android.uiautomator.core.*;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.ifeng.pollutionreport.object.PM25Desc;
import com.ifeng.pollutionreport.object.PM25Domain;
import com.ifeng.pollutionreport.util.BaseActionUtil;
import com.ifeng.pollutionreport.util.UiConst;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by Vincent on 2015/6/1.
 * 处理PM 2.5页面验证的类
 */
public class PM25Handle {
    //TAG值，用于在日志前面做标记
    private static final  String TAG="TAG:"+PM25Handle.class.getName().toString()+"    ";
    //存放PM2.5值与描述的 映射列表
    private static  Map<PM25Domain,PM25Desc>  pm25Map=new HashMap<PM25Domain, PM25Desc>();
    //PM2.5值的 划分域
    private static  int [][]  pm25DomainArr={{0,51},{51,101},{101,201},{201,1000}};
    //与划分域对应的描述
    private static  String [][]  pm25DescArr={{"空气质量令人满意,基本无空气污染","各类人群可正常活动"},
            {"空气质量可接受,但某些污染物可能对极少数异常敏感人群健康有较弱影响","极少数异常敏感人群应减少户外活动"},
            {"易感人群症状有轻度加剧,健康人群出现刺激症状","儿童、老年人及心脏病、呼吸系统疾病患者应减少长时间、高强度的户外锻炼"},
            {"加剧易感人群症状,可能对健康人群心脏、呼吸系统有影响","儿童、老年人和心脏病、肺病患者应停留在室内,停止户外运动,一般人群减少户外运动"}};

    //初始化 PM 2.5值与描述的映射
    private static  void  initPM25Desc(){
        //for循环初始化
        for(int i=0;i<pm25DomainArr.length;i++){
            //构造 PM2.5范围对象
            PM25Domain  domain=new PM25Domain();
            //设置范围的低值和高值
            domain.setLow(pm25DomainArr[i][0]);
            domain.setHigh(pm25DomainArr[i][1]);

            //构造 PM2.5描述对象
            PM25Desc  desc=new PM25Desc();
            //设置空气质量情况描述
            desc.setAirCondiDesc(pm25DescArr[i][0]);
            //设置建议及措施 描述
            desc.setSuggestion(pm25DescArr[i][1]);
            //将设置好的值与描述映射放入 Map中
            pm25Map.put(domain,desc);
        }
    }

    //检查主页面PM2.5值与描述是否匹配
    public static void testPM25(UiAutomatorTestCase case1,UiDevice  device) throws RemoteException,UiObjectNotFoundException{
        System.out.println(TAG+"准备初始化 PM2.5集合");
        //初始化PM2.5值与描述映射
        initPM25Desc();
        System.out.println(TAG+"PM2.5集合初始化完毕");

        //获取 PM2.5页面链接
        UiObject pm25Link=new UiObject(new UiSelector().text("今日pm2.5"));
        BaseActionUtil.takeScreenShotAndAssert(pm25Link,"GotoPM25Failure",TAG+"进入系统主界面失败");
        System.out.println(TAG+"已经进入系统主界面");
        //点击链接等待新窗口出现
        pm25Link.clickAndWaitForNewWindow(5000);
        System.out.println(TAG+"准备单击进入 PM2.5界面");
        //获取 “雾霾指数” 总View
        UiCollection uiContentView=new UiCollection(new UiSelector().description("雾霾指数"));
        //验证是否成功进入 PM2.5页面
        BaseActionUtil.takeScreenShotAndAssert(uiContentView,"GotoPM25DescFailure",TAG+"进入PM 2.5页面失败");
        System.out.println(TAG+"进入PM2.5 页面成功");
        //获取 PM2.5日期视图组件
        UiObject  uiDate=uiContentView.getChildByInstance(new UiSelector().className("android.view.View"), UiConst.DATE_IDX);
        //确定 日期视图组件出现
        BaseActionUtil.takeScreenShotAndAssert(uiDate,"DateComponentFailure",TAG+"日期视图组件没有加载出来");

        System.out.println(TAG+"已经成功获取日期视图组件");
        //获取 PM2.5组件
        UiObject  uiPM25=uiContentView.getChildByInstance(new UiSelector().className("android.view.View"), UiConst.PM25_IDX);
        System.out.println(TAG+"已经成功获取PM2.5视图组件");
        //获取空气质量描述组件
        UiObject  uiQualityDesc=uiContentView.getChildByInstance(new UiSelector().className("android.view.View"),UiConst.QUALITY_DESC_IDX);
        System.out.println(TAG+"已经成功获取空气质量视图组件");
        //获取建议组件
        UiObject  uiSuggestDesc=uiContentView.getChildByInstance(new UiSelector().className("android.view.View"),UiConst.SUGGEST_DESC_IDX);
        System.out.println(TAG+"已经成功获取建议视图组件");


        //获取日期字符串
        String dateStr=uiDate.getContentDescription();
        System.out.println(TAG+"当前日期值为："+dateStr);
        //获取PM2.5字符串
        String pm25Str=uiPM25.getContentDescription();
        System.out.println(TAG+"今日PM2.5值为："+pm25Str);
        //获取质量描述字符串
        String qualityStr=uiQualityDesc.getContentDescription();
        System.out.println(TAG+"空气质量描述为："+qualityStr);
        //获取建议描述字符串
        String suggestStr=uiSuggestDesc.getContentDescription();
        System.out.println(TAG+"建议为:"+suggestStr);
        System.out.println(TAG+"开始验证 PM2.5值与描述是否对应...");
        //验证今日 PM2.5值 和相应描述是否对应
        if(validatePM25Desc(pm25Str,qualityStr,suggestStr)){
            //对应，打印输出提示
            System.out.println(TAG+"PM2.5 值和描述匹配成功");
        }else{
            //不对应，打印相应提示
            System.out.println(TAG+"PM2.5 值不匹配描述");
        }

        System.out.println(TAG+"验证PM 2.5值 与描述是否匹配完毕...");

        //获取界面上PM2.5可滑动的视图
        UiObject  swipeObj=uiContentView.getChildByInstance(new UiSelector().className("android.view.View"),UiConst.SWIPE_VIEW_IDX);
        //获取可滑动区域
        Rect  rectSwipe=swipeObj.getBounds();
        int  startX=0;
        int  startY=rectSwipe.centerY();
        int  endX=startX+300;
        int  endY=startY;
        //向左滑动300个像素
        device.swipe(startX,startY,endX,endY,5);
        //获取滑动以后的日期值
        String afterSwipeDateStr=uiDate.getText();

        System.out.println(TAG+"开始验证滑动功能...");

        case1.assertTrue(TAG+"滑动后日期不再相同",!afterSwipeDateStr.equals(dateStr));
        //对比滑动后的日期与滑动前的日期字符串
        if(!afterSwipeDateStr.equals(dateStr)){
            //若不相同，打印成功信息
            System.out.println(TAG+"滑动后日期不再相同，滑动功能验证成功...");
        }else{
            //若相同，打印失败信息
            System.out.println(TAG+"滑动失败，仍停留在当前日期...");
        }

        //等待一定时间便于观察
        case1.sleep(15000);


    }

    /*
    **验证当日 PN2.5值与描述匹配的函数
    * @param  pm25Str: 今日的PM2.5值，以字符串形式表示
    * @param qualityStr:从界面获得的 空气质量描述字符串
    * @param suggestStr:从界面获得的 建议及措施字符串
     */
    private static boolean  validatePM25Desc(String pm25Str,String qualityStr,String suggestStr){
        //标志变量，标志界面与 期待的描述是否匹配
        boolean validRes=false;
        //字符串转整型
        int pm25Val=Integer.parseInt(pm25Str);
        //获取迭代器，准备迭代
        Iterator iter=pm25Map.entrySet().iterator();
        //迭代
        while(iter.hasNext()){
            Map.Entry  entry=(Map.Entry)iter.next();
            PM25Domain  domain=(PM25Domain)entry.getKey();
            PM25Desc  desc=(PM25Desc)entry.getValue();
            //判断 PM2.5值是否在当前域范围内，如果是，继续
            if(pm25Val>=domain.getLow() && pm25Val<domain.getHigh()){
                //判断描述是否与期待的描述匹配，如果匹配，继续
                if(qualityStr.equals(desc.getAirCondiDesc()) && suggestStr.equals(desc.getSuggestion())){
                    //打印实际和期待的空气质量描述值
                    System.out.println(TAG+"实际的空气质量:"+qualityStr);
                    System.out.println(TAG+"期待的空气质量:"+desc.getAirCondiDesc());
                    //打印实际和期待的 意见和建议值
                    System.out.println(TAG+"实际的建议值:"+suggestStr);
                    System.out.println(TAG+"期待的建议值:"+desc.getSuggestion());
                    //说明找到了，将标记值 设置为true
                    validRes=true;
                    //跳出当前迭代
                    break;
                }
            }
        }
        //返回标记值
        return validRes;
    }
}
