package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Course;
import com.mobileclient.service.CourseService;
import com.mobileclient.util.ActivityUtils;import com.mobileclient.util.CourseSimpleAdapter;
import com.mobileclient.util.HttpUtil;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class CourseStudentListActivity extends Activity {
	CourseSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	String courseNo;
	/* ʵ��γ̲���ҵ���߼������ */
	CourseService courseService = new CourseService();
	/*�����ѯ����������ʵ��γ̶���*/
	private Course queryConditionCourse;

	private MyProgressDialog dialog; //������	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.course_list);
		dialog = MyProgressDialog.getInstance(this);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		//�������ؼ�
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(CourseStudentListActivity.this, CourseQueryActivity.class);
				startActivityForResult(intent,ActivityUtils.QUERY_CODE);//�˴���requestCodeӦ�������������е��õ�requestCodeһ��
			}
		});
		search.setVisibility(View.GONE);
		
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("ʵ��γ̲�ѯ�б�");
		ImageView add_btn = (ImageView) this.findViewById(R.id.add_btn);
		add_btn.setOnClickListener(new android.view.View.OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(CourseStudentListActivity.this, CourseAddActivity.class);
				startActivityForResult(intent,ActivityUtils.ADD_CODE);
			}
		});
		add_btn.setVisibility(View.GONE);
		
		setViews();
	}

	//���������������secondActivity�з���ʱ���ô˺���
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ActivityUtils.QUERY_CODE && resultCode==RESULT_OK){
        	Bundle extras = data.getExtras();
        	if(extras != null)
        		queryConditionCourse = (Course)extras.getSerializable("queryConditionCourse");
        	setViews();
        }
        if(requestCode==ActivityUtils.EDIT_CODE && resultCode==RESULT_OK){
        	setViews();
        }
        if(requestCode == ActivityUtils.ADD_CODE && resultCode == RESULT_OK) {
        	queryConditionCourse = null;
        	setViews();
        }
    }

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		dialog.show();
		final Handler handler = new Handler();
		new Thread(){
			@Override
			public void run() {
				//�����߳��н����������ݲ���
				list = getDatas();
				//������ʧ��handler��֪ͨ���߳��������
				handler.post(new Runnable() {
					@Override
					public void run() {
						dialog.cancel();
						adapter = new CourseSimpleAdapter(CourseStudentListActivity.this, list,
	        					R.layout.course_list_item,
	        					new String[] { "courseNo","classObj","courseName","courseHours","courseScore","teacherObj","courseDesc" },
	        					new int[] { R.id.tv_courseNo,R.id.tv_classObj,R.id.tv_courseName,R.id.tv_courseHours,R.id.tv_courseScore,R.id.tv_teacherObj,R.id.tv_courseDesc,},lv);
	        			lv.setAdapter(adapter);
					}
				});
			}
		}.start(); 

		// ��ӳ������
		lv.setOnCreateContextMenuListener(courseListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	String courseNo = list.get(arg2).get("courseNo").toString();
            	Intent intent = new Intent();
            	intent.setClass(CourseStudentListActivity.this, CourseDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putString("courseNo", courseNo);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener courseListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			//menu.add(0, 0, 0, "�༭ʵ��γ���Ϣ"); 
			//menu.add(0, 1, 0, "ɾ��ʵ��γ���Ϣ");
		}
	};

	// �����˵���Ӧ����
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //�༭ʵ��γ���Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ�γ̱��
			courseNo = list.get(position).get("courseNo").toString();
			Intent intent = new Intent();
			intent.setClass(CourseStudentListActivity.this, CourseEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("courseNo", courseNo);
			intent.putExtras(bundle);
			startActivityForResult(intent,ActivityUtils.EDIT_CODE);
		} else if (item.getItemId() == 1) {// ɾ��ʵ��γ���Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ�γ̱��
			courseNo = list.get(position).get("courseNo").toString();
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// ɾ��
	protected void dialog() {
		Builder builder = new Builder(CourseStudentListActivity.this);
		builder.setMessage("ȷ��ɾ����");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = courseService.DeleteCourse(courseNo);
				Toast.makeText(getApplicationContext(), result, 1).show();
				setViews();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private List<Map<String, Object>> getDatas() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			/* ��ѯʵ��γ���Ϣ */
			Declare declare = (Declare) CourseStudentListActivity.this.getApplication(); 
			List<Course> courseList = courseService.studentQueryCourse(declare.getUserName());
			for (int i = 0; i < courseList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("courseNo", courseList.get(i).getCourseNo());
				map.put("classObj", courseList.get(i).getClassObj());
				map.put("courseName", courseList.get(i).getCourseName());
				map.put("courseHours", courseList.get(i).getCourseHours());
				map.put("courseScore", courseList.get(i).getCourseScore());
				map.put("teacherObj", courseList.get(i).getTeacherObj());
				map.put("courseDesc", courseList.get(i).getCourseDesc());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

}
