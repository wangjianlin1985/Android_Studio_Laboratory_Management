package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Student;
import com.mobileclient.service.StudentService;
import com.mobileclient.util.ActivityUtils;import com.mobileclient.util.StudentSimpleAdapter;
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

public class StudentListActivity extends Activity {
	StudentSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	String studentNo;
	/* ѧ������ҵ���߼������ */
	StudentService studentService = new StudentService();
	/*�����ѯ����������ѧ������*/
	private Student queryConditionStudent;

	private MyProgressDialog dialog; //������	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.student_list);
		dialog = MyProgressDialog.getInstance(this);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		//�������ؼ�
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(StudentListActivity.this, StudentQueryActivity.class);
				startActivityForResult(intent,ActivityUtils.QUERY_CODE);//�˴���requestCodeӦ�������������е��õ�requestCodeһ��
			}
		});
		 
		
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("ѧ����ѯ�б�");
		ImageView add_btn = (ImageView) this.findViewById(R.id.add_btn);
		add_btn.setOnClickListener(new android.view.View.OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(StudentListActivity.this, StudentAddActivity.class);
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
        		queryConditionStudent = (Student)extras.getSerializable("queryConditionStudent");
        	setViews();
        }
        if(requestCode==ActivityUtils.EDIT_CODE && resultCode==RESULT_OK){
        	setViews();
        }
        if(requestCode == ActivityUtils.ADD_CODE && resultCode == RESULT_OK) {
        	queryConditionStudent = null;
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
						adapter = new StudentSimpleAdapter(StudentListActivity.this, list,
	        					R.layout.student_list_item,
	        					new String[] { "studentNo","classObj","name","gender","birthDate","studentPhoto","telephone" },
	        					new int[] { R.id.tv_studentNo,R.id.tv_classObj,R.id.tv_name,R.id.tv_gender,R.id.tv_birthDate,R.id.iv_studentPhoto,R.id.tv_telephone,},lv);
	        			lv.setAdapter(adapter);
					}
				});
			}
		}.start(); 

		// ��ӳ������
		lv.setOnCreateContextMenuListener(studentListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	String studentNo = list.get(arg2).get("studentNo").toString();
            	Intent intent = new Intent();
            	intent.setClass(StudentListActivity.this, StudentDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putString("studentNo", studentNo);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener studentListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			//menu.add(0, 0, 0, "�༭ѧ����Ϣ"); 
			//menu.add(0, 1, 0, "ɾ��ѧ����Ϣ");
		}
	};

	// �����˵���Ӧ����
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //�༭ѧ����Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡѧ��
			studentNo = list.get(position).get("studentNo").toString();
			Intent intent = new Intent();
			intent.setClass(StudentListActivity.this, StudentEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("studentNo", studentNo);
			intent.putExtras(bundle);
			startActivityForResult(intent,ActivityUtils.EDIT_CODE);
		} else if (item.getItemId() == 1) {// ɾ��ѧ����Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡѧ��
			studentNo = list.get(position).get("studentNo").toString();
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// ɾ��
	protected void dialog() {
		Builder builder = new Builder(StudentListActivity.this);
		builder.setMessage("ȷ��ɾ����");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = studentService.DeleteStudent(studentNo);
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
			/* ��ѯѧ����Ϣ */
			List<Student> studentList = studentService.QueryStudent(queryConditionStudent);
			for (int i = 0; i < studentList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("studentNo", studentList.get(i).getStudentNo());
				map.put("classObj", studentList.get(i).getClassObj());
				map.put("name", studentList.get(i).getName());
				map.put("gender", studentList.get(i).getGender());
				map.put("birthDate", studentList.get(i).getBirthDate());
				/*byte[] studentPhoto_data = ImageService.getImage(HttpUtil.BASE_URL+ studentList.get(i).getStudentPhoto());// ��ȡͼƬ����
				BitmapFactory.Options studentPhoto_opts = new BitmapFactory.Options();  
				studentPhoto_opts.inJustDecodeBounds = true;  
				BitmapFactory.decodeByteArray(studentPhoto_data, 0, studentPhoto_data.length, studentPhoto_opts); 
				studentPhoto_opts.inSampleSize = photoListActivity.computeSampleSize(studentPhoto_opts, -1, 100*100); 
				studentPhoto_opts.inJustDecodeBounds = false; 
				try {
					Bitmap studentPhoto = BitmapFactory.decodeByteArray(studentPhoto_data, 0, studentPhoto_data.length, studentPhoto_opts);
					map.put("studentPhoto", studentPhoto);
				} catch (OutOfMemoryError err) { }*/
				map.put("studentPhoto", HttpUtil.BASE_URL+ studentList.get(i).getStudentPhoto());
				map.put("telephone", studentList.get(i).getTelephone());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

}
