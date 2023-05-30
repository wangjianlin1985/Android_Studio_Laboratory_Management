<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.CourseTest" %>
<%@ page import="com.chengxusheji.domain.Course" %>
<%@ page import="com.chengxusheji.domain.WeekInfo" %>
<%@ page import="com.chengxusheji.domain.LabInfo" %>
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

%>
<HTML><HEAD><TITLE>�鿴�γ�ʵ��</TITLE>
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
    <td width=30%>ʵ��id:</td>
    <td width=70%><%=courseTest.getTestId() %></td>
  </tr>

  <tr>
    <td width=30%>�����γ�:</td>
    <td width=70%>
      <%=courseTest.getCourseObj().getCourseName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>ʵ������:</td>
    <td width=70%><%=courseTest.getTestName() %></td>
  </tr>

  <tr>
    <td width=30%>�Ͽ���:</td>
    <td width=70%>
      <%=courseTest.getWeekObj().getWeekName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>ʵ��ʱ��:</td>
    <td width=70%><%=courseTest.getLabTime() %></td>
  </tr>

  <tr>
    <td width=30%>�Ͽ�ʵ����:</td>
    <td width=70%>
      <%=courseTest.getLabObj().getLabName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>ʵ�鱸ע:</td>
    <td width=70%><%=courseTest.getTestMemo() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="����" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
