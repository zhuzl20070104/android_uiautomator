package com.ifeng.pollutionreport.object;



/**
 * Created by Vincent on 2015/6/2.
 * PM2.5描述类
 */
public class PM25Desc {
    private String airCondiDesc;  //空气质量情况
    private String suggestion;    //建议及措施

    public String getAirCondiDesc() {
        return airCondiDesc;
    }

    public void setAirCondiDesc(String airCondiDesc) {
        this.airCondiDesc = airCondiDesc;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
}
