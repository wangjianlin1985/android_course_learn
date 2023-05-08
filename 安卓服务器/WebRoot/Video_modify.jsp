<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Video" %>
<%@ page import="com.chengxusheji.domain.Chapter" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�Chapter��Ϣ
    List<Chapter> chapterList = (List<Chapter>)request.getAttribute("chapterList");
    Video video = (Video)request.getAttribute("video");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸���Ƶ��Ϣ</TITLE>
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
    var title = document.getElementById("video.title").value;
    if(title=="") {
        alert('��������Ƶ���ϱ���!');
        return false;
    }
    var path = document.getElementById("video.path").value;
    if(path=="") {
        alert('�������ļ�·��!');
        return false;
    }
    var addTime = document.getElementById("video.addTime").value;
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
    <TD align="left" vAlign=top ><s:form action="Video/Video_ModifyVideo.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>��¼���:</td>
    <td width=70%><input id="video.id" name="video.id" type="text" value="<%=video.getId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>��Ƶ���ϱ���:</td>
    <td width=70%><input id="video.title" name="video.title" type="text" size="50" value='<%=video.getTitle() %>'/></td>
  </tr>

  <tr>
    <td width=30%>������:</td>
    <td width=70%>
      <select name="video.chapterId.id">
      <%
        for(Chapter chapter:chapterList) {
          String selected = "";
          if(chapter.getId() == video.getChapterId().getId())
            selected = "selected";
      %>
          <option value='<%=chapter.getId() %>' <%=selected %>><%=chapter.getTitle() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>�ļ�·��:</td>
    <td width=70%><input id="video.path" name="video.path" type="text" size="50" value='<%=video.getPath() %>'/></td>
  </tr>

  <tr>
    <td width=30%>���ʱ��:</td>
    <td width=70%><input id="video.addTime" name="video.addTime" type="text" size="20" value='<%=video.getAddTime() %>'/></td>
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
