package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.CourseTest;
import com.mobileclient.service.CourseTestService;
import com.mobileclient.domain.Course;
import com.mobileclient.service.CourseService;
import com.mobileclient.domain.WeekInfo;
import com.mobileclient.service.WeekInfoService;
import com.mobileclient.domain.LabInfo;
import com.mobileclient.service.LabInfoService;
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
public class CourseTestTeacherAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// ���������γ�������
	private Spinner spinner_courseObj;
	private ArrayAdapter<String> courseObj_adapter;
	private static  String[] courseObj_ShowText  = null;
	private List<Course> courseList = null;
	/*�����γ̹���ҵ���߼���*/
	private CourseService courseService = new CourseService();
	// ����ʵ�����������
	private EditText ET_testName;
	// �����Ͽ���������
	private Spinner spinner_weekObj;
	private ArrayAdapter<String> weekObj_adapter;
	private static  String[] weekObj_ShowText  = null;
	private List<WeekInfo> weekInfoList = null;
	/*�Ͽ��ܹ���ҵ���߼���*/
	private WeekInfoService weekInfoService = new WeekInfoService();
	// ����ʵ��ʱ�������
	private EditText ET_labTime;
	// �����Ͽ�ʵ����������
	private Spinner spinner_labObj;
	private ArrayAdapter<String> labObj_adapter;
	private static  String[] labObj_ShowText  = null;
	private List<LabInfo> labInfoList = null;
	/*�Ͽ�ʵ���ҹ���ҵ���߼���*/
	private LabInfoService labInfoService = new LabInfoService();
	// ����ʵ�鱸ע�����
	private EditText ET_testMemo;
	protected String carmera_path;
	/*Ҫ����Ŀγ�ʵ����Ϣ*/
	CourseTest courseTest = new CourseTest();
	/*�γ�ʵ�����ҵ���߼���*/
	private CourseTestService courseTestService = new CourseTestService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.coursetest_teacher_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("��ӿγ�ʵ��");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		spinner_courseObj = (Spinner) findViewById(R.id.Spinner_courseObj);
		// ��ȡ���е������γ�
		try {
			Declare declare = (Declare) CourseTestTeacherAddActivity.this.getApplication();
			courseList = courseService.teacherQueryCourse(declare.getUserName());
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int courseCount = courseList.size();
		courseObj_ShowText = new String[courseCount];
		for(int i=0;i<courseCount;i++) { 
			courseObj_ShowText[i] = courseList.get(i).getCourseName();
		}
		// ����ѡ������ArrayAdapter��������
		courseObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, courseObj_ShowText);
		// ���������б�ķ��
		courseObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_courseObj.setAdapter(courseObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_courseObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				courseTest.setCourseObj(courseList.get(arg2).getCourseNo()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_courseObj.setVisibility(View.VISIBLE);
		ET_testName = (EditText) findViewById(R.id.ET_testName);
		spinner_weekObj = (Spinner) findViewById(R.id.Spinner_weekObj);
		// ��ȡ���е��Ͽ���
		try {
			weekInfoList = weekInfoService.QueryWeekInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int weekInfoCount = weekInfoList.size();
		weekObj_ShowText = new String[weekInfoCount];
		for(int i=0;i<weekInfoCount;i++) { 
			weekObj_ShowText[i] = weekInfoList.get(i).getWeekName();
		}
		// ����ѡ������ArrayAdapter��������
		weekObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, weekObj_ShowText);
		// ���������б�ķ��
		weekObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_weekObj.setAdapter(weekObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_weekObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				courseTest.setWeekObj(weekInfoList.get(arg2).getWeekId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_weekObj.setVisibility(View.VISIBLE);
		ET_labTime = (EditText) findViewById(R.id.ET_labTime);
		spinner_labObj = (Spinner) findViewById(R.id.Spinner_labObj);
		// ��ȡ���е��Ͽ�ʵ����
		try {
			labInfoList = labInfoService.QueryLabInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int labInfoCount = labInfoList.size();
		labObj_ShowText = new String[labInfoCount];
		for(int i=0;i<labInfoCount;i++) { 
			labObj_ShowText[i] = labInfoList.get(i).getLabName();
		}
		// ����ѡ������ArrayAdapter��������
		labObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labObj_ShowText);
		// ���������б�ķ��
		labObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_labObj.setAdapter(labObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_labObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				courseTest.setLabObj(labInfoList.get(arg2).getLabId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_labObj.setVisibility(View.VISIBLE);
		ET_testMemo = (EditText) findViewById(R.id.ET_testMemo);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*������ӿγ�ʵ�鰴ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡʵ������*/ 
					if(ET_testName.getText().toString().equals("")) {
						Toast.makeText(CourseTestTeacherAddActivity.this, "ʵ���������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_testName.setFocusable(true);
						ET_testName.requestFocus();
						return;	
					}
					courseTest.setTestName(ET_testName.getText().toString());
					/*��֤��ȡʵ��ʱ��*/ 
					if(ET_labTime.getText().toString().equals("")) {
						Toast.makeText(CourseTestTeacherAddActivity.this, "ʵ��ʱ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_labTime.setFocusable(true);
						ET_labTime.requestFocus();
						return;	
					}
					courseTest.setLabTime(ET_labTime.getText().toString());
					/*��֤��ȡʵ�鱸ע*/ 
					if(ET_testMemo.getText().toString().equals("")) {
						Toast.makeText(CourseTestTeacherAddActivity.this, "ʵ�鱸ע���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_testMemo.setFocusable(true);
						ET_testMemo.requestFocus();
						return;	
					}
					courseTest.setTestMemo(ET_testMemo.getText().toString());
					/*����ҵ���߼����ϴ��γ�ʵ����Ϣ*/
					CourseTestTeacherAddActivity.this.setTitle("�����ϴ��γ�ʵ����Ϣ���Ե�...");
					String result = courseTestService.AddCourseTest(courseTest);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					//Intent intent = getIntent();
					//setResult(RESULT_OK,intent);
					//finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
