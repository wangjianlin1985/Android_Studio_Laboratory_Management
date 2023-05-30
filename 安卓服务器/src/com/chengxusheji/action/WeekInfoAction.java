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

    private int weekId;
    public void setWeekId(int weekId) {
        this.weekId = weekId;
    }
    public int getWeekId() {
        return weekId;
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
    @Resource WeekInfoDAO weekInfoDAO;

    /*��������WeekInfo����*/
    private WeekInfo weekInfo;
    public void setWeekInfo(WeekInfo weekInfo) {
        this.weekInfo = weekInfo;
    }
    public WeekInfo getWeekInfo() {
        return this.weekInfo;
    }

    /*��ת�����WeekInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���WeekInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddWeekInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            weekInfoDAO.AddWeekInfo(weekInfo);
            ctx.put("message",  java.net.URLEncoder.encode("WeekInfo��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("WeekInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯWeekInfo��Ϣ*/
    public String QueryWeekInfo() {
        if(currentPage == 0) currentPage = 1;
        List<WeekInfo> weekInfoList = weekInfoDAO.QueryWeekInfoInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        weekInfoDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = weekInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = weekInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("weekInfoList",  weekInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryWeekInfoOutputToExcel() { 
        List<WeekInfo> weekInfoList = weekInfoDAO.QueryWeekInfoInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "WeekInfo��Ϣ��¼"; 
        String[] headers = { "����ϢId","����Ϣ����"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"WeekInfo.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯWeekInfo��Ϣ*/
    public String FrontQueryWeekInfo() {
        if(currentPage == 0) currentPage = 1;
        List<WeekInfo> weekInfoList = weekInfoDAO.QueryWeekInfoInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        weekInfoDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = weekInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = weekInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("weekInfoList",  weekInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�WeekInfo��Ϣ*/
    public String ModifyWeekInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������weekId��ȡWeekInfo����*/
        WeekInfo weekInfo = weekInfoDAO.GetWeekInfoByWeekId(weekId);

        ctx.put("weekInfo",  weekInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�WeekInfo��Ϣ*/
    public String FrontShowWeekInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������weekId��ȡWeekInfo����*/
        WeekInfo weekInfo = weekInfoDAO.GetWeekInfoByWeekId(weekId);

        ctx.put("weekInfo",  weekInfo);
        return "front_show_view";
    }

    /*�����޸�WeekInfo��Ϣ*/
    public String ModifyWeekInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            weekInfoDAO.UpdateWeekInfo(weekInfo);
            ctx.put("message",  java.net.URLEncoder.encode("WeekInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("WeekInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��WeekInfo��Ϣ*/
    public String DeleteWeekInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            weekInfoDAO.DeleteWeekInfo(weekId);
            ctx.put("message",  java.net.URLEncoder.encode("WeekInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("WeekInfoɾ��ʧ��!"));
            return "error";
        }
    }

}
