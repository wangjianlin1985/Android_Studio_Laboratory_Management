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

	/*图片或文件字段labPhoto参数接收*/
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
    /*界面层需要查询的属性: 实验室名称*/
    private String labName;
    public void setLabName(String labName) {
        this.labName = labName;
    }
    public String getLabName() {
        return this.labName;
    }

    /*界面层需要查询的属性: 实验室状态*/
    private LatState labStateObj;
    public void setLabStateObj(LatState labStateObj) {
        this.labStateObj = labStateObj;
    }
    public LatState getLabStateObj() {
        return this.labStateObj;
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

    private int labId;
    public void setLabId(int labId) {
        this.labId = labId;
    }
    public int getLabId() {
        return labId;
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
    @Resource LatStateDAO latStateDAO;
    @Resource LabInfoDAO labInfoDAO;

    /*待操作的LabInfo对象*/
    private LabInfo labInfo;
    public void setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
    }
    public LabInfo getLabInfo() {
        return this.labInfo;
    }

    /*跳转到添加LabInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的LatState信息*/
        List<LatState> latStateList = latStateDAO.QueryAllLatStateInfo();
        ctx.put("latStateList", latStateList);
        return "add_view";
    }

    /*添加LabInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddLabInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            LatState labStateObj = latStateDAO.GetLatStateByStateId(labInfo.getLabStateObj().getStateId());
            labInfo.setLabStateObj(labStateObj);
            /*处理实验室图片上传*/
            String labPhotoPath = "upload/noimage.jpg"; 
       	 	if(labPhotoFile != null)
       	 		labPhotoPath = photoUpload(labPhotoFile,labPhotoFileContentType);
       	 	labInfo.setLabPhoto(labPhotoPath);
            labInfoDAO.AddLabInfo(labInfo);
            ctx.put("message",  java.net.URLEncoder.encode("LabInfo添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LabInfo添加失败!"));
            return "error";
        }
    }

    /*查询LabInfo信息*/
    public String QueryLabInfo() {
        if(currentPage == 0) currentPage = 1;
        if(labName == null) labName = "";
        List<LabInfo> labInfoList = labInfoDAO.QueryLabInfoInfo(labName, labStateObj, currentPage);
        /*计算总的页数和总的记录数*/
        labInfoDAO.CalculateTotalPageAndRecordNumber(labName, labStateObj);
        /*获取到总的页码数目*/
        totalPage = labInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryLabInfoOutputToExcel() { 
        if(labName == null) labName = "";
        List<LabInfo> labInfoList = labInfoDAO.QueryLabInfoInfo(labName,labStateObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "LabInfo信息记录"; 
        String[] headers = { "实验室id","实验室名称","实验室图片","实验室面积","容纳人数","实验室地址","实验室状态"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"LabInfo.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询LabInfo信息*/
    public String FrontQueryLabInfo() {
        if(currentPage == 0) currentPage = 1;
        if(labName == null) labName = "";
        List<LabInfo> labInfoList = labInfoDAO.QueryLabInfoInfo(labName, labStateObj, currentPage);
        /*计算总的页数和总的记录数*/
        labInfoDAO.CalculateTotalPageAndRecordNumber(labName, labStateObj);
        /*获取到总的页码数目*/
        totalPage = labInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的LabInfo信息*/
    public String ModifyLabInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键labId获取LabInfo对象*/
        LabInfo labInfo = labInfoDAO.GetLabInfoByLabId(labId);

        List<LatState> latStateList = latStateDAO.QueryAllLatStateInfo();
        ctx.put("latStateList", latStateList);
        ctx.put("labInfo",  labInfo);
        return "modify_view";
    }

    /*查询要修改的LabInfo信息*/
    public String FrontShowLabInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键labId获取LabInfo对象*/
        LabInfo labInfo = labInfoDAO.GetLabInfoByLabId(labId);

        List<LatState> latStateList = latStateDAO.QueryAllLatStateInfo();
        ctx.put("latStateList", latStateList);
        ctx.put("labInfo",  labInfo);
        return "front_show_view";
    }

    /*更新修改LabInfo信息*/
    public String ModifyLabInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            LatState labStateObj = latStateDAO.GetLatStateByStateId(labInfo.getLabStateObj().getStateId());
            labInfo.setLabStateObj(labStateObj);
            /*处理实验室图片上传*/
            if(labPhotoFile != null) {
            	String labPhotoPath = photoUpload(labPhotoFile,labPhotoFileContentType);
            	labInfo.setLabPhoto(labPhotoPath);
            }
            labInfoDAO.UpdateLabInfo(labInfo);
            ctx.put("message",  java.net.URLEncoder.encode("LabInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LabInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除LabInfo信息*/
    public String DeleteLabInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            labInfoDAO.DeleteLabInfo(labId);
            ctx.put("message",  java.net.URLEncoder.encode("LabInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LabInfo删除失败!"));
            return "error";
        }
    }

}
