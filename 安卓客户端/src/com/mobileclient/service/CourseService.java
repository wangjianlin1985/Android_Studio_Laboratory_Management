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

/*实验课程管理业务逻辑层*/
public class CourseService {
	/* 添加实验课程 */
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

	/* 查询实验课程 */
	public List<Course> QueryCourse(Course queryConditionCourse) throws Exception {
		String urlString = HttpUtil.BASE_URL + "CourseServlet?action=query";
		if(queryConditionCourse != null) {
			urlString += "&courseNo=" + URLEncoder.encode(queryConditionCourse.getCourseNo(), "UTF-8") + "";
			urlString += "&classObj=" + URLEncoder.encode(queryConditionCourse.getClassObj(), "UTF-8") + "";
			urlString += "&courseName=" + URLEncoder.encode(queryConditionCourse.getCourseName(), "UTF-8") + "";
			urlString += "&teacherObj=" + URLEncoder.encode(queryConditionCourse.getTeacherObj(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
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
		//第2种是基于json数据格式解析，我们采用的是第2种
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
	
	
	/* 查询实验课程 */
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
	
	
	/* 老师查询自己的实验课程 */
	public List<Course> teacherQueryCourse(String teacherNo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "CourseServlet?action=teacherQuery";
		 
		urlString += "&teacherNo=" + teacherNo;

		 
		//基于json数据格式解析 
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

	
	

	/* 更新实验课程 */
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

	/* 删除实验课程 */
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
			return "实验课程信息删除失败!";
		}
	}

	/* 根据课程编号获取实验课程对象 */
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
