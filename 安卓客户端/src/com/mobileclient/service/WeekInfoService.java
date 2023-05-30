package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.WeekInfo;
import com.mobileclient.util.HttpUtil;

/*����Ϣ����ҵ���߼���*/
public class WeekInfoService {
	/* �������Ϣ */
	public String AddWeekInfo(WeekInfo weekInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("weekId", weekInfo.getWeekId() + "");
		params.put("weekName", weekInfo.getWeekName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "WeekInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯ����Ϣ */
	public List<WeekInfo> QueryWeekInfo(WeekInfo queryConditionWeekInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "WeekInfoServlet?action=query";
		if(queryConditionWeekInfo != null) {
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		WeekInfoListHandler weekInfoListHander = new WeekInfoListHandler();
		xr.setContentHandler(weekInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<WeekInfo> weekInfoList = weekInfoListHander.getWeekInfoList();
		return weekInfoList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<WeekInfo> weekInfoList = new ArrayList<WeekInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				WeekInfo weekInfo = new WeekInfo();
				weekInfo.setWeekId(object.getInt("weekId"));
				weekInfo.setWeekName(object.getString("weekName"));
				weekInfoList.add(weekInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return weekInfoList;
	}

	/* ��������Ϣ */
	public String UpdateWeekInfo(WeekInfo weekInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("weekId", weekInfo.getWeekId() + "");
		params.put("weekName", weekInfo.getWeekName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "WeekInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ������Ϣ */
	public String DeleteWeekInfo(int weekId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("weekId", weekId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "WeekInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "����Ϣ��Ϣɾ��ʧ��!";
		}
	}

	/* ��������ϢId��ȡ����Ϣ���� */
	public WeekInfo GetWeekInfo(int weekId)  {
		List<WeekInfo> weekInfoList = new ArrayList<WeekInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("weekId", weekId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "WeekInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				WeekInfo weekInfo = new WeekInfo();
				weekInfo.setWeekId(object.getInt("weekId"));
				weekInfo.setWeekName(object.getString("weekName"));
				weekInfoList.add(weekInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = weekInfoList.size();
		if(size>0) return weekInfoList.get(0); 
		else return null; 
	}
}
