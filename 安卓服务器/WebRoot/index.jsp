<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%> <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>181��׿android��У����ʽʵ���ҹ���-��ҳ</title>
<link href="<%=basePath %>css/index.css" rel="stylesheet" type="text/css" />
 </head>
<body>
<div id="container">
	<div id="banner"><img src="<%=basePath %>images/logo.gif" /></div>
	<div id="globallink">
		<ul>
			<li><a href="<%=basePath %>index.jsp">��ҳ</a></li>
			<li><a href="<%=basePath %>LabInfo/LabInfo_FrontQueryLabInfo.action" target="OfficeMain">ʵ����</a></li> 
			<li><a href="<%=basePath %>LatState/LatState_FrontQueryLatState.action" target="OfficeMain">ʵ����״̬</a></li> 
			<li><a href="<%=basePath %>ClassInfo/ClassInfo_FrontQueryClassInfo.action" target="OfficeMain">�༶</a></li> 
			<li><a href="<%=basePath %>Student/Student_FrontQueryStudent.action" target="OfficeMain">ѧ��</a></li> 
			<li><a href="<%=basePath %>Teacher/Teacher_FrontQueryTeacher.action" target="OfficeMain">��ʦ</a></li> 
			<li><a href="<%=basePath %>Course/Course_FrontQueryCourse.action" target="OfficeMain">ʵ��γ�</a></li> 
			<li><a href="<%=basePath %>CourseTest/CourseTest_FrontQueryCourseTest.action" target="OfficeMain">�γ�ʵ��</a></li> 
			<li><a href="<%=basePath %>WeekInfo/WeekInfo_FrontQueryWeekInfo.action" target="OfficeMain">����Ϣ</a></li> 
			<li><a href="<%=basePath %>Device/Device_FrontQueryDevice.action" target="OfficeMain">ʵ���豸</a></li> 
		</ul>
		<br />
	</div> 
	<div id="main">
	 <iframe id="frame1" src="<%=basePath %>desk.jsp" name="OfficeMain" width="100%" height="100%" scrolling="yes" marginwidth=0 marginheight=0 frameborder=0 vspace=0 hspace=0 >
	 </iframe>
	</div>
	<div id="footer">
		<p> <a href="<%=basePath%>login/login_view.action"><font color=red>��̨��½</font></a></p>
	</div>
</div>
</body>
</html>
