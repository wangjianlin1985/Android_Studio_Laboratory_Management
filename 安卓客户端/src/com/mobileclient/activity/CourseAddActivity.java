package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Course;
import com.mobileclient.service.CourseService;
import com.mobileclient.domain.ClassInfo;
import com.mobileclient.service.ClassInfoService;
import com.mobileclient.domain.Teacher;
import com.mobileclient.service.TeacherService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class CourseAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// �����γ̱�������
	private EditText ET_courseNo;
	// ���������༶������
	private Spinner spinner_classObj;
	private ArrayAdapter<String> classObj_adapter;
	private static  String[] classObj_ShowText  = null;
	private List<ClassInfo> classInfoList = null;
	/*�����༶����ҵ���߼���*/
	private ClassInfoService classInfoService = new ClassInfoService();
	// �����γ����������
	private EditText ET_courseName;
	// �����γ���ѧʱ�����
	private EditText ET_courseHours;
	// �����γ�ѧ�������
	private EditText ET_courseScore;
	// �����Ͽ���ʦ������
	private Spinner spinner_teacherObj;
	private ArrayAdapter<String> teacherObj_adapter;
	private static  String[] teacherObj_ShowText  = null;
	private List<Teacher> teacherList = null;
	/*�Ͽ���ʦ����ҵ���߼���*/
	private TeacherService teacherService = new TeacherService();
	// �����γ����������
	private EditText ET_courseDesc;
	protected String carmera_path;
	/*Ҫ�����ʵ��γ���Ϣ*/
	Course course = new Course();
	/*ʵ��γ̹���ҵ���߼���*/
	private CourseService courseService = new CourseService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.course_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("���ʵ��γ�");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_courseNo = (EditText) findViewById(R.id.ET_courseNo);
		spinner_classObj = (Spinner) findViewById(R.id.Spinner_classObj);
		// ��ȡ���е������༶
		try {
			classInfoList = classInfoService.QueryClassInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int classInfoCount = classInfoList.size();
		classObj_ShowText = new String[classInfoCount];
		for(int i=0;i<classInfoCount;i++) { 
			classObj_ShowText[i] = classInfoList.get(i).getClassName();
		}
		// ����ѡ������ArrayAdapter��������
		classObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, classObj_ShowText);
		// ���������б�ķ��
		classObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_classObj.setAdapter(classObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_classObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				course.setClassObj(classInfoList.get(arg2).getClassNo()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_classObj.setVisibility(View.VISIBLE);
		ET_courseName = (EditText) findViewById(R.id.ET_courseName);
		ET_courseHours = (EditText) findViewById(R.id.ET_courseHours);
		ET_courseScore = (EditText) findViewById(R.id.ET_courseScore);
		spinner_teacherObj = (Spinner) findViewById(R.id.Spinner_teacherObj);
		// ��ȡ���е��Ͽ���ʦ
		try {
			teacherList = teacherService.QueryTeacher(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int teacherCount = teacherList.size();
		teacherObj_ShowText = new String[teacherCount];
		for(int i=0;i<teacherCount;i++) { 
			teacherObj_ShowText[i] = teacherList.get(i).getName();
		}
		// ����ѡ������ArrayAdapter��������
		teacherObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, teacherObj_ShowText);
		// ���������б�ķ��
		teacherObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_teacherObj.setAdapter(teacherObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_teacherObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				course.setTeacherObj(teacherList.get(arg2).getTeacherNo()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_teacherObj.setVisibility(View.VISIBLE);
		ET_courseDesc = (EditText) findViewById(R.id.ET_courseDesc);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*�������ʵ��γ̰�ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ�γ̱��*/
					if(ET_courseNo.getText().toString().equals("")) {
						Toast.makeText(CourseAddActivity.this, "�γ̱�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_courseNo.setFocusable(true);
						ET_courseNo.requestFocus();
						return;
					}
					course.setCourseNo(ET_courseNo.getText().toString());
					/*��֤��ȡ�γ�����*/ 
					if(ET_courseName.getText().toString().equals("")) {
						Toast.makeText(CourseAddActivity.this, "�γ��������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_courseName.setFocusable(true);
						ET_courseName.requestFocus();
						return;	
					}
					course.setCourseName(ET_courseName.getText().toString());
					/*��֤��ȡ�γ���ѧʱ*/ 
					if(ET_courseHours.getText().toString().equals("")) {
						Toast.makeText(CourseAddActivity.this, "�γ���ѧʱ���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_courseHours.setFocusable(true);
						ET_courseHours.requestFocus();
						return;	
					}
					course.setCourseHours(Integer.parseInt(ET_courseHours.getText().toString()));
					/*��֤��ȡ�γ�ѧ��*/ 
					if(ET_courseScore.getText().toString().equals("")) {
						Toast.makeText(CourseAddActivity.this, "�γ�ѧ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_courseScore.setFocusable(true);
						ET_courseScore.requestFocus();
						return;	
					}
					course.setCourseScore(Float.parseFloat(ET_courseScore.getText().toString()));
					/*��֤��ȡ�γ�����*/ 
					if(ET_courseDesc.getText().toString().equals("")) {
						Toast.makeText(CourseAddActivity.this, "�γ��������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_courseDesc.setFocusable(true);
						ET_courseDesc.requestFocus();
						return;	
					}
					course.setCourseDesc(ET_courseDesc.getText().toString());
					/*����ҵ���߼����ϴ�ʵ��γ���Ϣ*/
					CourseAddActivity.this.setTitle("�����ϴ�ʵ��γ���Ϣ���Ե�...");
					String result = courseService.AddCourse(course);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
