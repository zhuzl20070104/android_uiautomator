package com.ifeng.pollutionreport.object;


/**
 * Created by Vincent on 2015/6/2.
 * PM25 域的范围
 * 比如在 0-50,描述应该对应 ：空气质量令人满意，基本无空气污染
 */
public class PM25Domain {
    private int low;
    private int high;

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }
}
