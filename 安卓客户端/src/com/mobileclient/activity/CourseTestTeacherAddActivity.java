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
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明所属课程下拉框
	private Spinner spinner_courseObj;
	private ArrayAdapter<String> courseObj_adapter;
	private static  String[] courseObj_ShowText  = null;
	private List<Course> courseList = null;
	/*所属课程管理业务逻辑层*/
	private CourseService courseService = new CourseService();
	// 声明实验名称输入框
	private EditText ET_testName;
	// 声明上课周下拉框
	private Spinner spinner_weekObj;
	private ArrayAdapter<String> weekObj_adapter;
	private static  String[] weekObj_ShowText  = null;
	private List<WeekInfo> weekInfoList = null;
	/*上课周管理业务逻辑层*/
	private WeekInfoService weekInfoService = new WeekInfoService();
	// 声明实验时间输入框
	private EditText ET_labTime;
	// 声明上课实验室下拉框
	private Spinner spinner_labObj;
	private ArrayAdapter<String> labObj_adapter;
	private static  String[] labObj_ShowText  = null;
	private List<LabInfo> labInfoList = null;
	/*上课实验室管理业务逻辑层*/
	private LabInfoService labInfoService = new LabInfoService();
	// 声明实验备注输入框
	private EditText ET_testMemo;
	protected String carmera_path;
	/*要保存的课程实验信息*/
	CourseTest courseTest = new CourseTest();
	/*课程实验管理业务逻辑层*/
	private CourseTestService courseTestService = new CourseTestService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.coursetest_teacher_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加课程实验");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		spinner_courseObj = (Spinner) findViewById(R.id.Spinner_courseObj);
		// 获取所有的所属课程
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
		// 将可选内容与ArrayAdapter连接起来
		courseObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, courseObj_ShowText);
		// 设置下拉列表的风格
		courseObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_courseObj.setAdapter(courseObj_adapter);
		// 添加事件Spinner事件监听
		spinner_courseObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				courseTest.setCourseObj(courseList.get(arg2).getCourseNo()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_courseObj.setVisibility(View.VISIBLE);
		ET_testName = (EditText) findViewById(R.id.ET_testName);
		spinner_weekObj = (Spinner) findViewById(R.id.Spinner_weekObj);
		// 获取所有的上课周
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
		// 将可选内容与ArrayAdapter连接起来
		weekObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, weekObj_ShowText);
		// 设置下拉列表的风格
		weekObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_weekObj.setAdapter(weekObj_adapter);
		// 添加事件Spinner事件监听
		spinner_weekObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				courseTest.setWeekObj(weekInfoList.get(arg2).getWeekId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_weekObj.setVisibility(View.VISIBLE);
		ET_labTime = (EditText) findViewById(R.id.ET_labTime);
		spinner_labObj = (Spinner) findViewById(R.id.Spinner_labObj);
		// 获取所有的上课实验室
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
		// 将可选内容与ArrayAdapter连接起来
		labObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labObj_ShowText);
		// 设置下拉列表的风格
		labObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_labObj.setAdapter(labObj_adapter);
		// 添加事件Spinner事件监听
		spinner_labObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				courseTest.setLabObj(labInfoList.get(arg2).getLabId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_labObj.setVisibility(View.VISIBLE);
		ET_testMemo = (EditText) findViewById(R.id.ET_testMemo);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加课程实验按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取实验名称*/ 
					if(ET_testName.getText().toString().equals("")) {
						Toast.makeText(CourseTestTeacherAddActivity.this, "实验名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_testName.setFocusable(true);
						ET_testName.requestFocus();
						return;	
					}
					courseTest.setTestName(ET_testName.getText().toString());
					/*验证获取实验时间*/ 
					if(ET_labTime.getText().toString().equals("")) {
						Toast.makeText(CourseTestTeacherAddActivity.this, "实验时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_labTime.setFocusable(true);
						ET_labTime.requestFocus();
						return;	
					}
					courseTest.setLabTime(ET_labTime.getText().toString());
					/*验证获取实验备注*/ 
					if(ET_testMemo.getText().toString().equals("")) {
						Toast.makeText(CourseTestTeacherAddActivity.this, "实验备注输入不能为空!", Toast.LENGTH_LONG).show();
						ET_testMemo.setFocusable(true);
						ET_testMemo.requestFocus();
						return;	
					}
					courseTest.setTestMemo(ET_testMemo.getText().toString());
					/*调用业务逻辑层上传课程实验信息*/
					CourseTestTeacherAddActivity.this.setTitle("正在上传课程实验信息，稍等...");
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
