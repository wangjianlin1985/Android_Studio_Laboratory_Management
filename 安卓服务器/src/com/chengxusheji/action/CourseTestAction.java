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

    /*界面层需要查询的属性: 所属课程*/
    private Course courseObj;
    public void setCourseObj(Course courseObj) {
        this.courseObj = courseObj;
    }
    public Course getCourseObj() {
        return this.courseObj;
    }

    /*界面层需要查询的属性: 实验名称*/
    private String testName;
    public void setTestName(String testName) {
        this.testName = testName;
    }
    public String getTestName() {
        return this.testName;
    }

    /*界面层需要查询的属性: 上课周*/
    private WeekInfo weekObj;
    public void setWeekObj(WeekInfo weekObj) {
        this.weekObj = weekObj;
    }
    public WeekInfo getWeekObj() {
        return this.weekObj;
    }

    /*界面层需要查询的属性: 上课实验室*/
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

    private int testId;
    public void setTestId(int testId) {
        this.testId = testId;
    }
    public int getTestId() {
        return testId;
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
    @Resource CourseDAO courseDAO;
    @Resource WeekInfoDAO weekInfoDAO;
    @Resource LabInfoDAO labInfoDAO;
    @Resource CourseTestDAO courseTestDAO;

    /*待操作的CourseTest对象*/
    private CourseTest courseTest;
    public void setCourseTest(CourseTest courseTest) {
        this.courseTest = courseTest;
    }
    public CourseTest getCourseTest() {
        return this.courseTest;
    }

    /*跳转到添加CourseTest视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Course信息*/
        List<Course> courseList = courseDAO.QueryAllCourseInfo();
        ctx.put("courseList", courseList);
        /*查询所有的WeekInfo信息*/
        List<WeekInfo> weekInfoList = weekInfoDAO.QueryAllWeekInfoInfo();
        ctx.put("weekInfoList", weekInfoList);
        /*查询所有的LabInfo信息*/
        List<LabInfo> labInfoList = labInfoDAO.QueryAllLabInfoInfo();
        ctx.put("labInfoList", labInfoList);
        return "add_view";
    }

    /*添加CourseTest信息*/
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
            ctx.put("message",  java.net.URLEncoder.encode("CourseTest添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CourseTest添加失败!"));
            return "error";
        }
    }

    /*查询CourseTest信息*/
    public String QueryCourseTest() {
        if(currentPage == 0) currentPage = 1;
        if(testName == null) testName = "";
        List<CourseTest> courseTestList = courseTestDAO.QueryCourseTestInfo(courseObj, testName, weekObj, labObj, currentPage);
        /*计算总的页数和总的记录数*/
        courseTestDAO.CalculateTotalPageAndRecordNumber(courseObj, testName, weekObj, labObj);
        /*获取到总的页码数目*/
        totalPage = courseTestDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryCourseTestOutputToExcel() { 
        if(testName == null) testName = "";
        List<CourseTest> courseTestList = courseTestDAO.QueryCourseTestInfo(courseObj,testName,weekObj,labObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "CourseTest信息记录"; 
        String[] headers = { "实验id","所属课程","实验名称","上课周","实验时间","上课实验室"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"CourseTest.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询CourseTest信息*/
    public String FrontQueryCourseTest() {
        if(currentPage == 0) currentPage = 1;
        if(testName == null) testName = "";
        List<CourseTest> courseTestList = courseTestDAO.QueryCourseTestInfo(courseObj, testName, weekObj, labObj, currentPage);
        /*计算总的页数和总的记录数*/
        courseTestDAO.CalculateTotalPageAndRecordNumber(courseObj, testName, weekObj, labObj);
        /*获取到总的页码数目*/
        totalPage = courseTestDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的CourseTest信息*/
    public String ModifyCourseTestQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键testId获取CourseTest对象*/
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

    /*查询要修改的CourseTest信息*/
    public String FrontShowCourseTestQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键testId获取CourseTest对象*/
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

    /*更新修改CourseTest信息*/
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
            ctx.put("message",  java.net.URLEncoder.encode("CourseTest信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CourseTest信息更新失败!"));
            return "error";
       }
   }

    /*删除CourseTest信息*/
    public String DeleteCourseTest() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            courseTestDAO.DeleteCourseTest(testId);
            ctx.put("message",  java.net.URLEncoder.encode("CourseTest删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CourseTest删除失败!"));
            return "error";
        }
    }

}
