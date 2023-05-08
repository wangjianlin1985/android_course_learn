<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.HomeworkUpload" %>
<%@ page import="com.chengxusheji.domain.HomeworkTask" %>
<%@ page import="com.chengxusheji.domain.Student" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的HomeworkTask信息
    List<HomeworkTask> homeworkTaskList = (List<HomeworkTask>)request.getAttribute("homeworkTaskList");
    //获取所有的Student信息
    List<Student> studentList = (List<Student>)request.getAttribute("studentList");
    HomeworkUpload homeworkUpload = (HomeworkUpload)request.getAttribute("homeworkUpload");

%>
<HTML><HEAD><TITLE>查看上传的作业</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>记录编号:</td>
    <td width=70%><%=homeworkUpload.getUploadId() %></td>
  </tr>

  <tr>
    <td width=30%>作业标题:</td>
    <td width=70%>
      <%=homeworkUpload.getHomeworkTaskObj().getTitle() %>
    </td>
  </tr>

  <tr>
    <td width=30%>提交的学生:</td>
    <td width=70%>
      <%=homeworkUpload.getStudentObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>作业文件:</td>
    <td width=70%><img src="<%=basePath %><%=homeworkUpload.getHomeworkFile() %>" width="200px" border="0px"/></td>
  </tr>
  <tr>
    <td width=30%>提交时间:</td>
    <td width=70%><%=homeworkUpload.getUploadTime() %></td>
  </tr>

  <tr>
    <td width=30%>批改结果文件:</td>
    <td width=70%><img src="<%=basePath %><%=homeworkUpload.getResultFile() %>" width="200px" border="0px"/></td>
  </tr>
  <tr>
    <td width=30%>批改时间:</td>
    <td width=70%><%=homeworkUpload.getPigaiTime() %></td>
  </tr>

  <tr>
    <td width=30%>是否批改:</td>
    <td width=70%><%=homeworkUpload.getPigaiFlag() %></td>
  </tr>

  <tr>
    <td width=30%>评语:</td>
    <td width=70%><%=homeworkUpload.getPingyu() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
