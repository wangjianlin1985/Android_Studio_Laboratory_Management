package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.Course;
import com.chengxusheji.domain.WeekInfo;
import com.chengxusheji.domain.LabInfo;
import com.chengxusheji.domain.CourseTest;

@Service @Transactional
public class CourseTestDAO {

	@Resource SessionFactory factory;
    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
    public void AddCourseTest(CourseTest courseTest) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(courseTest);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<CourseTest> QueryCourseTestInfo(Course courseObj,String testName,WeekInfo weekObj,LabInfo labObj,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From CourseTest courseTest where 1=1";
    	if(null != courseObj && !courseObj.getCourseNo().equals("")) hql += " and courseTest.courseObj.courseNo='" + courseObj.getCourseNo() + "'";
    	if(!testName.equals("")) hql = hql + " and courseTest.testName like '%" + testName + "%'";
    	if(null != weekObj && weekObj.getWeekId()!=0) hql += " and courseTest.weekObj.weekId=" + weekObj.getWeekId();
    	if(null != labObj && labObj.getLabId()!=0) hql += " and courseTest.labObj.labId=" + labObj.getLabId();
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List courseTestList = q.list();
    	return (ArrayList<CourseTest>) courseTestList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<CourseTest> QueryCourseTestInfo(Course courseObj,String testName,WeekInfo weekObj,LabInfo labObj) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From CourseTest courseTest where 1=1";
    	if(null != courseObj && !courseObj.getCourseNo().equals("")) hql += " and courseTest.courseObj.courseNo='" + courseObj.getCourseNo() + "'";
    	if(!testName.equals("")) hql = hql + " and courseTest.testName like '%" + testName + "%'";
    	if(null != weekObj && weekObj.getWeekId()!=0) hql += " and courseTest.weekObj.weekId=" + weekObj.getWeekId();
    	if(null != labObj && labObj.getLabId()!=0) hql += " and courseTest.labObj.labId=" + labObj.getLabId();
    	Query q = s.createQuery(hql);
    	List courseTestList = q.list();
    	return (ArrayList<CourseTest>) courseTestList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<CourseTest> QueryAllCourseTestInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From CourseTest";
        Query q = s.createQuery(hql);
        List courseTestList = q.list();
        return (ArrayList<CourseTest>) courseTestList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Course courseObj,String testName,WeekInfo weekObj,LabInfo labObj) {
        Session s = factory.getCurrentSession();
        String hql = "From CourseTest courseTest where 1=1";
        if(null != courseObj && !courseObj.getCourseNo().equals("")) hql += " and courseTest.courseObj.courseNo='" + courseObj.getCourseNo() + "'";
        if(!testName.equals("")) hql = hql + " and courseTest.testName like '%" + testName + "%'";
        if(null != weekObj && weekObj.getWeekId()!=0) hql += " and courseTest.weekObj.weekId=" + weekObj.getWeekId();
        if(null != labObj && labObj.getLabId()!=0) hql += " and courseTest.labObj.labId=" + labObj.getLabId();
        Query q = s.createQuery(hql);
        List courseTestList = q.list();
        recordNumber = courseTestList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public CourseTest GetCourseTestByTestId(int testId) {
        Session s = factory.getCurrentSession();
        CourseTest courseTest = (CourseTest)s.get(CourseTest.class, testId);
        return courseTest;
    }

    /*����CourseTest��Ϣ*/
    public void UpdateCourseTest(CourseTest courseTest) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(courseTest);
    }

    /*ɾ��CourseTest��Ϣ*/
    public void DeleteCourseTest (int testId) throws Exception {
        Session s = factory.getCurrentSession();
        Object courseTest = s.load(CourseTest.class, testId);
        s.delete(courseTest);
    }

}
