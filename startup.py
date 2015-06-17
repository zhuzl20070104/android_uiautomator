#!/usr/bin/python
#-*-coding:utf-8-*- 

import sys
import os
#reload(sys)
#sys.setdefaultencoding('utf-8')

##安装环保行动派apk到手机
def  run_app():
	grepcmd='adb shell pm list packages | grep "com.ifeng.pollutionreport"'
	greputf7='adb shell pm list  packages | grep "jp.jun_nama.test.utf7ime"'
	lines=os.popen(grepcmd).readlines()
	linesutf7=os.popen(greputf7).readlines()
	if  len(lines)==0  or  len(linesutf7)==0:
		os.popen("adb install -r ./relied_apks/pollutionreport.apk")
		os.popen("adb install -r ./relied_apks/Utf7Ime.apk")
		os.popen("adb shell  am start -n com.ifeng.pollutionreport/.activity.SplashActivity")
		print "\n\n\n"		
		print "友情提示:"
		print "\n"
		print "您是首次在此设备上运行环保行动派测试用例"
		print "请您根据系统提示完成如下操作："
		print "\n"		
		print '1.系统已帮助您打开环保行动派界面，您需要向右滑动界面并点击"立即行动"按钮进入主界面'
		print "进入主界面后，请按返回键关闭应用程序"
		print "\n"
		print "2.为了保证系统能够获得更好的中文支持，您需要到设置-->语言和输入法 界面"
		print "设置当前输入法为:UTF7 IME for UI Testing"
		print "不同手机的设置界面可能有所不同，请确保您正确的设置了输入法,否则系统将无法输入中文"
		print "设置成功后您可以在短信页面尝试输入，如果没有键盘出现，说明设置成功"
		print "\n"
		print "3.环保行动派测试版需要使用WLAN ifeng-staff进行登陆，否则将无法获取测试所需数据"
		print "您可以通过下拉菜单找到WLAN选项，到系统的设置界面进行设置，当设置好WLAN为ifeng-staff之后，"
		print "您需要打开系统浏览器进行登陆，登陆的用户名和密码为您的工号和密码"
		print "\n"		
		print "4.完成所有设置后，您可以再次运行本测试用例"
		print "\n\n"
		return
	else:
		run_uiautomator()
def  run_uiautomator():
	print "\n\n\n"
	print "获取Android 4.3对应的SDK的AndroidID："
	##获取4.3对应的Android ID行
	lines=os.popen("android list | grep 'android-18'").readlines()
	line=lines[0]
	line=line[line.index(":")+1:line.index("or")]
	line=line.strip(" ")
	print "Android ID获取成功，Android 4.3对应本机的Android ID为："+line

	
	print  "\n\n\n"
	print  "开始更新build.xml文件...."
	path=os.getcwd()
	createbuildxml="android create uitest-project -n  "+sys.argv[1]+"  -t "+line+" -p "+path
	lines=os.popen(createbuildxml).readlines()
	print  "build.xml文件更新完毕...."
	
	print  "\n\n\n"
	print  "运行ant clean,清除掉bin目录下的所有内容..."
	lines=os.popen("ant clean").readlines()
	print  "ant clean,运行完毕!!!"
	
	print  "\n\n\n"
	print  "运行ant build,重新构建bin目录下的所有内容..."
	lines=os.popen("ant build").readlines()
	print  "ant build,运行完毕!!!"

	print  "\n\n\n"
	wholeJarName=path+"/bin/"+sys.argv[1]+".jar"  ##生成的完整的jar文件的路径
	print  "将"+sys.argv[1]+"推送到Android手机上..."
	lines=os.popen("adb push  "+wholeJarName+"  data/local/tmp").readlines()
	print  "推送jar包完毕!!!"

	print  "\n\n\n"
	print  "开始删除Android手机图片目录下的所有图片文件"
	##列出该目录下的所有文件，并重定向到tmp.txt
	lines=os.popen("adb shell ls /data/local/tmp")
	for  line  in  lines:
		if line[-5:-2]=='png':
			cmdpngfile="adb shell rm  /data/local/tmp/"+line.strip()
			exeres=os.popen(cmdpngfile).readlines()		
	print  "安卓手机上的所有图片删除完毕!!!"

	print  "\n\n\n"
	casef=open("cases.txt","r")
	casemap={}
	for  line in casef:
		arr=line.split(":")
		casemap[arr[0].strip()]=arr[1].strip()
	runcases=[]

	if  sys.argv[2]=="":
		for key in  casemap:
			if  key!="":
				runcases.append(casemap[key])
	else:	
		for key in  casemap:
			if  key!=""  and key==sys.argv[2]:
				runcases.append(casemap[key])

	
	print  "开始执行测试用例..."
	runName=sys.argv[1]+".jar"
	for  i  in  range(0,len(runcases)):
		print "开始执行："+runcases[i]
		print "\n\n\n"
		reportFileName="report"+str(i)+".txt"
		runcasecmd="adb shell uiautomator runtest  "+runName+" -c  "+runcases[i]+"| tee "+reportFileName
		res=os.system(runcasecmd)	 		
	

	if os.path.exists("report.txt"):
		os.remove("report.txt")
	

	passcase=0
	failcase=0
	reportf=open("report.txt","w")
	for  i  in range(0,len(runcases)):
		reportFileName="report"+str(i)+".txt"
		lineok=os.popen("cat "+reportFileName+" | grep 'OK'").readlines()
		linefail=os.popen("cat "+reportFileName+"|grep 'Failures'").readlines()
		if  len(lineok)>0:
			passcase=passcase+1
			print "用例"+runcases[i]+"通过"
			reportf.write("用例"+runcases[i]+"通过\n")
		if  len(linefail)>0:
			failcase=failcase+1
			print "用例"+runcases[i]+"未通过"
			reportf.write("用例"+runcases[i]+"未通过\n")

	print  "总共通过的case数目为："+str(passcase)
	print  "总共未通过的case数目为:"+str(failcase)
	reportf.write("总共通过的case数目为："+str(passcase)+"\n")
	reportf.write("总共未通过的case数目为:"+str(failcase)+"\n")

	print  "\n\n\n"
	reportf.write("\n\n\n\n")
	reportf.write("用例执行详情如下:\n")
	
	reportf.write("\n\n\n\n")	


	for  i  in  range(0,len(runcases)):
		reportFileName="report"+str(i)+".txt"
		reportf.write("用例"+runcases[i]+"\n")
		lines=os.popen('cat '+reportFileName+' | grep "TAG"').readlines()
		for  line  in  lines:
			reportf.write(line)
		reportf.write("\n\n\n\n")


	
	##推送错误截图
	if os.path.exists("screenshots"):
		os.popen("rm -rf screenshots")
	

	print "开始将Android手机上的错误截图推送到工程目录下的screenshots目录"
	os.popen("adb  pull  /data/local/tmp  screenshots")

	for parent,dirnames,filenames in os.walk("screenshots"):
		for fname in filenames:
			if fname[-3:]!="png":
				os.popen("rm -rf screenshots/"+fname)
		for dirname in dirnames:
			if dirname!='screenshots':
				os.popen("rm -rf screenshots/"+dirname)
		

	print  "\n\n\n"
	print  "图片文件推送结束"
	print  "\n\n\n"
	
	##打印提示信息

	print  "您还可以通过查看工程目录下的report.txt文件了解case执行详情"
	print  "工程目录下的reportX.txt包含了case执行的详细信息"
	print  "工程目录下的runlog.log文件保存了case运行的错误信息"
	print  "关于该脚本使用的更多信息请查看工程目录下的ReadMe.txt文件"
	print  "所有测试用例执行完毕!!!"
def  main():
	if   len(sys.argv)< 3:
		print "请输入Jar包名称和运行的测试组名："
		print '类似：./startup.py  \"Runner1\"  \"滚动\"'
		return
	else:
		run_app()


if  __name__=="__main__":
	main()
