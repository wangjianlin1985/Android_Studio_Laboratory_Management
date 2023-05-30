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
    //获取所有的ClassInfo信息
    List<ClassInfo> classInfoList = (List<ClassInfo>)request.getAttribute("classInfoList");
    //获取所有的Teacher信息
    List<Teacher> teacherList = (List<Teacher>)request.getAttribute("teacherList");
    Course course = (Course)request.getAttribute("course");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改实验课程</TITLE>
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
    var courseNo = document.getElementById("course.courseNo").value;
    if(courseNo=="") {
        alert('请输入课程编号!');
        return false;
    }
    var courseName = document.getElementById("course.courseName").value;
    if(courseName=="") {
        alert('请输入课程名称!');
        return false;
    }
    var courseDesc = document.getElementById("course.courseDesc").value;
    if(courseDesc=="") {
        alert('请输入课程描述!');
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
    <td width=30%>课程编号:</td>
    <td width=70%><input id="course.courseNo" name="course.courseNo" type="text" value="<%=course.getCourseNo() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>所属班级:</td>
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
    <td width=30%>课程名称:</td>
    <td width=70%><input id="course.courseName" name="course.courseName" type="text" size="20" value='<%=course.getCourseName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>课程总学时:</td>
    <td width=70%><input id="course.courseHours" name="course.courseHours" type="text" size="8" value='<%=course.getCourseHours() %>'/></td>
  </tr>

  <tr>
    <td width=30%>课程学分:</td>
    <td width=70%><input id="course.courseScore" name="course.courseScore" type="text" size="8" value='<%=course.getCourseScore() %>'/></td>
  </tr>

  <tr>
    <td width=30%>上课老师:</td>
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
    <td width=30%>课程描述:</td>
    <td width=70%><textarea id="course.courseDesc" name="course.courseDesc" rows=5 cols=50><%=course.getCourseDesc() %></textarea></td>
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
