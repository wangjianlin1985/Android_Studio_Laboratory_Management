<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Device" %>
<%@ page import="com.chengxusheji.domain.LabInfo" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的LabInfo信息
    List<LabInfo> labInfoList = (List<LabInfo>)request.getAttribute("labInfoList");
    Device device = (Device)request.getAttribute("device");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改实验设备</TITLE>
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
    <TD align="left" vAlign=top ><s:form action="Device/Device_ModifyDevice.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>设备id:</td>
    <td width=70%><input id="device.deviceId" name="device.deviceId" type="text" value="<%=device.getDeviceId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>设备名称:</td>
    <td width=70%><input id="device.deviceName" name="device.deviceName" type="text" size="40" value='<%=device.getDeviceName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>所属实验室:</td>
    <td width=70%>
      <select name="device.labObj.labId">
      <%
        for(LabInfo labInfo:labInfoList) {
          String selected = "";
          if(labInfo.getLabId() == device.getLabObj().getLabId())
            selected = "selected";
      %>
          <option value='<%=labInfo.getLabId() %>' <%=selected %>><%=labInfo.getLabName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>设备图片:</td>
    <td width=70%><img src="<%=basePath %><%=device.getDevicePhoto() %>" width="200px" border="0px"/><br/>
    <input type=hidden name="device.devicePhoto" value="<%=device.getDevicePhoto() %>" />
    <input id="devicePhotoFile" name="devicePhotoFile" type="file" size="50" /></td>
  </tr>
  <tr>
    <td width=30%>设备价格:</td>
    <td width=70%><input id="device.devicePrice" name="device.devicePrice" type="text" size="8" value='<%=device.getDevicePrice() %>'/></td>
  </tr>

  <tr>
    <td width=30%>设备数量:</td>
    <td width=70%><input id="device.deviceCount" name="device.deviceCount" type="text" size="8" value='<%=device.getDeviceCount() %>'/></td>
  </tr>

  <tr>
    <td width=30%>设备描述:</td>
    <td width=70%><textarea id="device.deviceDesc" name="device.deviceDesc" rows=5 cols=50><%=device.getDeviceDesc() %></textarea></td>
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
