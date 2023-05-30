package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Course;
import com.mobileclient.util.HttpUtil;

/*ʵ��γ̹���ҵ���߼���*/
public class CourseService {
	/* ���ʵ��γ� */
	public String AddCourse(Course course) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("courseNo", course.getCourseNo());
		params.put("classObj", course.getClassObj());
		params.put("courseName", course.getCourseName());
		params.put("courseHours", course.getCourseHours() + "");
		params.put("courseScore", course.getCourseScore() + "");
		params.put("teacherObj", course.getTeacherObj());
		params.put("courseDesc", course.getCourseDesc());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CourseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯʵ��γ� */
	public List<Course> QueryCourse(Course queryConditionCourse) throws Exception {
		String urlString = HttpUtil.BASE_URL + "CourseServlet?action=query";
		if(queryConditionCourse != null) {
			urlString += "&courseNo=" + URLEncoder.encode(queryConditionCourse.getCourseNo(), "UTF-8") + "";
			urlString += "&classObj=" + URLEncoder.encode(queryConditionCourse.getClassObj(), "UTF-8") + "";
			urlString += "&courseName=" + URLEncoder.encode(queryConditionCourse.getCourseName(), "UTF-8") + "";
			urlString += "&teacherObj=" + URLEncoder.encode(queryConditionCourse.getTeacherObj(), "UTF-8") + "";
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		CourseListHandler courseListHander = new CourseListHandler();
		xr.setContentHandler(courseListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Course> courseList = courseListHander.getCourseList();
		return courseList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<Course> courseList = new ArrayList<Course>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Course course = new Course();
				course.setCourseNo(object.getString("courseNo"));
				course.setClassObj(object.getString("classObj"));
				course.setCourseName(object.getString("courseName"));
				course.setCourseHours(object.getInt("courseHours"));
				course.setCourseScore((float) object.getDouble("courseScore"));
				course.setTeacherObj(object.getString("teacherObj"));
				course.setCourseDesc(object.getString("courseDesc"));
				courseList.add(course);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return courseList;
	}
	
	
	/* ��ѯʵ��γ� */
	public List<Course> studentQueryCourse(String studentNo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "CourseServlet?action=studentQuery";
	 
		urlString += "&studentNo=" + studentNo;
		 
		List<Course> courseList = new ArrayList<Course>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Course course = new Course();
				course.setCourseNo(object.getString("courseNo"));
				course.setClassObj(object.getString("classObj"));
				course.setCourseName(object.getString("courseName"));
				course.setCourseHours(object.getInt("courseHours"));
				course.setCourseScore((float) object.getDouble("courseScore"));
				course.setTeacherObj(object.getString("teacherObj"));
				course.setCourseDesc(object.getString("courseDesc"));
				courseList.add(course);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return courseList;
	}
	
	
	/* ��ʦ��ѯ�Լ���ʵ��γ� */
	public List<Course> teacherQueryCourse(String teacherNo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "CourseServlet?action=teacherQuery";
		 
		urlString += "&teacherNo=" + teacherNo;

		 
		//����json���ݸ�ʽ���� 
		List<Course> courseList = new ArrayList<Course>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Course course = new Course();
				course.setCourseNo(object.getString("courseNo"));
				course.setClassObj(object.getString("classObj"));
				course.setCourseName(object.getString("courseName"));
				course.setCourseHours(object.getInt("courseHours"));
				course.setCourseScore((float) object.getDouble("courseScore"));
				course.setTeacherObj(object.getString("teacherObj"));
				course.setCourseDesc(object.getString("courseDesc"));
				courseList.add(course);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return courseList;
	}

	
	

	/* ����ʵ��γ� */
	public String UpdateCourse(Course course) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("courseNo", course.getCourseNo());
		params.put("classObj", course.getClassObj());
		params.put("courseName", course.getCourseName());
		params.put("courseHours", course.getCourseHours() + "");
		params.put("courseScore", course.getCourseScore() + "");
		params.put("teacherObj", course.getTeacherObj());
		params.put("courseDesc", course.getCourseDesc());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CourseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ��ʵ��γ� */
	public String DeleteCourse(String courseNo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("courseNo", courseNo);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CourseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "ʵ��γ���Ϣɾ��ʧ��!";
		}
	}

	/* ���ݿγ̱�Ż�ȡʵ��γ̶��� */
	public Course GetCourse(String courseNo)  {
		List<Course> courseList = new ArrayList<Course>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("courseNo", courseNo);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CourseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Course course = new Course();
				course.setCourseNo(object.getString("courseNo"));
				course.setClassObj(object.getString("classObj"));
				course.setCourseName(object.getString("courseName"));
				course.setCourseHours(object.getInt("courseHours"));
				course.setCourseScore((float) object.getDouble("courseScore"));
				course.setTeacherObj(object.getString("teacherObj"));
				course.setCourseDesc(object.getString("courseDesc"));
				courseList.add(course);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = courseList.size();
		if(size>0) return courseList.get(0); 
		else return null; 
	}
}
