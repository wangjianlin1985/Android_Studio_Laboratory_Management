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
	// �������ذ�ť
	private Button btnReturn;
	// ����ʵ��id�ؼ�
	private TextView TV_testId;
	// ���������γ̿ؼ�
	private TextView TV_courseObj;
	// ����ʵ�����ƿؼ�
	private TextView TV_testName;
	// �����Ͽ��ܿؼ�
	private TextView TV_weekObj;
	// ����ʵ��ʱ��ؼ�
	private TextView TV_labTime;
	// �����Ͽ�ʵ���ҿؼ�
	private TextView TV_labObj;
	// ����ʵ�鱸ע�ؼ�
	private TextView TV_testMemo;
	/* Ҫ����Ŀγ�ʵ����Ϣ */
	CourseTest courseTest = new CourseTest(); 
	/* �γ�ʵ�����ҵ���߼��� */
	private CourseTestService courseTestService = new CourseTestService();
	private CourseService courseService = new CourseService();
	private WeekInfoService weekInfoService = new WeekInfoService();
	private LabInfoService labInfoService = new LabInfoService();
	private int testId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.coursetest_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴�γ�ʵ������");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
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
	/* ��ʼ����ʾ������������ */
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
