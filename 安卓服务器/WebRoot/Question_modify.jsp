<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Question" %>
<%@ page import="com.chengxusheji.domain.Teacher" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�Teacher��Ϣ
    List<Teacher> teacherList = (List<Teacher>)request.getAttribute("teacherList");
    Question question = (Question)request.getAttribute("question");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸������ʴ�</TITLE>
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
    var questioner = document.getElementById("question.questioner").value;
    if(questioner=="") {
        alert('������������!');
        return false;
    }
    var content = document.getElementById("question.content").value;
    if(content=="") {
        alert('��������������!');
        return false;
    }
    var reply = document.getElementById("question.reply").value;
    if(reply=="") {
        alert('������ظ�����!');
        return false;
    }
    var addTime = document.getElementById("question.addTime").value;
    if(addTime=="") {
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
    <TD align="left" vAlign=top ><s:form action="Question/Question_ModifyQuestion.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>��¼���:</td>
    <td width=70%><input id="question.id" name="question.id" type="text" value="<%=question.getId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>���ʵ���ʦ:</td>
    <td width=70%>
      <select name="question.teacherId.id">
      <%
        for(Teacher teacher:teacherList) {
          String selected = "";
          if(teacher.getId() == question.getTeacherId().getId())
            selected = "selected";
      %>
          <option value='<%=teacher.getId() %>' <%=selected %>><%=teacher.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>������:</td>
    <td width=70%><input id="question.questioner" name="question.questioner" type="text" size="20" value='<%=question.getQuestioner() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><textarea id="question.content" name="question.content" rows=5 cols=50><%=question.getContent() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>�ظ�����:</td>
    <td width=70%><textarea id="question.reply" name="question.reply" rows=5 cols=50><%=question.getReply() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>����ʱ��:</td>
    <td width=70%><input id="question.addTime" name="question.addTime" type="text" size="20" value='<%=question.getAddTime() %>'/></td>
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
