1、在maven项目查找当前项目的type(例如java)的文件个数

2、命令
mvn yuan:findtype -Dproject.directory=${basedir}（必填） -Dtype=properties（选填，默认是java）
