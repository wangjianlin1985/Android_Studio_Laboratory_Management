package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.CourseTest;
import com.mobileclient.util.HttpUtil;

/*�γ�ʵ�����ҵ���߼���*/
public class CourseTestService {
	/* ��ӿγ�ʵ�� */
	public String AddCourseTest(CourseTest courseTest) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("testId", courseTest.getTestId() + "");
		params.put("courseObj", courseTest.getCourseObj());
		params.put("testName", courseTest.getTestName());
		params.put("weekObj", courseTest.getWeekObj() + "");
		params.put("labTime", courseTest.getLabTime());
		params.put("labObj", courseTest.getLabObj() + "");
		params.put("testMemo", courseTest.getTestMemo());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CourseTestServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯ�γ�ʵ�� */
	public List<CourseTest> QueryCourseTest(CourseTest queryConditionCourseTest) throws Exception {
		String urlString = HttpUtil.BASE_URL + "CourseTestServlet?action=query";
		if(queryConditionCourseTest != null) {
			urlString += "&courseObj=" + URLEncoder.encode(queryConditionCourseTest.getCourseObj(), "UTF-8") + "";
			urlString += "&testName=" + URLEncoder.encode(queryConditionCourseTest.getTestName(), "UTF-8") + "";
			urlString += "&weekObj=" + queryConditionCourseTest.getWeekObj();
			urlString += "&labObj=" + queryConditionCourseTest.getLabObj();
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		CourseTestListHandler courseTestListHander = new CourseTestListHandler();
		xr.setContentHandler(courseTestListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<CourseTest> courseTestList = courseTestListHander.getCourseTestList();
		return courseTestList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<CourseTest> courseTestList = new ArrayList<CourseTest>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				CourseTest courseTest = new CourseTest();
				courseTest.setTestId(object.getInt("testId"));
				courseTest.setCourseObj(object.getString("courseObj"));
				courseTest.setTestName(object.getString("testName"));
				courseTest.setWeekObj(object.getInt("weekObj"));
				courseTest.setLabTime(object.getString("labTime"));
				courseTest.setLabObj(object.getInt("labObj"));
				courseTest.setTestMemo(object.getString("testMemo"));
				courseTestList.add(courseTest);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return courseTestList;
	}
	
	
	/* ѧ����ѯ���༶�Ŀγ�ʵ�� */
	public List<CourseTest> studentQueryCourseTest(String studentNo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "CourseTestServlet?action=studentQuery";
		urlString += "&studentNo=" + studentNo;
		 
		List<CourseTest> courseTestList = new ArrayList<CourseTest>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				CourseTest courseTest = new CourseTest();
				courseTest.setTestId(object.getInt("testId"));
				courseTest.setCourseObj(object.getString("courseObj"));
				courseTest.setTestName(object.getString("testName"));
				courseTest.setWeekObj(object.getInt("weekObj"));
				courseTest.setLabTime(object.getString("labTime"));
				courseTest.setLabObj(object.getInt("labObj"));
				courseTest.setTestMemo(object.getString("testMemo"));
				courseTestList.add(courseTest);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return courseTestList;
	}
	
	

	/* ���¿γ�ʵ�� */
	public String UpdateCourseTest(CourseTest courseTest) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("testId", courseTest.getTestId() + "");
		params.put("courseObj", courseTest.getCourseObj());
		params.put("testName", courseTest.getTestName());
		params.put("weekObj", courseTest.getWeekObj() + "");
		params.put("labTime", courseTest.getLabTime());
		params.put("labObj", courseTest.getLabObj() + "");
		params.put("testMemo", courseTest.getTestMemo());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CourseTestServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	/* ��ѯ�γ�ʵ�� */
	public List<CourseTest> teacherQueryCourseTest(String teacherNo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "CourseTestServlet?action=teacherQuery";
		 
		urlString += "&teacherNo=" + teacherNo;
		 
		List<CourseTest> courseTestList = new ArrayList<CourseTest>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				CourseTest courseTest = new CourseTest();
				courseTest.setTestId(object.getInt("testId"));
				courseTest.setCourseObj(object.getString("courseObj"));
				courseTest.setTestName(object.getString("testName"));
				courseTest.setWeekObj(object.getInt("weekObj"));
				courseTest.setLabTime(object.getString("labTime"));
				courseTest.setLabObj(object.getInt("labObj"));
				courseTest.setTestMemo(object.getString("testMemo"));
				courseTestList.add(courseTest);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return courseTestList;
	}

	 
	

	/* ɾ���γ�ʵ�� */
	public String DeleteCourseTest(int testId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("testId", testId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CourseTestServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "�γ�ʵ����Ϣɾ��ʧ��!";
		}
	}

	/* ����ʵ��id��ȡ�γ�ʵ����� */
	public CourseTest GetCourseTest(int testId)  {
		List<CourseTest> courseTestList = new ArrayList<CourseTest>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("testId", testId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CourseTestServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				CourseTest courseTest = new CourseTest();
				courseTest.setTestId(object.getInt("testId"));
				courseTest.setCourseObj(object.getString("courseObj"));
				courseTest.setTestName(object.getString("testName"));
				courseTest.setWeekObj(object.getInt("weekObj"));
				courseTest.setLabTime(object.getString("labTime"));
				courseTest.setLabObj(object.getInt("labObj"));
				courseTest.setTestMemo(object.getString("testMemo"));
				courseTestList.add(courseTest);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = courseTestList.size();
		if(size>0) return courseTestList.get(0); 
		else return null; 
	}
}
