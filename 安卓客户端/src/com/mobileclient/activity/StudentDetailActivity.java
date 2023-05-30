package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Student;
import com.mobileclient.service.StudentService;
import com.mobileclient.domain.ClassInfo;
import com.mobileclient.service.ClassInfoService;
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
public class StudentDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明学号控件
	private TextView TV_studentNo;
	// 声明登录密码控件
	private TextView TV_password;
	// 声明所在班级控件
	private TextView TV_classObj;
	// 声明姓名控件
	private TextView TV_name;
	// 声明性别控件
	private TextView TV_gender;
	// 声明出生日期控件
	private TextView TV_birthDate;
	// 声明学生照片图片框
	private ImageView iv_studentPhoto;
	// 声明联系电话控件
	private TextView TV_telephone;
	// 声明邮箱控件
	private TextView TV_email;
	// 声明家庭地址控件
	private TextView TV_address;
	/* 要保存的学生信息 */
	Student student = new Student(); 
	/* 学生管理业务逻辑层 */
	private StudentService studentService = new StudentService();
	private ClassInfoService classInfoService = new ClassInfoService();
	private String studentNo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.student_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看学生详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_studentNo = (TextView) findViewById(R.id.TV_studentNo);
		TV_password = (TextView) findViewById(R.id.TV_password);
		TV_classObj = (TextView) findViewById(R.id.TV_classObj);
		TV_name = (TextView) findViewById(R.id.TV_name);
		TV_gender = (TextView) findViewById(R.id.TV_gender);
		TV_birthDate = (TextView) findViewById(R.id.TV_birthDate);
		iv_studentPhoto = (ImageView) findViewById(R.id.iv_studentPhoto); 
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		TV_email = (TextView) findViewById(R.id.TV_email);
		TV_address = (TextView) findViewById(R.id.TV_address);
		Bundle extras = this.getIntent().getExtras();
		studentNo = extras.getString("studentNo");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				StudentDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    student = studentService.GetStudent(studentNo); 
		this.TV_studentNo.setText(student.getStudentNo());
		this.TV_password.setText(student.getPassword());
		ClassInfo classObj = classInfoService.GetClassInfo(student.getClassObj());
		this.TV_classObj.setText(classObj.getClassName());
		this.TV_name.setText(student.getName());
		this.TV_gender.setText(student.getGender());
		Date birthDate = new Date(student.getBirthDate().getTime());
		String birthDateStr = (birthDate.getYear() + 1900) + "-" + (birthDate.getMonth()+1) + "-" + birthDate.getDate();
		this.TV_birthDate.setText(birthDateStr);
		byte[] studentPhoto_data = null;
		try {
			// 获取图片数据
			studentPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + student.getStudentPhoto());
			Bitmap studentPhoto = BitmapFactory.decodeByteArray(studentPhoto_data, 0,studentPhoto_data.length);
			this.iv_studentPhoto.setImageBitmap(studentPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TV_telephone.setText(student.getTelephone());
		this.TV_email.setText(student.getEmail());
		this.TV_address.setText(student.getAddress());
	} 
}
