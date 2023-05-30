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

	/*ͼƬ���ļ��ֶ�devicePhoto��������*/
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
    /*�������Ҫ��ѯ������: �豸����*/
    private String deviceName;
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
    public String getDeviceName() {
        return this.deviceName;
    }

    /*�������Ҫ��ѯ������: ����ʵ����*/
    private LabInfo labObj;
    public void setLabObj(LabInfo labObj) {
        this.labObj = labObj;
    }
    public LabInfo getLabObj() {
        return this.labObj;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
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

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource LabInfoDAO labInfoDAO;
    @Resource DeviceDAO deviceDAO;

    /*��������Device����*/
    private Device device;
    public void setDevice(Device device) {
        this.device = device;
    }
    public Device getDevice() {
        return this.device;
    }

    /*��ת�����Device��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�LabInfo��Ϣ*/
        List<LabInfo> labInfoList = labInfoDAO.QueryAllLabInfoInfo();
        ctx.put("labInfoList", labInfoList);
        return "add_view";
    }

    /*���Device��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddDevice() {
        ActionContext ctx = ActionContext.getContext();
        try {
            LabInfo labObj = labInfoDAO.GetLabInfoByLabId(device.getLabObj().getLabId());
            device.setLabObj(labObj);
            /*�����豸ͼƬ�ϴ�*/
            String devicePhotoPath = "upload/noimage.jpg"; 
       	 	if(devicePhotoFile != null)
       	 		devicePhotoPath = photoUpload(devicePhotoFile,devicePhotoFileContentType);
       	 	device.setDevicePhoto(devicePhotoPath);
            deviceDAO.AddDevice(device);
            ctx.put("message",  java.net.URLEncoder.encode("Device��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Device���ʧ��!"));
            return "error";
        }
    }

    /*��ѯDevice��Ϣ*/
    public String QueryDevice() {
        if(currentPage == 0) currentPage = 1;
        if(deviceName == null) deviceName = "";
        List<Device> deviceList = deviceDAO.QueryDeviceInfo(deviceName, labObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        deviceDAO.CalculateTotalPageAndRecordNumber(deviceName, labObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = deviceDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryDeviceOutputToExcel() { 
        if(deviceName == null) deviceName = "";
        List<Device> deviceList = deviceDAO.QueryDeviceInfo(deviceName,labObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Device��Ϣ��¼"; 
        String[] headers = { "�豸id","�豸����","����ʵ����","�豸ͼƬ","�豸�۸�","�豸����"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Device.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
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
    /*ǰ̨��ѯDevice��Ϣ*/
    public String FrontQueryDevice() {
        if(currentPage == 0) currentPage = 1;
        if(deviceName == null) deviceName = "";
        List<Device> deviceList = deviceDAO.QueryDeviceInfo(deviceName, labObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        deviceDAO.CalculateTotalPageAndRecordNumber(deviceName, labObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = deviceDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�Device��Ϣ*/
    public String ModifyDeviceQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������deviceId��ȡDevice����*/
        Device device = deviceDAO.GetDeviceByDeviceId(deviceId);

        List<LabInfo> labInfoList = labInfoDAO.QueryAllLabInfoInfo();
        ctx.put("labInfoList", labInfoList);
        ctx.put("device",  device);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Device��Ϣ*/
    public String FrontShowDeviceQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������deviceId��ȡDevice����*/
        Device device = deviceDAO.GetDeviceByDeviceId(deviceId);

        List<LabInfo> labInfoList = labInfoDAO.QueryAllLabInfoInfo();
        ctx.put("labInfoList", labInfoList);
        ctx.put("device",  device);
        return "front_show_view";
    }

    /*�����޸�Device��Ϣ*/
    public String ModifyDevice() {
        ActionContext ctx = ActionContext.getContext();
        try {
            LabInfo labObj = labInfoDAO.GetLabInfoByLabId(device.getLabObj().getLabId());
            device.setLabObj(labObj);
            /*�����豸ͼƬ�ϴ�*/
            if(devicePhotoFile != null) {
            	String devicePhotoPath = photoUpload(devicePhotoFile,devicePhotoFileContentType);
            	device.setDevicePhoto(devicePhotoPath);
            }
            deviceDAO.UpdateDevice(device);
            ctx.put("message",  java.net.URLEncoder.encode("Device��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Device��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Device��Ϣ*/
    public String DeleteDevice() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            deviceDAO.DeleteDevice(deviceId);
            ctx.put("message",  java.net.URLEncoder.encode("Deviceɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Deviceɾ��ʧ��!"));
            return "error";
        }
    }

}
