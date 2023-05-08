<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.HomeworkTask" %>
<%@ page import="com.chengxusheji.domain.Student" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�HomeworkTask��Ϣ
    List<HomeworkTask> homeworkTaskList = (List<HomeworkTask>)request.getAttribute("homeworkTaskList");
    //��ȡ���е�Student��Ϣ
    List<Student> studentList = (List<Student>)request.getAttribute("studentList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>����ϴ�����ҵ</TITLE> 
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
/*��֤��*/
function checkForm() {
    var uploadTime = document.getElementById("homeworkUpload.uploadTime").value;
    if(uploadTime=="") {
        alert('�������ύʱ��!');
        return false;
    }
    var pigaiTime = document.getElementById("homeworkUpload.pigaiTime").value;
    if(pigaiTime=="") {
        alert('����������ʱ��!');
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
    <TD align="left" vAlign=top >
    <s:form action="HomeworkUpload/HomeworkUpload_AddHomeworkUpload.action" method="post" id="homeworkUploadAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>��ҵ����:</td>
    <td width=70%>
      <select name="homeworkUpload.homeworkTaskObj.homeworkId">
      <%
        for(HomeworkTask homeworkTask:homeworkTaskList) {
      %>
          <option value='<%=homeworkTask.getHomeworkId() %>'><%=homeworkTask.getTitle() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>�ύ��ѧ��:</td>
    <td width=70%>
      <select name="homeworkUpload.studentObj.studentNumber">
      <%
        for(Student student:studentList) {
      %>
          <option value='<%=student.getStudentNumber() %>'><%=student.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��ҵ�ļ�:</td>
    <td width=70%><input id="homeworkFileFile" name="homeworkFileFile" type="file" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>�ύʱ��:</td>
    <td width=70%><input id="homeworkUpload.uploadTime" name="homeworkUpload.uploadTime" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>���Ľ���ļ�:</td>
    <td width=70%><input id="resultFileFile" name="resultFileFile" type="file" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>����ʱ��:</td>
    <td width=70%><input id="homeworkUpload.pigaiTime" name="homeworkUpload.pigaiTime" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>�Ƿ�����:</td>
    <td width=70%><input id="homeworkUpload.pigaiFlag" name="homeworkUpload.pigaiFlag" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="homeworkUpload.pingyu" name="homeworkUpload.pingyu" type="text" size="80" /></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
