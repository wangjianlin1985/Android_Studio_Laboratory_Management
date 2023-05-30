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
	// ������ѯ��ť
	private Button btnQuery;
	// ���������γ�������
	private Spinner spinner_courseObj;
	private ArrayAdapter<String> courseObj_adapter;
	private static  String[] courseObj_ShowText  = null;
	private List<Course> courseList = null; 
	/*ʵ��γ̹���ҵ���߼���*/
	private CourseService courseService = new CourseService();
	// ����ʵ�����������
	private EditText ET_testName;
	// �����Ͽ���������
	private Spinner spinner_weekObj;
	private ArrayAdapter<String> weekObj_adapter;
	private static  String[] weekObj_ShowText  = null;
	private List<WeekInfo> weekInfoList = null; 
	/*����Ϣ����ҵ���߼���*/
	private WeekInfoService weekInfoService = new WeekInfoService();
	// �����Ͽ�ʵ����������
	private Spinner spinner_labObj;
	private ArrayAdapter<String> labObj_adapter;
	private static  String[] labObj_ShowText  = null;
	private List<LabInfo> labInfoList = null; 
	/*ʵ���ҹ���ҵ���߼���*/
	private LabInfoService labInfoService = new LabInfoService();
	/*��ѯ�����������浽���������*/
	private CourseTest queryConditionCourseTest = new CourseTest();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.coursetest_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("���ÿγ�ʵ���ѯ����");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_courseObj = (Spinner) findViewById(R.id.Spinner_courseObj);
		// ��ȡ���е�ʵ��γ�
		try {
			courseList = courseService.QueryCourse(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int courseCount = courseList.size();
		courseObj_ShowText = new String[courseCount+1];
		courseObj_ShowText[0] = "������";
		for(int i=1;i<=courseCount;i++) { 
			courseObj_ShowText[i] = courseList.get(i-1).getCourseName();
		} 
		// ����ѡ������ArrayAdapter��������
		courseObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, courseObj_ShowText);
		// ���������γ������б�ķ��
		courseObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_courseObj.setAdapter(courseObj_adapter);
		// ����¼�Spinner�¼�����
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
		// ����Ĭ��ֵ
		spinner_courseObj.setVisibility(View.VISIBLE);
		ET_testName = (EditText) findViewById(R.id.ET_testName);
		spinner_weekObj = (Spinner) findViewById(R.id.Spinner_weekObj);
		// ��ȡ���е�����Ϣ
		try {
			weekInfoList = weekInfoService.QueryWeekInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int weekInfoCount = weekInfoList.size();
		weekObj_ShowText = new String[weekInfoCount+1];
		weekObj_ShowText[0] = "������";
		for(int i=1;i<=weekInfoCount;i++) { 
			weekObj_ShowText[i] = weekInfoList.get(i-1).getWeekName();
		} 
		// ����ѡ������ArrayAdapter��������
		weekObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, weekObj_ShowText);
		// �����Ͽ��������б�ķ��
		weekObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_weekObj.setAdapter(weekObj_adapter);
		// ����¼�Spinner�¼�����
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
		// ����Ĭ��ֵ
		spinner_weekObj.setVisibility(View.VISIBLE);
		spinner_labObj = (Spinner) findViewById(R.id.Spinner_labObj);
		// ��ȡ���е�ʵ����
		try {
			labInfoList = labInfoService.QueryLabInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int labInfoCount = labInfoList.size();
		labObj_ShowText = new String[labInfoCount+1];
		labObj_ShowText[0] = "������";
		for(int i=1;i<=labInfoCount;i++) { 
			labObj_ShowText[i] = labInfoList.get(i-1).getLabName();
		} 
		// ����ѡ������ArrayAdapter��������
		labObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labObj_ShowText);
		// �����Ͽ�ʵ���������б�ķ��
		labObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_labObj.setAdapter(labObj_adapter);
		// ����¼�Spinner�¼�����
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
		// ����Ĭ��ֵ
		spinner_labObj.setVisibility(View.VISIBLE);
		/*������ѯ��ť*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��ȡ��ѯ����*/
					queryConditionCourseTest.setTestName(ET_testName.getText().toString());
					Intent intent = getIntent();
					//����ʹ��bundle��������������
					Bundle bundle =new Bundle();
					//�����������Ȼ�Ǽ�ֵ�Ե���ʽ
					bundle.putSerializable("queryConditionCourseTest", queryConditionCourseTest);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
