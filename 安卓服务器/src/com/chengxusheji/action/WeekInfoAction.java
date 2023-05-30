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
import com.chengxusheji.dao.WeekInfoDAO;
import com.chengxusheji.domain.WeekInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class WeekInfoAction extends BaseAction {

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

    private int weekId;
    public void setWeekId(int weekId) {
        this.weekId = weekId;
    }
    public int getWeekId() {
        return weekId;
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
    @Resource WeekInfoDAO weekInfoDAO;

    /*待操作的WeekInfo对象*/
    private WeekInfo weekInfo;
    public void setWeekInfo(WeekInfo weekInfo) {
        this.weekInfo = weekInfo;
    }
    public WeekInfo getWeekInfo() {
        return this.weekInfo;
    }

    /*跳转到添加WeekInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加WeekInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddWeekInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            weekInfoDAO.AddWeekInfo(weekInfo);
            ctx.put("message",  java.net.URLEncoder.encode("WeekInfo添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("WeekInfo添加失败!"));
            return "error";
        }
    }

    /*查询WeekInfo信息*/
    public String QueryWeekInfo() {
        if(currentPage == 0) currentPage = 1;
        List<WeekInfo> weekInfoList = weekInfoDAO.QueryWeekInfoInfo(currentPage);
        /*计算总的页数和总的记录数*/
        weekInfoDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = weekInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = weekInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("weekInfoList",  weekInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryWeekInfoOutputToExcel() { 
        List<WeekInfo> weekInfoList = weekInfoDAO.QueryWeekInfoInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "WeekInfo信息记录"; 
        String[] headers = { "周信息Id","周信息名称"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<weekInfoList.size();i++) {
        	WeekInfo weekInfo = weekInfoList.get(i); 
        	dataset.add(new String[]{weekInfo.getWeekId() + "",weekInfo.getWeekName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"WeekInfo.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询WeekInfo信息*/
    public String FrontQueryWeekInfo() {
        if(currentPage == 0) currentPage = 1;
        List<WeekInfo> weekInfoList = weekInfoDAO.QueryWeekInfoInfo(currentPage);
        /*计算总的页数和总的记录数*/
        weekInfoDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = weekInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = weekInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("weekInfoList",  weekInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的WeekInfo信息*/
    public String ModifyWeekInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键weekId获取WeekInfo对象*/
        WeekInfo weekInfo = weekInfoDAO.GetWeekInfoByWeekId(weekId);

        ctx.put("weekInfo",  weekInfo);
        return "modify_view";
    }

    /*查询要修改的WeekInfo信息*/
    public String FrontShowWeekInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键weekId获取WeekInfo对象*/
        WeekInfo weekInfo = weekInfoDAO.GetWeekInfoByWeekId(weekId);

        ctx.put("weekInfo",  weekInfo);
        return "front_show_view";
    }

    /*更新修改WeekInfo信息*/
    public String ModifyWeekInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            weekInfoDAO.UpdateWeekInfo(weekInfo);
            ctx.put("message",  java.net.URLEncoder.encode("WeekInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("WeekInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除WeekInfo信息*/
    public String DeleteWeekInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            weekInfoDAO.DeleteWeekInfo(weekId);
            ctx.put("message",  java.net.URLEncoder.encode("WeekInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("WeekInfo删除失败!"));
            return "error";
        }
    }

}
