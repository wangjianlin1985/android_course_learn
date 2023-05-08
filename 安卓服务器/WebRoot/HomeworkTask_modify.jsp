<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.HomeworkTask" %>
<%@ page import="com.chengxusheji.domain.Teacher" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�Teacher��Ϣ
    List<Teacher> teacherList = (List<Teacher>)request.getAttribute("teacherList");
    HomeworkTask homeworkTask = (HomeworkTask)request.getAttribute("homeworkTask");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸���ҵ����</TITLE>
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
    var title = document.getElementById("homeworkTask.title").value;
    if(title=="") {
        alert('��������ҵ����!');
        return false;
    }
    var content = document.getElementById("homeworkTask.content").value;
    if(content=="") {
        alert('��������ҵ����!');
        return false;
    }
    var addTime = document.getElementById("homeworkTask.addTime").value;
    if(addTime=="") {
        alert('�����뷢��ʱ��!');
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
    <TD align="left" vAlign=top ><s:form action="HomeworkTask/HomeworkTask_ModifyHomeworkTask.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>��¼���:</td>
    <td width=70%><input id="homeworkTask.homeworkId" name="homeworkTask.homeworkId" type="text" value="<%=homeworkTask.getHomeworkId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>��ʦ:</td>
    <td width=70%>
      <select name="homeworkTask.teacherObj.id">
      <%
        for(Teacher teacher:teacherList) {
          String selected = "";
          if(teacher.getId() == homeworkTask.getTeacherObj().getId())
            selected = "selected";
      %>
          <option value='<%=teacher.getId() %>' <%=selected %>><%=teacher.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��ҵ����:</td>
    <td width=70%><input id="homeworkTask.title" name="homeworkTask.title" type="text" size="50" value='<%=homeworkTask.getTitle() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ҵ����:</td>
    <td width=70%><textarea id="homeworkTask.content" name="homeworkTask.content" rows=5 cols=50><%=homeworkTask.getContent() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>����ʱ��:</td>
    <td width=70%><input id="homeworkTask.addTime" name="homeworkTask.addTime" type="text" size="20" value='<%=homeworkTask.getAddTime() %>'/></td>
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
