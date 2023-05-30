package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.LabInfo;
import com.mobileclient.util.HttpUtil;

/*ʵ���ҹ���ҵ���߼���*/
public class LabInfoService {
	/* ���ʵ���� */
	public String AddLabInfo(LabInfo labInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("labId", labInfo.getLabId() + "");
		params.put("labName", labInfo.getLabName());
		params.put("labPhoto", labInfo.getLabPhoto());
		params.put("labArea", labInfo.getLabArea() + "");
		params.put("personNum", labInfo.getPersonNum() + "");
		params.put("labAddress", labInfo.getLabAddress());
		params.put("labStateObj", labInfo.getLabStateObj());
		params.put("labDesc", labInfo.getLabDesc());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LabInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯʵ���� */
	public List<LabInfo> QueryLabInfo(LabInfo queryConditionLabInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "LabInfoServlet?action=query";
		if(queryConditionLabInfo != null) {
			urlString += "&labName=" + URLEncoder.encode(queryConditionLabInfo.getLabName(), "UTF-8") + "";
			urlString += "&labStateObj=" + URLEncoder.encode(queryConditionLabInfo.getLabStateObj(), "UTF-8") + "";
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		LabInfoListHandler labInfoListHander = new LabInfoListHandler();
		xr.setContentHandler(labInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<LabInfo> labInfoList = labInfoListHander.getLabInfoList();
		return labInfoList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<LabInfo> labInfoList = new ArrayList<LabInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				LabInfo labInfo = new LabInfo();
				labInfo.setLabId(object.getInt("labId"));
				labInfo.setLabName(object.getString("labName"));
				labInfo.setLabPhoto(object.getString("labPhoto"));
				labInfo.setLabArea((float) object.getDouble("labArea"));
				labInfo.setPersonNum(object.getInt("personNum"));
				labInfo.setLabAddress(object.getString("labAddress"));
				labInfo.setLabStateObj(object.getString("labStateObj"));
				labInfo.setLabDesc(object.getString("labDesc"));
				labInfoList.add(labInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return labInfoList;
	}

	/* ����ʵ���� */
	public String UpdateLabInfo(LabInfo labInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("labId", labInfo.getLabId() + "");
		params.put("labName", labInfo.getLabName());
		params.put("labPhoto", labInfo.getLabPhoto());
		params.put("labArea", labInfo.getLabArea() + "");
		params.put("personNum", labInfo.getPersonNum() + "");
		params.put("labAddress", labInfo.getLabAddress());
		params.put("labStateObj", labInfo.getLabStateObj());
		params.put("labDesc", labInfo.getLabDesc());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LabInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ��ʵ���� */
	public String DeleteLabInfo(int labId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("labId", labId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LabInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "ʵ������Ϣɾ��ʧ��!";
		}
	}

	/* ����ʵ����id��ȡʵ���Ҷ��� */
	public LabInfo GetLabInfo(int labId)  {
		List<LabInfo> labInfoList = new ArrayList<LabInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("labId", labId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LabInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				LabInfo labInfo = new LabInfo();
				labInfo.setLabId(object.getInt("labId"));
				labInfo.setLabName(object.getString("labName"));
				labInfo.setLabPhoto(object.getString("labPhoto"));
				labInfo.setLabArea((float) object.getDouble("labArea"));
				labInfo.setPersonNum(object.getInt("personNum"));
				labInfo.setLabAddress(object.getString("labAddress"));
				labInfo.setLabStateObj(object.getString("labStateObj"));
				labInfo.setLabDesc(object.getString("labDesc"));
				labInfoList.add(labInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = labInfoList.size();
		if(size>0) return labInfoList.get(0); 
		else return null; 
	}
}
