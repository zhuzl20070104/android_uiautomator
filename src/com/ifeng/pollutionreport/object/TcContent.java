package com.ifeng.pollutionreport.object;

/**
 * Created by Vincent on 2015/5/29.
 */
public class TcContent {

    private String tucaoDesc; //吐槽描述
    private int []  photoIndexes;  //照片索引


    public TcContent(String tucaoDesc,int []  photoIndexes){
        this.tucaoDesc=tucaoDesc;
        this.photoIndexes=photoIndexes;
    }

    public String getTucaoDesc() {
        return tucaoDesc;
    }

    public void setTucaoDesc(String tucaoDesc) {
        this.tucaoDesc = tucaoDesc;
    }

    public int[] getPhotoIndexes() {
        return photoIndexes;
    }

    public void setPhotoIndexes(int[] photoIndexes) {
        this.photoIndexes = photoIndexes;
    }
}
