package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Teacher;
import com.mobileclient.service.TeacherService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class TeacherEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明教师编号TextView
	private TextView TV_teacherNo;
	// 声明登录密码输入框
	private EditText ET_password;
	// 声明姓名输入框
	private EditText ET_name;
	// 声明性别输入框
	private EditText ET_sex;
	// 出版入职日期控件
	private DatePicker dp_inDate;
	// 声明教师照片图片框控件
	private ImageView iv_teacherPhoto;
	private Button btn_teacherPhoto;
	protected int REQ_CODE_SELECT_IMAGE_teacherPhoto = 1;
	private int REQ_CODE_CAMERA_teacherPhoto = 2;
	// 声明联系电话输入框
	private EditText ET_telephone;
	// 声明教师描述输入框
	private EditText ET_teacherDesc;
	protected String carmera_path;
	/*要保存的老师信息*/
	Teacher teacher = new Teacher();
	/*老师管理业务逻辑层*/
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
		setContentView(R.layout.teacher_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑老师信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_teacherNo = (TextView) findViewById(R.id.TV_teacherNo);
		ET_password = (EditText) findViewById(R.id.ET_password);
		ET_name = (EditText) findViewById(R.id.ET_name);
		ET_sex = (EditText) findViewById(R.id.ET_sex);
		dp_inDate = (DatePicker)this.findViewById(R.id.dp_inDate);
		iv_teacherPhoto = (ImageView) findViewById(R.id.iv_teacherPhoto);
		/*单击图片显示控件时进行图片的选择*/
		iv_teacherPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(TeacherEditActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_teacherPhoto);
			}
		});
		btn_teacherPhoto = (Button) findViewById(R.id.btn_teacherPhoto);
		btn_teacherPhoto.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_teacherPhoto.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_teacherPhoto);  
			}
		});
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_teacherDesc = (EditText) findViewById(R.id.ET_teacherDesc);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		teacherNo = extras.getString("teacherNo");
		/*单击修改老师按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取登录密码*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(TeacherEditActivity.this, "登录密码输入不能为空!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					teacher.setPassword(ET_password.getText().toString());
					/*验证获取姓名*/ 
					if(ET_name.getText().toString().equals("")) {
						Toast.makeText(TeacherEditActivity.this, "姓名输入不能为空!", Toast.LENGTH_LONG).show();
						ET_name.setFocusable(true);
						ET_name.requestFocus();
						return;	
					}
					teacher.setName(ET_name.getText().toString());
					/*验证获取性别*/ 
					if(ET_sex.getText().toString().equals("")) {
						Toast.makeText(TeacherEditActivity.this, "性别输入不能为空!", Toast.LENGTH_LONG).show();
						ET_sex.setFocusable(true);
						ET_sex.requestFocus();
						return;	
					}
					teacher.setSex(ET_sex.getText().toString());
					/*获取出版日期*/
					Date inDate = new Date(dp_inDate.getYear()-1900,dp_inDate.getMonth(),dp_inDate.getDayOfMonth());
					teacher.setInDate(new Timestamp(inDate.getTime()));
					if (!teacher.getTeacherPhoto().startsWith("upload/")) {
						//如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
						TeacherEditActivity.this.setTitle("正在上传图片，稍等...");
						String teacherPhoto = HttpUtil.uploadFile(teacher.getTeacherPhoto());
						TeacherEditActivity.this.setTitle("图片上传完毕！");
						teacher.setTeacherPhoto(teacherPhoto);
					} 
					/*验证获取联系电话*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(TeacherEditActivity.this, "联系电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					teacher.setTelephone(ET_telephone.getText().toString());
					/*验证获取教师描述*/ 
					if(ET_teacherDesc.getText().toString().equals("")) {
						Toast.makeText(TeacherEditActivity.this, "教师描述输入不能为空!", Toast.LENGTH_LONG).show();
						ET_teacherDesc.setFocusable(true);
						ET_teacherDesc.requestFocus();
						return;	
					}
					teacher.setTeacherDesc(ET_teacherDesc.getText().toString());
					/*调用业务逻辑层上传老师信息*/
					TeacherEditActivity.this.setTitle("正在更新老师信息，稍等...");
					String result = teacherService.UpdateTeacher(teacher);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					//Intent intent = getIntent();
					//setResult(RESULT_OK,intent);
					//finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    teacher = teacherService.GetTeacher(teacherNo);
		this.TV_teacherNo.setText(teacherNo);
		this.ET_password.setText(teacher.getPassword());
		this.ET_name.setText(teacher.getName());
		this.ET_sex.setText(teacher.getSex());
		Date inDate = new Date(teacher.getInDate().getTime());
		this.dp_inDate.init(inDate.getYear() + 1900,inDate.getMonth(), inDate.getDate(), null);
		byte[] teacherPhoto_data = null;
		try {
			// 获取图片数据
			teacherPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + teacher.getTeacherPhoto());
			Bitmap teacherPhoto = BitmapFactory.decodeByteArray(teacherPhoto_data, 0, teacherPhoto_data.length);
			this.iv_teacherPhoto.setImageBitmap(teacherPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.ET_telephone.setText(teacher.getTelephone());
		this.ET_teacherDesc.setText(teacher.getTeacherDesc());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE_CAMERA_teacherPhoto  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_teacherPhoto.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_teacherPhoto.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// 把数据写入文件 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_teacherPhoto.setImageBitmap(booImageBm);
				this.iv_teacherPhoto.setScaleType(ScaleType.FIT_CENTER);
				this.teacher.setTeacherPhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_teacherPhoto && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/" + filename;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(filepath, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 128*128);
			opts.inJustDecodeBounds = false; 
			try { 
				Bitmap bm = BitmapFactory.decodeFile(filepath, opts);
				this.iv_teacherPhoto.setImageBitmap(bm); 
				this.iv_teacherPhoto.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			teacher.setTeacherPhoto(filename); 
		}
	}
}
