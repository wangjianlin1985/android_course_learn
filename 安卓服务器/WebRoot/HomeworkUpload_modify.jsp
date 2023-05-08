<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.HomeworkUpload" %>
<%@ page import="com.chengxusheji.domain.HomeworkTask" %>
<%@ page import="com.chengxusheji.domain.Student" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的HomeworkTask信息
    List<HomeworkTask> homeworkTaskList = (List<HomeworkTask>)request.getAttribute("homeworkTaskList");
    //获取所有的Student信息
    List<Student> studentList = (List<Student>)request.getAttribute("studentList");
    HomeworkUpload homeworkUpload = (HomeworkUpload)request.getAttribute("homeworkUpload");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改上传的作业</TITLE>
<STYLE type=text/css>
BODY {
	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*验证表单*/
function checkForm() {
    var uploadTime = document.getElementById("homeworkUpload.uploadTime").value;
    if(uploadTime=="") {
        alert('请输入提交时间!');
        return false;
    }
    var pigaiTime = document.getElementById("homeworkUpload.pigaiTime").value;
    if(pigaiTime=="") {
        alert('请输入批改时间!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>
<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="HomeworkUpload/HomeworkUpload_ModifyHomeworkUpload.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>记录编号:</td>
    <td width=70%><input id="homeworkUpload.uploadId" name="homeworkUpload.uploadId" type="text" value="<%=homeworkUpload.getUploadId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>作业标题:</td>
    <td width=70%>
      <select name="homeworkUpload.homeworkTaskObj.homeworkId">
      <%
        for(HomeworkTask homeworkTask:homeworkTaskList) {
          String selected = "";
          if(homeworkTask.getHomeworkId() == homeworkUpload.getHomeworkTaskObj().getHomeworkId())
            selected = "selected";
      %>
          <option value='<%=homeworkTask.getHomeworkId() %>' <%=selected %>><%=homeworkTask.getTitle() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>提交的学生:</td>
    <td width=70%>
      <select name="homeworkUpload.studentObj.studentNumber">
      <%
        for(Student student:studentList) {
          String selected = "";
          if(student.getStudentNumber().equals(homeworkUpload.getStudentObj().getStudentNumber()))
            selected = "selected";
      %>
          <option value='<%=student.getStudentNumber() %>' <%=selected %>><%=student.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>作业文件:</td>
    <td width=70%><img src="<%=basePath %><%=homeworkUpload.getHomeworkFile() %>" width="200px" border="0px"/><br/>
    <input type=hidden name="homeworkUpload.homeworkFile" value="<%=homeworkUpload.getHomeworkFile() %>" />
    <input id="homeworkFileFile" name="homeworkFileFile" type="file" size="50" /></td>
  </tr>
  <tr>
    <td width=30%>提交时间:</td>
    <td width=70%><input id="homeworkUpload.uploadTime" name="homeworkUpload.uploadTime" type="text" size="20" value='<%=homeworkUpload.getUploadTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>批改结果文件:</td>
    <td width=70%><img src="<%=basePath %><%=homeworkUpload.getResultFile() %>" width="200px" border="0px"/><br/>
    <input type=hidden name="homeworkUpload.resultFile" value="<%=homeworkUpload.getResultFile() %>" />
    <input id="resultFileFile" name="resultFileFile" type="file" size="50" /></td>
  </tr>
  <tr>
    <td width=30%>批改时间:</td>
    <td width=70%><input id="homeworkUpload.pigaiTime" name="homeworkUpload.pigaiTime" type="text" size="20" value='<%=homeworkUpload.getPigaiTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>是否批改:</td>
    <td width=70%><input id="homeworkUpload.pigaiFlag" name="homeworkUpload.pigaiFlag" type="text" size="8" value='<%=homeworkUpload.getPigaiFlag() %>'/></td>
  </tr>

  <tr>
    <td width=30%>评语:</td>
    <td width=70%><input id="homeworkUpload.pingyu" name="homeworkUpload.pingyu" type="text" size="80" value='<%=homeworkUpload.getPingyu() %>'/></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
