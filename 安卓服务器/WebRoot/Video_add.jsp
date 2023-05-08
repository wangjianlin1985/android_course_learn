<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.Chapter" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Chapter信息
    List<Chapter> chapterList = (List<Chapter>)request.getAttribute("chapterList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加视频信息</TITLE> 
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
    var title = document.getElementById("video.title").value;
    if(title=="") {
        alert('请输入视频资料标题!');
        return false;
    }
    var path = document.getElementById("video.path").value;
    if(path=="") {
        alert('请输入文件路径!');
        return false;
    }
    var addTime = document.getElementById("video.addTime").value;
    if(addTime=="") {
        alert('请输入添加时间!');
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
    <s:form action="Video/Video_AddVideo.action" method="post" id="videoAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>视频资料标题:</td>
    <td width=70%><input id="video.title" name="video.title" type="text" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>所属章:</td>
    <td width=70%>
      <select name="video.chapterId.id">
      <%
        for(Chapter chapter:chapterList) {
      %>
          <option value='<%=chapter.getId() %>'><%=chapter.getTitle() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>文件路径:</td>
    <td width=70%><input id="video.path" name="video.path" type="text" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>添加时间:</td>
    <td width=70%><input id="video.addTime" name="video.addTime" type="text" size="20" /></td>
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
