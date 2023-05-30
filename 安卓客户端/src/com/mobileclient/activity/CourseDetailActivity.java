package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Course;
import com.mobileclient.service.CourseService;
import com.mobileclient.domain.ClassInfo;
import com.mobileclient.service.ClassInfoService;
import com.mobileclient.domain.Teacher;
import com.mobileclient.service.TeacherService;
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
public class CourseDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明课程编号控件
	private TextView TV_courseNo;
	// 声明所属班级控件
	private TextView TV_classObj;
	// 声明课程名称控件
	private TextView TV_courseName;
	// 声明课程总学时控件
	private TextView TV_courseHours;
	// 声明课程学分控件
	private TextView TV_courseScore;
	// 声明上课老师控件
	private TextView TV_teacherObj;
	// 声明课程描述控件
	private TextView TV_courseDesc;
	/* 要保存的实验课程信息 */
	Course course = new Course(); 
	/* 实验课程管理业务逻辑层 */
	private CourseService courseService = new CourseService();
	private ClassInfoService classInfoService = new ClassInfoService();
	private TeacherService teacherService = new TeacherService();
	private String courseNo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.course_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看实验课程详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_courseNo = (TextView) findViewById(R.id.TV_courseNo);
		TV_classObj = (TextView) findViewById(R.id.TV_classObj);
		TV_courseName = (TextView) findViewById(R.id.TV_courseName);
		TV_courseHours = (TextView) findViewById(R.id.TV_courseHours);
		TV_courseScore = (TextView) findViewById(R.id.TV_courseScore);
		TV_teacherObj = (TextView) findViewById(R.id.TV_teacherObj);
		TV_courseDesc = (TextView) findViewById(R.id.TV_courseDesc);
		Bundle extras = this.getIntent().getExtras();
		courseNo = extras.getString("courseNo");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CourseDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    course = courseService.GetCourse(courseNo); 
		this.TV_courseNo.setText(course.getCourseNo());
		ClassInfo classObj = classInfoService.GetClassInfo(course.getClassObj());
		this.TV_classObj.setText(classObj.getClassName());
		this.TV_courseName.setText(course.getCourseName());
		this.TV_courseHours.setText(course.getCourseHours() + "");
		this.TV_courseScore.setText(course.getCourseScore() + "");
		Teacher teacherObj = teacherService.GetTeacher(course.getTeacherObj());
		this.TV_teacherObj.setText(teacherObj.getName());
		this.TV_courseDesc.setText(course.getCourseDesc());
	} 
}
