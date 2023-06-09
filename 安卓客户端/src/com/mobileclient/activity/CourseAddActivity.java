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
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明课程编号输入框
	private EditText ET_courseNo;
	// 声明所属班级下拉框
	private Spinner spinner_classObj;
	private ArrayAdapter<String> classObj_adapter;
	private static  String[] classObj_ShowText  = null;
	private List<ClassInfo> classInfoList = null;
	/*所属班级管理业务逻辑层*/
	private ClassInfoService classInfoService = new ClassInfoService();
	// 声明课程名称输入框
	private EditText ET_courseName;
	// 声明课程总学时输入框
	private EditText ET_courseHours;
	// 声明课程学分输入框
	private EditText ET_courseScore;
	// 声明上课老师下拉框
	private Spinner spinner_teacherObj;
	private ArrayAdapter<String> teacherObj_adapter;
	private static  String[] teacherObj_ShowText  = null;
	private List<Teacher> teacherList = null;
	/*上课老师管理业务逻辑层*/
	private TeacherService teacherService = new TeacherService();
	// 声明课程描述输入框
	private EditText ET_courseDesc;
	protected String carmera_path;
	/*要保存的实验课程信息*/
	Course course = new Course();
	/*实验课程管理业务逻辑层*/
	private CourseService courseService = new CourseService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.course_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加实验课程");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_courseNo = (EditText) findViewById(R.id.ET_courseNo);
		spinner_classObj = (Spinner) findViewById(R.id.Spinner_classObj);
		// 获取所有的所属班级
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
		// 将可选内容与ArrayAdapter连接起来
		classObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, classObj_ShowText);
		// 设置下拉列表的风格
		classObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_classObj.setAdapter(classObj_adapter);
		// 添加事件Spinner事件监听
		spinner_classObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				course.setClassObj(classInfoList.get(arg2).getClassNo()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_classObj.setVisibility(View.VISIBLE);
		ET_courseName = (EditText) findViewById(R.id.ET_courseName);
		ET_courseHours = (EditText) findViewById(R.id.ET_courseHours);
		ET_courseScore = (EditText) findViewById(R.id.ET_courseScore);
		spinner_teacherObj = (Spinner) findViewById(R.id.Spinner_teacherObj);
		// 获取所有的上课老师
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
		// 将可选内容与ArrayAdapter连接起来
		teacherObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, teacherObj_ShowText);
		// 设置下拉列表的风格
		teacherObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_teacherObj.setAdapter(teacherObj_adapter);
		// 添加事件Spinner事件监听
		spinner_teacherObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				course.setTeacherObj(teacherList.get(arg2).getTeacherNo()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_teacherObj.setVisibility(View.VISIBLE);
		ET_courseDesc = (EditText) findViewById(R.id.ET_courseDesc);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加实验课程按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取课程编号*/
					if(ET_courseNo.getText().toString().equals("")) {
						Toast.makeText(CourseAddActivity.this, "课程编号输入不能为空!", Toast.LENGTH_LONG).show();
						ET_courseNo.setFocusable(true);
						ET_courseNo.requestFocus();
						return;
					}
					course.setCourseNo(ET_courseNo.getText().toString());
					/*验证获取课程名称*/ 
					if(ET_courseName.getText().toString().equals("")) {
						Toast.makeText(CourseAddActivity.this, "课程名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_courseName.setFocusable(true);
						ET_courseName.requestFocus();
						return;	
					}
					course.setCourseName(ET_courseName.getText().toString());
					/*验证获取课程总学时*/ 
					if(ET_courseHours.getText().toString().equals("")) {
						Toast.makeText(CourseAddActivity.this, "课程总学时输入不能为空!", Toast.LENGTH_LONG).show();
						ET_courseHours.setFocusable(true);
						ET_courseHours.requestFocus();
						return;	
					}
					course.setCourseHours(Integer.parseInt(ET_courseHours.getText().toString()));
					/*验证获取课程学分*/ 
					if(ET_courseScore.getText().toString().equals("")) {
						Toast.makeText(CourseAddActivity.this, "课程学分输入不能为空!", Toast.LENGTH_LONG).show();
						ET_courseScore.setFocusable(true);
						ET_courseScore.requestFocus();
						return;	
					}
					course.setCourseScore(Float.parseFloat(ET_courseScore.getText().toString()));
					/*验证获取课程描述*/ 
					if(ET_courseDesc.getText().toString().equals("")) {
						Toast.makeText(CourseAddActivity.this, "课程描述输入不能为空!", Toast.LENGTH_LONG).show();
						ET_courseDesc.setFocusable(true);
						ET_courseDesc.requestFocus();
						return;	
					}
					course.setCourseDesc(ET_courseDesc.getText().toString());
					/*调用业务逻辑层上传实验课程信息*/
					CourseAddActivity.this.setTitle("正在上传实验课程信息，稍等...");
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
