<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Student" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    Student student = (Student)request.getAttribute("student");

%>
<HTML><HEAD><TITLE>�鿴ѧ����Ϣ</TITLE>
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
    <td width=30%>ѧ��:</td>
    <td width=70%><%=student.getStudentNumber() %></td>
  </tr>

  <tr>
    <td width=30%>��½����:</td>
    <td width=70%><%=student.getPassword() %></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><%=student.getName() %></td>
  </tr>

  <tr>
    <td width=30%>�Ա�:</td>
    <td width=70%><%=student.getSex() %></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
        <% java.text.DateFormat birthdaySDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=birthdaySDF.format(student.getBirthday()) %></td>
  </tr>

  <tr>
    <td width=30%>������ò:</td>
    <td width=70%><%=student.getZzmm() %></td>
  </tr>

  <tr>
    <td width=30%>���ڰ༶:</td>
    <td width=70%><%=student.getClassName() %></td>
  </tr>

  <tr>
    <td width=30%>��ϵ�绰:</td>
    <td width=70%><%=student.getTelephone() %></td>
  </tr>

  <tr>
    <td width=30%>������Ƭ:</td>
    <td width=70%><img src="<%=basePath %><%=student.getPhoto() %>" width="200px" border="0px"/></td>
  </tr>
  <tr>
    <td width=30%>��ͥ��ַ:</td>
    <td width=70%><%=student.getAddress() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="����" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
