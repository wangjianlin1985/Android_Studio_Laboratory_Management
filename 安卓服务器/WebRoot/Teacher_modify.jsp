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
<HTML><HEAD><TITLE>修改老师</TITLE>
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
    var teacherNo = document.getElementById("teacher.teacherNo").value;
    if(teacherNo=="") {
        alert('请输入教师编号!');
        return false;
    }
    var password = document.getElementById("teacher.password").value;
    if(password=="") {
        alert('请输入登录密码!');
        return false;
    }
    var name = document.getElementById("teacher.name").value;
    if(name=="") {
        alert('请输入姓名!');
        return false;
    }
    var sex = document.getElementById("teacher.sex").value;
    if(sex=="") {
        alert('请输入性别!');
        return false;
    }
    var telephone = document.getElementById("teacher.telephone").value;
    if(telephone=="") {
        alert('请输入联系电话!');
        return false;
    }
    var teacherDesc = document.getElementById("teacher.teacherDesc").value;
    if(teacherDesc=="") {
        alert('请输入教师描述!');
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
    <td width=30%>教师编号:</td>
    <td width=70%><input id="teacher.teacherNo" name="teacher.teacherNo" type="text" value="<%=teacher.getTeacherNo() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>登录密码:</td>
    <td width=70%><input id="teacher.password" name="teacher.password" type="text" size="20" value='<%=teacher.getPassword() %>'/></td>
  </tr>

  <tr>
    <td width=30%>姓名:</td>
    <td width=70%><input id="teacher.name" name="teacher.name" type="text" size="20" value='<%=teacher.getName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>性别:</td>
    <td width=70%><input id="teacher.sex" name="teacher.sex" type="text" size="4" value='<%=teacher.getSex() %>'/></td>
  </tr>

  <tr>
    <td width=30%>入职日期:</td>
    <% DateFormat inDateSDF = new SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><input type="text" readonly  id="teacher.inDate"  name="teacher.inDate" onclick="setDay(this);" value='<%=inDateSDF.format(teacher.getInDate()) %>'/></td>
  </tr>

  <tr>
    <td width=30%>教师照片:</td>
    <td width=70%><img src="<%=basePath %><%=teacher.getTeacherPhoto() %>" width="200px" border="0px"/><br/>
    <input type=hidden name="teacher.teacherPhoto" value="<%=teacher.getTeacherPhoto() %>" />
    <input id="teacherPhotoFile" name="teacherPhotoFile" type="file" size="50" /></td>
  </tr>
  <tr>
    <td width=30%>联系电话:</td>
    <td width=70%><input id="teacher.telephone" name="teacher.telephone" type="text" size="20" value='<%=teacher.getTelephone() %>'/></td>
  </tr>

  <tr>
    <td width=30%>教师描述:</td>
    <td width=70%><textarea id="teacher.teacherDesc" name="teacher.teacherDesc" rows=5 cols=50><%=teacher.getTeacherDesc() %></textarea></td>
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
