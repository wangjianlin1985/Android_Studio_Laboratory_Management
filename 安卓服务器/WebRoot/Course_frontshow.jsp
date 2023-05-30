<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Course" %>
<%@ page import="com.chengxusheji.domain.ClassInfo" %>
<%@ page import="com.chengxusheji.domain.Teacher" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的ClassInfo信息
    List<ClassInfo> classInfoList = (List<ClassInfo>)request.getAttribute("classInfoList");
    //获取所有的Teacher信息
    List<Teacher> teacherList = (List<Teacher>)request.getAttribute("teacherList");
    Course course = (Course)request.getAttribute("course");

%>
<HTML><HEAD><TITLE>查看实验课程</TITLE>
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
    <td width=30%>课程编号:</td>
    <td width=70%><%=course.getCourseNo() %></td>
  </tr>

  <tr>
    <td width=30%>所属班级:</td>
    <td width=70%>
      <%=course.getClassObj().getClassName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>课程名称:</td>
    <td width=70%><%=course.getCourseName() %></td>
  </tr>

  <tr>
    <td width=30%>课程总学时:</td>
    <td width=70%><%=course.getCourseHours() %></td>
  </tr>

  <tr>
    <td width=30%>课程学分:</td>
    <td width=70%><%=course.getCourseScore() %></td>
  </tr>

  <tr>
    <td width=30%>上课老师:</td>
    <td width=70%>
      <%=course.getTeacherObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>课程描述:</td>
    <td width=70%><%=course.getCourseDesc() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
