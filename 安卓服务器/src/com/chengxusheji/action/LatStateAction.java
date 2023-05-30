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
import com.chengxusheji.dao.LatStateDAO;
import com.chengxusheji.domain.LatState;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class LatStateAction extends BaseAction {

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

    private String stateId;
    public void setStateId(String stateId) {
        this.stateId = stateId;
    }
    public String getStateId() {
        return stateId;
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

    /*待操作的LatState对象*/
    private LatState latState;
    public void setLatState(LatState latState) {
        this.latState = latState;
    }
    public LatState getLatState() {
        return this.latState;
    }

    /*跳转到添加LatState视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加LatState信息*/
    @SuppressWarnings("deprecation")
    public String AddLatState() {
        ActionContext ctx = ActionContext.getContext();
        /*验证状态id是否已经存在*/
        String stateId = latState.getStateId();
        LatState db_latState = latStateDAO.GetLatStateByStateId(stateId);
        if(null != db_latState) {
            ctx.put("error",  java.net.URLEncoder.encode("该状态id已经存在!"));
            return "error";
        }
        try {
            latStateDAO.AddLatState(latState);
            ctx.put("message",  java.net.URLEncoder.encode("LatState添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LatState添加失败!"));
            return "error";
        }
    }

    /*查询LatState信息*/
    public String QueryLatState() {
        if(currentPage == 0) currentPage = 1;
        List<LatState> latStateList = latStateDAO.QueryLatStateInfo(currentPage);
        /*计算总的页数和总的记录数*/
        latStateDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = latStateDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = latStateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("latStateList",  latStateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryLatStateOutputToExcel() { 
        List<LatState> latStateList = latStateDAO.QueryLatStateInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "LatState信息记录"; 
        String[] headers = { "状态id","状态名称"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<latStateList.size();i++) {
        	LatState latState = latStateList.get(i); 
        	dataset.add(new String[]{latState.getStateId(),latState.getStateName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"LatState.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询LatState信息*/
    public String FrontQueryLatState() {
        if(currentPage == 0) currentPage = 1;
        List<LatState> latStateList = latStateDAO.QueryLatStateInfo(currentPage);
        /*计算总的页数和总的记录数*/
        latStateDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = latStateDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = latStateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("latStateList",  latStateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的LatState信息*/
    public String ModifyLatStateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键stateId获取LatState对象*/
        LatState latState = latStateDAO.GetLatStateByStateId(stateId);

        ctx.put("latState",  latState);
        return "modify_view";
    }

    /*查询要修改的LatState信息*/
    public String FrontShowLatStateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键stateId获取LatState对象*/
        LatState latState = latStateDAO.GetLatStateByStateId(stateId);

        ctx.put("latState",  latState);
        return "front_show_view";
    }

    /*更新修改LatState信息*/
    public String ModifyLatState() {
        ActionContext ctx = ActionContext.getContext();
        try {
            latStateDAO.UpdateLatState(latState);
            ctx.put("message",  java.net.URLEncoder.encode("LatState信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LatState信息更新失败!"));
            return "error";
       }
   }

    /*删除LatState信息*/
    public String DeleteLatState() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            latStateDAO.DeleteLatState(stateId);
            ctx.put("message",  java.net.URLEncoder.encode("LatState删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LatState删除失败!"));
            return "error";
        }
    }

}
