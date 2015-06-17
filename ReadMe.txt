本文件将帮助您更好的理解和执行环保行动派的测试用例。
Windows环境：
若要使用Powershell运行该测试用例，计算机需具备的条件，
1.Windows 7及以上系统，
2.Powershell已安装，
3.Android SDK和JDK已安装。


执行本测试用例所需的步骤，
1.启动Windows Powershell
2.改变目录到工程目录，如您的工程目录在D:\testPollutio，您可以使用下列命令来达到目的，
  D:
  cd  testPollution
3.在Powershell命令行中输入如下命令启动测试程序，
  .\startup.ps1  -jarName "Runner1"   -caseName "PM25" | Select-String "TAG"
  参数说明：
	a)jarName参数: 用于指定生成并推送到Android手机上运行的jar文件名称，您可以将这个名称修改成任何您想要的名字。
	b)caseName参数: 您要选择执行的case组名，您现在可以将需要运行的Case进行分组，您可以单独执行某一组case，您只需要在-caseName参数后面输入相应的组名就可以了。
	  如果您给定一个空组名，类似""，将默认执行所有case。
	  有关组和case的相应对应关系，您可以查看工程目录下的cases.txt文件。其中组和case名之间以:分隔（注意:为英文冒号），您可以将组名修改为任何您想要的组名，然后通过CaseName参数进行指定相应的组名。

4.程序运行过程中将会在Powershell界面显示运行过程，您也可以通过查看程序运行完毕后生成的report.txt文件查看用例运行的结果。
  工程目录下的runlog.log文件包含了详细的错误信息。

Linux环境：
  若要使用Python运行该测试用例，计算机需具备的条件，
  1.Android SDK和JDK已安装。
 
 执行本测试用例所需的步骤，
1.启动终端，
2.改变目录到工程目录，如您的工程目录在/home/build/work/testPollution，您可以使用下列命令来达到目的，
  cd  /home/build/work/testPollution
3.在终端中输入如下命令启动测试程序，
  ./startup.py  "Runner1"  ""
  参数说明：
	a)参数1：用于指定生成并推送到Android手机上运行的jar文件名称，您可以将这个名称修改成任何您想要的名字。
	b)参数2: 您要选择执行的case组名，您现在可以将需要运行的Case进行分组，您可以单独执行某一组case，您只需要在-caseName参数后面输入相应的组名就可以了。
	  如果您给定一个空组名，类似""，将默认执行所有case。
	  有关组和case的相应对应关系，您可以查看工程目录下的cases.txt文件。其中组和case名之间以:分隔（注意:为英文冒号），您可以将组名修改为任何您想要的组名，然后通过CaseName参数进行指定相应的组名。

4.程序运行过程中将会在终端界面显示运行过程，您也可以通过查看程序运行完毕后生成的report.txt文件查看用例运行的结果。
  工程目录下的runlog.log文件包含了详细的错误信息。
