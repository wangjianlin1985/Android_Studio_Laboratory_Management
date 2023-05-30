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
import com.chengxusheji.dao.CourseTestDAO;
import com.chengxusheji.domain.CourseTest;
import com.chengxusheji.dao.CourseDAO;
import com.chengxusheji.domain.Course;
import com.chengxusheji.dao.WeekInfoDAO;
import com.chengxusheji.domain.WeekInfo;
import com.chengxusheji.dao.LabInfoDAO;
import com.chengxusheji.domain.LabInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class CourseTestAction extends BaseAction {

    /*�������Ҫ��ѯ������: �����γ�*/
    private Course courseObj;
    public void setCourseObj(Course courseObj) {
        this.courseObj = courseObj;
    }
    public Course getCourseObj() {
        return this.courseObj;
    }

    /*�������Ҫ��ѯ������: ʵ������*/
    private String testName;
    public void setTestName(String testName) {
        this.testName = testName;
    }
    public String getTestName() {
        return this.testName;
    }

    /*�������Ҫ��ѯ������: �Ͽ���*/
    private WeekInfo weekObj;
    public void setWeekObj(WeekInfo weekObj) {
        this.weekObj = weekObj;
    }
    public WeekInfo getWeekObj() {
        return this.weekObj;
    }

    /*�������Ҫ��ѯ������: �Ͽ�ʵ����*/
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

    private int testId;
    public void setTestId(int testId) {
        this.testId = testId;
    }
    public int getTestId() {
        return testId;
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
    @Resource CourseDAO courseDAO;
    @Resource WeekInfoDAO weekInfoDAO;
    @Resource LabInfoDAO labInfoDAO;
    @Resource CourseTestDAO courseTestDAO;

    /*��������CourseTest����*/
    private CourseTest courseTest;
    public void setCourseTest(CourseTest courseTest) {
        this.courseTest = courseTest;
    }
    public CourseTest getCourseTest() {
        return this.courseTest;
    }

    /*��ת�����CourseTest��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Course��Ϣ*/
        List<Course> courseList = courseDAO.QueryAllCourseInfo();
        ctx.put("courseList", courseList);
        /*��ѯ���е�WeekInfo��Ϣ*/
        List<WeekInfo> weekInfoList = weekInfoDAO.QueryAllWeekInfoInfo();
        ctx.put("weekInfoList", weekInfoList);
        /*��ѯ���е�LabInfo��Ϣ*/
        List<LabInfo> labInfoList = labInfoDAO.QueryAllLabInfoInfo();
        ctx.put("labInfoList", labInfoList);
        return "add_view";
    }

    /*���CourseTest��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddCourseTest() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Course courseObj = courseDAO.GetCourseByCourseNo(courseTest.getCourseObj().getCourseNo());
            courseTest.setCourseObj(courseObj);
            WeekInfo weekObj = weekInfoDAO.GetWeekInfoByWeekId(courseTest.getWeekObj().getWeekId());
            courseTest.setWeekObj(weekObj);
            LabInfo labObj = labInfoDAO.GetLabInfoByLabId(courseTest.getLabObj().getLabId());
            courseTest.setLabObj(labObj);
            courseTestDAO.AddCourseTest(courseTest);
            ctx.put("message",  java.net.URLEncoder.encode("CourseTest��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CourseTest���ʧ��!"));
            return "error";
        }
    }

    /*��ѯCourseTest��Ϣ*/
    public String QueryCourseTest() {
        if(currentPage == 0) currentPage = 1;
        if(testName == null) testName = "";
        List<CourseTest> courseTestList = courseTestDAO.QueryCourseTestInfo(courseObj, testName, weekObj, labObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        courseTestDAO.CalculateTotalPageAndRecordNumber(courseObj, testName, weekObj, labObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = courseTestDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = courseTestDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("courseTestList",  courseTestList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("courseObj", courseObj);
        List<Course> courseList = courseDAO.QueryAllCourseInfo();
        ctx.put("courseList", courseList);
        ctx.put("testName", testName);
        ctx.put("weekObj", weekObj);
        List<WeekInfo> weekInfoList = weekInfoDAO.QueryAllWeekInfoInfo();
        ctx.put("weekInfoList", weekInfoList);
        ctx.put("labObj", labObj);
        List<LabInfo> labInfoList = labInfoDAO.QueryAllLabInfoInfo();
        ctx.put("labInfoList", labInfoList);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryCourseTestOutputToExcel() { 
        if(testName == null) testName = "";
        List<CourseTest> courseTestList = courseTestDAO.QueryCourseTestInfo(courseObj,testName,weekObj,labObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "CourseTest��Ϣ��¼"; 
        String[] headers = { "ʵ��id","�����γ�","ʵ������","�Ͽ���","ʵ��ʱ��","�Ͽ�ʵ����"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<courseTestList.size();i++) {
        	CourseTest courseTest = courseTestList.get(i); 
        	dataset.add(new String[]{courseTest.getTestId() + "",courseTest.getCourseObj().getCourseName(),
courseTest.getTestName(),courseTest.getWeekObj().getWeekName(),
courseTest.getLabTime(),courseTest.getLabObj().getLabName()
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
			response.setHeader("Content-disposition","attachment; filename="+"CourseTest.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯCourseTest��Ϣ*/
    public String FrontQueryCourseTest() {
        if(currentPage == 0) currentPage = 1;
        if(testName == null) testName = "";
        List<CourseTest> courseTestList = courseTestDAO.QueryCourseTestInfo(courseObj, testName, weekObj, labObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        courseTestDAO.CalculateTotalPageAndRecordNumber(courseObj, testName, weekObj, labObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = courseTestDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = courseTestDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("courseTestList",  courseTestList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("courseObj", courseObj);
        List<Course> courseList = courseDAO.QueryAllCourseInfo();
        ctx.put("courseList", courseList);
        ctx.put("testName", testName);
        ctx.put("weekObj", weekObj);
        List<WeekInfo> weekInfoList = weekInfoDAO.QueryAllWeekInfoInfo();
        ctx.put("weekInfoList", weekInfoList);
        ctx.put("labObj", labObj);
        List<LabInfo> labInfoList = labInfoDAO.QueryAllLabInfoInfo();
        ctx.put("labInfoList", labInfoList);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�CourseTest��Ϣ*/
    public String ModifyCourseTestQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������testId��ȡCourseTest����*/
        CourseTest courseTest = courseTestDAO.GetCourseTestByTestId(testId);

        List<Course> courseList = courseDAO.QueryAllCourseInfo();
        ctx.put("courseList", courseList);
        List<WeekInfo> weekInfoList = weekInfoDAO.QueryAllWeekInfoInfo();
        ctx.put("weekInfoList", weekInfoList);
        List<LabInfo> labInfoList = labInfoDAO.QueryAllLabInfoInfo();
        ctx.put("labInfoList", labInfoList);
        ctx.put("courseTest",  courseTest);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�CourseTest��Ϣ*/
    public String FrontShowCourseTestQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������testId��ȡCourseTest����*/
        CourseTest courseTest = courseTestDAO.GetCourseTestByTestId(testId);

        List<Course> courseList = courseDAO.QueryAllCourseInfo();
        ctx.put("courseList", courseList);
        List<WeekInfo> weekInfoList = weekInfoDAO.QueryAllWeekInfoInfo();
        ctx.put("weekInfoList", weekInfoList);
        List<LabInfo> labInfoList = labInfoDAO.QueryAllLabInfoInfo();
        ctx.put("labInfoList", labInfoList);
        ctx.put("courseTest",  courseTest);
        return "front_show_view";
    }

    /*�����޸�CourseTest��Ϣ*/
    public String ModifyCourseTest() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Course courseObj = courseDAO.GetCourseByCourseNo(courseTest.getCourseObj().getCourseNo());
            courseTest.setCourseObj(courseObj);
            WeekInfo weekObj = weekInfoDAO.GetWeekInfoByWeekId(courseTest.getWeekObj().getWeekId());
            courseTest.setWeekObj(weekObj);
            LabInfo labObj = labInfoDAO.GetLabInfoByLabId(courseTest.getLabObj().getLabId());
            courseTest.setLabObj(labObj);
            courseTestDAO.UpdateCourseTest(courseTest);
            ctx.put("message",  java.net.URLEncoder.encode("CourseTest��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CourseTest��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��CourseTest��Ϣ*/
    public String DeleteCourseTest() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            courseTestDAO.DeleteCourseTest(testId);
            ctx.put("message",  java.net.URLEncoder.encode("CourseTestɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CourseTestɾ��ʧ��!"));
            return "error";
        }
    }

}
