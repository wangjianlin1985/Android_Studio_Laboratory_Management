<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Device" %>
<%@ page import="com.chengxusheji.domain.LabInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的LabInfo信息
    List<LabInfo> labInfoList = (List<LabInfo>)request.getAttribute("labInfoList");
    Device device = (Device)request.getAttribute("device");

%>
<HTML><HEAD><TITLE>查看实验设备</TITLE>
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
    <td width=30%>设备id:</td>
    <td width=70%><%=device.getDeviceId() %></td>
  </tr>

  <tr>
    <td width=30%>设备名称:</td>
    <td width=70%><%=device.getDeviceName() %></td>
  </tr>

  <tr>
    <td width=30%>所属实验室:</td>
    <td width=70%>
      <%=device.getLabObj().getLabName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>设备图片:</td>
    <td width=70%><img src="<%=basePath %><%=device.getDevicePhoto() %>" width="200px" border="0px"/></td>
  </tr>
  <tr>
    <td width=30%>设备价格:</td>
    <td width=70%><%=device.getDevicePrice() %></td>
  </tr>

  <tr>
    <td width=30%>设备数量:</td>
    <td width=70%><%=device.getDeviceCount() %></td>
  </tr>

  <tr>
    <td width=30%>设备描述:</td>
    <td width=70%><%=device.getDeviceDesc() %></td>
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
