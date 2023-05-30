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
import com.chengxusheji.domain.LatState;

@Service @Transactional
public class LatStateDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddLatState(LatState latState) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(latState);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<LatState> QueryLatStateInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From LatState latState where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List latStateList = q.list();
    	return (ArrayList<LatState>) latStateList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<LatState> QueryLatStateInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From LatState latState where 1=1";
    	Query q = s.createQuery(hql);
    	List latStateList = q.list();
    	return (ArrayList<LatState>) latStateList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<LatState> QueryAllLatStateInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From LatState";
        Query q = s.createQuery(hql);
        List latStateList = q.list();
        return (ArrayList<LatState>) latStateList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From LatState latState where 1=1";
        Query q = s.createQuery(hql);
        List latStateList = q.list();
        recordNumber = latStateList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public LatState GetLatStateByStateId(String stateId) {
        Session s = factory.getCurrentSession();
        LatState latState = (LatState)s.get(LatState.class, stateId);
        return latState;
    }

    /*更新LatState信息*/
    public void UpdateLatState(LatState latState) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(latState);
    }

    /*删除LatState信息*/
    public void DeleteLatState (String stateId) throws Exception {
        Session s = factory.getCurrentSession();
        Object latState = s.load(LatState.class, stateId);
        s.delete(latState);
    }

}
