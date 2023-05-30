<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Course" %>
<%@ page import="com.chengxusheji.domain.ClassInfo" %>
<%@ page import="com.chengxusheji.domain.Teacher" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�ClassInfo��Ϣ
    List<ClassInfo> classInfoList = (List<ClassInfo>)request.getAttribute("classInfoList");
    //��ȡ���е�Teacher��Ϣ
    List<Teacher> teacherList = (List<Teacher>)request.getAttribute("teacherList");
    Course course = (Course)request.getAttribute("course");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸�ʵ��γ�</TITLE>
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
    var courseNo = document.getElementById("course.courseNo").value;
    if(courseNo=="") {
        alert('������γ̱��!');
        return false;
    }
    var courseName = document.getElementById("course.courseName").value;
    if(courseName=="") {
        alert('������γ�����!');
        return false;
    }
    var courseDesc = document.getElementById("course.courseDesc").value;
    if(courseDesc=="") {
        alert('������γ�����!');
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
    <TD align="left" vAlign=top ><s:form action="Course/Course_ModifyCourse.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>�γ̱��:</td>
    <td width=70%><input id="course.courseNo" name="course.courseNo" type="text" value="<%=course.getCourseNo() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>�����༶:</td>
    <td width=70%>
      <select name="course.classObj.classNo">
      <%
        for(ClassInfo classInfo:classInfoList) {
          String selected = "";
          if(classInfo.getClassNo().equals(course.getClassObj().getClassNo()))
            selected = "selected";
      %>
          <option value='<%=classInfo.getClassNo() %>' <%=selected %>><%=classInfo.getClassName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>�γ�����:</td>
    <td width=70%><input id="course.courseName" name="course.courseName" type="text" size="20" value='<%=course.getCourseName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�γ���ѧʱ:</td>
    <td width=70%><input id="course.courseHours" name="course.courseHours" type="text" size="8" value='<%=course.getCourseHours() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�γ�ѧ��:</td>
    <td width=70%><input id="course.courseScore" name="course.courseScore" type="text" size="8" value='<%=course.getCourseScore() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�Ͽ���ʦ:</td>
    <td width=70%>
      <select name="course.teacherObj.teacherNo">
      <%
        for(Teacher teacher:teacherList) {
          String selected = "";
          if(teacher.getTeacherNo().equals(course.getTeacherObj().getTeacherNo()))
            selected = "selected";
      %>
          <option value='<%=teacher.getTeacherNo() %>' <%=selected %>><%=teacher.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>�γ�����:</td>
    <td width=70%><textarea id="course.courseDesc" name="course.courseDesc" rows=5 cols=50><%=course.getCourseDesc() %></textarea></td>
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
