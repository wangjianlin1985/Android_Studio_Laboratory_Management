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
    //��ȡ���е�Course��Ϣ
    List<Course> courseList = (List<Course>)request.getAttribute("courseList");
    //��ȡ���е�WeekInfo��Ϣ
    List<WeekInfo> weekInfoList = (List<WeekInfo>)request.getAttribute("weekInfoList");
    //��ȡ���е�LabInfo��Ϣ
    List<LabInfo> labInfoList = (List<LabInfo>)request.getAttribute("labInfoList");
    CourseTest courseTest = (CourseTest)request.getAttribute("courseTest");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸Ŀγ�ʵ��</TITLE>
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
    var testName = document.getElementById("courseTest.testName").value;
    if(testName=="") {
        alert('������ʵ������!');
        return false;
    }
    var labTime = document.getElementById("courseTest.labTime").value;
    if(labTime=="") {
        alert('������ʵ��ʱ��!');
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
    <td width=30%>ʵ��id:</td>
    <td width=70%><input id="courseTest.testId" name="courseTest.testId" type="text" value="<%=courseTest.getTestId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>�����γ�:</td>
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
    <td width=30%>ʵ������:</td>
    <td width=70%><input id="courseTest.testName" name="courseTest.testName" type="text" size="30" value='<%=courseTest.getTestName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�Ͽ���:</td>
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
    <td width=30%>ʵ��ʱ��:</td>
    <td width=70%><input id="courseTest.labTime" name="courseTest.labTime" type="text" size="20" value='<%=courseTest.getLabTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�Ͽ�ʵ����:</td>
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
    <td width=30%>ʵ�鱸ע:</td>
    <td width=70%><textarea id="courseTest.testMemo" name="courseTest.testMemo" rows=5 cols=50><%=courseTest.getTestMemo() %></textarea></td>
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
