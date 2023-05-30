package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.CourseTest;
import com.mobileclient.domain.Course;
import com.mobileclient.service.CourseService;
import com.mobileclient.domain.WeekInfo;
import com.mobileclient.service.WeekInfoService;
import com.mobileclient.domain.LabInfo;
import com.mobileclient.service.LabInfoService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class CourseTestQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明所属课程下拉框
	private Spinner spinner_courseObj;
	private ArrayAdapter<String> courseObj_adapter;
	private static  String[] courseObj_ShowText  = null;
	private List<Course> courseList = null; 
	/*实验课程管理业务逻辑层*/
	private CourseService courseService = new CourseService();
	// 声明实验名称输入框
	private EditText ET_testName;
	// 声明上课周下拉框
	private Spinner spinner_weekObj;
	private ArrayAdapter<String> weekObj_adapter;
	private static  String[] weekObj_ShowText  = null;
	private List<WeekInfo> weekInfoList = null; 
	/*周信息管理业务逻辑层*/
	private WeekInfoService weekInfoService = new WeekInfoService();
	// 声明上课实验室下拉框
	private Spinner spinner_labObj;
	private ArrayAdapter<String> labObj_adapter;
	private static  String[] labObj_ShowText  = null;
	private List<LabInfo> labInfoList = null; 
	/*实验室管理业务逻辑层*/
	private LabInfoService labInfoService = new LabInfoService();
	/*查询过滤条件保存到这个对象中*/
	private CourseTest queryConditionCourseTest = new CourseTest();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.coursetest_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置课程实验查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_courseObj = (Spinner) findViewById(R.id.Spinner_courseObj);
		// 获取所有的实验课程
		try {
			courseList = courseService.QueryCourse(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int courseCount = courseList.size();
		courseObj_ShowText = new String[courseCount+1];
		courseObj_ShowText[0] = "不限制";
		for(int i=1;i<=courseCount;i++) { 
			courseObj_ShowText[i] = courseList.get(i-1).getCourseName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		courseObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, courseObj_ShowText);
		// 设置所属课程下拉列表的风格
		courseObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_courseObj.setAdapter(courseObj_adapter);
		// 添加事件Spinner事件监听
		spinner_courseObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionCourseTest.setCourseObj(courseList.get(arg2-1).getCourseNo()); 
				else
					queryConditionCourseTest.setCourseObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_courseObj.setVisibility(View.VISIBLE);
		ET_testName = (EditText) findViewById(R.id.ET_testName);
		spinner_weekObj = (Spinner) findViewById(R.id.Spinner_weekObj);
		// 获取所有的周信息
		try {
			weekInfoList = weekInfoService.QueryWeekInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int weekInfoCount = weekInfoList.size();
		weekObj_ShowText = new String[weekInfoCount+1];
		weekObj_ShowText[0] = "不限制";
		for(int i=1;i<=weekInfoCount;i++) { 
			weekObj_ShowText[i] = weekInfoList.get(i-1).getWeekName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		weekObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, weekObj_ShowText);
		// 设置上课周下拉列表的风格
		weekObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_weekObj.setAdapter(weekObj_adapter);
		// 添加事件Spinner事件监听
		spinner_weekObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionCourseTest.setWeekObj(weekInfoList.get(arg2-1).getWeekId()); 
				else
					queryConditionCourseTest.setWeekObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_weekObj.setVisibility(View.VISIBLE);
		spinner_labObj = (Spinner) findViewById(R.id.Spinner_labObj);
		// 获取所有的实验室
		try {
			labInfoList = labInfoService.QueryLabInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int labInfoCount = labInfoList.size();
		labObj_ShowText = new String[labInfoCount+1];
		labObj_ShowText[0] = "不限制";
		for(int i=1;i<=labInfoCount;i++) { 
			labObj_ShowText[i] = labInfoList.get(i-1).getLabName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		labObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labObj_ShowText);
		// 设置上课实验室下拉列表的风格
		labObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_labObj.setAdapter(labObj_adapter);
		// 添加事件Spinner事件监听
		spinner_labObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionCourseTest.setLabObj(labInfoList.get(arg2-1).getLabId()); 
				else
					queryConditionCourseTest.setLabObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_labObj.setVisibility(View.VISIBLE);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionCourseTest.setTestName(ET_testName.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionCourseTest", queryConditionCourseTest);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
