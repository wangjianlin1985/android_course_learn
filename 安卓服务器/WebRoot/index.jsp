<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%> <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>基于安卓精品课程学习系统-首页</title>
<link href="<%=basePath %>css/index.css" rel="stylesheet" type="text/css" />
 </head>
<body>
<div id="container">
	<div id="banner"><img src="<%=basePath %>images/logo.gif" /></div>
	<div id="globallink">
		<ul>
			<li><a href="<%=basePath %>index.jsp">首页</a></li>
			<li><a href="<%=basePath %>Student/Student_FrontQueryStudent.action" target="OfficeMain">学生信息</a></li> 
			<li><a href="<%=basePath %>Teacher/Teacher_FrontQueryTeacher.action" target="OfficeMain">教师信息</a></li> 
			<li><a href="<%=basePath %>CourseInfo/CourseInfo_FrontQueryCourseInfo.action" target="OfficeMain">课程信息</a></li> 
			<li><a href="<%=basePath %>Kejian/Kejian_FrontQueryKejian.action" target="OfficeMain">课件信息</a></li> 
			<li><a href="<%=basePath %>Chapter/Chapter_FrontQueryChapter.action" target="OfficeMain">章信息</a></li> 
			<li><a href="<%=basePath %>Video/Video_FrontQueryVideo.action" target="OfficeMain">视频信息</a></li> 
			<li><a href="<%=basePath %>Exercise/Exercise_FrontQueryExercise.action" target="OfficeMain">习题信息</a></li> 
			<li><a href="<%=basePath %>Question/Question_FrontQueryQuestion.action" target="OfficeMain">在线问答</a></li> 
			<li><a href="<%=basePath %>HomeworkTask/HomeworkTask_FrontQueryHomeworkTask.action" target="OfficeMain">作业任务</a></li> 
			<li><a href="<%=basePath %>HomeworkUpload/HomeworkUpload_FrontQueryHomeworkUpload.action" target="OfficeMain">上传的作业</a></li> 
		</ul>
		<br />
	</div> 
	<div id="main">
	 <iframe id="frame1" src="<%=basePath %>desk.jsp" name="OfficeMain" width="100%" height="100%" scrolling="yes" marginwidth=0 marginheight=0 frameborder=0 vspace=0 hspace=0 >
	 </iframe>
	</div>
	<div id="footer">
		<p>双鱼林设计 QQ:287307421或254540457 &copy;版权所有 <a href="http://www.shuangyulin.com" target="_blank">双鱼林设计网</a>&nbsp;&nbsp;<a href="<%=basePath%>login/login_view.action"><font color=red>后台登陆</font></a></p>
	</div>
</div>
</body>
</html>
