package com.mobileclient.activity;

import java.util.Date;
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
public class TeacherDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// ������ʦ��ſؼ�
	private TextView TV_teacherNo;
	// ������¼����ؼ�
	private TextView TV_password;
	// ���������ؼ�
	private TextView TV_name;
	// �����Ա�ؼ�
	private TextView TV_sex;
	// ������ְ���ڿؼ�
	private TextView TV_inDate;
	// ������ʦ��ƬͼƬ��
	private ImageView iv_teacherPhoto;
	// ������ϵ�绰�ؼ�
	private TextView TV_telephone;
	// ������ʦ�����ؼ�
	private TextView TV_teacherDesc;
	/* Ҫ�������ʦ��Ϣ */
	Teacher teacher = new Teacher(); 
	/* ��ʦ����ҵ���߼��� */
	private TeacherService teacherService = new TeacherService();
	private String teacherNo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.teacher_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴��ʦ����");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_teacherNo = (TextView) findViewById(R.id.TV_teacherNo);
		TV_password = (TextView) findViewById(R.id.TV_password);
		TV_name = (TextView) findViewById(R.id.TV_name);
		TV_sex = (TextView) findViewById(R.id.TV_sex);
		TV_inDate = (TextView) findViewById(R.id.TV_inDate);
		iv_teacherPhoto = (ImageView) findViewById(R.id.iv_teacherPhoto); 
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		TV_teacherDesc = (TextView) findViewById(R.id.TV_teacherDesc);
		Bundle extras = this.getIntent().getExtras();
		teacherNo = extras.getString("teacherNo");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				TeacherDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    teacher = teacherService.GetTeacher(teacherNo); 
		this.TV_teacherNo.setText(teacher.getTeacherNo());
		this.TV_password.setText(teacher.getPassword());
		this.TV_name.setText(teacher.getName());
		this.TV_sex.setText(teacher.getSex());
		Date inDate = new Date(teacher.getInDate().getTime());
		String inDateStr = (inDate.getYear() + 1900) + "-" + (inDate.getMonth()+1) + "-" + inDate.getDate();
		this.TV_inDate.setText(inDateStr);
		byte[] teacherPhoto_data = null;
		try {
			// ��ȡͼƬ����
			teacherPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + teacher.getTeacherPhoto());
			Bitmap teacherPhoto = BitmapFactory.decodeByteArray(teacherPhoto_data, 0,teacherPhoto_data.length);
			this.iv_teacherPhoto.setImageBitmap(teacherPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TV_telephone.setText(teacher.getTelephone());
		this.TV_teacherDesc.setText(teacher.getTeacherDesc());
	} 
}
