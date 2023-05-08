<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.Teacher" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Teacher信息
    List<Teacher> teacherList = (List<Teacher>)request.getAttribute("teacherList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加在线问答</TITLE> 
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
    var questioner = document.getElementById("question.questioner").value;
    if(questioner=="") {
        alert('请输入提问者!');
        return false;
    }
    var content = document.getElementById("question.content").value;
    if(content=="") {
        alert('请输入提问内容!');
        return false;
    }
    var reply = document.getElementById("question.reply").value;
    if(reply=="") {
        alert('请输入回复内容!');
        return false;
    }
    var addTime = document.getElementById("question.addTime").value;
    if(addTime=="") {
        alert('请输入提问时间!');
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
    <s:form action="Question/Question_AddQuestion.action" method="post" id="questionAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>提问的老师:</td>
    <td width=70%>
      <select name="question.teacherId.id">
      <%
        for(Teacher teacher:teacherList) {
      %>
          <option value='<%=teacher.getId() %>'><%=teacher.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>提问者:</td>
    <td width=70%><input id="question.questioner" name="question.questioner" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>提问内容:</td>
    <td width=70%><textarea id="question.content" name="question.content" rows="5" cols="50"></textarea></td>
  </tr>

  <tr>
    <td width=30%>回复内容:</td>
    <td width=70%><textarea id="question.reply" name="question.reply" rows="5" cols="50"></textarea></td>
  </tr>

  <tr>
    <td width=30%>提问时间:</td>
    <td width=70%><input id="question.addTime" name="question.addTime" type="text" size="20" /></td>
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
