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
import com.chengxusheji.dao.LabInfoDAO;
import com.chengxusheji.domain.LabInfo;
import com.chengxusheji.dao.LatStateDAO;
import com.chengxusheji.domain.LatState;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class LabInfoAction extends BaseAction {

	/*ͼƬ���ļ��ֶ�labPhoto��������*/
	private File labPhotoFile;
	private String labPhotoFileFileName;
	private String labPhotoFileContentType;
	public File getLabPhotoFile() {
		return labPhotoFile;
	}
	public void setLabPhotoFile(File labPhotoFile) {
		this.labPhotoFile = labPhotoFile;
	}
	public String getLabPhotoFileFileName() {
		return labPhotoFileFileName;
	}
	public void setLabPhotoFileFileName(String labPhotoFileFileName) {
		this.labPhotoFileFileName = labPhotoFileFileName;
	}
	public String getLabPhotoFileContentType() {
		return labPhotoFileContentType;
	}
	public void setLabPhotoFileContentType(String labPhotoFileContentType) {
		this.labPhotoFileContentType = labPhotoFileContentType;
	}
    /*�������Ҫ��ѯ������: ʵ��������*/
    private String labName;
    public void setLabName(String labName) {
        this.labName = labName;
    }
    public String getLabName() {
        return this.labName;
    }

    /*�������Ҫ��ѯ������: ʵ����״̬*/
    private LatState labStateObj;
    public void setLabStateObj(LatState labStateObj) {
        this.labStateObj = labStateObj;
    }
    public LatState getLabStateObj() {
        return this.labStateObj;
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

    private int labId;
    public void setLabId(int labId) {
        this.labId = labId;
    }
    public int getLabId() {
        return labId;
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
    @Resource LatStateDAO latStateDAO;
    @Resource LabInfoDAO labInfoDAO;

    /*��������LabInfo����*/
    private LabInfo labInfo;
    public void setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
    }
    public LabInfo getLabInfo() {
        return this.labInfo;
    }

    /*��ת�����LabInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�LatState��Ϣ*/
        List<LatState> latStateList = latStateDAO.QueryAllLatStateInfo();
        ctx.put("latStateList", latStateList);
        return "add_view";
    }

    /*���LabInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddLabInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            LatState labStateObj = latStateDAO.GetLatStateByStateId(labInfo.getLabStateObj().getStateId());
            labInfo.setLabStateObj(labStateObj);
            /*����ʵ����ͼƬ�ϴ�*/
            String labPhotoPath = "upload/noimage.jpg"; 
       	 	if(labPhotoFile != null)
       	 		labPhotoPath = photoUpload(labPhotoFile,labPhotoFileContentType);
       	 	labInfo.setLabPhoto(labPhotoPath);
            labInfoDAO.AddLabInfo(labInfo);
            ctx.put("message",  java.net.URLEncoder.encode("LabInfo��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LabInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯLabInfo��Ϣ*/
    public String QueryLabInfo() {
        if(currentPage == 0) currentPage = 1;
        if(labName == null) labName = "";
        List<LabInfo> labInfoList = labInfoDAO.QueryLabInfoInfo(labName, labStateObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        labInfoDAO.CalculateTotalPageAndRecordNumber(labName, labStateObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = labInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = labInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("labInfoList",  labInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("labName", labName);
        ctx.put("labStateObj", labStateObj);
        List<LatState> latStateList = latStateDAO.QueryAllLatStateInfo();
        ctx.put("latStateList", latStateList);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryLabInfoOutputToExcel() { 
        if(labName == null) labName = "";
        List<LabInfo> labInfoList = labInfoDAO.QueryLabInfoInfo(labName,labStateObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "LabInfo��Ϣ��¼"; 
        String[] headers = { "ʵ����id","ʵ��������","ʵ����ͼƬ","ʵ�������","��������","ʵ���ҵ�ַ","ʵ����״̬"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<labInfoList.size();i++) {
        	LabInfo labInfo = labInfoList.get(i); 
        	dataset.add(new String[]{labInfo.getLabId() + "",labInfo.getLabName(),labInfo.getLabPhoto(),labInfo.getLabArea() + "",labInfo.getPersonNum() + "",labInfo.getLabAddress(),labInfo.getLabStateObj().getStateName()
});
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
			response.setHeader("Content-disposition","attachment; filename="+"LabInfo.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯLabInfo��Ϣ*/
    public String FrontQueryLabInfo() {
        if(currentPage == 0) currentPage = 1;
        if(labName == null) labName = "";
        List<LabInfo> labInfoList = labInfoDAO.QueryLabInfoInfo(labName, labStateObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        labInfoDAO.CalculateTotalPageAndRecordNumber(labName, labStateObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = labInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = labInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("labInfoList",  labInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("labName", labName);
        ctx.put("labStateObj", labStateObj);
        List<LatState> latStateList = latStateDAO.QueryAllLatStateInfo();
        ctx.put("latStateList", latStateList);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�LabInfo��Ϣ*/
    public String ModifyLabInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������labId��ȡLabInfo����*/
        LabInfo labInfo = labInfoDAO.GetLabInfoByLabId(labId);

        List<LatState> latStateList = latStateDAO.QueryAllLatStateInfo();
        ctx.put("latStateList", latStateList);
        ctx.put("labInfo",  labInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�LabInfo��Ϣ*/
    public String FrontShowLabInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������labId��ȡLabInfo����*/
        LabInfo labInfo = labInfoDAO.GetLabInfoByLabId(labId);

        List<LatState> latStateList = latStateDAO.QueryAllLatStateInfo();
        ctx.put("latStateList", latStateList);
        ctx.put("labInfo",  labInfo);
        return "front_show_view";
    }

    /*�����޸�LabInfo��Ϣ*/
    public String ModifyLabInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            LatState labStateObj = latStateDAO.GetLatStateByStateId(labInfo.getLabStateObj().getStateId());
            labInfo.setLabStateObj(labStateObj);
            /*����ʵ����ͼƬ�ϴ�*/
            if(labPhotoFile != null) {
            	String labPhotoPath = photoUpload(labPhotoFile,labPhotoFileContentType);
            	labInfo.setLabPhoto(labPhotoPath);
            }
            labInfoDAO.UpdateLabInfo(labInfo);
            ctx.put("message",  java.net.URLEncoder.encode("LabInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LabInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��LabInfo��Ϣ*/
    public String DeleteLabInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            labInfoDAO.DeleteLabInfo(labId);
            ctx.put("message",  java.net.URLEncoder.encode("LabInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LabInfoɾ��ʧ��!"));
            return "error";
        }
    }

}
