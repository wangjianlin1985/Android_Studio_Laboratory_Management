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
    public void AddLatState(LatState latState) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(latState);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<LatState> QueryLatStateInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From LatState latState where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
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

    /*�����ܵ�ҳ���ͼ�¼��*/
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

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public LatState GetLatStateByStateId(String stateId) {
        Session s = factory.getCurrentSession();
        LatState latState = (LatState)s.get(LatState.class, stateId);
        return latState;
    }

    /*����LatState��Ϣ*/
    public void UpdateLatState(LatState latState) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(latState);
    }

    /*ɾ��LatState��Ϣ*/
    public void DeleteLatState (String stateId) throws Exception {
        Session s = factory.getCurrentSession();
        Object latState = s.load(LatState.class, stateId);
        s.delete(latState);
    }

}
