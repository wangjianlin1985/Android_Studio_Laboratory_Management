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

    private String stateId;
    public void setStateId(String stateId) {
        this.stateId = stateId;
    }
    public String getStateId() {
        return stateId;
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

    /*��������LatState����*/
    private LatState latState;
    public void setLatState(LatState latState) {
        this.latState = latState;
    }
    public LatState getLatState() {
        return this.latState;
    }

    /*��ת�����LatState��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���LatState��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddLatState() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤״̬id�Ƿ��Ѿ�����*/
        String stateId = latState.getStateId();
        LatState db_latState = latStateDAO.GetLatStateByStateId(stateId);
        if(null != db_latState) {
            ctx.put("error",  java.net.URLEncoder.encode("��״̬id�Ѿ�����!"));
            return "error";
        }
        try {
            latStateDAO.AddLatState(latState);
            ctx.put("message",  java.net.URLEncoder.encode("LatState��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LatState���ʧ��!"));
            return "error";
        }
    }

    /*��ѯLatState��Ϣ*/
    public String QueryLatState() {
        if(currentPage == 0) currentPage = 1;
        List<LatState> latStateList = latStateDAO.QueryLatStateInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        latStateDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = latStateDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = latStateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("latStateList",  latStateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryLatStateOutputToExcel() { 
        List<LatState> latStateList = latStateDAO.QueryLatStateInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "LatState��Ϣ��¼"; 
        String[] headers = { "״̬id","״̬����"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"LatState.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯLatState��Ϣ*/
    public String FrontQueryLatState() {
        if(currentPage == 0) currentPage = 1;
        List<LatState> latStateList = latStateDAO.QueryLatStateInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        latStateDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = latStateDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = latStateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("latStateList",  latStateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�LatState��Ϣ*/
    public String ModifyLatStateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������stateId��ȡLatState����*/
        LatState latState = latStateDAO.GetLatStateByStateId(stateId);

        ctx.put("latState",  latState);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�LatState��Ϣ*/
    public String FrontShowLatStateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������stateId��ȡLatState����*/
        LatState latState = latStateDAO.GetLatStateByStateId(stateId);

        ctx.put("latState",  latState);
        return "front_show_view";
    }

    /*�����޸�LatState��Ϣ*/
    public String ModifyLatState() {
        ActionContext ctx = ActionContext.getContext();
        try {
            latStateDAO.UpdateLatState(latState);
            ctx.put("message",  java.net.URLEncoder.encode("LatState��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LatState��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��LatState��Ϣ*/
    public String DeleteLatState() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            latStateDAO.DeleteLatState(stateId);
            ctx.put("message",  java.net.URLEncoder.encode("LatStateɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LatStateɾ��ʧ��!"));
            return "error";
        }
    }

}
