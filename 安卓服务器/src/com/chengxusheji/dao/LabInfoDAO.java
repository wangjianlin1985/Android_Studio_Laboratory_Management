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
import com.chengxusheji.domain.LabInfo;

@Service @Transactional
public class LabInfoDAO {

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
    public void AddLabInfo(LabInfo labInfo) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(labInfo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<LabInfo> QueryLabInfoInfo(String labName,LatState labStateObj,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From LabInfo labInfo where 1=1";
    	if(!labName.equals("")) hql = hql + " and labInfo.labName like '%" + labName + "%'";
    	if(null != labStateObj && !labStateObj.getStateId().equals("")) hql += " and labInfo.labStateObj.stateId='" + labStateObj.getStateId() + "'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List labInfoList = q.list();
    	return (ArrayList<LabInfo>) labInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<LabInfo> QueryLabInfoInfo(String labName,LatState labStateObj) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From LabInfo labInfo where 1=1";
    	if(!labName.equals("")) hql = hql + " and labInfo.labName like '%" + labName + "%'";
    	if(null != labStateObj && !labStateObj.getStateId().equals("")) hql += " and labInfo.labStateObj.stateId='" + labStateObj.getStateId() + "'";
    	Query q = s.createQuery(hql);
    	List labInfoList = q.list();
    	return (ArrayList<LabInfo>) labInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<LabInfo> QueryAllLabInfoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From LabInfo";
        Query q = s.createQuery(hql);
        List labInfoList = q.list();
        return (ArrayList<LabInfo>) labInfoList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String labName,LatState labStateObj) {
        Session s = factory.getCurrentSession();
        String hql = "From LabInfo labInfo where 1=1";
        if(!labName.equals("")) hql = hql + " and labInfo.labName like '%" + labName + "%'";
        if(null != labStateObj && !labStateObj.getStateId().equals("")) hql += " and labInfo.labStateObj.stateId='" + labStateObj.getStateId() + "'";
        Query q = s.createQuery(hql);
        List labInfoList = q.list();
        recordNumber = labInfoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public LabInfo GetLabInfoByLabId(int labId) {
        Session s = factory.getCurrentSession();
        LabInfo labInfo = (LabInfo)s.get(LabInfo.class, labId);
        return labInfo;
    }

    /*����LabInfo��Ϣ*/
    public void UpdateLabInfo(LabInfo labInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(labInfo);
    }

    /*ɾ��LabInfo��Ϣ*/
    public void DeleteLabInfo (int labId) throws Exception {
        Session s = factory.getCurrentSession();
        Object labInfo = s.load(LabInfo.class, labId);
        s.delete(labInfo);
    }

}
