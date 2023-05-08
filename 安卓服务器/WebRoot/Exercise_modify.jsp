<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Exercise" %>
<%@ page import="com.chengxusheji.domain.Chapter" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�Chapter��Ϣ
    List<Chapter> chapterList = (List<Chapter>)request.getAttribute("chapterList");
    Exercise exercise = (Exercise)request.getAttribute("exercise");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸�ϰ����Ϣ</TITLE>
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
    var title = document.getElementById("exercise.title").value;
    if(title=="") {
        alert('������ϰ������!');
        return false;
    }
    var content = document.getElementById("exercise.content").value;
    if(content=="") {
        alert('��������ϰ����!');
        return false;
    }
    var addTime = document.getElementById("exercise.addTime").value;
    if(addTime=="") {
        alert('���������ʱ��!');
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
    <TD align="left" vAlign=top ><s:form action="Exercise/Exercise_ModifyExercise.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>��¼���:</td>
    <td width=70%><input id="exercise.id" name="exercise.id" type="text" value="<%=exercise.getId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>ϰ������:</td>
    <td width=70%><input id="exercise.title" name="exercise.title" type="text" size="20" value='<%=exercise.getTitle() %>'/></td>
  </tr>

  <tr>
    <td width=30%>������:</td>
    <td width=70%>
      <select name="exercise.chapterId.id">
      <%
        for(Chapter chapter:chapterList) {
          String selected = "";
          if(chapter.getId() == exercise.getChapterId().getId())
            selected = "selected";
      %>
          <option value='<%=chapter.getId() %>' <%=selected %>><%=chapter.getTitle() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��ϰ����:</td>
    <td width=70%><textarea id="exercise.content" name="exercise.content" rows=5 cols=50><%=exercise.getContent() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>����ʱ��:</td>
    <td width=70%><input id="exercise.addTime" name="exercise.addTime" type="text" size="20" value='<%=exercise.getAddTime() %>'/></td>
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
