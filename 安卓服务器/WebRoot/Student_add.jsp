<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>���ѧ����Ϣ</TITLE> 
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
    var studentNumber = document.getElementById("student.studentNumber").value;
    if(studentNumber=="") {
        alert('������ѧ��!');
        return false;
    }
    var password = document.getElementById("student.password").value;
    if(password=="") {
        alert('�������½����!');
        return false;
    }
    var name = document.getElementById("student.name").value;
    if(name=="") {
        alert('����������!');
        return false;
    }
    var sex = document.getElementById("student.sex").value;
    if(sex=="") {
        alert('�������Ա�!');
        return false;
    }
    var zzmm = document.getElementById("student.zzmm").value;
    if(zzmm=="") {
        alert('������������ò!');
        return false;
    }
    var className = document.getElementById("student.className").value;
    if(className=="") {
        alert('���������ڰ༶!');
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
    <s:form action="Student/Student_AddStudent.action" method="post" id="studentAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>ѧ��:</td>
    <td width=70%><input id="student.studentNumber" name="student.studentNumber" type="text" /></td>
  </tr>

  <tr>
    <td width=30%>��½����:</td>
    <td width=70%><input id="student.password" name="student.password" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="student.name" name="student.name" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>�Ա�:</td>
    <td width=70%><input id="student.sex" name="student.sex" type="text" size="4" /></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><input type="text" readonly id="student.birthday"  name="student.birthday" onclick="setDay(this);"/></td>
  </tr>

  <tr>
    <td width=30%>������ò:</td>
    <td width=70%><input id="student.zzmm" name="student.zzmm" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>���ڰ༶:</td>
    <td width=70%><input id="student.className" name="student.className" type="text" size="30" /></td>
  </tr>

  <tr>
    <td width=30%>��ϵ�绰:</td>
    <td width=70%><input id="student.telephone" name="student.telephone" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>������Ƭ:</td>
    <td width=70%><input id="photoFile" name="photoFile" type="file" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>��ͥ��ַ:</td>
    <td width=70%><input id="student.address" name="student.address" type="text" size="80" /></td>
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
