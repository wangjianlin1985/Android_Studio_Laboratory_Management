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
import com.chengxusheji.domain.WeekInfo;

@Service @Transactional
public class WeekInfoDAO {

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
    public void AddWeekInfo(WeekInfo weekInfo) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(weekInfo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<WeekInfo> QueryWeekInfoInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From WeekInfo weekInfo where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List weekInfoList = q.list();
    	return (ArrayList<WeekInfo>) weekInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<WeekInfo> QueryWeekInfoInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From WeekInfo weekInfo where 1=1";
    	Query q = s.createQuery(hql);
    	List weekInfoList = q.list();
    	return (ArrayList<WeekInfo>) weekInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<WeekInfo> QueryAllWeekInfoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From WeekInfo";
        Query q = s.createQuery(hql);
        List weekInfoList = q.list();
        return (ArrayList<WeekInfo>) weekInfoList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From WeekInfo weekInfo where 1=1";
        Query q = s.createQuery(hql);
        List weekInfoList = q.list();
        recordNumber = weekInfoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public WeekInfo GetWeekInfoByWeekId(int weekId) {
        Session s = factory.getCurrentSession();
        WeekInfo weekInfo = (WeekInfo)s.get(WeekInfo.class, weekId);
        return weekInfo;
    }

    /*����WeekInfo��Ϣ*/
    public void UpdateWeekInfo(WeekInfo weekInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(weekInfo);
    }

    /*ɾ��WeekInfo��Ϣ*/
    public void DeleteWeekInfo (int weekId) throws Exception {
        Session s = factory.getCurrentSession();
        Object weekInfo = s.load(WeekInfo.class, weekId);
        s.delete(weekInfo);
    }

}
