<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.CourseTest" %>
<%@ page import="com.chengxusheji.domain.Course" %>
<%@ page import="com.chengxusheji.domain.WeekInfo" %>
<%@ page import="com.chengxusheji.domain.LabInfo" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Course信息
    List<Course> courseList = (List<Course>)request.getAttribute("courseList");
    //获取所有的WeekInfo信息
    List<WeekInfo> weekInfoList = (List<WeekInfo>)request.getAttribute("weekInfoList");
    //获取所有的LabInfo信息
    List<LabInfo> labInfoList = (List<LabInfo>)request.getAttribute("labInfoList");
    CourseTest courseTest = (CourseTest)request.getAttribute("courseTest");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改课程实验</TITLE>
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
    var testName = document.getElementById("courseTest.testName").value;
    if(testName=="") {
        alert('请输入实验名称!');
        return false;
    }
    var labTime = document.getElementById("courseTest.labTime").value;
    if(labTime=="") {
        alert('请输入实验时间!');
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
    <TD align="left" vAlign=top ><s:form action="CourseTest/CourseTest_ModifyCourseTest.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>实验id:</td>
    <td width=70%><input id="courseTest.testId" name="courseTest.testId" type="text" value="<%=courseTest.getTestId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>所属课程:</td>
    <td width=70%>
      <select name="courseTest.courseObj.courseNo">
      <%
        for(Course course:courseList) {
          String selected = "";
          if(course.getCourseNo().equals(courseTest.getCourseObj().getCourseNo()))
            selected = "selected";
      %>
          <option value='<%=course.getCourseNo() %>' <%=selected %>><%=course.getCourseName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>实验名称:</td>
    <td width=70%><input id="courseTest.testName" name="courseTest.testName" type="text" size="30" value='<%=courseTest.getTestName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>上课周:</td>
    <td width=70%>
      <select name="courseTest.weekObj.weekId">
      <%
        for(WeekInfo weekInfo:weekInfoList) {
          String selected = "";
          if(weekInfo.getWeekId() == courseTest.getWeekObj().getWeekId())
            selected = "selected";
      %>
          <option value='<%=weekInfo.getWeekId() %>' <%=selected %>><%=weekInfo.getWeekName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>实验时间:</td>
    <td width=70%><input id="courseTest.labTime" name="courseTest.labTime" type="text" size="20" value='<%=courseTest.getLabTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>上课实验室:</td>
    <td width=70%>
      <select name="courseTest.labObj.labId">
      <%
        for(LabInfo labInfo:labInfoList) {
          String selected = "";
          if(labInfo.getLabId() == courseTest.getLabObj().getLabId())
            selected = "selected";
      %>
          <option value='<%=labInfo.getLabId() %>' <%=selected %>><%=labInfo.getLabName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>实验备注:</td>
    <td width=70%><textarea id="courseTest.testMemo" name="courseTest.testMemo" rows=5 cols=50><%=courseTest.getTestMemo() %></textarea></td>
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
