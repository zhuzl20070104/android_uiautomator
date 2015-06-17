package com.ifeng.pollutionreport.util;

import java.io.*;
import com.android.uiautomator.core.*;
/**
 * Created by Fredric,Zhu on 2015/5/29.
 */
public class AppLaunchUtil {


    public static void startPhoenixApp()throws UiObjectNotFoundException{
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec("am start -n com.ifeng.pollutionreport/.activity.SplashActivity");
            InputStream errorStream = process.getErrorStream();
            InputStream inputStream = process.getInputStream();
            InputStreamReader errorReader = new InputStreamReader(errorStream);
            BufferedReader errorBuf = new BufferedReader(errorReader);
            String line;
            while ( (line = errorBuf.readLine()) != null) {
                System.err.println(line);
            }
            errorReader.close();
            InputStreamReader isReader = new InputStreamReader(inputStream);
            BufferedReader isBuf = new BufferedReader(isReader);
            while ( (line = isBuf.readLine()) != null) {
                System.out.println(line);
            }
            isReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void publicStopApp(){

        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("am force-stop com.ifeng.pollutionreport");
            InputStream errorStream = process.getErrorStream();
            InputStream inputStream = process.getInputStream();
            InputStreamReader errorReader = new InputStreamReader(errorStream);
            BufferedReader errorBuf = new BufferedReader(errorReader);
            String line;
            while ( (line = errorBuf.readLine()) != null) {
                System.err.println(line);
            }
            errorReader.close();
            InputStreamReader isReader = new InputStreamReader(inputStream);
            BufferedReader isBuf = new BufferedReader(isReader);
            while ( (line = isBuf.readLine()) != null) {
                System.out.println(line);
            }
            isReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
