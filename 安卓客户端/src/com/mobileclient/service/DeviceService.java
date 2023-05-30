package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Device;
import com.mobileclient.util.HttpUtil;

/*ʵ���豸����ҵ���߼���*/
public class DeviceService {
	/* ���ʵ���豸 */
	public String AddDevice(Device device) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("deviceId", device.getDeviceId() + "");
		params.put("deviceName", device.getDeviceName());
		params.put("labObj", device.getLabObj() + "");
		params.put("devicePhoto", device.getDevicePhoto());
		params.put("devicePrice", device.getDevicePrice() + "");
		params.put("deviceCount", device.getDeviceCount() + "");
		params.put("deviceDesc", device.getDeviceDesc());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DeviceServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯʵ���豸 */
	public List<Device> QueryDevice(Device queryConditionDevice) throws Exception {
		String urlString = HttpUtil.BASE_URL + "DeviceServlet?action=query";
		if(queryConditionDevice != null) {
			urlString += "&deviceName=" + URLEncoder.encode(queryConditionDevice.getDeviceName(), "UTF-8") + "";
			urlString += "&labObj=" + queryConditionDevice.getLabObj();
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		DeviceListHandler deviceListHander = new DeviceListHandler();
		xr.setContentHandler(deviceListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Device> deviceList = deviceListHander.getDeviceList();
		return deviceList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<Device> deviceList = new ArrayList<Device>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Device device = new Device();
				device.setDeviceId(object.getInt("deviceId"));
				device.setDeviceName(object.getString("deviceName"));
				device.setLabObj(object.getInt("labObj"));
				device.setDevicePhoto(object.getString("devicePhoto"));
				device.setDevicePrice((float) object.getDouble("devicePrice"));
				device.setDeviceCount(object.getInt("deviceCount"));
				device.setDeviceDesc(object.getString("deviceDesc"));
				deviceList.add(device);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deviceList;
	}

	/* ����ʵ���豸 */
	public String UpdateDevice(Device device) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("deviceId", device.getDeviceId() + "");
		params.put("deviceName", device.getDeviceName());
		params.put("labObj", device.getLabObj() + "");
		params.put("devicePhoto", device.getDevicePhoto());
		params.put("devicePrice", device.getDevicePrice() + "");
		params.put("deviceCount", device.getDeviceCount() + "");
		params.put("deviceDesc", device.getDeviceDesc());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DeviceServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ��ʵ���豸 */
	public String DeleteDevice(int deviceId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("deviceId", deviceId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DeviceServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "ʵ���豸��Ϣɾ��ʧ��!";
		}
	}

	/* �����豸id��ȡʵ���豸���� */
	public Device GetDevice(int deviceId)  {
		List<Device> deviceList = new ArrayList<Device>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("deviceId", deviceId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DeviceServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Device device = new Device();
				device.setDeviceId(object.getInt("deviceId"));
				device.setDeviceName(object.getString("deviceName"));
				device.setLabObj(object.getInt("labObj"));
				device.setDevicePhoto(object.getString("devicePhoto"));
				device.setDevicePrice((float) object.getDouble("devicePrice"));
				device.setDeviceCount(object.getInt("deviceCount"));
				device.setDeviceDesc(object.getString("deviceDesc"));
				deviceList.add(device);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = deviceList.size();
		if(size>0) return deviceList.get(0); 
		else return null; 
	}
}
