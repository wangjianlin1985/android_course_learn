<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Teacher" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    Teacher teacher = (Teacher)request.getAttribute("teacher");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸Ľ�ʦ��Ϣ</TITLE>
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
    var name = document.getElementById("teacher.name").value;
    if(name=="") {
        alert('����������!');
        return false;
    }
    var position = document.getElementById("teacher.position").value;
    if(position=="") {
        alert('������ְ��!');
        return false;
    }
    var password = document.getElementById("teacher.password").value;
    if(password=="") {
        alert('����������!');
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
    <TD align="left" vAlign=top ><s:form action="Teacher/Teacher_ModifyTeacher.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>��¼���:</td>
    <td width=70%><input id="teacher.id" name="teacher.id" type="text" value="<%=teacher.getId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="teacher.name" name="teacher.name" type="text" size="20" value='<%=teacher.getName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>ְ��:</td>
    <td width=70%><input id="teacher.position" name="teacher.position" type="text" size="12" value='<%=teacher.getPosition() %>'/></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="teacher.password" name="teacher.password" type="text" size="20" value='<%=teacher.getPassword() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ʦ���:</td>
    <td width=70%><textarea id="teacher.introduction" name="teacher.introduction" rows=5 cols=50><%=teacher.getIntroduction() %></textarea></td>
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
