package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.LatState;
import com.mobileclient.util.HttpUtil;

/*实验室状态管理业务逻辑层*/
public class LatStateService {
	/* 添加实验室状态 */
	public String AddLatState(LatState latState) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stateId", latState.getStateId());
		params.put("stateName", latState.getStateName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LatStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询实验室状态 */
	public List<LatState> QueryLatState(LatState queryConditionLatState) throws Exception {
		String urlString = HttpUtil.BASE_URL + "LatStateServlet?action=query";
		if(queryConditionLatState != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		LatStateListHandler latStateListHander = new LatStateListHandler();
		xr.setContentHandler(latStateListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<LatState> latStateList = latStateListHander.getLatStateList();
		return latStateList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<LatState> latStateList = new ArrayList<LatState>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				LatState latState = new LatState();
				latState.setStateId(object.getString("stateId"));
				latState.setStateName(object.getString("stateName"));
				latStateList.add(latState);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return latStateList;
	}

	/* 更新实验室状态 */
	public String UpdateLatState(LatState latState) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stateId", latState.getStateId());
		params.put("stateName", latState.getStateName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LatStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除实验室状态 */
	public String DeleteLatState(String stateId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stateId", stateId);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LatStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "实验室状态信息删除失败!";
		}
	}

	/* 根据状态id获取实验室状态对象 */
	public LatState GetLatState(String stateId)  {
		List<LatState> latStateList = new ArrayList<LatState>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stateId", stateId);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LatStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				LatState latState = new LatState();
				latState.setStateId(object.getString("stateId"));
				latState.setStateName(object.getString("stateName"));
				latStateList.add(latState);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = latStateList.size();
		if(size>0) return latStateList.get(0); 
		else return null; 
	}
}
