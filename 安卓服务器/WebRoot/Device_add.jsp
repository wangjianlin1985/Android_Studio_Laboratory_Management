<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.LabInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的LabInfo信息
    List<LabInfo> labInfoList = (List<LabInfo>)request.getAttribute("labInfoList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加实验设备</TITLE> 
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
    var deviceName = document.getElementById("device.deviceName").value;
    if(deviceName=="") {
        alert('请输入设备名称!');
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
    <s:form action="Device/Device_AddDevice.action" method="post" id="deviceAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>设备名称:</td>
    <td width=70%><input id="device.deviceName" name="device.deviceName" type="text" size="40" /></td>
  </tr>

  <tr>
    <td width=30%>所属实验室:</td>
    <td width=70%>
      <select name="device.labObj.labId">
      <%
        for(LabInfo labInfo:labInfoList) {
      %>
          <option value='<%=labInfo.getLabId() %>'><%=labInfo.getLabName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>设备图片:</td>
    <td width=70%><input id="devicePhotoFile" name="devicePhotoFile" type="file" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>设备价格:</td>
    <td width=70%><input id="device.devicePrice" name="device.devicePrice" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>设备数量:</td>
    <td width=70%><input id="device.deviceCount" name="device.deviceCount" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>设备描述:</td>
    <td width=70%><textarea id="device.deviceDesc" name="device.deviceDesc" rows="5" cols="50"></textarea></td>
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
