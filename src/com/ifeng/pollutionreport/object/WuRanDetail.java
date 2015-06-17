package com.ifeng.pollutionreport.object;

/**
 * Created by Vincent on 2015/5/29.
 * 污染详细类
 */
public class WuRanDetail {
    private String wuranDanwei;  //污染单位
    private String paisheAddr; //拍摄地点
    private String jbPeople;  //举报人
    private String phoneNumber; //电话号码

    public WuRanDetail(String wuranDanwei,String paisheAddr,String jbPeople,String phoneNumber){
        this.wuranDanwei=wuranDanwei;
        this.paisheAddr=paisheAddr;
        this.jbPeople=jbPeople;
        this.phoneNumber=phoneNumber;
    }
    public String getPaisheAddr() {
        return paisheAddr;
    }

    public void setPaisheAddr(String paisheAddr) {
        this.paisheAddr = paisheAddr;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getJbPeople() {
        return jbPeople;
    }

    public void setJbPeople(String jbPeople) {
        this.jbPeople = jbPeople;
    }

    public String getWuranDanwei() {
        return wuranDanwei;
    }

    public void setWuranDanwei(String wuranDanwei) {
        this.wuranDanwei = wuranDanwei;
    }
}
