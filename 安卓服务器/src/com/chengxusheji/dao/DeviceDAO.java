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
import com.chengxusheji.domain.LabInfo;
import com.chengxusheji.domain.Device;

@Service @Transactional
public class DeviceDAO {

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
    public void AddDevice(Device device) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(device);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Device> QueryDeviceInfo(String deviceName,LabInfo labObj,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Device device where 1=1";
    	if(!deviceName.equals("")) hql = hql + " and device.deviceName like '%" + deviceName + "%'";
    	if(null != labObj && labObj.getLabId()!=0) hql += " and device.labObj.labId=" + labObj.getLabId();
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List deviceList = q.list();
    	return (ArrayList<Device>) deviceList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Device> QueryDeviceInfo(String deviceName,LabInfo labObj) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Device device where 1=1";
    	if(!deviceName.equals("")) hql = hql + " and device.deviceName like '%" + deviceName + "%'";
    	if(null != labObj && labObj.getLabId()!=0) hql += " and device.labObj.labId=" + labObj.getLabId();
    	Query q = s.createQuery(hql);
    	List deviceList = q.list();
    	return (ArrayList<Device>) deviceList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Device> QueryAllDeviceInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Device";
        Query q = s.createQuery(hql);
        List deviceList = q.list();
        return (ArrayList<Device>) deviceList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String deviceName,LabInfo labObj) {
        Session s = factory.getCurrentSession();
        String hql = "From Device device where 1=1";
        if(!deviceName.equals("")) hql = hql + " and device.deviceName like '%" + deviceName + "%'";
        if(null != labObj && labObj.getLabId()!=0) hql += " and device.labObj.labId=" + labObj.getLabId();
        Query q = s.createQuery(hql);
        List deviceList = q.list();
        recordNumber = deviceList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Device GetDeviceByDeviceId(int deviceId) {
        Session s = factory.getCurrentSession();
        Device device = (Device)s.get(Device.class, deviceId);
        return device;
    }

    /*更新Device信息*/
    public void UpdateDevice(Device device) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(device);
    }

    /*删除Device信息*/
    public void DeleteDevice (int deviceId) throws Exception {
        Session s = factory.getCurrentSession();
        Object device = s.load(Device.class, deviceId);
        s.delete(device);
    }

}
