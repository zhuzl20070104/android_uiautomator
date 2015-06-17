package com.ifeng.pollutionreport.object;



/**
 * Created by Vincent on 2015/5/29.
 */

public class JbContent {
    private String wuranType;   //污染类型
    private String wuranDesc;   //污染描述
    private int [] zhaopianIndex; //需要添加的照片索引
    private WuRanDetail  wuRanDetail;  //污染详细信息


    public  JbContent( String wuranType,String wuranDesc,int []  zhaopianIndex,WuRanDetail  wuRanDetail){
        this.wuranType=wuranType;
        this.wuranDesc=wuranDesc;
        this.zhaopianIndex=zhaopianIndex;
        this.wuRanDetail=wuRanDetail;
    }

    public String getWuranType() {
        return wuranType;
    }

    public void setWuranType(String wuranType) {
        this.wuranType = wuranType;
    }

    public int [] getZhaopianIndex() {
        return zhaopianIndex;
    }

    public void setZhaopianIndex(int[] zhaopianIndex) {
        this.zhaopianIndex = zhaopianIndex;
    }

    public String getWuranDesc() {
        return wuranDesc;
    }

    public void setWuranDesc(String wuranDesc) {
        this.wuranDesc = wuranDesc;
    }

    public WuRanDetail getWuRanDetail() {
        return wuRanDetail;
    }

    public void setWuRanDetail(WuRanDetail wuRanDetail) {
        this.wuRanDetail = wuRanDetail;
    }
}
