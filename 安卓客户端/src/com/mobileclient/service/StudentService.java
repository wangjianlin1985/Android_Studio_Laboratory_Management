package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Student;
import com.mobileclient.util.HttpUtil;

/*ѧ������ҵ���߼���*/
public class StudentService {
	/* ���ѧ�� */
	public String AddStudent(Student student) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("studentNo", student.getStudentNo());
		params.put("password", student.getPassword());
		params.put("classObj", student.getClassObj());
		params.put("name", student.getName());
		params.put("gender", student.getGender());
		params.put("birthDate", student.getBirthDate().toString());
		params.put("studentPhoto", student.getStudentPhoto());
		params.put("telephone", student.getTelephone());
		params.put("email", student.getEmail());
		params.put("address", student.getAddress());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "StudentServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯѧ�� */
	public List<Student> QueryStudent(Student queryConditionStudent) throws Exception {
		String urlString = HttpUtil.BASE_URL + "StudentServlet?action=query";
		if(queryConditionStudent != null) {
			urlString += "&studentNo=" + URLEncoder.encode(queryConditionStudent.getStudentNo(), "UTF-8") + "";
			urlString += "&classObj=" + URLEncoder.encode(queryConditionStudent.getClassObj(), "UTF-8") + "";
			urlString += "&name=" + URLEncoder.encode(queryConditionStudent.getName(), "UTF-8") + "";
			if(queryConditionStudent.getBirthDate() != null) {
				urlString += "&birthDate=" + URLEncoder.encode(queryConditionStudent.getBirthDate().toString(), "UTF-8");
			}
			urlString += "&telephone=" + URLEncoder.encode(queryConditionStudent.getTelephone(), "UTF-8") + "";
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		StudentListHandler studentListHander = new StudentListHandler();
		xr.setContentHandler(studentListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Student> studentList = studentListHander.getStudentList();
		return studentList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<Student> studentList = new ArrayList<Student>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Student student = new Student();
				student.setStudentNo(object.getString("studentNo"));
				student.setPassword(object.getString("password"));
				student.setClassObj(object.getString("classObj"));
				student.setName(object.getString("name"));
				student.setGender(object.getString("gender"));
				student.setBirthDate(Timestamp.valueOf(object.getString("birthDate")));
				student.setStudentPhoto(object.getString("studentPhoto"));
				student.setTelephone(object.getString("telephone"));
				student.setEmail(object.getString("email"));
				student.setAddress(object.getString("address"));
				studentList.add(student);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentList;
	}

	/* ����ѧ�� */
	public String UpdateStudent(Student student) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("studentNo", student.getStudentNo());
		params.put("password", student.getPassword());
		params.put("classObj", student.getClassObj());
		params.put("name", student.getName());
		params.put("gender", student.getGender());
		params.put("birthDate", student.getBirthDate().toString());
		params.put("studentPhoto", student.getStudentPhoto());
		params.put("telephone", student.getTelephone());
		params.put("email", student.getEmail());
		params.put("address", student.getAddress());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "StudentServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ��ѧ�� */
	public String DeleteStudent(String studentNo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("studentNo", studentNo);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "StudentServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "ѧ����Ϣɾ��ʧ��!";
		}
	}

	/* ����ѧ�Ż�ȡѧ������ */
	public Student GetStudent(String studentNo)  {
		List<Student> studentList = new ArrayList<Student>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("studentNo", studentNo);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "StudentServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Student student = new Student();
				student.setStudentNo(object.getString("studentNo"));
				student.setPassword(object.getString("password"));
				student.setClassObj(object.getString("classObj"));
				student.setName(object.getString("name"));
				student.setGender(object.getString("gender"));
				student.setBirthDate(Timestamp.valueOf(object.getString("birthDate")));
				student.setStudentPhoto(object.getString("studentPhoto"));
				student.setTelephone(object.getString("telephone"));
				student.setEmail(object.getString("email"));
				student.setAddress(object.getString("address"));
				studentList.add(student);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = studentList.size();
		if(size>0) return studentList.get(0); 
		else return null; 
	}
}
