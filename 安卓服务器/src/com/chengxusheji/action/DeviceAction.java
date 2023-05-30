package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.DeviceDAO;
import com.chengxusheji.domain.Device;
import com.chengxusheji.dao.LabInfoDAO;
import com.chengxusheji.domain.LabInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class DeviceAction extends BaseAction {

	/*图片或文件字段devicePhoto参数接收*/
	private File devicePhotoFile;
	private String devicePhotoFileFileName;
	private String devicePhotoFileContentType;
	public File getDevicePhotoFile() {
		return devicePhotoFile;
	}
	public void setDevicePhotoFile(File devicePhotoFile) {
		this.devicePhotoFile = devicePhotoFile;
	}
	public String getDevicePhotoFileFileName() {
		return devicePhotoFileFileName;
	}
	public void setDevicePhotoFileFileName(String devicePhotoFileFileName) {
		this.devicePhotoFileFileName = devicePhotoFileFileName;
	}
	public String getDevicePhotoFileContentType() {
		return devicePhotoFileContentType;
	}
	public void setDevicePhotoFileContentType(String devicePhotoFileContentType) {
		this.devicePhotoFileContentType = devicePhotoFileContentType;
	}
    /*界面层需要查询的属性: 设备名称*/
    private String deviceName;
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
    public String getDeviceName() {
        return this.deviceName;
    }

    /*界面层需要查询的属性: 所属实验室*/
    private LabInfo labObj;
    public void setLabObj(LabInfo labObj) {
        this.labObj = labObj;
    }
    public LabInfo getLabObj() {
        return this.labObj;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int deviceId;
    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }
    public int getDeviceId() {
        return deviceId;
    }

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource LabInfoDAO labInfoDAO;
    @Resource DeviceDAO deviceDAO;

    /*待操作的Device对象*/
    private Device device;
    public void setDevice(Device device) {
        this.device = device;
    }
    public Device getDevice() {
        return this.device;
    }

    /*跳转到添加Device视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的LabInfo信息*/
        List<LabInfo> labInfoList = labInfoDAO.QueryAllLabInfoInfo();
        ctx.put("labInfoList", labInfoList);
        return "add_view";
    }

    /*添加Device信息*/
    @SuppressWarnings("deprecation")
    public String AddDevice() {
        ActionContext ctx = ActionContext.getContext();
        try {
            LabInfo labObj = labInfoDAO.GetLabInfoByLabId(device.getLabObj().getLabId());
            device.setLabObj(labObj);
            /*处理设备图片上传*/
            String devicePhotoPath = "upload/noimage.jpg"; 
       	 	if(devicePhotoFile != null)
       	 		devicePhotoPath = photoUpload(devicePhotoFile,devicePhotoFileContentType);
       	 	device.setDevicePhoto(devicePhotoPath);
            deviceDAO.AddDevice(device);
            ctx.put("message",  java.net.URLEncoder.encode("Device添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Device添加失败!"));
            return "error";
        }
    }

    /*查询Device信息*/
    public String QueryDevice() {
        if(currentPage == 0) currentPage = 1;
        if(deviceName == null) deviceName = "";
        List<Device> deviceList = deviceDAO.QueryDeviceInfo(deviceName, labObj, currentPage);
        /*计算总的页数和总的记录数*/
        deviceDAO.CalculateTotalPageAndRecordNumber(deviceName, labObj);
        /*获取到总的页码数目*/
        totalPage = deviceDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = deviceDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("deviceList",  deviceList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("deviceName", deviceName);
        ctx.put("labObj", labObj);
        List<LabInfo> labInfoList = labInfoDAO.QueryAllLabInfoInfo();
        ctx.put("labInfoList", labInfoList);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryDeviceOutputToExcel() { 
        if(deviceName == null) deviceName = "";
        List<Device> deviceList = deviceDAO.QueryDeviceInfo(deviceName,labObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Device信息记录"; 
        String[] headers = { "设备id","设备名称","所属实验室","设备图片","设备价格","设备数量"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<deviceList.size();i++) {
        	Device device = deviceList.get(i); 
        	dataset.add(new String[]{device.getDeviceId() + "",device.getDeviceName(),device.getLabObj().getLabName(),
device.getDevicePhoto(),device.getDevicePrice() + "",device.getDeviceCount() + ""});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Device.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*前台查询Device信息*/
    public String FrontQueryDevice() {
        if(currentPage == 0) currentPage = 1;
        if(deviceName == null) deviceName = "";
        List<Device> deviceList = deviceDAO.QueryDeviceInfo(deviceName, labObj, currentPage);
        /*计算总的页数和总的记录数*/
        deviceDAO.CalculateTotalPageAndRecordNumber(deviceName, labObj);
        /*获取到总的页码数目*/
        totalPage = deviceDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = deviceDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("deviceList",  deviceList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("deviceName", deviceName);
        ctx.put("labObj", labObj);
        List<LabInfo> labInfoList = labInfoDAO.QueryAllLabInfoInfo();
        ctx.put("labInfoList", labInfoList);
        return "front_query_view";
    }

    /*查询要修改的Device信息*/
    public String ModifyDeviceQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键deviceId获取Device对象*/
        Device device = deviceDAO.GetDeviceByDeviceId(deviceId);

        List<LabInfo> labInfoList = labInfoDAO.QueryAllLabInfoInfo();
        ctx.put("labInfoList", labInfoList);
        ctx.put("device",  device);
        return "modify_view";
    }

    /*查询要修改的Device信息*/
    public String FrontShowDeviceQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键deviceId获取Device对象*/
        Device device = deviceDAO.GetDeviceByDeviceId(deviceId);

        List<LabInfo> labInfoList = labInfoDAO.QueryAllLabInfoInfo();
        ctx.put("labInfoList", labInfoList);
        ctx.put("device",  device);
        return "front_show_view";
    }

    /*更新修改Device信息*/
    public String ModifyDevice() {
        ActionContext ctx = ActionContext.getContext();
        try {
            LabInfo labObj = labInfoDAO.GetLabInfoByLabId(device.getLabObj().getLabId());
            device.setLabObj(labObj);
            /*处理设备图片上传*/
            if(devicePhotoFile != null) {
            	String devicePhotoPath = photoUpload(devicePhotoFile,devicePhotoFileContentType);
            	device.setDevicePhoto(devicePhotoPath);
            }
            deviceDAO.UpdateDevice(device);
            ctx.put("message",  java.net.URLEncoder.encode("Device信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Device信息更新失败!"));
            return "error";
       }
   }

    /*删除Device信息*/
    public String DeleteDevice() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            deviceDAO.DeleteDevice(deviceId);
            ctx.put("message",  java.net.URLEncoder.encode("Device删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Device删除失败!"));
            return "error";
        }
    }

}
