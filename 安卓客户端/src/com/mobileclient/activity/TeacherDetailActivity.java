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
	// 声明返回按钮
	private Button btnReturn;
	// 声明教师编号控件
	private TextView TV_teacherNo;
	// 声明登录密码控件
	private TextView TV_password;
	// 声明姓名控件
	private TextView TV_name;
	// 声明性别控件
	private TextView TV_sex;
	// 声明入职日期控件
	private TextView TV_inDate;
	// 声明教师照片图片框
	private ImageView iv_teacherPhoto;
	// 声明联系电话控件
	private TextView TV_telephone;
	// 声明教师描述控件
	private TextView TV_teacherDesc;
	/* 要保存的老师信息 */
	Teacher teacher = new Teacher(); 
	/* 老师管理业务逻辑层 */
	private TeacherService teacherService = new TeacherService();
	private String teacherNo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.teacher_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看老师详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
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
	/* 初始化显示详情界面的数据 */
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
			// 获取图片数据
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
