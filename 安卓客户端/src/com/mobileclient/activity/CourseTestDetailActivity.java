package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.CourseTest;
import com.mobileclient.service.CourseTestService;
import com.mobileclient.domain.Course;
import com.mobileclient.service.CourseService;
import com.mobileclient.domain.WeekInfo;
import com.mobileclient.service.WeekInfoService;
import com.mobileclient.domain.LabInfo;
import com.mobileclient.service.LabInfoService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class CourseTestDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明实验id控件
	private TextView TV_testId;
	// 声明所属课程控件
	private TextView TV_courseObj;
	// 声明实验名称控件
	private TextView TV_testName;
	// 声明上课周控件
	private TextView TV_weekObj;
	// 声明实验时间控件
	private TextView TV_labTime;
	// 声明上课实验室控件
	private TextView TV_labObj;
	// 声明实验备注控件
	private TextView TV_testMemo;
	/* 要保存的课程实验信息 */
	CourseTest courseTest = new CourseTest(); 
	/* 课程实验管理业务逻辑层 */
	private CourseTestService courseTestService = new CourseTestService();
	private CourseService courseService = new CourseService();
	private WeekInfoService weekInfoService = new WeekInfoService();
	private LabInfoService labInfoService = new LabInfoService();
	private int testId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.coursetest_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看课程实验详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_testId = (TextView) findViewById(R.id.TV_testId);
		TV_courseObj = (TextView) findViewById(R.id.TV_courseObj);
		TV_testName = (TextView) findViewById(R.id.TV_testName);
		TV_weekObj = (TextView) findViewById(R.id.TV_weekObj);
		TV_labTime = (TextView) findViewById(R.id.TV_labTime);
		TV_labObj = (TextView) findViewById(R.id.TV_labObj);
		TV_testMemo = (TextView) findViewById(R.id.TV_testMemo);
		Bundle extras = this.getIntent().getExtras();
		testId = extras.getInt("testId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CourseTestDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    courseTest = courseTestService.GetCourseTest(testId); 
		this.TV_testId.setText(courseTest.getTestId() + "");
		Course courseObj = courseService.GetCourse(courseTest.getCourseObj());
		this.TV_courseObj.setText(courseObj.getCourseName());
		this.TV_testName.setText(courseTest.getTestName());
		WeekInfo weekObj = weekInfoService.GetWeekInfo(courseTest.getWeekObj());
		this.TV_weekObj.setText(weekObj.getWeekName());
		this.TV_labTime.setText(courseTest.getLabTime());
		LabInfo labObj = labInfoService.GetLabInfo(courseTest.getLabObj());
		this.TV_labObj.setText(labObj.getLabName());
		this.TV_testMemo.setText(courseTest.getTestMemo());
	} 
}
